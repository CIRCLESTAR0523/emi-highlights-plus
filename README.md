# EMI Highlights Plus

![EMI Highlights Plus](release/assets/emi_highlights_plus_three_line_wordmark_candidate_400.png)

[English overview](release/PROJECT_PAGE_en.md) · [日本語の紹介](release/PROJECT_PAGE_ja.md) · [Release notes](release/RELEASE_NOTES_0.2.1_en.md) · [リリースノート](release/RELEASE_NOTES_0.2.1_ja.md)

EMI Recipe Tree / Crafting Modeの必要素材ハイライトと材料在庫判定を、独自の収納画面へ補完するクライアント用NeoForge Modです。

## 必須環境

- Minecraft 1.21.1
- NeoForge 21.1.234以降
- EMI 1.1.24以降

## 対応する任意連携

- Sophisticated Storage 1.5.63以降
- Sophisticated Backpacks 3.25.64以降
- ProjectE PE1.1.0以降（Transmutation Table / Tabletの優先表示）
- Project Expansion 1.21.1-1.0.6以降（Arcane Transmutation Tablet）
- Applied Energistics 2 19.2.17以降（収納Terminalの優先表示）
- Refined Storage 2.0.9以降（Grid／Wireless Gridの優先表示・水色ハイライト）

任意連携先は必須依存ではありません。導入されている対応先だけが有効になります。

## 導入

1. EMIと、使用する対応先Modを導入します。
2. `emi_highlights_plus-0.2.1.jar`をMinecraftの`mods`フォルダーへ入れます。
3. 旧Mod IDの`emi_container_highlights-*.jar`または旧名の`emi_container_highlight_compat-*.jar`がある場合は、二重ロードを避けるため取り除きます。

## 設定

初回起動後、`config/emi_highlights_plus-client.toml`が生成されます。

旧Mod IDの`config/emi_container_highlights-client.toml`は自動移行・自動削除されません。旧設定が必要な場合は、内容を確認して新しい設定ファイルへ手動で反映してください。

- `enabled`: Mod全体の有効化
- `sophisticatedStorageEnabled`: Sophisticated Storage連携
- `sophisticatedBackpacksEnabled`: Sophisticated Backpacks連携
- `projectEEnabled`: ProjectE Transmutation Table / Tabletの優先表示
- `projectExpansionEnabled`: Arcane Transmutation Tablet連携
- `ae2Enabled`: AE2収納Terminalの優先表示
- `refinedStorageEnabled`: RS Gridの優先表示
- `requiredMaterialPriority`: 必要素材表示中、対応Transmutation画面の候補を優先表示（初期値ON）
- `debug`: レート制限付きデバッグログ

Arcane Transmutation Tabletでは、現在表示中の16枠を材料数へ加えません。代わりに、全学習済みアイテムについて現在のEMCから生成できる個数をEMIへ渡すため、検索結果やページを切り替えても材料判定が変化しません。EMIのレシピ投入はProject Expansionの既存サーバー処理を利用します。

ProjectE本体とArcane Tabletの必要素材優先表示では、必要素材同士とその他同士の元のEMC順を維持します。検索、燃料枠、ロック枠は変更せず、必要素材の種類や表示ON/OFFが変わった時だけ最初のページへ戻します。数量だけの変更ではページを戻しません。

AE2とRSでは検索・表示フィルターを通過した通常一覧だけを必要素材優先にし、必要素材内とその他内では各Modで選択中のソートを維持します。AE2の固定行は並べ替えず、必要素材の種類が変わった時だけ一覧を更新します。AE2 Terminal／Wireless TerminalとRS Grid／Wireless Gridは実機確認済みです。

Refined Storageの対応は実験的です。問題があれば`refinedStorageEnabled=false`で無効にできます。

## ビルド

JDK 21を使用します。

```bash
./gradlew build
```

成果JARは`build/libs/`へ生成されます。依存ModのJAR、Modpack、設定、ログ、セーブデータ、検証資料は成果物やリポジトリへ含めません。

## ライセンス

このプロジェクトは[MIT License](LICENSE)で公開します。依存Modはそれぞれの権利者とライセンスに帰属します。

開発・文書にAI補助を使用。アイコンはEMIのMITライセンス画像を参考にAI生成しました。[アイコンの出典・ライセンス](release/ICON_DERIVATION_NOTICE.md)。EMI非公式アドオンです。
