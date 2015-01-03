
alter table content add locked_user int null

alter table content add locked_creator int null

alter table content add locked_developer int null

alter table content add locked_editor int null

alter table content add locked_publisher int null

alter table content add locked_administrator int null

alter table content add locked_schedule int null

alter table content add locked_unschedule int null

alter table content_archive add locked_user int null

alter table content_archive add locked_creator int null

alter table content_archive add locked_developer int null

alter table content_archive add locked_editor int null

alter table content_archive add locked_publisher int null

alter table content_archive add locked_administrator int null

alter table content_archive add locked_schedule int null

alter table content_archive add locked_unschedule int null

alter table content_public add locked_user int null

alter table content_public add locked_creator int null

alter table content_public add locked_developer int null

alter table content_public add locked_editor int null

alter table content_public add locked_publisher int null

alter table content_public add locked_administrator int null

alter table content_public add locked_schedule int null

alter table content_public add locked_unschedule int null

create index c_l_schedule_idx on content (locked_schedule)

create index ca_l_schedule_idx on content_archive (locked_schedule)

create index cp_l_schedule_idx on content_public (locked_schedule) 

create index c_l_unschedule_idx on content (locked_unschedule)

create index ca_l_unschedule_idx on content_archive (locked_unschedule)

create index cp_l_unschedule_idx on content_public (locked_unschedule) 

update config set configvalue='6.9' where configname='database_version'

