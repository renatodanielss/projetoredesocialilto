
alter table content add status_by varchar(255);

alter table content_archive add status_by varchar(255);

alter table content_public add status_by varchar(255);

update content set status_by = '' where status_by is null;

update content_archive set status_by = '' where status_by is null;

update content_public set status_by = '' where status_by is null;


alter table content add menuitem varchar(3);

alter table content_archive add menuitem varchar(3);

alter table content_public add menuitem varchar(3);

update content set menuitem = searchable where menuitem is null;

update content_archive set menuitem = searchable where menuitem is null;

update content_public set menuitem = searchable where menuitem is null;


alter table workflow add userrestriction varchar(20);

update workflow set userrestriction = '' where userrestriction is null;


alter table contentgroups add parentgroup varchar(255);

alter table contenttypes add parenttype varchar(255);

alter table filegroups add parentgroup varchar(255);

alter table filetypes add parenttype varchar(255);

alter table imagegroups add parentgroup varchar(255);

alter table imagetypes add parenttype varchar(255);

alter table linkgroups add parentgroup varchar(255);

alter table linktypes add parenttype varchar(255);

alter table productgroups add parentgroup varchar(255);

alter table producttypes add parenttype varchar(255);

alter table usergroups add parentgroup varchar(255);

alter table usertypes add parenttype varchar(255);


alter table usergroups add users_users text;

alter table usergroups add creators_users text;

alter table usergroups add editors_users text;

alter table usergroups add publishers_users text;

alter table usergroups add administrators_users text;

alter table usergroups add subscribers_users text;

alter table usertypes add users_users text;

alter table usertypes add creators_users text;

alter table usertypes add editors_users text;

alter table usertypes add publishers_users text;

alter table usertypes add administrators_users text;

alter table usertypes add subscribers_users text;



update config set configvalue='7.3' where configname='database_version'

