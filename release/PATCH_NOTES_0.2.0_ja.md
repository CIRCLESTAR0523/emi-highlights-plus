# EMI Highlights Plus 0.2.0 パッチノート

- リリース種別：Stable
- GitHub公開日：2026-09-21
- 前version：0.1.0（旧Mod IDのソース履歴）
- 対応Minecraft：1.21.1
- 対応Loader：NeoForge 21.1.234以降
- 対応Java：21

## 追加

- `ADD-001` ProjectEのTransmutation Table／TabletとProject ExpansionのArcane Transmutation Tabletに、必要素材優先表示を追加。
- `ADD-002` AE2収納Terminalに、検索・表示filter・元のsort・固定行を維持する必要素材優先表示を追加。
- `ADD-003` Refined Storage 2.0.9 Grid向けの優先表示を追加。自動検証済み、実機未確認。
- `ADD-004` ProjectE系とAE2／RSの連携別設定、および必要素材優先表示の設定を追加。

## 変更

- `CHG-001` 公開名をEMI Highlights Plus、Mod IDを`emi_highlights_plus`、設定ファイルを`emi_highlights_plus-client.toml`へ変更。
- `CHG-002` Arcane Tabletの学習済みアイテムを、現在のEMCから生成可能な数量としてEMIへ提供。表示中の出力候補は実在庫に数えない。
- `CHG-003` 必要素材の種類が変わった場合だけ候補を更新し、各連携先の元の並び順をグループ内で維持。

## 修正

- `FIX-001` Arcane TabletでEMIがcraftable一覧を再計算する際、重い正確な転送判定を全recipeへ繰り返して画面が固まる問題を修正。

## 削除

- なし。

## 互換性と依存関係

- `COMPAT-001` EMI 1.1.24以降を必須とし、Sophisticated Core／Storage／Backpacks、ProjectE、Project Expansion、AE2、Refined Storageを任意依存としてmetadataへ記載。
- `COMPAT-002` client機能をdedicated serverで安全に読み込める境界、任意依存なしでの起動を再確認。

## 設定とデータ

- `DATA-001` 旧`emi_container_highlights-client.toml`は自動移行・自動削除しない。world・player dataの形式変更なし。

## 破壊的変更と移行

- `BREAK-001` Mod ID変更により旧新JARは別Modとして認識される。旧JARを外し、必要な設定だけを新設定へ手動で移す。

## 既知の問題

- `KNOWN-001` Refined Storage 2.0.9連携は実際のGridで未確認。問題時は`refinedStorageEnabled=false`を回避策とする。

## 成果物

- ファイル：`emi_highlights_plus-0.2.0.jar`
- サイズ：58,615 bytes
- SHA-256：`F25F587BF70F599111D5295D7F1ED08446085F4B425F2BC42A45E316794ED290`
