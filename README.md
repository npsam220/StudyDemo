# 社員管理システム（StudyDemo）

## 📌 概要
Spring BootとReactを使用し、社員情報を管理するWebアプリケーションを開発しました。  
社員の登録・検索・更新・削除（CRUD）機能に加え、Spring Securityによる認証機能を実装しています。

---

## 🔧 主な機能
- 社員登録（Create）
- 社員一覧表示（Read）
- 社員情報更新（Update）
- 社員削除（Delete）
- 条件検索（ID / 名前 / 年齢 / Email）
- ログイン認証機能（Spring Security）

---

## 🏗 システム構成
- フロントエンドとバックエンドを分離した構成（SPA）
- バックエンド：Spring Boot（REST API）
- フロントエンド：React（CDN + Axios）
- データベース：MySQL

---

## 🔐 認証機能（Spring Security）
Spring Securityを用いてログイン認証機能を実装しました。  
UserDetailsServiceを利用し、データベースに登録されたユーザー情報をもとに認証処理を行っています。

- フォームログイン対応
- 認証が必要なAPI（登録・更新・削除）を制御
- 未認証ユーザーはアクセス制限

---

## 💡 工夫した点
- 検索条件を複数指定できるように設計（動的クエリ）
- フロントエンドとバックエンドの責務を分離
- 可読性・保守性を意識したコード構成
- RESTfulなAPI設計を意識

---

## 🚀 起動方法
1. MySQLを起動
2. application.propertiesでDB接続設定
3. Spring Bootアプリケーションを起動
4. ブラウザで以下にアクセス  

http://localhost:8080/employee/employee-management.html

---

## 📚 使用技術
- Java / Spring Boot
- Spring Security
- JPA（Hibernate）
- MySQL
- React（CDN）
- Axios
- Git / GitHub
