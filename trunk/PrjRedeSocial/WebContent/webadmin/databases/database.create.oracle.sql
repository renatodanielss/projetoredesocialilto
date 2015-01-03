create table @@@table@@@ (
	id int not null,
	primary key (id)
)

create sequence @@@table@@@_seq

create or replace trigger @@@table@@@_bir before insert on @@@table@@@ referencing old as old new as new for each row
begin
 select @@@table@@@_seq.nextval into :new.id from dual;
end;
/

