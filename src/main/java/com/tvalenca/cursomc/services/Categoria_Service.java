package com.tvalenca.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tvalenca.cursomc.domain.Categoria;
import com.tvalenca.cursomc.repositories.Categoria_Repository;
import com.tvalenca.cursomc.services.exceptions.DataIntegrityException;
import com.tvalenca.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class Categoria_Service {
    
    @Autowired
    private Categoria_Repository repo;

    public Categoria buscar(Integer id){
        Optional<Categoria> obj = repo.findById(id); 
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                                     + Categoria.class.getName()));
    }

    public Categoria insert(Categoria obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update(Categoria obj){
        buscar(obj.getId());
        return repo.save(obj);
    }

    public void delete(Integer id){
        buscar(id);
        try{
            repo.deleteById(id);
        }
        catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possivel excluir uma Categoria que possui produtos");
        }

    }

    public List<Categoria> findAll(){
        return repo.findAll();
    }
    
}