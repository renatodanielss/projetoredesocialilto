create table guestbook (
	id int not null auto_increment,
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

alter table config change name configname varchar(255) not null

alter table config change value configvalue varchar(255)

update config set configvalue='2.1' where configname='database_version'

