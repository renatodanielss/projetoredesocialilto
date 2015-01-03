create sequence @@@table@@@_seq

create table @@@table@@@ (
	id int not null default nextval('@@@table@@@_seq'),
	primary key (id)
)

