package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo;

@Service
@SessionScope
public class CartService {

	// El carrito se almacena en memoria
	private final List<Long> cartItems = new ArrayList<>();
	// Añadido para mantener consistencia entre carrito y catálogo
    private final ProductRepo productRepo;
    
    public CartService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }
	// Mostrar items del carrito
	public List<Product> mostrarItems()
	{
        List<Product> products = new ArrayList<>();

        // Eliminamos del carrito IDs de productos que ya no existen
        cartItems.removeIf(id -> !productRepo.existsById(id));

        // Recuperamos de BD la versión actual de cada producto
        for (Long id : cartItems) {
            productRepo.findById(id).ifPresent(products::add);
        }

        return products;
	}
	
	// Añadir productos al carrito
	public void addItem(Product p)
	{
		if (p != null)
			cartItems.add(p.getId());
	}
	
	// Eliminar producto del carrito
	public void deleteItem(Long id)
	{
		cartItems.removeIf(productId -> productId.equals(id));
	}
}
