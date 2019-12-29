drop database if exists campus_card;
drop user if exists campus_card;
create database campus_card character set utf8 collate utf8_general_ci;
create user campus_card identified by 'campus_card';
grant all privileges on campus_card.* to campus_card@'%';
use campus_card;

create table user (
    id int primary key auto_increment,
    type int not null,
    username text not null,
    password text not null
);

create table card (
    id int primary key auto_increment,
    type int not null,
    user_id int not null,
    enabled bool default true,
    balance decimal(65,2) default 0,
    daily_limit decimal(65,2) default 1000
);

create table transaction (
    id int primary key auto_increment,
    from_card_id int,
    to_card_id int,
    amount decimal(65,2) not null,
    create_at timestamp default now()
);

create table user_type (
    id int primary key auto_increment,
    name varchar(20) not null unique
);

create table card_type (
    id int primary key auto_increment,
    name varchar(20) not null unique
);

insert into user_type (name) values ('学生'), ('管理员'), ('老师');

create view user_view as
    select user.id, type, username, password, name as type_name from user left join user_type on type = user_type.id;

create view card_view as
    select card.id, user_id, type, name as type_name, enabled, balance, daily_limit from card left join card_type on type = card_type.id;

insert into user (type, username, password) VALUES (1,'student1','123');
insert into user (type, username, password) VALUES (1,'student2','123');
insert into user (type, username, password) VALUES (2,'admin','123');
insert into user (type, username, password) VALUES (3,'teacher','123');

insert into card (type, user_id) VALUES (1,1);
insert into card (type, user_id) VALUES (1,2);
insert into card (type, user_id) VALUES (1,3);
insert into card (type, user_id) VALUES (1,4);

insert into transaction (from_card_id, to_card_id, amount) VALUES (0,2,50);
insert into transaction (from_card_id, to_card_id, amount) VALUES (1,3,50);

insert into card_type (id, name) VALUES (1,'正式卡');
