alter table question modify id bigint auto_increment;
alter table user modify id bigint auto_increment;

alter table comment modify commentator bigint;
alter table question modify creator bigint;
