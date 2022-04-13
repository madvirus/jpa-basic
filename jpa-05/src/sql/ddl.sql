create table jpabegin.hotel_info (
  hotel_id varchar(50) not null primary key,
  nm varchar(50),
  year int,
  grade varchar(2),
  created datetime,
  modified datetime
) engine innodb character set utf8mb4;

create table jpabegin.review (
  review_id integer not null auto_increment,
  hotel_id varchar(50) not null,
  mark integer,
  writer_name varchar(20),
  `comment` text,
  created datetime
) engine innodb character set utf8mb4;

create table jpabegin.access_log (
  id integer not null,
  path varchar(200),
  accessed datetime
) engine innodb character set utf8mb4;

create table id_seq (
    entity varchar(100) not null primary key,
    nextval bigint
) engine innodb character set utf8mb4;