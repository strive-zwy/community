create table likeLog
(
    id bigint auto_increment,
    liker bigint not null,
    outerId bigint not null,
    type int not null,
    gmt_create bigint not null,
    status int default 1 not null,
    constraint likeLog_pk
        primary key (id)
);
