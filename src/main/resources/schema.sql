drop table if exists users;
create table users(
id int auto_increment not null,
username varchar(255) not null,
phone int not null,
first_name varchar(255) not null,
last_name varchar(255) not null,
email varchar(255) not null,
primary key(id)

);