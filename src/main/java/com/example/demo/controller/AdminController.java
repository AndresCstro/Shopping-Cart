package com.example.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.DatabaseExportService;
import com.example.demo.service.ProductService;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final DatabaseExportService databaseExportService;
    private final ProductService productService;
    
    public AdminController(DatabaseExportService databaseExportService, ProductService productService) {
        this.databaseExportService = databaseExportService;
        this.productService = productService;
    }
    
 // Mostrar página de administración
    @GetMapping
    public String admin(Model model) {

        model.addAttribute("products", productService.getAllProducts());

        return "admin";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportDatabase() {
    	// Generar archivo SQL con los products
        byte[] sqlFile = databaseExportService.exportDatabaseToSql();
        
        // Respuesta HTTP al navegador --> descargar archivo
        return ResponseEntity.ok()
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"products.sql\""
                )
                .contentType(MediaType.TEXT_PLAIN)
                .body(sqlFile);
    }
}