package com.tvalenca.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.tvalenca.cursomc.domain.Cliente;
import com.tvalenca.cursomc.domain.enums.Tipo_Cliente;
import com.tvalenca.cursomc.dto.ClienteNewDTO;
import com.tvalenca.cursomc.repositories.Cliente_Repository;
import com.tvalenca.cursomc.resources.exceptions.FieldMessage;
import com.tvalenca.cursomc.services.validation.utils.BR;

public class Cliente_InsertValidator implements ConstraintValidator<Cliente_Insert, ClienteNewDTO> {

    @Autowired
    private  Cliente_Repository repo;

    @Override
    public void initialize(Cliente_Insert ann) {}

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getTipo().equals(Tipo_Cliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCpnj())){
            list.add(new FieldMessage("cpfOuCpnj","CPF Inválido"));
        }

        if(objDto.getTipo().equals(Tipo_Cliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCpnj())){
            list.add(new FieldMessage("cpfOuCpnj","CNPJ Inválido"));
        }

        Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux != null){
            list.add(new FieldMessage("email","Email já existente"));
        }

        // inclua os testes aqui, inserindo erros na lista
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
            .addPropertyNode(e.getFieldName()).addConstraintViolation();
            
        }
        return list.isEmpty();
    }
}
