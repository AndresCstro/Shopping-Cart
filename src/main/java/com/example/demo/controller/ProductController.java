package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;


@Controller
@RequestMapping("/products")
public class ProductController {
	
	private final ProductService productService;
	
	public ProductController(ProductService productService)
	{
		this.productService = productService;
	}
	
	//Listar los productos --> cuando se hace GET /products, 
	// el parámetro lo proporciona Spring
	// Luego, Thymeleaf accede a dichos datos mediante ${products}
	@GetMapping
	public String listProducts(Model model) {
		// Datos que ese documento puede emplear
		model.addAttribute("products", productService.getAllProducts());
		
		// Este return indica el documento
		return "products";
	}
	
	// Mostrar formulario para añadir product
	@GetMapping("/add")
	public String addProductForm(Model model) {
		model.addAttribute("product", new Product());
		
		return "product_form";
	}
	
	// Editar formulario de producto existente (manejar excepción)
	@GetMapping("/edit/{id}")
	public String editProductForm(@PathVariable Long id, Model model)
	{
		Product p = productService.getProductById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "Producto no encontrado: " + id
                    )
                );
		
		model.addAttribute("product", p);
		
		return "product_form";
	}
	
	// Guardar producto nuevo o editado
	// ModelAttribute convierte automaticamente los campos del HTML en un producto
	@PostMapping("/save")
	public String saveProduct(@ModelAttribute Product p)
	{
		productService.saveProduct(p);
		// Uso redirect para recargar el model (se listan de nuevo todos los productos)
		return "redirect:/products";
	}
	
	//Eliminar producto dado un id
	@PostMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Long id)
	{
		productService.deleteProduct(id);
		return "redirect:/products";
	}
	
	
	
}
