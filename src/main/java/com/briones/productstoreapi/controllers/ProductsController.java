package com.briones.productstoreapi.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.briones.productstoreapi.models.Product;
import com.briones.productstoreapi.services.ProductsRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsRepository repo;

    @GetMapping({"","/"})
    public String showProductsList(Model model) {
        List<Product> products = repo.findAll();
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping({"/create"})
    public String showCreatePage(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "products/createProduct";
    }

    @PostMapping({"/create"})
    public String createProduct (@Valid @ModelAttribute ProductDto productDto, BindingResult result) {
        if(productDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("productDto", "imageFile", "The image file is required"));
        }

        if(result.hasErrors()) {
            return "products/CreateProduct";
        }

        MultipartFile image = productDto.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
        
        try {
            String uploadDir = "public/img/";
            Path uploadPath = (Path) Paths.get(uploadDir);

            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCreatedAt(createdAt);
        product.setImageFileName(storageFileName);

        repo.save(product);

        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        try {
            Product product = repo.findById(id).get();
            model.addAttribute("product", product);

            ProductDto productDto = new ProductDto();
            product.setName(product.getName());
            product.setBrand(product.getBrand());
            product.setCategory(product.getCategory());
            product.setPrice(product.getPrice());
            product.setDescription(product.getDescription());
            
            model.addAttribute("productDto", productDto);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/products";
        }
        return "products/editProduct";
    }
    
    @PostMapping("/edit")
    public String postMethodName(Model model, @RequestParam int id, @Valid @ModelAttribute ProductDto productDto, BindingResult result) {
        try {
            Product product = repo.findById(id).get();
            model.addAttribute("product", product);

            if(result.hasErrors()) {
                return "products/editProduct";
            }

            if(!productDto.getImageFile().isEmpty()) {
                String uploadDir = "public/img/";
                Path oldImagePath = Paths.get(uploadDir + product.getImageFileName());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }

                MultipartFile image = productDto.getImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                }
                product.setImageFileName(storageFileName);

                product.setName(productDto.getName());
                product.setBrand(productDto.getBrand());
                product.setCategory(productDto.getCategory());
                product.setPrice(productDto.getPrice());
                product.setDescription(productDto.getDescription());

                repo.save(product);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return "redirect:/products";
    }
    
}
