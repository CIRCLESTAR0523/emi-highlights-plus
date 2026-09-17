# EMI Highlights Plus 0.2.0 リリースノート

- リリース種別：Stable
- 公開日：未公開
- 前version：0.1.0（旧Mod IDのソース履歴）

## 概要

EMIのRecipe Tree／Crafting Modeで必要な素材を、対応する収納・変換画面でも見つけやすくするクライアント用互換Modです。Sophisticated Storage／Backpacksのハイライトに加え、ProjectE系とAE2で必要素材を優先表示します。

## 主な変更

- 名称をEMI Highlights Plus、Mod IDを`emi_highlights_plus`へ変更しました。
- ProjectEのTransmutation Table／Tablet、Project ExpansionのArcane Transmutation Tablet、AE2収納Terminalへ必要素材優先表示を追加しました。
- Refined Storage 2.0.9向け処理を追加しましたが、Gridでの実機確認は未完了です。
- Arcane Tabletの材料判定を軽量化し、表示中の出力候補を実在庫へ重複加算しないようにしました。

## 対応環境

- Minecraft：1.21.1
- Loader：NeoForge 21.1.234以降
- Java：21
- Side：client機能。dedicated serverで安全に読み込めることを確認済み
- 検証済み：Sophisticated Storage／Backpacks、ProjectE、Project Expansion、AE2。Refined Storageは自動検証のみ

## 依存関係

- Required：EMI 1.1.24以降
- Optional：Sophisticated Core 1.4.60以降、Sophisticated Storage 1.5.63以降、Sophisticated Backpacks 3.25.64以降、ProjectE 1.1.0以降、Project Expansion 1.21.1-1.0.6以降、AE2 19.2.17以降、Refined Storage 2.0.9以降

## 更新を推奨する対象

対応画面でEMIの必要素材表示や優先表示を利用したい方が対象です。旧Mod ID版を使用している場合は、下記の移行手順が必要です。

## 導入・更新方法

1. Minecraftを終了します。
2. 旧`emi_container_highlights-*.jar`を外し、`emi_highlights_plus-0.2.0.jar`を`mods`へ入れます。旧新JARを同時に導入しないでください。
3. 旧設定は自動移行されません。必要な値だけを新しい`config/emi_highlights_plus-client.toml`へ手動で反映します。

## 破壊的変更と移行

Mod IDと設定ファイル名が変わりました。worldやplayer dataは変更しません。旧JARを外し、必要な設定だけを手動で移してください。

## 既知の問題

Refined Storage 2.0.9連携は自動試験済みですが、実際のGridでは未確認です。問題がある場合は設定の`refinedStorageEnabled`を無効にし、GitHub Issuesへ報告してください。

## ダウンロードとサポート

- ダウンロード：未公開
- Issue・サポート：https://github.com/CIRCLESTAR0523/emi-highlights-plus/issues
- 成果物：`emi_highlights_plus-0.2.0.jar`
- SHA-256：`F25F587BF70F599111D5295D7F1ED08446085F4B425F2BC42A45E316794ED290`

## 謝辞

EMIおよび各任意連携Modの公開API・実装に基づいて互換機能を構築しています。開発と文書作成には生成AIの補助を使用し、作者が確認しています。
