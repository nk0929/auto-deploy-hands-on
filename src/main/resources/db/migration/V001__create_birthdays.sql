-- DDL: 誕生日テーブルの作成
CREATE TABLE birthdays (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    birth_date TEXT NOT NULL, -- YYYY-MM-DD形式で保存
    note TEXT
);