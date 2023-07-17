USE Application;
CREATE TABLE user(
         id bigint not null auto_increment,
         email varchar(50) not null,
         username varchar(24) not null,
         password varchar(100) not null,
         enabled boolean default 0 not null,
         account_nonexpired boolean default 1 not null,
         account_nonlocked boolean default 1 not null,
         credentials_nonexpired boolean default 1 not null,
         PRIMARY KEY (id)
);
CREATE TABLE user_role(
       userID bigint not null,
       roleID bigint not null,
       foreign key (userID) references user (id),
       foreign key (roleID) references role (id)
);
CREATE TABLE role(
       id bigint not null,
       name varchar(10) not null,
       PRIMARY KEY (id)
);
CREATE TABLE tokens(
    id     bigint  not null auto_increment,
    tokens varchar(128) null,
    expired_date date null,
    constraint id
        foreign key (id) references user (id)
);
CREATE TABLE role_privilege
(
    roleID      bigint not null,
    privilegeID bigint not null,

    foreign key (privilegeID) references privilege (id),
    foreign key (roleID) references role (id)
);
CREATE TABLE privilege
(
    id   bigint not null auto_increment,
    name varchar(24) not null,
    PRIMARY KEY (id)
);
CREATE TABLE attempts(
     username   varchar(24) not null,
     attempts int default 0 not null,
     locked_time date null
);
USE Application;
CREATE TABLE student(
                     id INTEGER PRIMARY KEY AUTO_INCREMENT,
                     username varchar(24) NOT NULL,
                     age INTEGER NOT NULL,
                     update_time timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP);
DROP TRIGGER IF EXISTS updateTrigger;
CREATE TRIGGER updateTrigger
    BEFORE
        UPDATE ON student FOR EACH ROW
BEGIN
    SET NEW.update_time=NOW();
END;
INSERT INTO student (username,age) VALUES ('Tom', 18);
INSERT INTO student (username,age) VALUES ('Jessica', 36);
INSERT INTO student (username,age) VALUES ('Jack', 20);
INSERT INTO student (username,age) VALUES ('Catherine', 10);

