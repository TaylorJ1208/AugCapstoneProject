package com.ecommerce.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
 
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/catalog")
public class ProductController {
 
    @Autowired
    private ProductService productService;

    @GetMapping("/customer/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/customer")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/customer/product/{name}")
    public List<Product> getProductsByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @PostMapping("/admin/add")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/admin/update")
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("admin/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}