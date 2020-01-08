drop database if exists campus_card;
drop user if exists campus_card;
create database campus_card character set utf8 collate utf8_general_ci;
create user campus_card identified by 'campus_card';
grant all privileges on campus_card.* to campus_card@'%';
use campus_card;

create table if not exists DType
(
  DTypeId int auto_increment comment '单位类型ID'
    primary key,
  DType   text null comment '单位类型'
)
  comment '单位类型表' engine = InnoDB;

create table if not exists card
(
  id          int auto_increment
    primary key comment '卡ID',
  type        int                                       not null comment '卡类型',
  user_id     int                                       not null comment '用户id',
  enabled     tinyint(1)              default '1'       null comment '卡是否被挂失或冻结',
  balance     decimal(65, 2) unsigned default '0.00'    null comment '余额',
  daily_limit decimal(65, 2) unsigned default '1000.00' null comment '每日上限'
)
  comment '卡表' engine = InnoDB;

create table if not exists card_type
(
  id   int auto_increment
    primary key comment '卡类型ID',
  name varchar(20) not null comment '卡类型名称',
  constraint name
  unique (name)
)
  comment '卡类型表' engine = InnoDB;

create table if not exists department
(
  id      int auto_increment
    primary key comment '单位ID',
  name    text not null comment '单位名称',
  DLink   text null comment '单位联系方式',
  DTypeID int  null comment '单位类型ID'
)
  comment '单位表' engine = InnoDB;

create table if not exists pos
(
  id      int auto_increment
    primary key comment '刷卡机ID',
  name    text not null comment '刷卡机名称',
  Did     int  not null comment '单位ID',
  Address text null comment '刷卡机地点',
  deleted bool default false comment '标记删除'
)
  comment 'pos机表' engine = InnoDB;

create table if not exists schedule
(
  date    date not null comment '排班时间',
  user_id int  not null comment '用户ID',
  primary key (date, user_id)
)
  comment '人员排班表' engine = InnoDB;

create table if not exists transaction
(
  id           int auto_increment
    primary key comment '流水ID',
  from_card_id int                                 null comment '转出的卡',
  to_card_id   int                                 null comment '转入的卡',
  amount       decimal(65, 2)                      not null comment '交易的金额数量',
  create_at    timestamp default CURRENT_TIMESTAMP not null comment '交易时间',
  pos_id       int                                 null comment 'pos机ID'
)
  comment '交易流水表' engine = InnoDB;

create table if not exists user
(
  id            int auto_increment
    primary key comment '用户ID',
  type          int  not null comment '用户类型',
  username      text not null comment '用户名',
  password      text not null comment '密码',
  department_id int  null comment '单位ID',
  job_title     text null comment '职称',
  gender        text null comment '性别',
  tel_number    text null comment '手机号码'
) comment '用户表' engine = InnoDB;

create table if not exists user_type
(
  id   int auto_increment
    primary key comment '类型ID',
  name varchar(20) not null,
  constraint name
  unique (name) comment '类型名称'
) comment '用户类型' engine = InnoDB;

create or replace view card_view as
  select `campus_card`.`card`.`id`          AS `id`,
         `campus_card`.`card`.`user_id`     AS `user_id`,
         `campus_card`.`card`.`type`        AS `type`,
         `campus_card`.`card_type`.`name`   AS `type_name`,
         `campus_card`.`card`.`enabled`     AS `enabled`,
         `campus_card`.`card`.`balance`     AS `balance`,
         `campus_card`.`card`.`daily_limit` AS `daily_limit`
  from (`campus_card`.`card`
    left join `campus_card`.`card_type` on ((`campus_card`.`card`.`type` = `campus_card`.`card_type`.`id`)));

-- comment on view card_view not supported: VIEW

create or replace view transaction_view as
  select `campus_card`.`transaction`.`id`           AS `id`,
         `campus_card`.`transaction`.`from_card_id` AS `from_card_id`,
         `campus_card`.`transaction`.`to_card_id`   AS `to_card_id`,
         `campus_card`.`transaction`.`amount`       AS `amount`,
         `campus_card`.`transaction`.`create_at`    AS `create_at`,
         `campus_card`.`pos`.`name`                 AS `pos_name`,
         `campus_card`.`pos`.id                     AS `pos_id`
  from (`campus_card`.`transaction`
    left join `campus_card`.`pos` on ((`campus_card`.`pos`.`id` = `campus_card`.`transaction`.`pos_id`)));

-- comment on view transaction_view not supported: VIEW

create or replace view user_view as
  select `campus_card`.`user`.`id`            AS `id`,
         `campus_card`.`user`.`type`          AS `type`,
         `campus_card`.`user`.`username`      AS `username`,
         `campus_card`.`user`.`password`      AS `password`,
         `campus_card`.`user_type`.`name`     AS `type_name`,
         `campus_card`.`user`.`gender`        AS `gender`,
         `campus_card`.`department`.`name`    AS `department`,
         `campus_card`.`user`.`job_title`     AS `job_title`,
         `campus_card`.`user`.`tel_number`    AS `tel_number`,
         `campus_card`.`user`.`department_id` AS `department_id`
  from ((`campus_card`.`user` left join `campus_card`.`user_type` on ((`campus_card`.`user`.`type` = `campus_card`.`user_type`.`id`)))
    left join `campus_card`.`department`
      on ((`campus_card`.`department`.`id` = `campus_card`.`user`.`department_id`)));

-- comment on column user_view.id not supported: 用户ID

-- comment on column user_view.type not supported: 用户类型

-- comment on column user_view.username not supported: 用户名

-- comment on column user_view.password not supported: 密码

-- comment on column user_view.gender not supported: 性别

-- comment on column user_view.department not supported: 单位名称

-- comment on column user_view.job_title not supported: 职称

-- comment on column user_view.tel_number not supported: 手机号码

-- comment on column user_view.department_id not supported: 单位ID


INSERT INTO campus_card.card (id, type, user_id, enabled, balance, daily_limit)
VALUES (1, 1, 1, 0, 5000.00, 1000.00);
INSERT INTO campus_card.card (id, type, user_id, enabled, balance, daily_limit)
VALUES (2, 1, 2, 0, 5000.00, 1000.00);
INSERT INTO campus_card.card (id, type, user_id, enabled, balance, daily_limit)
VALUES (3, 1, 3, 0, 5000.00, 1000.00);
INSERT INTO campus_card.card (id, type, user_id, enabled, balance, daily_limit)
VALUES (4, 1, 4, 1, 5000.00, 1000.00);
INSERT INTO campus_card.card (id, type, user_id, enabled, balance, daily_limit)
VALUES (5, 1, 1, 1, 5000.00, 1000.00);
INSERT INTO campus_card.card (id, type, user_id, enabled, balance, daily_limit)
VALUES (6, 1, 1, 1, 5000.00, 1000.00);
INSERT INTO campus_card.card (id, type, user_id, enabled, balance, daily_limit)
VALUES (7, 1, 1, 1, 5000.00, 1000.00);
INSERT INTO campus_card.card (id, type, user_id, enabled, balance, daily_limit)
VALUES (8, 1, 4, 1, 5000.00, 1000.00);
INSERT INTO campus_card.card (id, type, user_id, enabled, balance, daily_limit)
VALUES (9, 1, 1, 1, 5000.00, 1000.00);
INSERT INTO campus_card.card (id, type, user_id, enabled, balance, daily_limit)
VALUES (10, 2, 1, 1, 5000.00, 1000.00);
INSERT INTO campus_card.card_type (id, name)
VALUES (1, '正式卡');
INSERT INTO campus_card.card_type (id, name)
VALUES (2, '临时卡');
INSERT INTO campus_card.department (id, name, DLink, DTypeID)
VALUES (1, '车队', 'che@nchu.edu.cn', 1);
INSERT INTO campus_card.department (id, name, DLink, DTypeID)
VALUES (2, '食堂', 'tang@nchu.edu.cn', 2);
INSERT INTO campus_card.department (id, name, DLink, DTypeID)
VALUES (3, '售电中心', 'dian@nchu.edu.cn', 3);
INSERT INTO campus_card.DType (DTypeId, DType)
VALUES (1, '外包');
INSERT INTO campus_card.DType (DTypeId, DType)
VALUES (2, '合作');
INSERT INTO campus_card.DType (DTypeId, DType)
VALUES (3, '国企');
INSERT INTO campus_card.pos (id, name, Did, Address)
VALUES (1, '车队-1-1', 1, '车队-1-1');
INSERT INTO campus_card.pos (id, name, Did, Address)
VALUES (2, '食堂-2-1', 2, '食堂-2-1');
INSERT INTO campus_card.pos (id, name, Did, Address)
VALUES (3, '食堂-3-1', 2, '食堂-3-1');
INSERT INTO campus_card.pos (id, name, Did, Address)
VALUES (4, '售电中心-4-1', 3, '售电中心-4-1');
INSERT INTO campus_card.pos (id, name, Did, Address)
VALUES (5, '售电中心-4-2', 3, '售电中心-4-2');
INSERT INTO campus_card.transaction (id, from_card_id, to_card_id, amount, create_at, pos_id)
VALUES (1, 1, 0, 100.00, '2020-01-03 22:59:25', 1);
INSERT INTO campus_card.transaction (id, from_card_id, to_card_id, amount, create_at, pos_id)
VALUES (2, 1, 0, 1001.00, '2019-12-29 23:42:53', 2);
INSERT INTO campus_card.transaction (id, from_card_id, to_card_id, amount, create_at, pos_id)
VALUES (3, 4, 0, 50.00, '2019-12-29 23:07:36', 3);
INSERT INTO campus_card.transaction (id, from_card_id, to_card_id, amount, create_at, pos_id)
VALUES (4, 4, 0, 50.00, '2019-12-29 23:05:21', 4);
INSERT INTO campus_card.transaction (id, from_card_id, to_card_id, amount, create_at, pos_id)
VALUES (5, 4, 0, 100.00, '2020-01-08 06:16:13', 5);
INSERT INTO campus_card.transaction (id, from_card_id, to_card_id, amount, create_at, pos_id)
VALUES (6, 1, 0, 100.00, '2020-01-08 06:16:42', 5);
INSERT INTO campus_card.user (id, type, username, password, department_id, job_title, gender, tel_number)
VALUES (1, 1, 'student1', '123', 0, '学生', 'male', '13556422253');
INSERT INTO campus_card.user (id, type, username, password, department_id, job_title, gender, tel_number)
VALUES (2, 1, 'student2', '123', 0, '学生', 'female', '13556423253');
INSERT INTO campus_card.user (id, type, username, password, department_id, job_title, gender, tel_number)
VALUES (3, 2, 'admin', '123', 2, '信息中心主任', 'male', '13556429856');
INSERT INTO campus_card.user (id, type, username, password, department_id, job_title, gender, tel_number)
VALUES (4, 3, 'teacher', '123', 3, '职员', 'female', '135564266953');
INSERT INTO campus_card.schedule (date, user_id)
VALUES ('2020-01-15', 4);
INSERT INTO campus_card.user_type (id, name)
VALUES (1, '学生');
INSERT INTO campus_card.user_type (id, name)
VALUES (3, '教职工');
INSERT INTO campus_card.user_type (id, name)
VALUES (2, '管理员');
