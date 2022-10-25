package com.tvalenca.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.tvalenca.cursomc.security.UserSS;

public class User_Service {

    public static UserSS authenticated(){

        try{
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e){
            return null;
        }
    }
    
}
