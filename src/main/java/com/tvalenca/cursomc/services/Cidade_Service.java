package com.tvalenca.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tvalenca.cursomc.domain.Cidade;
import com.tvalenca.cursomc.repositories.Cidade_Repository;

@Service
public class Cidade_Service {
    
    @Autowired
    private Cidade_Repository repo;

    public List<Cidade> findByEstado(Integer estadoId){
        return repo.findCidades(estadoId);
    }
    
}
