create table jpabegin.team (
  id varchar(10) not null primary key,
  name varchar(50)
) engine innodb character set utf8mb4;

create table jpabegin.player (
  id varchar(10) not null primary key,
  team_id varchar(10),
  name varchar(50)
) engine innodb character set utf8mb4;

create table jpabegin.survey (
  id varchar(10) not null primary key,
  name varchar(50)
) engine innodb character set utf8mb4;

create table jpabegin.survey_question (
  id varchar(10) not null primary key,
  survey_id varchar(10),
  order_no int,
  title varchar(50)
) engine innodb character set utf8mb4;

create table jpabegin.game (
  id varchar(10) not null primary key,
  name varchar(50)
) engine innodb character set utf8mb4;

create table jpabegin.game_member (
  id varchar(10) not null primary key,
  name varchar(50),
  game_id varchar(10),
  role_name varchar(10)
) engine innodb character set utf8mb4;

