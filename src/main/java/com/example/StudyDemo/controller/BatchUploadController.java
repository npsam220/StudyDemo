package com.example.StudyDemo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/batch")
public class BatchUploadController {

    @PostMapping("/upload")
    public String uploadCsv(
            @RequestParam("file") MultipartFile file) throws IOException {

        // 🔥 檢查是否有檔案
        if (file.isEmpty()) {
            return "ファイルが選択されていません";
        }

        // 🔥 upload_files資料夾
        String uploadDir = "upload_files/";

        new File(uploadDir).mkdirs();

        // 🔥 原始檔名
        String filename = file.getOriginalFilename();

        // 🔥 儲存路徑
        Path path = Paths.get(uploadDir + filename);

        // 🔥 存檔
        Files.write(path, file.getBytes());

        return "CSVアップロード成功: " + filename;
    }
}