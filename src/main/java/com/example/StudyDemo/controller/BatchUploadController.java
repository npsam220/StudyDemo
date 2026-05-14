package com.example.StudyDemo.controller;

import com.example.StudyDemo.service.BatchService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.nio.charset.StandardCharsets;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.StudyDemo.Unity.DoCsv;

@RestController
@RequestMapping("/batch")
public class BatchUploadController {

    private final BatchService batchService;

    public BatchUploadController(BatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping("/uploadProductCsv")
    public ResponseEntity<String> uploadProductCsv(
            @RequestParam("file") MultipartFile file,
            HttpServletResponse response) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("CSVファイルを選択してください");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;

            int lineNumber = 0;

            List<HashMap<String, Object>> productList = new ArrayList<>();
            int errorCount = 0;

            while ((line = reader.readLine()) != null) {
                String errInformation = "";
                // 空行はスキップ
                if (line.isBlank()) {
                    lineNumber++;
                    continue;
                }
                // ヘッダー行はスキップ
                if (lineNumber == 0 || lineNumber == 1) {
                    lineNumber++;
                    continue;
                }
                HashMap<String, Object> productMap = new HashMap<>();
                String[] columns = line.split(",", -1);
                if (columns.length < 5) {
                    errorCount++;
                    errInformation += "CSVの列数が不足しています (行番号: " + lineNumber + ");";
                    productMap.put("error", errInformation);
                    productList.add(productMap);
                    lineNumber++;
                    continue;
                }
                String action = columns[0].trim();
                String productCode = columns[1].trim();
                String name = columns[2].trim();
                String price = columns[3].trim();
                String stock = columns[4].trim();

                // 基本的なバリデーション
                productMap.put("action", action);
                if (!action.equals("A") && !action.equals("U") && !action.equals("D")) {
                    errorCount++;
                    errInformation += "不正な処理区分: " + action + " (行番号: " + lineNumber + ");";
                    System.out.println("不正な処理区分: " + action + " (行番号: " + lineNumber + ")");
                }
                // 商品コードは必須で、13文字以内
                productMap.put("productCode", productCode);
                if (productCode.isEmpty()) {
                    errorCount++;
                    errInformation += "商品コードが入力されていません (行番号: " + lineNumber + ");";
                    System.out.println("商品コードが入力されていません (行番号: " + lineNumber + ")");
                } else if (productCode.length() > 13) {
                    errorCount++;
                    errInformation += "商品コードが長すぎます (行番号: " + lineNumber + ");";
                    System.out.println("商品コードが長すぎます (行番号: " + lineNumber + ")");
                }
                // 商品名は必須で、255文字以内
                productMap.put("name", name);
                if (name.isEmpty()) {
                    errorCount++;
                    errInformation += "商品名が入力されていません (行番号: " + lineNumber + ");";
                    System.out.println("商品名が入力されていません (行番号: " + lineNumber + ")");
                } else if (name.length() > 255) {
                    errorCount++;
                    errInformation += "商品名が長すぎます (行番号: " + lineNumber + ");";
                    System.out.println("商品名が長すぎます (行番号: " + lineNumber + ")");
                }
                productMap.put("price", price);
                if (price.isEmpty()) {
                    errorCount++;
                    errInformation += "価格が入力されていません (行番号: " + lineNumber + ");";
                    System.out.println("価格が入力されていません (行番号: " + lineNumber + ")");
                } else if (!price.matches("\\d+")) {
                    errorCount++;
                    errInformation += "価格は数字で入力してください (行番号: " + lineNumber + ");";
                    System.out.println("価格は数字で入力してください (行番号: " + lineNumber + ")");
                }
                productMap.put("stock", stock);
                if (stock.isEmpty()) {
                    errorCount++;
                    errInformation += "在庫数が入力されていません (行番号: " + lineNumber + ");";
                    System.out.println("在庫数が入力されていません (行番号: " + lineNumber + ")");
                } else if (!stock.matches("\\d+")) {
                    errorCount++;
                    errInformation += "在庫数は数字で入力してください (行番号: " + lineNumber + ");";
                    System.out.println("在庫数は数字で入力してください (行番号: " + lineNumber + ")");
                }
                if (!errInformation.isEmpty()) {
                    productMap.put("error", errInformation);
                }
                System.out.println("処理区分: " + columns[0]);
                System.out.println("商品コード: " + columns[1]);
                System.out.println("商品名: " + columns[2]);
                System.out.println("価格: " + columns[3]);
                System.out.println("在庫数: " + columns[4]);
                productList.add(productMap);
                lineNumber++;
            }

            if (errorCount > 0) {
                System.out.println("CSVファイルの処理中に " + errorCount + " 件のエラーが発生しました");
                DoCsv doCsv = new DoCsv();
                doCsv.exportCsv(
                        new String[] { "処理区分", "商品コード", "商品名", "価格", "在庫数", "エラー内容" },
                        new String[] { "action", "productCode", "name", "price", "stock", "error" },
                        "CSVアップロードエラー_" + System.currentTimeMillis(),
                        productList,
                        response);
                return null;
            }

            for (HashMap<String, Object> productMap : productList) {
                String action = (String) productMap.get("action");
                String productCode = (String) productMap.get("productCode");

                String dbError = batchService.validateDbRule(action, productCode);

                if (!dbError.isEmpty()) {
                    errorCount++;
                    productMap.put("error", dbError);
                }
            }

            if (errorCount > 0) {
                System.out.println("CSVファイルのDB検証中に " + errorCount + " 件のエラーが発生しました");
                DoCsv doCsv = new DoCsv();
                doCsv.exportCsv(
                        new String[] { "処理区分", "商品コード", "商品名", "価格", "在庫数", "エラー内容" },
                        new String[] { "action", "productCode", "name", "price", "stock", "error" },
                        "CSVアップロードエラー_" + System.currentTimeMillis(),
                        productList,
                        response);
                return null;
            }
            batchService.processCsv(productList);
            return ResponseEntity.ok("CSVアップロード成功");

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError()
                    .body("CSVアップロード失敗: " + e.getMessage());
        }
    }

}
