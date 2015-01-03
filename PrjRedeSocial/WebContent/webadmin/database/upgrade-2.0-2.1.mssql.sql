create table guestbook (
	id int not null identity(1,1),
	created varchar(20),
	updated varchar(20),
	published varchar(20),
	status varchar(255),
	created_by varchar(255),
	updated_by varchar(255),
	published_by varchar(255),
	title varchar(255),
	content text,
	name varchar(255),
	email varchar(255),
	website varchar(255),
	company varchar(255),
	primary key (id)
)

EXEC sp_rename 'config.name', 'configname', 'COLUMN'

EXEC sp_rename 'config.value', 'configvalue', 'COLUMN'

update config set configvalue='2.1' where configname='database_version'

