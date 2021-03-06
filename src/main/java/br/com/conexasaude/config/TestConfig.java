package br.com.conexasaude.config;

import br.com.conexasaude.services.impl.DBServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBServiceImpl dbService;

    @Bean
    public boolean instantiateDatabase() throws Exception {

        dbService.instantiateTestDatabase();

        return true;
    }
}
