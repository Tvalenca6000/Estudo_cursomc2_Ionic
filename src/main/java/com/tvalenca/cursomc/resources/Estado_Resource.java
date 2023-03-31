package com.tvalenca.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tvalenca.cursomc.domain.Cidade;
import com.tvalenca.cursomc.domain.Estado;
import com.tvalenca.cursomc.dto.Cidade_DTO;
import com.tvalenca.cursomc.dto.Estado_DTO;
import com.tvalenca.cursomc.services.Cidade_Service;
import com.tvalenca.cursomc.services.Estado_Service;

@RestController
@RequestMapping(value="/estados")
public class Estado_Resource {
    
    @Autowired
    private Estado_Service service;

    @Autowired
    private Cidade_Service cidade_Service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Estado_DTO>> findAll(){
        List<Estado> list = service.findAll();
        List<Estado_DTO> listDto = list.stream().map(obj -> new Estado_DTO(obj)).collect((Collectors.toList()));
        return ResponseEntity.ok().body(listDto);

    }

    @RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
    public ResponseEntity <List<Cidade_DTO>> find_Cidades(@PathVariable Integer estadoId){
        List<Cidade> list = cidade_Service.findByEstado(estadoId);
        List<Cidade_DTO> listDto = list.stream().map(obj -> new Cidade_DTO(obj)).collect((Collectors.toList()));
        return ResponseEntity.ok().body(listDto);
        
    }
}
