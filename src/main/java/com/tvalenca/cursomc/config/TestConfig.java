package com.tvalenca.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.tvalenca.cursomc.services.DB_Service;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DB_Service dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException{
        dbService.instantiateTestDatabase();
        return true;
    }
    
}
