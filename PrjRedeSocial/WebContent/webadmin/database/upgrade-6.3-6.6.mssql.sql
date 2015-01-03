alter table content add	requested_publish varchar(20) null

alter table content add	requested_unpublish varchar(20) null

alter table content_archive add requested_publish varchar(20) null

alter table content_archive add requested_unpublish varchar(20) null

alter table content_public add requested_publish varchar(20) null

alter table content_public add requested_unpublish varchar(20) null

create table workflow (
	id int not null identity(1,1),
	title varchar(255) null,
	action varchar(255) null,
	fromstate varchar(255) null,
	tostate varchar(255) null,
	usertype text null,
	usergroup text null,
	contentclass text null,
	contenttype text null,
	contentgroup text null,
	version text null,
	notify_email varchar(10) null,
	contentchanges text null,
	primary key (id)
)

create index w_title_idx on workflow (title)

create index w_action_idx on workflow (action)

create index w_fromstate_idx on workflow (fromstate)

create index w_tostate_idx on workflow (tostate)

update config set configvalue='6.6' where configname='database_version'

