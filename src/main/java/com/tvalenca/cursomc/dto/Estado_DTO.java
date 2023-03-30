package com.tvalenca.cursomc.dto;

import java.io.Serializable;

import com.tvalenca.cursomc.domain.Estado;

public class Estado_DTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public Estado_DTO(){}

    public Estado_DTO(Estado obj){
        id = obj.getId();
        nome = obj.getNome();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
