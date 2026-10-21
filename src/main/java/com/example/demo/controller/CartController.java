package com.example.demo.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Product;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {

	private final CartService cartService;
	private final ProductService productService;
	
	public CartController(CartService cartService, ProductService productService)
	{
		this.cartService = cartService;
		this.productService = productService;
	}
	
	// Listar el carrito
	@GetMapping
	public String mostrarCarrito(Model model)
	{
		model.addAttribute("cartItems", cartService.mostrarItems());
		return "cart";
	}
	
	// Añadir un producto en base a su id
	@PostMapping("/add/{id}")
	public String addItem(@PathVariable Long id)
	{
		// Puede que exista o no --> Optional
		Optional<Product> p = productService.getProductById(id);
		
		// Si existe lo añado
		if (p.isPresent())	cartService.addItem(p.get());
		
		return "redirect:/cart";
		
		
	}
	
	
	// Borrar producto del carrito
	@PostMapping("/remove/{id}")
	public String removeItem(@PathVariable Long id)
	{
		this.cartService.deleteItem(id);
		return "redirect:/cart";
	}
	
}
