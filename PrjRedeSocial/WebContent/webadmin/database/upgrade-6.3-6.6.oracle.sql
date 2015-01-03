alter table content add	requested_publish varchar(20)

alter table content add	requested_unpublish varchar(20)

alter table content_archive add requested_publish varchar(20)

alter table content_archive add requested_unpublish varchar(20)

alter table content_public add requested_publish varchar(20)

alter table content_public add requested_unpublish varchar(20)

create table workflow (
	id int not null,
	title varchar(255),
	action varchar(255),
	fromstate varchar(255),
	tostate varchar(255),
	usertype clob,
	usergroup clob,
	contentclass clob,
	contenttype clob,
	contentgroup clob,
	version clob,
	notify_email varchar(10),
	contentchanges clob,
	primary key (id)
)

create sequence workflow_seq

create or replace trigger workflow_bir before insert on workflow referencing old as old new as new for each row
begin
 select workflow_seq.nextval into :new.id from dual;
end;
/

create index w_title_idx on workflow (title)

create index w_action_idx on workflow (action)

create index w_fromstate_idx on workflow (fromstate)

create index w_tostate_idx on workflow (tostate)

update config set configvalue='6.6' where configname='database_version'

