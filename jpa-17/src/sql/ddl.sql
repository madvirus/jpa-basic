create table jpabegin.team (
  id varchar(10) not null primary key,
  name varchar(50)
) engine innodb character set utf8mb4;

create table jpabegin.player (
  id varchar(10) not null primary key,
  team_id varchar(10),
  name varchar(50)
) engine innodb character set utf8mb4;
