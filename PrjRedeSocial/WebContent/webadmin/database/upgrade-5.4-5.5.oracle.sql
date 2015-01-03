create table data (
	id int not null,
	title varchar(50),
	content clob,
	users_type varchar(255),
	users_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	searchresults varchar(10),
	searchresult varchar(10),
	viewpage varchar(10),
	primary key (id)
)

create sequence data_seq

create or replace trigger data_bir before insert on data referencing old as old new as new for each row
begin
 select data_seq.nextval into :new.id from dual;
end;
/

update config set configvalue='5.5' where configname='database_version'

