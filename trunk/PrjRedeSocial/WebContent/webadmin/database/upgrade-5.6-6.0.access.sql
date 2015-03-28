alter table usagelog add datetimefull varchar(20)

update usagelog set datetimefull = datetime

drop index u_datetime_idx on usagelog

alter table usagelog drop column [datetime]

create index u_datetime_idx on usagelog (datetimefull)

alter table usagelog alter column clientbrowser varchar(50)

alter table content add metainfo memo

alter table content add product_info memo

alter table content_archive add metainfo memo

alter table content_archive add product_info memo

alter table content_public add metainfo memo

alter table content_public add product_info memo

create index c_keywords_idx on content (keywords)

create index c_description_idx on content (description)

create index c_metainfo_idx on content (metainfo)

create index c_product_info_idx on content (product_info)

create index ca_keywords_idx on content_archive (keywords)

create index ca_description_idx on content_archive (description)

create index ca_metainfo_idx on content_archive (metainfo)

create index ca_product_info_idx on content_archive (product_info)

create index cp_keywords_idx on content_public (keywords)

create index cp_description_idx on content_public (description)

create index cp_metainfo_idx on content_public (metainfo)

create index cp_product_info_idx on content_public (product_info)

alter table websites add language memo

alter table websites add remote varchar(255)

alter table usergroups add login_page memo

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

alter table usertypes add login_page memo

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
	id counter,
	locked integer,
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

create index u_ug_username_idx on users_usergroups(username)

create index u_ug_usergroup_idx on users_usergroups(usergroup)

insert into users_usergroups (username,usergroup) select distinct users.username, usergroups2.subgroup from users,usergroups2 where users.usergroup=usergroups2.usergroup

create table users_usertypes (
	id counter,
	locked integer,
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

create index u_ut_username_idx on users_usertypes(username)

create index u_ut_usertype_idx on users_usertypes(usertype)

insert into users_usertypes (username,usertype) select distinct users.username, usertypes2.subtype from users,usertypes2 where users.usertype=usertypes2.usertype

create table permissions (
	id counter,
	action varchar(50),
	resource varchar(250),
	username varchar(50),
	userclass varchar(50),
	usergroup varchar(50),
	usertype varchar(50),
	primary key (id)
)

create index perm_action_idx on permissions(action)

create index perm_resource_idx on permissions(resource)

create index perm_username_idx on permissions(username)

create index perm_userclass_idx on permissions(userclass)

create index perm_usergroup_idx on permissions(usergroup)

create index perm_usertype_idx on permissions(usertype)

update config set configvalue='6.0' where configname='database_version'
