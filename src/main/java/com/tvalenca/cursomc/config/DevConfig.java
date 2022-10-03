package com.tvalenca.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.tvalenca.cursomc.services.DB_Service;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DB_Service dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDatabase() throws ParseException{

        if(!"create".equals(strategy)){
            return false;
        }
        
        dbService.instantiateTestDatabase();
        return true;
    }
    
}
