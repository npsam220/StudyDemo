# 社員管理システム（StudyDemo）

## 📌 概要
Spring BootとReactを使用して、社員管理システム（CRUD機能）を作成しました。

社員情報の登録・検索・更新・削除を行うことができます。
## 🔧 主な機能
- 社員登録（Create）
- 社員一覧表示（Read）
- 社員情報更新（Update）
- 社員削除（Delete）
- 条件検索（ID / 名前 / 年齢 / Email）

---

## 🏗 システム構成
- REST APIを使用したフロントエンド・バックエンド分離構成
- Spring BootでAPIを実装
- Reactで画面操作を実装

---

## 💡 工夫した点
- 検索条件を複数指定できるように設計
- フロントエンドとバックエンドの連携を意識した構成
- 可読性を意識したコード設計
---

## 🚀 起動方法

1. MySQLを起動
2. application.propertiesでデータベース接続を設定
3. Spring Bootアプリケーションを起動
4. ブラウザで以下にアクセス

http://localhost:8080/employee/employee-management.html
