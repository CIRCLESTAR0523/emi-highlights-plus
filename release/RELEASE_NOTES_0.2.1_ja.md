# EMI Highlights Plus 0.2.1 リリースノート

- リリース種別：Stable Patch
- 公開日：2026-09-24
- 前version：0.2.0

## 概要

対応する収納・変換画面で、EMI Crafting Modeの必要素材ハイライトを見やすくする更新です。水色overlayの描画順を統一し、アイテム個数やterminal記号がハイライトより前面に表示されるようにしました。

## 主な変更

- Refined StorageのGrid／Wireless Gridへ不足していた水色の必要素材overlayを追加しました。
- Refined Storage、AE2、ProjectE、Project Expansion、Sophisticated Storage／Backpacksで、ハイライト上の個数表示を読みやすくしました。
- 検索、並び順、クリック、設定、world／player dataは変更していません。

## 対応環境

- Minecraft：1.21.1
- Loader：NeoForge 21.1.234以降
- Java：21
- Required：EMI 1.1.24以降
- Optional：Sophisticated Core／Storage／Backpacks、ProjectE、Project Expansion、Applied Energistics 2、Refined Storage

## 更新を推奨する対象

対応連携を使用しており、必要素材ハイライトと個数表示を一貫した見た目にしたい`0.2.0`利用者向けです。`0.2.0`からの破壊的変更はありません。

## 更新方法

1. Minecraftを終了します。
2. `emi_highlights_plus-0.2.0.jar`を外します。
3. `emi_highlights_plus-0.2.1.jar`を導入します。両versionを同時に入れないでください。

設定、world、player dataの移行は不要です。

## ダウンロードとサポート

- GitHub：https://github.com/CIRCLESTAR0523/emi-highlights-plus/releases/tag/v0.2.1
- CurseForge：https://www.curseforge.com/minecraft/mc-mods/emi-highlights-plus
- Issue：https://github.com/CIRCLESTAR0523/emi-highlights-plus/issues
- 成果物：`emi_highlights_plus-0.2.1.jar`
- SHA-256：`C2F9D7567712890CBE045E363B1B0FB9DB7A8095AE0C4D267C7E0219ED473E61`

## 謝辞

EMIおよび各任意連携Modの公開API・実装に基づいて互換機能を構築しています。開発と文書作成には生成AIの補助を使用し、作者が確認しています。
