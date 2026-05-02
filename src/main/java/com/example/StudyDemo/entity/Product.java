package com.example.StudyDemo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "product")
@Schema(description = "商品情報")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "商品ID")
    private Long id;

    @Column(name = "product_code", unique = true, nullable = false)
    @NotBlank
    @Schema(description = "商品コード")
    private String productCode;

    @NotBlank
    @Schema(description = "商品名")
    private String name;

    @Schema(description = "価格")
    private Integer price;

    @Schema(description = "在庫数")
    private Integer stock;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
