# EMI Highlights Plus 0.2.1 パッチノート

- リリース種別：Stable Patch
- 公開日：2026-09-24
- 前version：0.2.0
- 対応Minecraft：1.21.1
- 対応Loader：NeoForge 21.1.234以降
- 対応Java：21

## 追加

- `ADD-001` Refined StorageのGrid／Wireless Gridに、EMI Crafting Modeの水色必要素材overlayを追加。

## 変更

- `CHG-001` 対応画面の描画順を統一し、アイテム個数とterminal記号を半透明overlayより前面へ表示。
- `CHG-002` 対応する標準container画面で、必要素材snapshotとhighlight rendererを共用。

## 修正

- `FIX-001` 対応する標準containerのoverlayがアイテム個数を着色または隠す問題を修正。

## 削除

- なし。

## 互換性と依存関係

- EMI 1.1.24以降が引き続きRequired。
- Sophisticated Core／Storage／Backpacks、ProjectE、Project Expansion、AE2、Refined Storageは引き続きOptional。
- AE2 19.2.17、Refined Storage 2.0.9を固定版契約の検証対象とした。

## 設定とデータ

- 設定形式の変更なし。
- world、player data、network、server transactionの変更なし。

## 破壊的変更と移行

- なし。`0.2.0` JARを外して`0.2.1`へ置き換え、両versionを同時に導入しない。

## 検証

- clean buildと22件の自動試験がPASS。
- RS Grid／Wireless Grid、AE2 Terminal／Wireless Terminal、ProjectE／Project Expansion、Sophisticated Storage／Backpacksで描画順を実機確認済み。

## 成果物

- ファイル：`emi_highlights_plus-0.2.1.jar`
- サイズ：73,215 bytes
- SHA-256：`C2F9D7567712890CBE045E363B1B0FB9DB7A8095AE0C4D267C7E0219ED473E61`
