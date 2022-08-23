package com.tvalenca.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.tvalenca.cursomc.domain.Categoria;

public class Categoria_DTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5 ,max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
    private String nome;


    public Categoria_DTO(){}

    public Categoria_DTO(Categoria obj){
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
