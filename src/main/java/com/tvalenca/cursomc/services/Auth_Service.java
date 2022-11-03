package com.tvalenca.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tvalenca.cursomc.domain.Cliente;
import com.tvalenca.cursomc.repositories.Cliente_Repository;
import com.tvalenca.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class Auth_Service {

    @Autowired
    private Cliente_Repository cliente_Repository;

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private Email_Service email_Service;

    private Random rand = new Random();

    public void sendNewPassword(String email){

        Cliente cliente = cliente_Repository.findByEmail(email);
        if(cliente == null){
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassowrd();
        cliente.setSenha(pe.encode(newPass));

        cliente_Repository.save(cliente);
        email_Service.sendNewPasswordEmail(cliente,newPass);


    }

    private String newPassowrd() {
        char[] vet = new char[10];
        for(int i=0; i<10; i++){
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if(opt == 0){ // Gera um digito
            return (char) (rand.nextInt(10) + 48);

        }else if(opt == 1){ // Gera uma letra maiscula
            return (char) (rand.nextInt(26) + 65);

        }else{ // Gera uma letra minuscula
            return (char) (rand.nextInt(26) + 97);

        }
    }
    
}
