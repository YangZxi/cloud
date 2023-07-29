create table bucket
(
    id          int auto_increment
        primary key,
    name        varchar(20)  null,
    zh_name     varchar(20)  null,
    path        varchar(255) null,
    user_id     int          null,
    type        varchar(10)  null,
    update_time datetime     null,
    create_time datetime     null
)
    row_format = DYNAMIC;

create table chunk
(
    id          int auto_increment
        primary key,
    hash        char(32)      not null comment '当前分块hash',
    file_hash   char(32)      not null comment '完整文件hash',
    `order`     int           not null comment '分块顺序',
    total       int default 0 not null comment '分块总数',
    size        bigint        null comment '分块大小',
    user_id     int           null,
    create_time datetime      null,
    type        tinyint       not null comment '1下载，2上传'
)
    row_format = DYNAMIC;

create table menu
(
    id             int auto_increment
        primary key,
    name           varchar(20)       null comment '菜单名称',
    type           tinyint default 1 null comment '菜单类型',
    parent_menu_id int               null comment '父级菜单，如果为 0 则表示为顶级菜单',
    icon           varchar(255)      null comment '前端图标',
    `order`        tinyint           null comment '排序',
    path           varchar(50)       null comment '组件路径',
    component      varchar(20)       null comment '组件名称（文件名）',
    permission     varchar(30)       null comment '权限名',
    status         tinyint default 1 null comment '状态',
    update_time    datetime          null,
    create_time    datetime          null,
    constraint permission_unique
        unique (permission),
    constraint self_parent_menu
        foreign key (parent_menu_id) references menu (id)
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

create index parent_menu_id_fk
    on menu (parent_menu_id);

create table properties
(
    id          int auto_increment
        primary key,
    prop_key    varchar(255) null comment '配置名',
    prop_value  varchar(255) null comment '配置值',
    type        varchar(10)  null comment '配置类型',
    create_time datetime     null,
    constraint prop_key_unique
        unique (prop_key)
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

create table resource
(
    id          bigint auto_increment
        primary key,
    name        varchar(255)     not null comment '文件名',
    path        varchar(255)     null comment '文件存放路径',
    hash        varchar(32)      null comment '文件Hash',
    size        bigint           null comment '文件大小',
    type        varchar(10)      null comment '文件类型',
    parent_id   bigint default 0 null comment '父级id',
    user_id     int              null,
    bucket_id   int              not null,
    ref_id      int              null,
    update_time datetime         null,
    create_time datetime         null,
    cdn         varchar(255)     null
)
    row_format = DYNAMIC;

create index idx_name_user_bucket
    on resource (name, user_id, bucket_id);

create table role
(
    id          int auto_increment
        primary key,
    name        varchar(30)       not null comment '角色名称',
    name_zh     varchar(30)       null comment '角色中文名称',
    `desc`      varchar(255)      null comment '描述',
    status      tinyint default 1 null comment '状态',
    update_time datetime          null,
    create_time datetime          null
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

create table role_menu
(
    role_id int null comment '角色id',
    menu_id int null comment '菜单id',
    constraint menu_fk
        foreign key (menu_id) references menu (id),
    constraint role_fk
        foreign key (role_id) references role (id)
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

create table share
(
    auto_id        int auto_increment
        primary key,
    id             char(32)      not null,
    short_code     varchar(6)    null,
    deadline       datetime      null comment '过期时间',
    password       char(4)       null,
    view_count     int default 0 null comment '浏览次数',
    download_count int default 0 null comment '下载次数',
    resource_ids   varchar(255)  null,
    user_id        int           null,
    create_time    datetime      null
)
    row_format = DYNAMIC;

create table user
(
    id          int auto_increment
        primary key,
    uuid        varchar(40) default '' not null comment '每次登录的uuid',
    username    varchar(20)            not null comment '用户名',
    nickname    varchar(20)            null comment '昵称或姓名',
    password    varchar(60)            not null comment '密码',
    email       varchar(32)            null comment '邮箱',
    gender      char(2)                null comment '性别',
    age         tinyint                null comment '年龄',
    avatar      varchar(255)           null comment '头像地址',
    status      tinyint     default 1  null comment '用户状态',
    update_time datetime               null,
    create_time datetime               null,
    constraint username_unique
        unique (username)
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

create table ban
(
    user_id int not null
        primary key,
    role_id int null,
    menu_id int null,
    constraint ban_menu_id_fk
        foreign key (menu_id) references menu (id),
    constraint ban_role_id_fk
        foreign key (role_id) references role (id),
    constraint ban_user_id_fk
        foreign key (user_id) references user (id)
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

create table log
(
    id          int auto_increment
        primary key,
    title       varchar(255) null comment '日志标题',
    error       text         null,
    content     text         null comment '日志内容',
    type        varchar(10)  null comment '日志类型',
    method      varchar(255) null comment '方法名称',
    user_id     int          null comment '用户id',
    time        int          null,
    ip          varchar(30)  null comment '请求地址',
    create_time datetime     null comment '创建时间',
    constraint log_user_Id_fk
        foreign key (user_id) references user (id)
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

create table task
(
    id          int auto_increment
        primary key,
    name        varchar(255) null comment '任务名称',
    cron        varchar(255) null comment 'cron表达式',
    `desc`      varchar(255) null comment '任务描述',
    class_name  varchar(255) null comment '全限定类名',
    method_name varchar(255) null comment '方法名',
    user_id     int          null comment '创建用户的id',
    status      tinyint      null comment '状态',
    update_time datetime     null,
    create_time datetime     null,
    constraint task_user_id_fk
        foreign key (user_id) references user (id)
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

create table user_open
(
    id          int auto_increment
        primary key,
    user_id     int               null comment '用户id',
    open_id     varchar(32)       null comment '快捷登录平台中的用户id',
    type        varchar(10)       null comment '快捷登录平台',
    nickname    varchar(255)      null comment '用户名称',
    avatar      varchar(255)      null comment '用户头像',
    status      tinyint default 1 null comment '状态',
    update_time datetime          null,
    create_time datetime          null,
    constraint open_userId_fk
        foreign key (user_id) references user (id)
)
    row_format = DYNAMIC;

create table user_role
(
    id      int auto_increment
        primary key,
    user_id int not null comment '用户id',
    role_id int not null comment '角色id',
    constraint role_id_fk
        foreign key (role_id) references role (id),
    constraint user_id_fk
        foreign key (user_id) references user (id)
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

create
    definer = cloud@`%` procedure selectByBucketAndFullPath(IN fullPath varchar(255))
begin
    SELECT * FROM resource WHERE path = fullPath;
end;

