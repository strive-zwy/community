create table comment
(
    id bigint not null AUTO_INCREMENT,
    parent_id bigint not null comment '父类Id',
    type int null comment '父类类型',
    commentator int not null comment '评论人Id',
    gmt_create bigint not null comment '评论创建时间',
    gmt_modified bigint not null comment '评论更新时间',
    like_count bigint default 0 null comment '点赞数',
    constraint comment_pk
        primary key (id)
);

