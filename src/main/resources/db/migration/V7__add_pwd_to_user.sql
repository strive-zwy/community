alter table user
    add password varchar(200) default '1',
    add loginType int default 0 null comment '登录类型 0普通，1 GitHub';

