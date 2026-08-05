package com.example.StudyDemo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.StudyDemo.entity.Product;

@Mapper
public interface ProductMapper {

    List<Product> search(Long id, String productCode, String name, Integer pricebegin, Integer priceend);

    Product save(Product product);

    Product findById(Long id);

    void deleteById(Long id);

}
