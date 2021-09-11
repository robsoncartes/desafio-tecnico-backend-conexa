package br.com.conexasaude.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                null, // clientID
                null, // clientSecret
                null, // realm
                null, // appName
                null, // apiKey
                ApiKeyVehicle.HEADER, // apiKeyVehicle
                "Authorization", //apiKeyName
                "," // scopeSeparator
        );
    }

    private ApiKey apiKeys() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.conexasaude.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .securitySchemes(newArrayList(apiKeys()));
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("API Agendamento de Consultas - Teste Back-end Java Developer: Conexa Saúde")
                .description("Obs.: Login para médicos cadastrados: email1@email.com e email2@email.com; Senha para ambos médicos: pass")
                .version("1.0.0")
                .contact(
                        new Contact("Robson Sousa",
                                "https://gitlab.com/robsonsousa/desafio-tecnico-backend-conexa", "robsoncartes@outlook.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }
}
