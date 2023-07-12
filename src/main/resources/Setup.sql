USE Application;
CREATE TABLE USER(
         id int not null,
         email varchar(50) not null,
         username varchar(24) not null,
         password varchar(100) not null,
         enabled boolean default 0 not null,
         PRIMARY KEY (id)
);
CREATE TABLE USER_ROLE(
       userID int not null,
       roleID int not null
);
CREATE TABLE ROLE(
       id int not null,
       name varchar(10) not null,
       PRIMARY KEY (id)
);
CREATE TABLE TOKENS
(
    id     int  not null,
    tokens varchar(20) null,
    constraint id
        foreign key (id) references USER (id)
);
