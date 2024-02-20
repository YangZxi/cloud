CREATE TABLE bucket (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(20) NULL,
    zh_name VARCHAR(20) NULL,
    path VARCHAR(255) NULL,
    user_id INTEGER NULL,
    type VARCHAR(10) NULL,
    update_time DATETIME NULL,
    create_time DATETIME NULL
);

CREATE TABLE chunk (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    hash CHAR(32) NOT NULL,
    file_hash CHAR(32) NOT NULL,
    "order" INTEGER NOT NULL,
    total INTEGER DEFAULT 0 NOT NULL,
    size BIGINT NULL,
    user_id INTEGER NULL,
    create_time DATETIME NULL,
    type INTEGER NOT NULL
);

CREATE TABLE menu (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(20) NULL,
    type INTEGER DEFAULT 1 NULL,
    parent_menu_id INTEGER NULL,
    icon VARCHAR(255) NULL,
    "order" INTEGER NULL,
    path VARCHAR(50) NULL,
    component VARCHAR(20) NULL,
    permission VARCHAR(30) NULL,
    status INTEGER DEFAULT 1 NULL,
    update_time DATETIME NULL,
    create_time DATETIME NULL,
    CONSTRAINT permission_unique UNIQUE (permission),
    CONSTRAINT self_parent_menu FOREIGN KEY (parent_menu_id) REFERENCES menu (id)
);

CREATE INDEX parent_menu_id_fk ON menu (parent_menu_id);

CREATE TABLE properties (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    prop_key VARCHAR(255) NULL,
    prop_value VARCHAR(255) NULL,
    type VARCHAR(10) NULL,
    create_time DATETIME NULL,
    CONSTRAINT prop_key_unique UNIQUE (prop_key)
);

CREATE TABLE resource (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255) NOT NULL,
    path VARCHAR(255) NULL,
    hash VARCHAR(32) NULL,
    size BIGINT NULL,
    type VARCHAR(10) NULL,
    parent_id INTEGER DEFAULT 0 NULL,
    user_id INTEGER NULL,
    bucket_id INTEGER NOT NULL,
    ref_id INTEGER NULL,
    update_time DATETIME NULL,
    create_time DATETIME NULL,
    cdn VARCHAR(255) NULL
);

CREATE INDEX idx_name_user_bucket ON resource (name, user_id, bucket_id);

CREATE TABLE role (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(30) NOT NULL,
    name_zh VARCHAR(30) NULL,
    "desc" VARCHAR(255) NULL,
    status INTEGER DEFAULT 1 NULL,
    update_time DATETIME NULL,
    create_time DATETIME NULL
);

CREATE TABLE role_menu (
    role_id INTEGER NULL,
    menu_id INTEGER NULL,
    CONSTRAINT menu_fk FOREIGN KEY (menu_id) REFERENCES menu (id),
    CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE share (
    auto_id INTEGER PRIMARY KEY AUTOINCREMENT,
    id CHAR(32) NOT NULL,
    short_code VARCHAR(6) NULL,
    deadline DATETIME NULL,
    password CHAR(4) NULL,
    view_count INTEGER DEFAULT 0 NULL,
    download_count INTEGER DEFAULT 0 NULL,
    resource_ids VARCHAR(255) NULL,
    user_id INTEGER NULL,
    create_time DATETIME NULL
);

CREATE TABLE user (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    uuid VARCHAR(40) DEFAULT '' NOT NULL,
    username VARCHAR(20) NOT NULL,
    nickname VARCHAR(20) NULL,
    password VARCHAR(60) NOT NULL,
    email VARCHAR(32) NULL,
    gender CHAR(2) NULL,
    age INTEGER NULL,
    avatar VARCHAR(255) NULL,
    status INTEGER DEFAULT 1 NULL,
    update_time DATETIME NULL,
    create_time DATETIME NULL,
    CONSTRAINT username_unique UNIQUE (username)
);

CREATE TABLE ban (
    user_id INTEGER NOT NULL PRIMARY KEY,
    role_id INTEGER NULL,
    menu_id INTEGER NULL,
    CONSTRAINT ban_menu_id_fk FOREIGN KEY (menu_id) REFERENCES menu (id),
    CONSTRAINT ban_role_id_fk FOREIGN KEY (role_id) REFERENCES role (id),
    CONSTRAINT ban_user_id_fk FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE log (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title VARCHAR(255) NULL,
    error TEXT NULL,
    content TEXT NULL,
    type VARCHAR(10) NULL,
    method VARCHAR(255) NULL,
    user_id INTEGER NULL,
    time INTEGER NULL,
    ip VARCHAR(30) NULL,
    create_time DATETIME NULL,
    CONSTRAINT log_user_Id_fk FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE task (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255) NULL,
    cron VARCHAR(255) NULL,
    "desc" VARCHAR(255) NULL,
    class_name VARCHAR(255) NULL,
    method_name VARCHAR(255) NULL,
    user_id INTEGER NULL,
    status INTEGER NULL,
    update_time DATETIME NULL,
    create_time DATETIME NULL,
    CONSTRAINT task_user_id_fk FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE user_open (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NULL,
    open_id VARCHAR(32) NULL,
    type VARCHAR(10) NULL,
    nickname VARCHAR(255) NULL,
    avatar VARCHAR(255) NULL,
    status INTEGER DEFAULT 1 NULL,
    update_time DATETIME NULL,
    create_time DATETIME NULL,
    CONSTRAINT open_userId_fk FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE user_role (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES role (id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user (id)
);
