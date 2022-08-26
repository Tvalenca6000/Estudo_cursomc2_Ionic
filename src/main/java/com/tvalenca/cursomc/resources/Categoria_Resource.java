package com.tvalenca.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tvalenca.cursomc.domain.Categoria;
import com.tvalenca.cursomc.dto.Categoria_DTO;
import com.tvalenca.cursomc.services.Categoria_Service;

@RestController
@RequestMapping(value="/categorias")
public class Categoria_Resource {

    @Autowired
    private Categoria_Service service;
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Categoria> find(@PathVariable Integer id){
        Categoria obj = service.buscar(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Categoria_DTO objDto){
        Categoria obj = service.fromDto(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody Categoria_DTO objDto, @PathVariable Integer id){
        Categoria obj = service.fromDto(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Categoria_DTO>> findAll(){
        List<Categoria> list = service.findAll();
        List<Categoria_DTO> listDto = list.stream().map(obj -> new Categoria_DTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
    
    @RequestMapping(value="/page", method=RequestMethod.GET)
    public ResponseEntity<Page<Categoria_DTO>> findPage(
            @RequestParam(value = "page",defaultValue = "0") Integer page, 
            @RequestParam(value = "linesPerPage",defaultValue = "24") Integer linesPerPage, //DefaultValue 24 pois ele é multiplo de 1,2,3,4. Fica mais facil de organizar o layout responsivo
            @RequestParam(value = "orderBy",defaultValue = "nome") String orderBy, 
            @RequestParam(value = "direction",defaultValue = "ASC") String direction) {

        Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<Categoria_DTO> listDto = list.map(obj -> new Categoria_DTO(obj));
        return ResponseEntity.ok().body(listDto);
    }

}