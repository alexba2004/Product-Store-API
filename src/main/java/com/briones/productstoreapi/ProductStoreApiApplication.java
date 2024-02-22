package com.briones.productstoreapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.briones.productstoreapi", "com.briones.lib.seeders"})
public class ProductStoreApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductStoreApiApplication.class, args);
    }
}

