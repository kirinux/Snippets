package com.bnpparibas.sit.pact.dojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 *
 *
 */
@SpringBootApplication
@Configuration
public class Application {

    @Value("${file.upload.folder}")
    private String uploadFolderPath;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public File getUploadFolder() {
        return new File(uploadFolderPath);
    }
}
