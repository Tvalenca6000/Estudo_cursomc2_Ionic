package com.tvalenca.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.tvalenca.cursomc.domain.Cliente;
import com.tvalenca.cursomc.dto.Cliente_DTO;
import com.tvalenca.cursomc.repositories.Cliente_Repository;
import com.tvalenca.cursomc.resources.exceptions.FieldMessage;

public class Cliente_UpdateValidator implements ConstraintValidator<Cliente_Update, Cliente_DTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private  Cliente_Repository repo;

    @Override
    public void initialize(Cliente_Update ann) {}

    @Override
    public boolean isValid(Cliente_DTO objDto, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriID = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux != null && !aux.getId().equals(uriID)){
            list.add(new FieldMessage("email","Email já existente"));
        }

        // inclua os testes aqui, inserindo erros na lista
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
            .addPropertyNode(e.getFieldName()).addConstraintViolation();
            
        }
        return list.isEmpty();
    }
}
