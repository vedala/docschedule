 
create table schedule (
	start_date		date not null,
	end_date		date not null,
	accepted_yn		char(1) not null
);

create table schedule_physicians (
	schedule_date				date 	not null,
	physician_id				int(3)	not null,
	shift_id					int(1)  not null,
	accepted_yn					char(1)	not null
);

create table shifts (
	shift_id		int(1)	not null,
	start_time		time,
	end_time		time,
	shortname		varchar(10)
);

create table physicians (
	physician_id		int(5)	not null auto_increment,
	first_name			varchar(30) not null,
	last_name			varchar(30) not null,
	night_order			int(2) not null,
	side_id				tinyint(1),
	primary key (physician_id)
);

alter table physicians auto_increment=1;

create table sides (
	side_id		tinyint(1) not null,
	side_name	varchar(100),
	start_date	date
);


create table users (
	userid		int(10) not null auto_increment,
	username	varchar(100),
	password	varchar(100),
    verified    tinyint default 0,
    primary key (userid)
);

alter table users auto_increment=1;
