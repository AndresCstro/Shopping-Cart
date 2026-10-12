package com.example.demo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo;

/*Con service indico que contiene la lógica de servicio de la app */
@Service
public class ProductService {
	
	private final ProductRepo productRepo;
	
	//Inyección de dependencias, para acceder al repo
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }
    
    // Obtener los productos --> Usar las funciones importadas de JPArepo
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
    
    // Buscar producto por ID (puede que no exista --> Optional)
    public Optional<Product> getProductById(Long id) {
        return productRepo.findById(id);
    }
    
    // Guardar producto
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }
    
    // Eliminar producto (mediante ID)
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}
