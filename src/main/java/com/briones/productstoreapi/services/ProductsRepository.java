package com.briones.productstoreapi.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.briones.productstoreapi.models.Product;

public interface ProductsRepository extends JpaRepository <Product, Integer> {
    
}
