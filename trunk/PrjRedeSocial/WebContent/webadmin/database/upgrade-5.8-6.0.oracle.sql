alter table usagelog rename column datetime to datetimefull

alter table usagelog modify clientbrowser varchar(50)

alter table content add metainfo clob

alter table content add product_info clob

alter table content_archive add metainfo clob

alter table content_archive add product_info clob

alter table content_public add metainfo clob

alter table content_public add product_info clob

alter table websites add language varchar(255)

alter table usergroups add login_page varchar(255)

alter table usergroups add users_type varchar(255)

alter table usergroups add users_group varchar(255)

alter table usergroups add creators_type varchar(255)

alter table usergroups add creators_group varchar(255)

alter table usergroups add editors_type varchar(255)

alter table usergroups add editors_group varchar(255)

alter table usergroups add publishers_type varchar(255)

alter table usergroups add publishers_group varchar(255)

alter table usergroups add administrators_type varchar(255)

alter table usergroups add administrators_group varchar(255)

alter table usergroups add subscribers_type varchar(255)

alter table usergroups add subscribers_group varchar(255)

alter table usertypes add login_page varchar(255)

alter table usertypes add users_type varchar(255)

alter table usertypes add users_group varchar(255)

alter table usertypes add creators_type varchar(255)

alter table usertypes add creators_group varchar(255)

alter table usertypes add editors_type varchar(255)

alter table usertypes add editors_group varchar(255)

alter table usertypes add publishers_type varchar(255)

alter table usertypes add publishers_group varchar(255)

alter table usertypes add administrators_type varchar(255)

alter table usertypes add administrators_group varchar(255)

alter table usertypes add subscribers_type varchar(255)

alter table usertypes add subscribers_group varchar(255)

create table users_usergroups (
	id int not null,
	locked int,
	created varchar(20),
	updated varchar(20),
	created_by varchar(255),
	updated_by varchar(255),
	scheduled_publish varchar(20),
	scheduled_unpublish varchar(20),
	username varchar(255),
	usergroup varchar(255),
	primary key (id)
)

create sequence users_usergroups_seq

create or replace trigger users_usergroups_bir before insert on users_usergroups referencing old as old new as new for each row
begin
 select users_usergroups_seq.nextval into :new.id from dual;
end;
/

create index u_ug_username_idx on users_usergroups(username)

create index u_ug_usergroup_idx on users_usergroups(usergroup)

insert into users_usergroups (username,usergroup) select distinct users.username, usergroups2.subgroup from users,usergroups2 where users.usergroup=usergroups2.usergroup

create table users_usertypes (
	id int not null,
	locked int,
	created varchar(20),
	updated varchar(20),
	created_by varchar(255),
	updated_by varchar(255),
	scheduled_publish varchar(20),
	scheduled_unpublish varchar(20),
	username varchar(255),
	usertype varchar(255),
	primary key (id)
)

create sequence users_usertypes_seq

create or replace trigger users_usertypes_bir before insert on users_usertypes referencing old as old new as new for each row
begin
 select users_usertypes_seq.nextval into :new.id from dual;
end;
/

create index u_ut_username_idx on users_usertypes(username)

create index u_ut_usertype_idx on users_usertypes(usertype)

insert into users_usertypes (username,usertype) select distinct users.username, usertypes2.subtype from users,usertypes2 where users.usertype=usertypes2.usertype

create table permissions (
	id int,
	action varchar(50),
	"resource" varchar(250),
	username varchar(50),
	userclass varchar(50),
	usergroup varchar(50),
	usertype varchar(50),
	primary key (id)
)

create sequence permissions_seq

create or replace trigger permissions_bir before insert on permissions referencing old as old new as new for each row
begin
 select permissions_seq.nextval into :new.id from dual;
end;
/

create index perm_action_idx on permissions(action)

create index perm_resource_idx on permissions("resource")

create index perm_username_idx on permissions(username)

create index perm_userclass_idx on permissions(userclass)

create index perm_usergroup_idx on permissions(usergroup)

create index perm_usertype_idx on permissions(usertype)

update config set configvalue='6.0' where configname='database_version'
