create sequence hibernate_sequence start 2 increment 1;

create table appeal (
id int8 not null,
addtimestamp timestamp,
donetimestamp timestamp,
response varchar(2048),
status varchar(255),
text varchar(2048),
user_id int8,
primary key (id)
);

create table user_role (
user_id int8 not null,
roles varchar(255)
);

create table users (
id int8 not null,
activation_code varchar(255),
active boolean not null,
mail varchar(255) not null,
password varchar(255),
username varchar(255),
primary key (id)
);

alter table if exists users
add constraint UK_r43af9ap4edm43mmtq01oddj6
unique (username);

alter table if exists appeal
add constraint FKn1an9lq9scvn1qjbt3uwlj1g8
foreign key (user_id) references users;

alter table if exists user_role
add constraint FKj345gk1bovqvfame88rcx7yyx
foreign key (user_id) references users;
