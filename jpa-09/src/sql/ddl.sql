create table jpabegin.question (
  id varchar(20) not null primary key,
  text varchar(200)
) engine innodb character set utf8mb4;

create table jpabegin.question_choice (
  question_id varchar(20),
  idx int,
  text varchar(50),
  input boolean,
  primary key (question_id, text)
) engine innodb character set utf8mb4;
