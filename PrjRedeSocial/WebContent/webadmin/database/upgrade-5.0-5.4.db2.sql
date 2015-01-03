create table tempconfig (
        configname varchar(255) not null unique,
        configvalue clob,
        primary key (configname)
)

insert into tempconfig select configname, configvalue from config

drop table config

rename table tempconfig to config



alter table content add contentformat varchar(10)

alter table content_archive add contentformat varchar(10)

alter table content_public add contentformat varchar(10)



alter table orders add user_id int



alter table users add content_editor varchar(20)

alter table users add hardcore_language varchar(20)

alter table users add hardcore_upload varchar(10)

alter table users add hardcore_format varchar(10)

alter table users add hardcore_width varchar(10)

alter table users add hardcore_height varchar(10)

alter table users add hardcore_onenter clob

alter table users add hardcore_onctrlenter clob

alter table users add hardcore_onshiftenter clob

alter table users add hardcore_onaltenter clob

alter table users add hardcore_toolbar1 clob

alter table users add hardcore_toolbar2 clob

alter table users add hardcore_toolbar3 clob

alter table users add hardcore_toolbar4 clob

alter table users add hardcore_toolbar5 clob

alter table users add scheduled_publish varchar(20)

alter table users add scheduled_unpublish varchar(20)

alter table users add card_type clob

alter table users add card_number clob

alter table users add card_issuedmonth clob

alter table users add card_issuedyear clob

alter table users add card_expirymonth clob

alter table users add card_expiryyear clob

alter table users add card_name clob

alter table users add card_cvc clob

alter table users add card_issue clob

alter table users add card_postalcode clob

alter table users add delivery_name clob

alter table users add delivery_address clob

alter table users add delivery_postalcode clob

alter table users add delivery_city clob

alter table users add delivery_state clob

alter table users add delivery_country clob

alter table users add delivery_phone clob

alter table users add delivery_fax clob

alter table users add delivery_email clob

alter table users add invoice_name clob

alter table users add invoice_address clob

alter table users add invoice_postalcode clob

alter table users add invoice_city clob

alter table users add invoice_state clob

alter table users add invoice_country clob

alter table users add invoice_phone clob

alter table users add invoice_fax clob

alter table users add invoice_email clob

alter table users add notes clob



create table user2groups (
	user_id int,
	usergroup varchar(255),
	scheduled_publish varchar(20),
	scheduled_unpublish varchar(20)
)

create index u2g_userid_idx on user2groups(user_id)

create table user2types (
	user_id int,
	usertype varchar(255),
	scheduled_publish varchar(20),
	scheduled_unpublish varchar(20)
)

create index u2t_userid_idx on user2types(user_id)



update config set configvalue='5.4' where configname='database_version'

