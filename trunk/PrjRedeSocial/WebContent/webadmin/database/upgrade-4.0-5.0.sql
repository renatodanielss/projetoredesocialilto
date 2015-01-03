alter table content add searchable varchar(3)

alter table content_archive add searchable varchar(3)

alter table content_public add searchable varchar(3)

create index c_searchable_idx on content(searchable)

create index ca_searchable_idx on content_archive(searchable)

create index cp_searchable_idx on content_public(searchable)

alter table contentgroups add users_type varchar(255)

alter table contentgroups add users_group varchar(255)

alter table contentgroups add developers_type varchar(255)

alter table contentgroups add developers_group varchar(255)

alter table contentgroups add creators_type varchar(255)

alter table contentgroups add creators_group varchar(255)

alter table contentgroups add editors_type varchar(255)

alter table contentgroups add editors_group varchar(255)

alter table contentgroups add publishers_type varchar(255)

alter table contentgroups add publishers_group varchar(255)

alter table contentgroups add administrators_type varchar(255)

alter table contentgroups add administrators_group varchar(255)

alter table contentgroups add template varchar(10)

alter table contentgroups add stylesheet varchar(10)

alter table contenttypes add users_type varchar(255)

alter table contenttypes add users_group varchar(255)

alter table contenttypes add developers_type varchar(255)

alter table contenttypes add developers_group varchar(255)

alter table contenttypes add creators_type varchar(255)

alter table contenttypes add creators_group varchar(255)

alter table contenttypes add editors_type varchar(255)

alter table contenttypes add editors_group varchar(255)

alter table contenttypes add publishers_type varchar(255)

alter table contenttypes add publishers_group varchar(255)

alter table contenttypes add administrators_type varchar(255)

alter table contenttypes add administrators_group varchar(255)

alter table contenttypes add template varchar(10)

alter table contenttypes add stylesheet varchar(10)

alter table filegroups add users_type varchar(255)

alter table filegroups add users_group varchar(255)

alter table filegroups add developers_type varchar(255)

alter table filegroups add developers_group varchar(255)

alter table filegroups add creators_type varchar(255)

alter table filegroups add creators_group varchar(255)

alter table filegroups add editors_type varchar(255)

alter table filegroups add editors_group varchar(255)

alter table filegroups add publishers_type varchar(255)

alter table filegroups add publishers_group varchar(255)

alter table filegroups add administrators_type varchar(255)

alter table filegroups add administrators_group varchar(255)

alter table filegroups add template varchar(10)

alter table filegroups add stylesheet varchar(10)

alter table filetypes add users_type varchar(255)

alter table filetypes add users_group varchar(255)

alter table filetypes add developers_type varchar(255)

alter table filetypes add developers_group varchar(255)

alter table filetypes add creators_type varchar(255)

alter table filetypes add creators_group varchar(255)

alter table filetypes add editors_type varchar(255)

alter table filetypes add editors_group varchar(255)

alter table filetypes add publishers_type varchar(255)

alter table filetypes add publishers_group varchar(255)

alter table filetypes add administrators_type varchar(255)

alter table filetypes add administrators_group varchar(255)

alter table filetypes add template varchar(10)

alter table filetypes add stylesheet varchar(10)

alter table imagegroups add users_type varchar(255)

alter table imagegroups add users_group varchar(255)

alter table imagegroups add developers_type varchar(255)

alter table imagegroups add developers_group varchar(255)

alter table imagegroups add creators_type varchar(255)

alter table imagegroups add creators_group varchar(255)

alter table imagegroups add editors_type varchar(255)

alter table imagegroups add editors_group varchar(255)

alter table imagegroups add publishers_type varchar(255)

alter table imagegroups add publishers_group varchar(255)

alter table imagegroups add administrators_type varchar(255)

alter table imagegroups add administrators_group varchar(255)

alter table imagegroups add template varchar(10)

alter table imagegroups add stylesheet varchar(10)

alter table imagetypes add users_type varchar(255)

alter table imagetypes add users_group varchar(255)

alter table imagetypes add developers_type varchar(255)

alter table imagetypes add developers_group varchar(255)

alter table imagetypes add creators_type varchar(255)

alter table imagetypes add creators_group varchar(255)

alter table imagetypes add editors_type varchar(255)

alter table imagetypes add editors_group varchar(255)

alter table imagetypes add publishers_type varchar(255)

alter table imagetypes add publishers_group varchar(255)

alter table imagetypes add administrators_type varchar(255)

alter table imagetypes add administrators_group varchar(255)

alter table imagetypes add template varchar(10)

alter table imagetypes add stylesheet varchar(10)

alter table linkgroups add users_type varchar(255)

alter table linkgroups add users_group varchar(255)

alter table linkgroups add developers_type varchar(255)

alter table linkgroups add developers_group varchar(255)

alter table linkgroups add creators_type varchar(255)

alter table linkgroups add creators_group varchar(255)

alter table linkgroups add editors_type varchar(255)

alter table linkgroups add editors_group varchar(255)

alter table linkgroups add publishers_type varchar(255)

alter table linkgroups add publishers_group varchar(255)

alter table linkgroups add administrators_type varchar(255)

alter table linkgroups add administrators_group varchar(255)

alter table linkgroups add template varchar(10)

alter table linkgroups add stylesheet varchar(10)

alter table linktypes add users_type varchar(255)

alter table linktypes add users_group varchar(255)

alter table linktypes add developers_type varchar(255)

alter table linktypes add developers_group varchar(255)

alter table linktypes add creators_type varchar(255)

alter table linktypes add creators_group varchar(255)

alter table linktypes add editors_type varchar(255)

alter table linktypes add editors_group varchar(255)

alter table linktypes add publishers_type varchar(255)

alter table linktypes add publishers_group varchar(255)

alter table linktypes add administrators_type varchar(255)

alter table linktypes add administrators_group varchar(255)

alter table linktypes add template varchar(10)

alter table linktypes add stylesheet varchar(10)

alter table productgroups add users_type varchar(255)

alter table productgroups add users_group varchar(255)

alter table productgroups add developers_type varchar(255)

alter table productgroups add developers_group varchar(255)

alter table productgroups add creators_type varchar(255)

alter table productgroups add creators_group varchar(255)

alter table productgroups add editors_type varchar(255)

alter table productgroups add editors_group varchar(255)

alter table productgroups add publishers_type varchar(255)

alter table productgroups add publishers_group varchar(255)

alter table productgroups add administrators_type varchar(255)

alter table productgroups add administrators_group varchar(255)

alter table productgroups add template varchar(10)

alter table productgroups add stylesheet varchar(10)

alter table producttypes add users_type varchar(255)

alter table producttypes add users_group varchar(255)

alter table producttypes add developers_type varchar(255)

alter table producttypes add developers_group varchar(255)

alter table producttypes add creators_type varchar(255)

alter table producttypes add creators_group varchar(255)

alter table producttypes add editors_type varchar(255)

alter table producttypes add editors_group varchar(255)

alter table producttypes add publishers_type varchar(255)

alter table producttypes add publishers_group varchar(255)

alter table producttypes add administrators_type varchar(255)

alter table producttypes add administrators_group varchar(255)

alter table producttypes add template varchar(10)

alter table producttypes add stylesheet varchar(10)

create table usergroups2 (
	usergroup varchar(255),
	subgroup varchar(255)
)

create index ug2_usergroup_idx on usergroups2(usergroup)

create table usertypes2 (
	usertype varchar(255),
	subtype varchar(255)
)

create index ut2_usertype_idx on usertypes2(usertype)

update config set configvalue='5.0' where configname='database_version'

