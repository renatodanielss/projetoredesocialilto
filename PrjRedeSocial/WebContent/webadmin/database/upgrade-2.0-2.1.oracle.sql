create table guestbook (
	id int not null,
	created varchar(20),
	updated varchar(20),
	published varchar(20),
	status varchar(255),
	created_by varchar(255),
	updated_by varchar(255),
	published_by varchar(255),
	title varchar(255),
	content clob,
	name varchar(255),
	email varchar(255),
	website varchar(255),
	company varchar(255),
	primary key (id)
)

create sequence guestbook_seq

create or replace trigger guestbook_bir before insert on guestbook referencing old as old new as new for each row
begin
 select guestbook_seq.nextval into :new.id from dual;
end;
/

create table tempconfig (
        configname varchar(255) not null,
        configvalue varchar(255),
        primary key (configname)
)

insert into tempconfig select name as configname, value as configvalue from config

drop table config

rename tempconfig to config

update config set configvalue='2.1' where configname='database_version'

