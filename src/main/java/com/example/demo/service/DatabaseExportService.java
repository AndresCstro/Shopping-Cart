package com.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo;

@Service
public class DatabaseExportService {

    private final ProductRepo productRepo;

    public DatabaseExportService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public byte[] exportDatabaseToSql() {
    	// Obtener productos de H2
        List<Product> products = productRepo.findAll();
        // Texto a rellenar
        StringBuilder sql = new StringBuilder();
        
        // Para crear las peticiones SQL
        for (Product product : products) {
            sql.append("INSERT INTO product (id, name, price) VALUES (")
        			.append(product.getId())
	                .append(", '")
	                .append(product.getName().replace("'", "''"))
	                .append("', ")
	                .append(product.getPrice())
	                .append(");\n");
        }
        // Importante UTF_8
        return sql.toString().getBytes(StandardCharsets.UTF_8);
    }
}