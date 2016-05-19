

insert into physicians (first_name, last_name, night_order, side_id)
values ('Jenny', 'Jones', 1, 1);

insert into physicians (first_name, last_name, night_order, side_id)
values ('Mary', 'Malone', 2, 1);

insert into physicians (first_name, last_name, night_order, side_id)
values ('Nick', 'Nelson', 3, 1);

insert into physicians (first_name, last_name, night_order, side_id)
values ('Steve', 'Smith', 4, 1);



insert into physicians (first_name, last_name, night_order, side_id)
values ('Paul', 'Pancer', 1, 2);

insert into physicians (first_name, last_name, night_order, side_id)
values ('Peter', 'Passley', 2, 2);

insert into physicians (first_name, last_name, night_order, side_id)
values ('Peggy', 'Peroni', 3, 2);

insert into physicians (first_name, last_name, night_order, side_id)
values ('Priscilla', 'Pichard', 4, 2);



insert into shifts (shift_id, start_time, end_time, shortname)
values (1, sec_to_time(7*3600), sec_to_time(19*3600), 'Day');

insert into shifts (shift_id, start_time, end_time, shortname)
values (2, sec_to_time(19*3600), sec_to_time(7:3600), 'Night');


insert into sides(side_id, side_name, start_date)
values (1, 'Side A', '2015-08-04');

insert into sides(side_id, side_name, start_date)
values (2, 'Side B', '2015-08-11');


-- password is secret99, encoded in SHA256

insert into users(userid, username, password)
values
	(1, "appuser99", "2550cd7fef492d4110956ffffb48210d8fd05f3854758f8883c640dca6999972");
