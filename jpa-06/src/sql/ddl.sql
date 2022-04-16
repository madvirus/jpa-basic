create table jpabegin.hotel_info (
  hotel_id varchar(50) not null primary key,
  nm varchar(50),
  year int,
  grade varchar(2),
  addr1 varchar(100),
  addr2 varchar(100),
  zipcode varchar(5),
  created datetime,
  modified datetime
) engine innodb character set utf8mb4;

create table jpabegin.employee (
  id varchar(10) not null primary key,
  addr1 varchar(100),
  addr2 varchar(100),
  zipcode varchar(5),
  waddr1 varchar(100),
  waddr2 varchar(100),
  wzipcode varchar(5)
) engine innodb character set utf8mb4;