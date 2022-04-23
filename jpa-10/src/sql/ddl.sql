create table jpabegin.doc (
  id varchar(20) not null primary key,
  title varchar(200),
  content varchar(200),
) engine innodb character set utf8mb4;

create table jpabegin.doc_prop (
  doc_id varchar(20),
  name varchar(50),
  value varchar(100),
  enabled boolean,
  primary key (doc_id, name)
) engine innodb character set utf8mb4;
