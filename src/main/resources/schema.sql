-- テーブル削除
--DROP TABLE IF EXISTS order_details;
--DROP TABLE IF EXISTS orders;
--DROP TABLE IF EXISTS items;
--DROP TABLE IF EXISTS categories;
--DROP TABLE IF EXISTS customers;
-- アカウントテーブル
CREATE TABLE account
(
   id SERIAL PRIMARY KEY,
   name TEXT,
   email TEXT;
   password TEXT,
   FOREIGN KEY (authorise_id) REFERENCES authorise_name (authorise_id)
);
-- 勤怠参照テーブル
CREATE TABLE attendance
(
   FOREIGN KEY (id) REFERENCES account (id),
   submit_date date,
   FOREIGN KEY (attendance_id) REFERENCES attendance_type (attendance_id)
);
-- 休暇テーブル
CREATE TABLE leave
(
   FOREIGN KEY (id) REFERENCES account (id),
   FOREIGN KEY (leave_id) REFERENCES leave_type (leave_id),
   FOREIGN KEY (approval_id) REFERENCES approval_status (approval_id),
);
-- 勤怠状況テーブル（主キー）
CREATE TABLE attendance_type
(
   attendance_id Integer PRIMARY KEY,
   attendance_type text
);
-- 権限テーブル（主キー）
CREATE TABLE authorise_name
(
   authorise_id Integer PRIMARY KEY,
   authorise_name text
);
-- 休暇種類テーブル（主キー）
CREATE TABLE leave_type
(
   leave_id Integer PRIMARY KEY,
   leave_type text
);
-- 承認状態テーブル（主キー）
CREATE TABLE approval_status
(
   approval_id Integer PRIMARY KEY,
   approval_status text
);