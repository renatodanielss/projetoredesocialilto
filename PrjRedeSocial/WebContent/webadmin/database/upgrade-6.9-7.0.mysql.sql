
alter table content change stylesheet stylesheet varchar(255)

alter table content_archive change stylesheet stylesheet varchar(255)

alter table content_public change stylesheet stylesheet varchar(255)

alter table websites add default_template varchar(10)

alter table websites add default_stylesheet varchar(10)

alter table content add users_users text

alter table content add creators_users text

alter table content add editors_users text

alter table content add publishers_users text

alter table content add developers_users text

alter table content add administrators_users text

alter table content_archive add users_users text

alter table content_archive add creators_users text

alter table content_archive add editors_users text

alter table content_archive add publishers_users text

alter table content_archive add developers_users text

alter table content_archive add administrators_users text

alter table content_public add users_users text

alter table content_public add creators_users text

alter table content_public add editors_users text

alter table content_public add publishers_users text

alter table content_public add developers_users text

alter table content_public add administrators_users text

alter table content add scripts varchar(255)

alter table content_archive add scripts varchar(255)

alter table content_public add scripts varchar(255)

alter table content add contentpackage varchar(255)

alter table content_archive add contentpackage varchar(255)

alter table content_public add contentpackage varchar(255)

create index c_contentpackage_idx on content (contentpackage)

create index ca_contentpackage_idx on content_archive (contentpackage)

create index cp_contentpackage_idx on content_public (contentpackage) 

alter table contentgroups add users_users text

alter table contentgroups add creators_users text

alter table contentgroups add editors_users text

alter table contentgroups add publishers_users text

alter table contentgroups add developers_users text

alter table contentgroups add administrators_users text

alter table contenttypes add users_users text

alter table contenttypes add creators_users text

alter table contenttypes add editors_users text

alter table contenttypes add publishers_users text

alter table contenttypes add developers_users text

alter table contenttypes add administrators_users text

alter table imagegroups add users_users text

alter table imagegroups add creators_users text

alter table imagegroups add editors_users text

alter table imagegroups add publishers_users text

alter table imagegroups add developers_users text

alter table imagegroups add administrators_users text

alter table imagetypes add users_users text

alter table imagetypes add creators_users text

alter table imagetypes add editors_users text

alter table imagetypes add publishers_users text

alter table imagetypes add developers_users text

alter table imagetypes add administrators_users text

alter table filegroups add users_users text

alter table filegroups add creators_users text

alter table filegroups add editors_users text

alter table filegroups add publishers_users text

alter table filegroups add developers_users text

alter table filegroups add administrators_users text

alter table filetypes add users_users text

alter table filetypes add creators_users text

alter table filetypes add editors_users text

alter table filetypes add publishers_users text

alter table filetypes add developers_users text

alter table filetypes add administrators_users text

alter table linkgroups add users_users text

alter table linkgroups add creators_users text

alter table linkgroups add editors_users text

alter table linkgroups add publishers_users text

alter table linkgroups add developers_users text

alter table linkgroups add administrators_users text

alter table linktypes add users_users text

alter table linktypes add creators_users text

alter table linktypes add editors_users text

alter table linktypes add publishers_users text

alter table linktypes add developers_users text

alter table linktypes add administrators_users text

alter table productgroups add users_users text

alter table productgroups add creators_users text

alter table productgroups add editors_users text

alter table productgroups add publishers_users text

alter table productgroups add developers_users text

alter table productgroups add administrators_users text

alter table producttypes add users_users text

alter table producttypes add creators_users text

alter table producttypes add editors_users text

alter table producttypes add publishers_users text

alter table producttypes add developers_users text

alter table producttypes add administrators_users text

alter table data add users_users text

alter table data add creators_users text

alter table data add editors_users text

alter table data add publishers_users text

alter table data add administrators_users text


update config set configvalue='7.0' where configname='database_version'

