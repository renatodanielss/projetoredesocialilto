alter table content add requested_publish varchar(20)

alter table content add requested_unpublish varchar(20)

alter table content_archive add requested_publish varchar(20)

alter table content_archive add requested_unpublish varchar(20)

alter table content_public add requested_publish varchar(20)

alter table content_public add requested_unpublish varchar(20)

create table workflow (
	id int not null auto_increment,
	title varchar(255),
	action varchar(255),
	fromstate varchar(255),
	tostate varchar(255),
	usertype text,
	usergroup text,
	contentclass text,
	contenttype text,
	contentgroup text,
	version text,
	notify_email varchar(10),
	contentchanges text,
	primary key (id)
)

create index w_title_idx on workflow (title)

create index w_action_idx on workflow (action)

create index w_fromstate_idx on workflow (fromstate)

create index w_tostate_idx on workflow (tostate)

update config set configvalue='6.6' where configname='database_version'

