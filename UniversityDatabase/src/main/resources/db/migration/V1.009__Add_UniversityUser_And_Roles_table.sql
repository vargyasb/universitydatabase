create table university_user (username varchar(255) not null, password varchar(255), primary key (username));
create table university_user_roles (university_user_username varchar(255) not null, roles varchar(255));
alter table if exists university_user_roles add constraint FKqs3ja2ryxwebnlmvf0qgwg0ph foreign key (university_user_username) references university_user;