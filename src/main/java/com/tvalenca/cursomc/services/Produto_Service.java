package com.tvalenca.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.tvalenca.cursomc.domain.Categoria;
import com.tvalenca.cursomc.domain.Produto;
import com.tvalenca.cursomc.repositories.Categoria_Repository;
import com.tvalenca.cursomc.repositories.Produto_Repository;
import com.tvalenca.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class Produto_Service {
    
    @Autowired
    private Produto_Repository repo;

    @Autowired
    private Categoria_Repository categoria_Repository;

    public Produto buscar(Integer id){
        Optional<Produto> obj = repo.findById(id); 
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                                     + Produto.class.getName()));
    }

    public Page<Produto> search(String nome, List<Integer>ids,Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoria_Repository.findAllById(ids);
        return repo.search(nome, categorias, pageRequest);
    }
}