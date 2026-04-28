package com.example.StudyDemo.controller;

import com.example.StudyDemo.entity.Product;
import com.example.StudyDemo.exception.ProductNotFoundException;
import com.example.StudyDemo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {
    ProductService service;
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    public ProductController(ProductService service) {
        this.service = service;
    }
      // 搜尋
    @GetMapping("/search")
    public List<Product> search(
        @RequestParam(required = false) Long id,
        @RequestParam(required = false) String productCode,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Integer pricebegin,
        @RequestParam(required = false) Integer priceend
       ) {
        log.info("product search - id: {}, productCode: {}, name: {}, pricebegin: {}, priceend: {}", id, productCode, name, pricebegin, priceend);
     
        List<Product> result = service.search(
            id,
            productCode,
            name,
            pricebegin,
            priceend
        );
       /* for (Product p : result) {
            System.out.println("Found product: " + p.getId() + ", " + p.getName() + ", " + p.getPrice());
        }*/ 
        return result;
    }
     // 更新
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
         Product existing = service.findById(id);
         log.info("product search - id: {}", id);
        if (existing == null) {
           throw new ProductNotFoundException("商品が存在しません");
        }
        existing.setProductCode(product.getProductCode());
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());

       
        return service.save(existing);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Product existing = service.findById(id);
        if (existing == null) {
           throw new ProductNotFoundException("商品が存在しません");  
        }
        service.deleteById(id);
    }
    @PostMapping("/create")
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }
    

}
