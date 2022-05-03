create table jpabegin.user (
  email varchar(50) not null primary key,
  name varchar(50),
  create_date datetime
) engine innodb character set utf8mb4;

create table jpabegin.membership_card (
  card_no varchar(50) not null primary key,
  user_email varchar(50),
  expiry_date date,
  enabled boolean
) engine innodb character set utf8mb4;

create table jpabegin.best_pick (
  user_email varchar(50) not null primary key,
  title varchar(100)
) engine innodb character set utf8mb4;
