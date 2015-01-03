create table content_dependencies (
	content_id integer,
	dependency varchar(255),
	dependency_id integer
)

create index cd_content_id_idx on content_dependencies (content_id)

create index cd_dependency_id_idx on content_dependencies (dependency_id)

create table content_public_dependencies (
	content_id integer,
	dependency varchar(255),
	dependency_id integer
)

create index cpd_content_id_idx on content_public_dependencies (content_id)

create index cpd_dependency_id_idx on content_public_dependencies (dependency_id)

insert into config (configname, configvalue) values ('use_contentdependencies_tables', 'no')

update config set configvalue='6.7' where configname='database_version'

