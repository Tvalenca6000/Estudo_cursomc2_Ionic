package com.tvalenca.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvalenca.cursomc.domain.Pagamento_Com_Boleto;
import com.tvalenca.cursomc.domain.Pagamento_Com_Cartao;

@Configuration
    public class JacksonConfig {
        // https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
        @Bean
        public Jackson2ObjectMapperBuilder objectMapperBuilder() {
            Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
                public void configure(ObjectMapper objectMapper) {
                    objectMapper.registerSubtypes(Pagamento_Com_Cartao.class);
                    objectMapper.registerSubtypes(Pagamento_Com_Boleto.class);
                    super.configure(objectMapper);
                }
            };
        return builder;
    }
}