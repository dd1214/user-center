-- auto-generated definition
create table user
(
    userID        varchar(64) charset utf8           not null comment '用户 id'
        primary key,
    nickName      varchar(64) charset utf8mb4        null comment '用户昵称',
    avatar        varchar(128) charset utf8mb4       null comment '用户头像',
    userPassword  varchar(64) charset utf8mb4        null comment '密码',
    creationLevel varchar(64) charset utf8mb4        null comment '创作等级',
    introduction  varchar(64) charset utf8mb4        null comment '简介',
    collectCount  int      default 0                 null comment '点赞数',
    viewCount     int      default 0                 null comment '阅读量',
    userStatus    tinyint  default 0                 null comment '用户状态 0-正常 1-冻结',
    userRole      tinyint  default 0                 null comment '用户权限 0-用户 1-管理员',
    currentTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete      tinyint  default 0                 null comment '逻辑删除 0-正常 1-删除',
    userAccount   varchar(64)                        null comment '账号',
    constraint user_userID_uindex
        unique (userID)
)
    comment '用户';

