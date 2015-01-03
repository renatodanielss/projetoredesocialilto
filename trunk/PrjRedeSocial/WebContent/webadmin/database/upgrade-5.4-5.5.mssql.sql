create table data (
	id int not null identity(1,1),
	title varchar(50),
	content text,
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



update config set configvalue='5.5' where configname='database_version'

