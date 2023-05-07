create table cmd_hot_key
(
    id      INTEGER not null primary key autoincrement,
    cmd     VARCHAR(64),
    row     INTEGER not null,
    hot_key VARCHAR(16)
);

create unique index uni_cmd
    on cmd_hot_key (cmd);

create table load_record
(
    id          integer                            not null primary key autoincrement,
    pathname    varchar(512)                       not null,
    modify_time datetime default CURRENT_TIMESTAMP not null
);

