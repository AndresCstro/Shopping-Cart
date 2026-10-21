package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import com.example.demo.model.Product;

@Service
@SessionScope
public class CartService {

	// El carrito se almacena en memoria
	private final List<Product> cartItems = new ArrayList<>();
	
	// Mostrar items del carrito
	public List<Product> mostrarItems()
	{
		return cartItems;
	}
	
	// Añadir productos al carrito
	public void addItem(Product p)
	{
		if (p != null)
			cartItems.add(p);
	}
	
	// Eliminar producto del carrito
	public void deleteItem(Long id)
	{
		cartItems.removeIf(product -> product.getId().equals(id));
	}
}
