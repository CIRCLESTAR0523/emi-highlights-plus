# EMI Highlights Plus 0.2.0 既知の問題

## Refined Storage Gridの実機確認

- 状態：自動検証済み、実機未確認。
- 影響：Refined Storage 2.0.9 Gridでの表示、検索、sort、取り出しを実際の環境では確認できていません。
- 回避策：問題がある場合は`config/emi_highlights_plus-client.toml`の`refinedStorageEnabled`を`false`にしてください。
- 報告先：https://github.com/CIRCLESTAR0523/emi-highlights-plus/issues

## 旧Mod IDからの移行

- 旧`emi_container_highlights-client.toml`は自動移行されません。
- 旧新JARを同時に導入すると別Modとして二重に読み込まれるため、旧JARを外してください。
- worldおよびplayer dataは本Modの移行対象ではありません。
