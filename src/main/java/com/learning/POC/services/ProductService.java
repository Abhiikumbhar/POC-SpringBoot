package com.learning.POC.services;

import com.learning.POC.entities.Product;
import com.learning.POC.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository prodRepo;

    public Product createProduct(Product product) {
        return prodRepo.save(product);
    }

    public List<Product> getAllProducts() {
        return prodRepo.findAll();
    }

    public Product getProduct(Long id) {
        return prodRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = prodRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setDescription(productDetails.getDescription());
        return prodRepo.save(product);
    }

    public void deleteProduct(Long id) {
        prodRepo.deleteById(id);
    }
}
