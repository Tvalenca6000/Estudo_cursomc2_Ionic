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

import com.tvalenca.cursomc.domain.Cliente;
import com.tvalenca.cursomc.dto.ClienteNewDTO;
import com.tvalenca.cursomc.dto.Cliente_DTO;
import com.tvalenca.cursomc.services.Cliente_Service;

@RestController
@RequestMapping(value="/clientes")
public class Cliente_Resource {

    @Autowired
    private Cliente_Service service;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Cliente> find(@PathVariable Integer id){
        Cliente obj = service.buscar(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto){
        Cliente obj = service.fromDTO(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody Cliente_DTO objDto, @PathVariable Integer id){
        Cliente obj = service.fromDto(objDto);
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
    public ResponseEntity<List<Cliente_DTO>> findAll(){
        List<Cliente> list = service.findAll();
        List<Cliente_DTO> listDto = list.stream().map(obj -> new Cliente_DTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
    
    @RequestMapping(value="/page", method=RequestMethod.GET)
    public ResponseEntity<Page<Cliente_DTO>> findPage(
            @RequestParam(value = "page",defaultValue = "0") Integer page, 
            @RequestParam(value = "linesPerPage",defaultValue = "24") Integer linesPerPage, //DefaultValue 24 pois ele é multiplo de 1,2,3,4. Fica mais facil de organizar o layout responsivo
            @RequestParam(value = "orderBy",defaultValue = "nome") String orderBy, 
            @RequestParam(value = "direction",defaultValue = "ASC") String direction) {

        Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<Cliente_DTO> listDto = list.map(obj -> new Cliente_DTO(obj));
        return ResponseEntity.ok().body(listDto);
    }
}
