package com.example.StudyDemo.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ErrorResponse {
    private String code;
    private String message;
    
    // タイムスタンプ項目を追加
    /*
    @JsonFormatは、JSONへシリアライズする際の日時フォーマットを指定するためのアノテーションです。
     主に、日付や時刻の表示形式を統一するために使用されます。
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
        // 初期化時に現在時刻を自動設定
        this.timestamp = LocalDateTime.now();
    }

    // GetterとSetter（またはLombokの@Dataを使用）
    public String getCode() { return code; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
}