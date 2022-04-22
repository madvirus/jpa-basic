create table jpabegin.role (
  id varchar(20) not null primary key,
  name varchar(100)
) engine innodb character set utf8mb4;

create table jpabegin.role_perm (
  role_id varchar(20),
  perm varchar(50),
  grantor varchar(50),
  primary key (role_id, perm)
) engine innodb character set utf8mb4;
