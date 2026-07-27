package com.example.StudyDemo.batch;

import com.example.StudyDemo.entity.Product;
import com.example.StudyDemo.service.ProductService;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductBatch {

    private final ProductService service;

    public ProductBatch(ProductService service) {
        this.service = service;
    }

    // 毎日午前2時にCSV出力を実行
    @Scheduled(cron = "0 0 2 * * ?")
    //@Scheduled(fixedRate = 30000) // 1分ごとに実行（テスト用）
    public void exportCsvBatch() {

        List<Product> list = service.findAll();
        String path = "download_files/";
        new File(path).mkdirs();
        String filename = path + "products_batch_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) +
                ".csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

            writer.write("\uFEFF"); // Excel防亂碼
            writer.println("ID,商品コード,商品名,価格,在庫数");

            for (Product p : list) {
                writer.println(
                        safe(p.getId()) + "," +
                                csv(p.getProductCode()) + "," +
                                csv(p.getName()) + "," +
                                safe(p.getPrice()) + "," +
                                safe(p.getStock()));
            }

            System.out.println("Batch CSV export success: " + filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String safe(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    private String csv(String str) {
        if (str == null)
            return "";
        return "\"" + str.replace("\"", "\"\"") + "\"";
    }
}