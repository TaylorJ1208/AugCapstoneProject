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
import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.MetricTelemetry;
 
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/catalog")
public class ProductController {
 
    @Autowired
    private ProductService productService;

    @Autowired
   	TelemetryClient telemetryClient;

    @GetMapping("/customer/{id}")
    public Product getProductById(@PathVariable Long id) {
        telemetryClient.trackEvent("/catalog/customer/id Request Triggered");
		long startTime = System.nanoTime();
        Product product = productService.getProductById(id);
		long endTime = System.nanoTime();
    
		MetricTelemetry benchmark = new MetricTelemetry();
		benchmark.setName("Get Product By ID Query (ms)");
		double timeInNano = endTime - startTime;
 		benchmark.setValue(timeInNano/1e6);
		telemetryClient.trackMetric(benchmark);
		return product;
    }

    @GetMapping("/customer")
    public List<Product> getAllProducts() {
        telemetryClient.trackEvent("/catalog/customer Request Triggered");
        long startTime = System.nanoTime();
        List<Product> products = productService.getAllProducts();
        long endTime = System.nanoTime();
    
		MetricTelemetry benchmark = new MetricTelemetry();
		benchmark.setName("Get All Products Query (ms)");
		double timeInNano = endTime - startTime;
 		benchmark.setValue(timeInNano/1e6);
		telemetryClient.trackMetric(benchmark);
		return products;

    }

    @GetMapping("/customer/product/{name}")
    public List<Product> getProductsByName(@PathVariable String name) {
        telemetryClient.trackEvent("/catalog/customer/product/name Request Triggered");
        long startTime = System.nanoTime();
        List<Product> products = productService.getProductByName(name);
        long endTime = System.nanoTime();
    
		MetricTelemetry benchmark = new MetricTelemetry();
		benchmark.setName("Get All Products Query (ms)");
		double timeInNano = endTime - startTime;
 		benchmark.setValue(timeInNano/1e6);
		telemetryClient.trackMetric(benchmark);
		return products;
    }

    @PostMapping("/admin/add")
    public void addProduct(@RequestBody Product product) {
        telemetryClient.trackEvent("/catalog/admin/add Request Triggered");
        productService.addProduct(product);
    }

    @PutMapping("/admin/update")
    public void updateProduct(@RequestBody Product product) {
        telemetryClient.trackEvent("/catalog/admin/update Request Triggered");
        productService.updateProduct(product);
    }

    @DeleteMapping("admin/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        telemetryClient.trackEvent("/catalog/admin/delete/id Request Triggered");
        productService.deleteProductById(id);
    }
}