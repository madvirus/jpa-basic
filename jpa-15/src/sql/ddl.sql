create table jpabegin.sight (
  id varchar(50) not null primary key,
  name varchar(50)
) engine innodb character set utf8mb4;

create table jpabegin.sight_review (
  id int not null auto_increment primary key,
  sight_id varchar(50) not null,
  grade int,
  comment varchar(200)
) engine innodb character set utf8mb4;
