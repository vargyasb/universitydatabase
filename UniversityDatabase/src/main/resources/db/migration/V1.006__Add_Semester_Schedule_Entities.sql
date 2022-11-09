create table schedule (id int4 not null, day_of_week int4, time time, course_id int4, primary key (id));
create table schedule_aud (id int4 not null, rev int4 not null, revtype int2, day_of_week int4, time time, course_id int4, primary key (id, rev));
create table semester (id int4 not null, duration int4 not null, name varchar(255), start_date date, primary key (id));
create table semester_aud (id int4 not null, rev int4 not null, revtype int2, duration int4, name varchar(255), start_date date, primary key (id, rev));
alter table if exists schedule add constraint FK_course_id foreign key (course_id) references course;
alter table if exists schedule_aud add constraint FK_rev foreign key (rev) references revinfo;
alter table if exists semester_aud add constraint FK_rev foreign key (rev) references revinfo;