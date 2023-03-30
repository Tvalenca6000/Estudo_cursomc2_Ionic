package com.tvalenca.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tvalenca.cursomc.domain.Estado;
import com.tvalenca.cursomc.repositories.Estado_Repository;

@Service
public class Estado_Service {
    
    @Autowired
    private Estado_Repository repo;

    public List<Estado> findAll(){
        return repo.findAllByOrderByNome();
    }

}
