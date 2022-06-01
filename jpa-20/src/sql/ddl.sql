create table jpabegin.notice (
  notice_id integer not null auto_increment primary key,
  title varchar(200) not null,
  content varchar(200) not null,
  open_yn char(1),
  cat varchar(10)
) engine innodb character set utf8mb4;

create table jpabegin.category (
  cat_id varchar(10) not null primary key,
  name varchar(20) not null
) engine innodb character set utf8mb4;

create table jpabegin.article (
  article_id integer not null auto_increment primary key,
  title varchar(200),
  writer_id integer,
  written_at datetime,
  content mediumtext
) engine innodb character set utf8mb4;

create table jpabegin.writer (
  id integer not null auto_increment primary key,
  name varchar(100)
) engine innodb character set utf8mb4;
