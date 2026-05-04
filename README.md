# 社員管理システム（StudyDemo）

## 📌 概要
Spring BootとReactを使用し、社員情報を管理するWebアプリケーションを開発しました。  
社員の登録・検索・更新・削除（CRUD）機能に加え、Spring Securityによる認証機能を実装しています。  

また、Docker Composeを利用することで、アプリケーションとデータベースを一括で起動できるようにしています。

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
- フロントエンドとバックエンドを分離した構成
- バックエンド：Spring Boot（REST API）
- フロントエンド：React（CDN + Axios）
- データベース：MySQL
- コンテナ：Docker / Docker Compose

---

## 🔐 認証機能（Spring Security）
Spring Securityを用いてログイン認証機能を実装しました。

- UserDetailsServiceによる認証処理
- フォームログイン対応
- 認証が必要なAPI（登録・更新・削除）を制御
- 未認証ユーザーのアクセス制限

---

## 💡 工夫した点
- 検索条件を複数指定できる動的クエリを実装
- フロントエンドとバックエンドの責務を分離
- RESTfulなAPI設計
- 可読性・保守性を意識したコード構成
- 初期データを自動投入（DataLoader）

---

## 🚀 起動方法（Docker推奨）

```bash
# 1. プロジェクトをビルド
./mvnw clean package

# 2. Dockerで起動
docker compose up -d --build

アクセスURL
アプリ画面
👉 http://localhost:8081/employee/employee-query.html
Swagger（API確認）
👉 http://localhost:8081/swagger-ui/index.html
🧪 デモ用アカウント
username: admin
password: 1234
🛠 ローカル起動方法（Dockerなし）
MySQLを起動
application.propertiesでDB接続設定
Spring Bootアプリを起動
http://localhost:8080/employee/employee-query.html

📚 使用技術
Java / Spring Boot
Spring Security
JPA（Hibernate）
MySQL
React（CDN）
Axios
Docker / Docker Compose
Git / GitHub