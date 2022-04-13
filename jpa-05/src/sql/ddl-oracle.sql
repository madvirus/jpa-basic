create table crm.activity_log
(
    id integer not null,
    user_id varchar2(50),
    activity_type varchar2(50),
    created DATE
);

ALTER TABLE crm.activity_log ADD CONSTRAINT al_idx01 PRIMARY KEY (id);

create sequence crm.activity_seq
start with 1 increment by 1 nomaxvalue;