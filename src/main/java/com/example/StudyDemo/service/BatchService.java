package com.example.StudyDemo.service;

import com.example.StudyDemo.entity.Product;
import com.example.StudyDemo.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;

@Service
public class BatchService {

    private final ProductRepository productRepository;

    public BatchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProducts(List<Product> products) {

        for (Product product : products) {

            // 測試 rollback 用
            if (product.getPrice() < 0) {
                throw new RuntimeException("価格が不正です");
            }

            productRepository.save(product);
        }
    }

    public void addProduct(Product product) {

        productRepository.save(product);
    }

    public void updateProduct(
            String productCode,
            String name,
            Integer price,
            Integer stock) {

        Product product = productRepository
                .findByProductCode(productCode)
                .orElseThrow(() -> new RuntimeException("更新対象なし"));

        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);

        productRepository.save(product);
    }

    public void deleteProduct(String productCode) {

        Product product = productRepository
                .findByProductCode(productCode)
                .orElseThrow(() -> new RuntimeException("削除対象なし"));

        productRepository.delete(product);
    }

    @Transactional
    public void processCsv(List<HashMap<String, Object>> rows) {

        for (HashMap<String, Object> row : rows) {

            String action = Normalizer.normalize((String) row.get("action"), Normalizer.Form.NFKC);
            String productCode = (String) row.get("productCode");
            String name = (String) row.get("name");
            Integer price = Integer.valueOf((String) row.get("price"));
            Integer stock = Integer.valueOf((String) row.get("stock"));

            switch (action) {
                case "A":
                    Product addProduct = new Product();
                    addProduct.setProductCode(productCode);
                    addProduct.setName(name);
                    addProduct.setPrice(price);
                    addProduct.setStock(stock);
                    addProduct(addProduct);
                    break;

                case "U":
                    updateProduct(productCode, name, price, stock);
                    break;

                case "D":
                    deleteProduct(productCode);
                    break;

                default:
                    throw new RuntimeException("不正な処理区分: " + action);
            }
        }
    }

    public String validateDbRule(String action, String productCode) {

        boolean exists = productRepository.findByProductCode(productCode).isPresent();

        switch (action) {
            case "A":
                if (exists) {
                    return "追加対象の商品コードが既に存在します";
                }
                break;

            case "U":
                if (!exists) {
                    return "更新対象の商品コードが存在しません";
                }
                break;

            case "D":
                if (!exists) {
                    return "削除対象の商品コードが存在しません";
                }
                break;

            default:
                return "不正な処理区分: " + action;
        }

        return "";
    }
}
