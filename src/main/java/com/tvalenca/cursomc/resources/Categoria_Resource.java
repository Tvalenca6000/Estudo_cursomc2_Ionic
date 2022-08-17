package com.tvalenca.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tvalenca.cursomc.domain.Categoria;
import com.tvalenca.cursomc.services.Categoria_Service;

@RestController
@RequestMapping(value="/categorias")
public class Categoria_Resource {

    @Autowired
    private Categoria_Service service;
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<?> Listar(@PathVariable Integer id){
        Categoria obj = service.buscar(id);

        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Categoria obj){
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
