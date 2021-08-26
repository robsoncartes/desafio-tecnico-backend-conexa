package br.com.conexasaude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {

        System.out.println("Project initialization.");

        SpringApplication.run(MainApplication.class, args);
    }
}
