package com.briones.productstoreapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.briones.productstoreapi.models.Product;
import com.briones.productstoreapi.services.ProductsRepository;

@Controller
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsRepository repo;

    public String showProductsList(Model model) {
        List<Product> products = repo.findAll();
        model.addAttribute("products", products);
        return "products/index";
    }
}
