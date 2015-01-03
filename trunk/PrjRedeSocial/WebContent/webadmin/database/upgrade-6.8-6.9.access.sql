
alter table content add locked_user integer

alter table content add locked_creator integer

alter table content add locked_developer integer

alter table content add locked_editor integer

alter table content add locked_publisher integer

alter table content add locked_administrator integer

alter table content add locked_schedule integer

alter table content add locked_unschedule integer

alter table content_archive add locked_user integer

alter table content_archive add locked_creator integer

alter table content_archive add locked_developer integer

alter table content_archive add locked_editor integer

alter table content_archive add locked_publisher integer

alter table content_archive add locked_administrator integer

alter table content_archive add locked_schedule integer

alter table content_archive add locked_unschedule integer

alter table content_public add locked_user integer

alter table content_public add locked_creator integer

alter table content_public add locked_developer integer

alter table content_public add locked_editor integer

alter table content_public add locked_publisher integer

alter table content_public add locked_administrator integer

alter table content_public add locked_schedule integer

alter table content_public add locked_unschedule integer

create index c_l_schedule_idx on content (locked_schedule)

create index ca_l_schedule_idx on content_archive (locked_schedule)

create index cp_l_schedule_idx on content_public (locked_schedule) 

create index c_l_unschedule_idx on content (locked_unschedule)

create index ca_l_unschedule_idx on content_archive (locked_unschedule)

create index cp_l_unschedule_idx on content_public (locked_unschedule) 

update config set configvalue='6.9' where configname='database_version'

