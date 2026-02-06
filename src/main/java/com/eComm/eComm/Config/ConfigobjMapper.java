package com.eComm.eComm.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
@Configuration
public class ConfigobjMapper {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}