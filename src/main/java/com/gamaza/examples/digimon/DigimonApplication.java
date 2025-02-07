package com.gamaza.examples.digimon;

import com.gamaza.examples.digimon.repository.base.impl.CustomRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
@EnableJpaAuditing
@EnableTransactionManagement
public class DigimonApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigimonApplication.class, args);
    }

}
