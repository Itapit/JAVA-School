package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public Product createProduct(Product product){
        return productRepository.save(product);
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public  Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
    public void updateProduct(Long Id,Product productDetails) {
        Product pro=getProductById(Id);
        pro.setDescription(productDetails.getDescription());
        pro.setId(productDetails.getId());
        pro.setPrice(productDetails.getPrice());
        pro.setQuantity(productDetails.getQuantity());
        pro.setName(productDetails.getName());

        productRepository.save(pro);
    }
    public void deleteProduct(Long Id){
        productRepository.delete(getProductById(Id));
    }
}
