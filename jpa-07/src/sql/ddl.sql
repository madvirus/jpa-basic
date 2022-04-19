create table jpabegin.writer (
  id integer not null auto_increment primary key,
  name varchar(100)
) engine innodb character set utf8mb4;

create table jpabegin.writer_intro (
  writer_id integer not null primary key,
  content_type varchar(50),
  content varchar(4000)
) engine innodb character set utf8mb4;

create table jpabegin.writer_address (
  writer_id integer not null primary key,
  addr1 varchar(100),
  addr2 varchar(100),
  zipcode varchar(5)
) engine innodb character set utf8mb4;