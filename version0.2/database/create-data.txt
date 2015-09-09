

insert into physicians (first_name, last_name, night_order, side_id)
values ('Steve', 'Smith', 1, 1);

insert into physicians (first_name, last_name, night_order, side_id)
values ('Jenny', 'Jones', 2, 1);

insert into physicians (first_name, last_name, night_order, side_id)
values ('Nick', 'Nelson', 3, 1);

insert into physicians (first_name, last_name, night_order, side_id)
values ('Mary', 'Malone', 4, 1);




insert into physicians (first_name, last_name, night_order, side_id)
values ('Paul', 'Pancer', 1, 2);

insert into physicians (first_name, last_name, night_order, side_id)
values ('Peter', 'Passley', 2, 2);

insert into physicians (first_name, last_name, night_order, side_id)
values ('Peggy', 'Peroni', 3, 2);

insert into physicians (first_name, last_name, night_order, side_id)
values ('Priscilla', 'Pichard', 4, 2);



insert into shifts (shift_id, start_time, end_time, shortname)
values (1, str_to_date('07:00', '%H:%i'), str_to_date('19:00','%H:%i'), 'Day');

insert into shifts (shift_id, start_time, end_time, shortname)
values (2, str_to_date('19:00','%H:%i'), str_to_date('07:00', '%H:%i'), 'Night');


insert into sides(side_id, side_name, start_date)
values (1, 'Side A', '2015-08-04');

insert into sides(side_id, side_name, start_date)
values (2, 'Side B', '2015-08-11');


insert into users(userid, username, password)
values
	(1, "appuser1", "secret1"),
	(2, "appuser2", "secret2"),
	(3, "appuser99", "secret99");