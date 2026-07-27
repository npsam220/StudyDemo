# 📦 StudyDemo（在庫・社員管理システム）

## 📖 概要
本プロジェクトは、Spring Boot と React を用いて開発した業務系Webアプリケーションです。  
社員管理および商品管理の機能に加え、CSVファイルによる一括登録（バッチ処理）を実装しています。

また、Spring Security による認証機能や、Docker Compose を利用した環境構築にも対応しています。

---

## 🛠 使用技術

### Backend
- Java 17
- Spring Boot
- Spring Data JPA（Hibernate）
- MyBatis
- Flyway
- Spring Security
- Spring Batch
- JasperReports
- Redis

### Frontend
- React（CDN）
- Axios

### Database
- MySQL

### その他
- Docker / Docker Compose
- Git / GitHub

---

## 🚀 主な機能

### 🔹 社員管理機能
- 社員の登録・一覧表示・更新・削除（CRUD）
- 条件検索（ID / 名前 / 年齢 / Email）

### 🔹 商品管理機能
- 商品の登録・一覧・更新・削除
- 検索機能

### 🔹 バッチ処理（CSVインポート）
- CSVファイルアップロードによる一括登録
- Spring Batch を利用したトランザクション制御
- 大量データ処理に対応

### 🔹 認証機能（Spring Security）
- ログイン認証（フォームログイン）
- 認証が必要なAPIの制御
- 未認証ユーザーのアクセス制限

---
### 🔹 学生管理機能
- 学生情報の登録・検索・更新
- 学籍番号および氏名による条件検索
- MyBatis XML Mapperを利用したデータベース操作
- JasperReportsを利用した学生情報のPDF出力
- Redisによる検索結果のキャッシュ

## 🏗 システム構成

```text
Frontend（React / HTML）
        ↓
Controller
        ↓
Service
        ↓
Repository（Spring Data JPA）
または
Mapper（MyBatis）
        ↓
MySQL

---

## 💡 技術的な工夫

- Spring Batch によるCSV一括処理を実装
- トランザクション管理でデータ整合性を保証
- 動的検索（複数条件）に対応
- RESTful API設計
- フロントエンドとバックエンドを分離
- Docker Compose による環境統一
- Spring Data JPAとMyBatisを用途に応じて使い分け
- MyBatis XML MapperによるSQLとJavaコードの分離
- Flywayによるデータベーススキーマのバージョン管理
- マイグレーションファイルを利用したテーブルの自動作成
- Redisによる学生検索結果のキャッシュ
- JasperReportsによる学生情報のPDF帳票出力
- Spring BatchによるCSV一括処理
- トランザクション管理によるデータ整合性の保証
- Docker Composeによる開発環境の統一

---
## 🗄 データベースマイグレーション

本プロジェクトでは、Flywayを利用してデータベーススキーマを管理しています。

アプリケーション起動時に、以下のマイグレーションファイルがバージョン順に適用されます。

- `V1__create_student.sql`：学生テーブルの作成
- `V2__create_teacher.sql`：教師テーブルの作成
- `V3__create_course.sql`：コーステーブルの作成

マイグレーションファイルは以下に配置しています。

`src/main/resources/db/migration`


## 🚀 起動方法（Docker推奨）

```bash
./mvnw clean package
docker compose up -d --build
```

# 起動
docker compose up -d --build
🌐 アクセスURL
Docker環境
アプリ画面
👉 http://localhost:8081/employee/employee-query.html

👉 http://localhost:8081/product/product-management.html
Swagger
👉 http://localhost:8081/swagger-ui/index.html
username: admin
password: 1234
🛠 ローカル起動（Dockerなし）
MySQLを起動
application.properties を設定
アプリを起動

👉 http://localhost:8080/employee/employee-query.html

📈 今後の改善予定
AWS環境へのデプロイ
APIドキュメントの強化（Swagger整理）
フロントエンドのSPA化
ログ管理・監視機能の追加
👤 作者

Sam（Java Backend Engineer）
