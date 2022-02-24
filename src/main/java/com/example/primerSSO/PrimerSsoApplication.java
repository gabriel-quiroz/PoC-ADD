package com.example.primerSSO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//README: se quitaron porque se crea el archivo de configuracion 
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class PrimerSsoApplication {

    public static void main(String[] args) {

        SpringApplication.run(PrimerSsoApplication.class, args);
    }

}
