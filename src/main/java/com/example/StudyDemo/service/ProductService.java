package com.example.StudyDemo.service;

import com.example.StudyDemo.entity.Product;
import com.example.StudyDemo.exception.ProductNotFoundException;
import com.example.StudyDemo.repository.ProductRepository;
import com.example.StudyDemo.mapper.ProductMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
   // private final ProductRepository repository;
   private final ProductMapper productMapper;

   // public ProductService(ProductRepository repository) {
   // this.repository = repository;
   // }

   public ProductService(ProductMapper productMapper) {
      this.productMapper = productMapper;
   }

   public List<Product> search(Long id, String productCode, String name, Integer pricebegin, Integer priceend) {

      // 如果全部條件都沒有 → 查全部
      boolean noId = (id == null);
      boolean noProductCode = (productCode == null || productCode.trim().isEmpty());
      boolean noName = (name == null || name.trim().isEmpty());
      boolean noPrice = (pricebegin == null && priceend == null);

      // if (noId && noProductCode && noName && noPrice) {
      // //return repository.findAll();
      // return productMapper.findAll();
      // }

      // 🔥 直接用 Integer，不要轉型
      // return repository.search(id, productCode, name, pricebegin, priceend);
      return productMapper.search(id, productCode, name, pricebegin, priceend);
   }

   public Product save(Product product) {
      // return repository.save(product);
      return productMapper.save(product);
   }

   public Product findById(Long id) {
      // return repository.findById(id)
      // .orElseThrow(() -> new ProductNotFoundException("商品が存在しません"));
      Product product = productMapper.findById(id);
      if (product == null) {
         throw new ProductNotFoundException("商品が存在しません");
      }
      return product;
   }

   public void deleteById(Long id) {
      // if (!repository.existsById(id)) {
      // throw new ProductNotFoundException("商品が存在しません");
      // }
      // repository.deleteById(id);
      Product product = productMapper.findById(id);
      if (product == null) {
         throw new ProductNotFoundException("商品が存在しません");
      }
      productMapper.deleteById(id);
   }

   public List<Product> findAll() {
      return productMapper.search(null, null, null, null, null);
      // return repository.findAll();
   }
}
