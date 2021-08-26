package br.com.conexasaude.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hello")
public class TestExampleEndpoint {

    @GetMapping
    public String helloConexa() {
        return "Hello Conexa Saúde!";
    }
}
