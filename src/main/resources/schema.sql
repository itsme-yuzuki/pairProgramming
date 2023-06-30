-- テーブル削除
DROP TABLE IF EXISTS attendance;
DROP TABLE IF EXISTS leave;
DROP TABLE IF EXISTS leave_status;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS attendance_type;
DROP TABLE IF EXISTS authorise_name;
DROP TABLE IF EXISTS leave_type;
DROP TABLE IF EXISTS approval_status;
DROP TABLE IF EXISTS date2023;
--2023年日付
CREATE TABLE date2023
(
   date_id serial,
   ymd Text,
   month Integer,
   weekname Text,
   holiday Text
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
-- アカウントテーブル
CREATE TABLE account
(
   account_id SERIAL PRIMARY KEY,
   name TEXT,
   email TEXT,
   password TEXT,
   authorise_id INTEGER REFERENCES authorise_name (authorise_id)
);
-- 休暇日数テーブル
CREATE TABLE leave_status
(
   account_id SERIAL REFERENCES account (account_id),
   leave_default Integer,
   leave_remain Integer
);
-- 勤怠参照テーブル
CREATE TABLE attendance
(
   id SERIAL,
   account_id SERIAL REFERENCES account (account_id),
   submit_date text,
   dow TEXT,
   arriving_time text,
   left_time text,
   attendance_id INTEGER REFERENCES attendance_type (attendance_id),
   telework Integer
);
-- 休暇テーブル
CREATE TABLE leave
(
   id SERIAL,
   account_id SERIAL REFERENCES account (account_id),
   authoriser_id SERIAL REFERENCES account (account_id),
   leave_id Integer REFERENCES leave_type (leave_id),
   approval_id Integer REFERENCES approval_status (approval_id),
   message TEXT
);