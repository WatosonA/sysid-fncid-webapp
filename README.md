# sysid-fncid-webapp
システム識別子＋機能識別子＋webapp

システムの機能ごとにWebAppを提供するモジュールのサンプル

機能によってSLAやパフォーマンスや変更頻度がことなるため、モジュールを独立させる

## Requirements
- JDK 17

## Features
- Spring Boot 3
- Open API 3.0 (Swagger)

## Quick Start

### ローカル環境での起動方法

#### RepositoryライブラリのPublish
sysid-dbid-repositoryのREADME.mdを参照

#### DB起動
sysid-dbid-repositoryのREADME.mdを参照

#### アプリケーションを起動
IDEまたはコマンドでbootRun
```sh
./gradlew bootRun
```
