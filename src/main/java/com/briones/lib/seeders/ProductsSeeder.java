package com.briones.lib.seeders;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.briones.productstoreapi.models.Product;
import com.briones.productstoreapi.services.ProductsRepository;

@Component
public class ProductsSeeder implements CommandLineRunner {
    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public void run(String... args) {
        try {
            if (productsRepository.count() == 0) {
                saveProduct("Hersheys", "Hershey's", "Candy", 1.99, "Delicious chocolate bar.", "hersheys.jpg");
                saveProduct("Monster", "Monster Energy", "Energy Drink", 2.49, "Energy drink for a boost.", "monster.jpg");
                saveProduct("Nescafe", "Nescafe", "Coffee", 3.99, "Classic instant coffee.", "nescafe.jpg");
                saveProduct("Oreo", "Oreo", "Snacks", 1.49, "Iconic sandwich cookie.", "oreo.jpg");
                saveProduct("Pan Blanco", "Bimbo", "Bread", 2.99, "Fresh white bread loaf.", "panblanco.jpg");
                saveProduct("Powerade", "Powerade", "Sports Drink", 1.79, "Electrolyte sports drink.", "powerade.jpg");
                saveProduct("Pringles", "Pringles", "Chips", 1.99, "Classic potato crisps.", "pringles.jpg");
                saveProduct("Redbull", "Red Bull", "Energy Drink", 2.99, "Energy drink for stimulation.", "redbull.jpg");
                saveProduct("Sabritas", "Sabritas", "Chips", 1.29, "Assorted chips.", "sabritas.jpg");
                saveProduct("Tortillinas", "Tia Rosa", "Bread", 0.99, "Traditional corn tortillas.", "tortillinas.jpg");
            }
        } catch (DataAccessException e) {
            System.out.println("Error al acceder a la base de datos: " + e.getMessage());
        }
    }

    private void saveProduct(String name, String brand, String category, double price, String description, String imageFileName) {
        Product product = new Product();
        product.setName(name);
        product.setBrand(brand);
        product.setCategory(category);
        product.setPrice(price);
        product.setDescription(description);
        product.setCreatedAt(new Date());
        product.setImageFileName(imageFileName);
        productsRepository.save(product);
    }
}
