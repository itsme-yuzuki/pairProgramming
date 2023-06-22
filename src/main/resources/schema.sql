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
   id SERIAL PRIMARY KEY,
   name TEXT,
   email TEXT,
   password TEXT,
   authorise_id INTEGER REFERENCES authorise_name (authorise_id)
);
-- 勤怠参照テーブル
CREATE TABLE attendance
(
   id SERIAL REFERENCES account (id),
   submit_date date,
   attendance_id INTEGER REFERENCES attendance_type (attendance_id)
);
-- 休暇テーブル
CREATE TABLE leave
(
   id SERIAL REFERENCES account (id),
   leave_id Integer REFERENCES leave_type (leave_id),
   approval_id Integer REFERENCES approval_status (approval_id),
);