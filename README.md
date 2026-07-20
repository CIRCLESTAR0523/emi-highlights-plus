# EMI Container Highlights

EMI Recipe Tree / Crafting Modeの必要素材ハイライトを、独自描画を使う収納画面へ補完するクライアント用NeoForge Modです。

## 必須環境

- Minecraft 1.21.1
- NeoForge 21.1.234以降
- EMI 1.1.24以降

## 対応する任意連携

- Sophisticated Storage 1.5.63以降
- Sophisticated Backpacks 3.25.64以降

任意連携先は必須依存ではありません。導入されている対応先だけが有効になります。

## 導入

1. EMIと、使用する対応先Modを導入します。
2. `emi_container_highlights-0.1.0.jar`をMinecraftの`mods`フォルダーへ入れます。
3. 旧名の`emi_container_highlight_compat-0.1.0.jar`がある場合は、二重ロードを避けるため取り除きます。

## 設定

初回起動後、`config/emi_container_highlights-client.toml`が生成されます。

- `enabled`: Mod全体の有効化
- `sophisticatedStorageEnabled`: Sophisticated Storage連携
- `sophisticatedBackpacksEnabled`: Sophisticated Backpacks連携
- `debug`: レート制限付きデバッグログ

## ビルド

JDK 21を使用します。

```bash
./gradlew build
```

成果JARは`build/libs/`へ生成されます。依存ModのJAR、Modpack、設定、ログ、セーブデータ、検証資料は成果物やリポジトリへ含めません。

## ライセンス

このプロジェクトは[MIT License](LICENSE)で公開します。依存Modはそれぞれの権利者とライセンスに帰属します。
