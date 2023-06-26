--authorise_nameテーブル
INSERT INTO authorise_name(authorise_id,authorise_name) VALUES(1,'上位');
INSERT INTO authorise_name(authorise_id,authorise_name) VALUES(2, '下位');

-- accountテーブルデータ
INSERT INTO account(name, email, password, authorise_id) VALUES('鈴木一郎', 'suzuki1@abc.com','suzuki1010', 1);
INSERT INTO account(name, email, password, authorise_id) VALUES('鈴木二郎', 'suzuki2@abc.com','suzuki2020', 2);
INSERT INTO account(name, email, password, authorise_id) VALUES('鈴木一郎', 'suzuki3@abc.com','suzuki3030', 1);
INSERT INTO account(name, email, password, authorise_id) VALUES('鈴木一郎', 'sato2@abc.com','sato2020', 2);

-- アテンダンステーブルデータ
--INSERT INTO attendance(submit_date, attendance_id) VALUES(, date, );
--INSERT INTO items(category_id, name, price) VALUES(1, 'MLB Fun', 980); 

--アテンダンスタイプテーブル
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(1,'出勤');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(2,'退勤');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(3,'遅刻');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(4,'早退');
INSERT INTO attendance_type(attendance_id, attendance_type) VALUES(5,'交通遅延');



--leave_typeテーブル
INSERT INTO leave_type(leave_type) VALUES('全日有給休暇');
INSERT INTO leave_type(leave_type) VALUES('午前有給休暇');
INSERT INTO leave_type(leave_type) VALUES('午後有給休暇');
INSERT INTO leave_type(leave_type) VALUES('全日傷病休暇');
INSERT INTO leave_type(leave_type) VALUES('午前傷病休暇');
INSERT INTO leave_type(leave_type) VALUES('午後傷病休暇');
INSERT INTO leave_type(leave_type) VALUES('休職');
INSERT INTO leave_type(leave_type) VALUES('産休');
INSERT INTO leave_type(leave_type) VALUES('育児休暇');
INSERT INTO leave_type(leave_type) VALUES('介護休暇');
INSERT INTO leave_type(leave_type) VALUES('連続特別休暇');
INSERT INTO leave_type(leave_type) VALUES('その他休暇');

--approval_statusテーブル
INSERT INTO approval_status(approval_status) VALUES('未申請')
INSERT INTO approval_status(approval_status) VALUES('承認待ち')
INSERT INTO approval_status(approval_status) VALUES('差し戻し')
INSERT INTO approval_status(approval_status) VALUES('承認済み')