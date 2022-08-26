package com.tvalenca.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.tvalenca.cursomc.domain.Cliente;
import com.tvalenca.cursomc.dto.Cliente_DTO;
import com.tvalenca.cursomc.repositories.Cliente_Repository;
import com.tvalenca.cursomc.services.exceptions.DataIntegrityException;
import com.tvalenca.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class Cliente_Service {
    
    @Autowired
    private Cliente_Repository repo;

    public Cliente buscar(Integer id){
        Optional<Cliente> obj = repo.findById(id); 
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                                     + Cliente.class.getName()));
    }

    public Cliente update(Cliente obj){
        Cliente newObj = buscar(obj.getId());
        updateData(newObj,obj);
        return repo.save(newObj);
    }

    public void delete(Integer id){
        buscar(id);
        try{
            repo.deleteById(id);
        }
        catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possivel excluir porque há entidades relacionadas");
        }

    }

    public List<Cliente> findAll(){
        return repo.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDto(Cliente_DTO objDto){
        return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(),null,null);
    }
    
    private void updateData(Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}