--authorise_nameテーブル
INSERT INTO authorise_name(authorise_id,authorise_name) VALUES(0,'管理者');
INSERT INTO authorise_name(authorise_id,authorise_name) VALUES(1,'上位');
INSERT INTO authorise_name(authorise_id,authorise_name) VALUES(2, '下位');

-- accountテーブルデータ
INSERT INTO account(name, email, password, authorise_id) VALUES('鈴木一郎', 'suzuki1abc1010@gmail.com','Suzuki1010@', 1);
INSERT INTO account(name, email, password, authorise_id) VALUES('鈴木二郎', 'suzuki2abc1010@gmail.com','Suzuki1010@', 2);
INSERT INTO account(name, email, password, authorise_id) VALUES('鈴木三郎', 'suzuki3@abc.com','suzuki3030', 1);
INSERT INTO account(name, email, password, authorise_id) VALUES('佐藤二朗', 'sato2@abc.com','sato2020', 0);


--アテンダンスタイプテーブル
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(1,'出勤');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(2,'退勤');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(3,'遅刻');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(4,'早退');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(6,'交通遅延');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(7, '全日有給休暇');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(8, '午前有給休暇');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(9, '午後有給休暇');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(10, '全日傷病休暇');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(11, '午前傷病休暇');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(12, '午後傷病休暇');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(13, '休職');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(14, '産休');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(15, '育児休暇');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(16, '介護休暇');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(17, '連続特別休暇');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(18, 'その他休暇');

--approval_statusテーブル
INSERT INTO approval_status(approval_id, approval_status) VALUES(1, '未申請');
INSERT INTO approval_status(approval_id, approval_status) VALUES(2, '承認待ち');
INSERT INTO approval_status(approval_id, approval_status) VALUES(3, '差し戻し');
INSERT INTO approval_status(approval_id, approval_status) VALUES(4, '承認済み');

--leave_statusデーブル
INSERT INTO leave_status(account_id, leave_default, leave_remain) VALUES(1, 10, 10);
INSERT INTO leave_status(account_id, leave_default, leave_remain) VALUES(2, 10, 10);
INSERT INTO leave_status(account_id, leave_default, leave_remain) VALUES(3, 10, 10);
INSERT INTO leave_status(account_id, leave_default, leave_remain) VALUES(4, 10, 10);

--date2023テーブル
insert into date2023(ymd) SELECT TO_CHAR('2023-01-01'::DATE + ARR.I, 'YYYY-MM-DD') AS ymd FROM GENERATE_SERIES(0, (SELECT EXTRACT(DAY FROM DATE_TRUNC('MONTH', CAST('2023-01-01'AS TIMESTAMP) + '1 MONTHS') + '-1 DAYS') -1)::INT) AS ARR(I);
insert into date2023(ymd) SELECT TO_CHAR('2023-02-01'::DATE + ARR.I, 'YYYY-MM-DD') AS ymd FROM GENERATE_SERIES(0, (SELECT EXTRACT(DAY FROM DATE_TRUNC('MONTH', CAST('2023-02-01'AS TIMESTAMP) + '1 MONTHS') + '-1 DAYS') -1)::INT) AS ARR(I);
insert into date2023(ymd) SELECT TO_CHAR('2023-03-01'::DATE + ARR.I, 'YYYY-MM-DD') AS ymd FROM GENERATE_SERIES(0, (SELECT EXTRACT(DAY FROM DATE_TRUNC('MONTH', CAST('2023-03-01'AS TIMESTAMP) + '1 MONTHS') + '-1 DAYS') -1)::INT) AS ARR(I);
insert into date2023(ymd) SELECT TO_CHAR('2023-04-01'::DATE + ARR.I, 'YYYY-MM-DD') AS ymd FROM GENERATE_SERIES(0, (SELECT EXTRACT(DAY FROM DATE_TRUNC('MONTH', CAST('2023-04-01'AS TIMESTAMP) + '1 MONTHS') + '-1 DAYS') -1)::INT) AS ARR(I);
insert into date2023(ymd) SELECT TO_CHAR('2023-05-01'::DATE + ARR.I, 'YYYY-MM-DD') AS ymd FROM GENERATE_SERIES(0, (SELECT EXTRACT(DAY FROM DATE_TRUNC('MONTH', CAST('2023-05-01'AS TIMESTAMP) + '1 MONTHS') + '-1 DAYS') -1)::INT) AS ARR(I);
insert into date2023(ymd) SELECT TO_CHAR('2023-06-01'::DATE + ARR.I, 'YYYY-MM-DD') AS ymd FROM GENERATE_SERIES(0, (SELECT EXTRACT(DAY FROM DATE_TRUNC('MONTH', CAST('2023-06-01'AS TIMESTAMP) + '1 MONTHS') + '-1 DAYS') -1)::INT) AS ARR(I);
insert into date2023(ymd) SELECT TO_CHAR('2023-07-01'::DATE + ARR.I, 'YYYY-MM-DD') AS ymd FROM GENERATE_SERIES(0, (SELECT EXTRACT(DAY FROM DATE_TRUNC('MONTH', CAST('2023-07-01'AS TIMESTAMP) + '1 MONTHS') + '-1 DAYS') -1)::INT) AS ARR(I);
insert into date2023(ymd) SELECT TO_CHAR('2023-08-01'::DATE + ARR.I, 'YYYY-MM-DD') AS ymd FROM GENERATE_SERIES(0, (SELECT EXTRACT(DAY FROM DATE_TRUNC('MONTH', CAST('2023-08-01'AS TIMESTAMP) + '1 MONTHS') + '-1 DAYS') -1)::INT) AS ARR(I);
insert into date2023(ymd) SELECT TO_CHAR('2023-09-01'::DATE + ARR.I, 'YYYY-MM-DD') AS ymd FROM GENERATE_SERIES(0, (SELECT EXTRACT(DAY FROM DATE_TRUNC('MONTH', CAST('2023-09-01'AS TIMESTAMP) + '1 MONTHS') + '-1 DAYS') -1)::INT) AS ARR(I);
insert into date2023(ymd) SELECT TO_CHAR('2023-10-01'::DATE + ARR.I, 'YYYY-MM-DD') AS ymd FROM GENERATE_SERIES(0, (SELECT EXTRACT(DAY FROM DATE_TRUNC('MONTH', CAST('2023-10-01'AS TIMESTAMP) + '1 MONTHS') + '-1 DAYS') -1)::INT) AS ARR(I);
insert into date2023(ymd) SELECT TO_CHAR('2023-11-01'::DATE + ARR.I, 'YYYY-MM-DD') AS ymd FROM GENERATE_SERIES(0, (SELECT EXTRACT(DAY FROM DATE_TRUNC('MONTH', CAST('2023-11-01'AS TIMESTAMP) + '1 MONTHS') + '-1 DAYS') -1)::INT) AS ARR(I);
insert into date2023(ymd) SELECT TO_CHAR('2023-12-01'::DATE + ARR.I, 'YYYY-MM-DD') AS ymd FROM GENERATE_SERIES(0, (SELECT EXTRACT(DAY FROM DATE_TRUNC('MONTH', CAST('2023-12-01'AS TIMESTAMP) + '1 MONTHS') + '-1 DAYS') -1)::INT) AS ARR(I);
UPDATE date2023 SET weekname = to_char(to_date(ymd, 'yyyy-mm-dd'), 'Day');
UPDATE date2023 SET holiday = '休日' WHERE weekname like 'S%';
UPDATE date2023 SET month = 1 WHERE ymd LIKE '%-01-%';
UPDATE date2023 SET month = 2 WHERE ymd LIKE '%-02-%';
UPDATE date2023 SET month = 3 WHERE ymd LIKE '%-03-%';
UPDATE date2023 SET month = 4 WHERE ymd LIKE '%-04-%';
UPDATE date2023 SET month = 5 WHERE ymd LIKE '%-05-%';
UPDATE date2023 SET month = 6 WHERE ymd LIKE '%-06-%';
UPDATE date2023 SET month = 7 WHERE ymd LIKE '%-07-%';
UPDATE date2023 SET month = 8 WHERE ymd LIKE '%-08-%';
UPDATE date2023 SET month = 9 WHERE ymd LIKE '%-09-%';
UPDATE date2023 SET month = 10 WHERE ymd LIKE '%-10-%';
UPDATE date2023 SET month = 11 WHERE ymd LIKE '%-11-%';
UPDATE date2023 SET month = 12 WHERE ymd LIKE '%-12-%';
