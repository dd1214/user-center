-- auto-generated definition
create table article
(
    articleID     varchar(64) charset utf8           not null comment '文章id'
        primary key,
    author        varchar(64) charset utf8           null comment '创建人',
    snapshot      varchar(128) charset utf8          null comment '快照',
    title         varchar(128) charset utf8mb4       null comment '文章题目',
    preview       varchar(2048) charset utf8mb4      null comment '预览',
    content       text charset utf8mb4               null comment '文章内容',
    label         varchar(256) charset utf8          null comment '标签',
    collectCount  int      default 0                 null comment '点赞数',
    viewCount     int      default 0                 null comment '阅读量',
    commentCount  int      default 0                 null comment '评论数',
    category      varchar(64) charset utf8           null comment '分类',
    articleStatus tinyint  default 0                 null comment '文章状态 0-草稿箱 1-待审核 2-已发布',
    currentTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete      tinyint  default 0                 null comment '逻辑删除 0-正常 1-异常',
    constraint article_articleID_uindex
        unique (articleID)
);

