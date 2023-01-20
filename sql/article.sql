-- auto-generated definition
create table article
(
    articleID       varchar(64)                        not null comment '文章id'
        primary key,
    createUser      varchar(64)                        null comment '创建人',
    snapshoot       varchar(64)                        null comment '快照',
    title           varchar(64)                        null comment '文章题目',
    preview         varchar(64)                        null comment '预览',
    content         varchar(2048)                      null comment '文章内容',
    label           varchar(256)                       null comment '标签',
    likesNumber     int      default 0                 null comment '点赞数',
    readingQuantity int      default 0                 null comment '阅读量',
    commentCount    int      default 0                 null comment '评论数',
    category        varchar(64)                        null comment '分类',
    articleStatus   tinyint  default 0                 null comment '文章状态 0-未发布 1-正常',
    currentTime     datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete        tinyint  default 0                 null comment '逻辑删除 0-正常 1-异常',
    constraint article_articleID_uindex
        unique (articleID)
);

