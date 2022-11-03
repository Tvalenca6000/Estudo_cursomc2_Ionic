package com.tvalenca.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tvalenca.cursomc.dto.EmailDTO;
import com.tvalenca.cursomc.security.JWTUtil;
import com.tvalenca.cursomc.security.UserSS;
import com.tvalenca.cursomc.services.Auth_Service;
import com.tvalenca.cursomc.services.User_Service;

@RestController
@RequestMapping(value = "/auth")
public class Auth_Resource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private Auth_Service service;

    @RequestMapping(value="/refresh_token", method=RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = User_Service.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/forgot", method=RequestMethod.POST)
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
        service.sendNewPassword(objDto.getEmail());
        return ResponseEntity.noContent().build();
    }
    
}
