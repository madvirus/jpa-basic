create table jpabegin.hotel_info (
  hotel_id varchar(50) not null primary key,
  nm varchar(50),
  year int,
  grade varchar(2),
  created datetime,
  modified datetime
) engine innodb character set utf8mb4;