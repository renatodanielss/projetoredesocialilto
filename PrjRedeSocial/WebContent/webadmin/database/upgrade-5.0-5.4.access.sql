alter table config add configvalue2 memo

update config set configvalue2=configvalue

select configname, configvalue2 into temp_config from config

drop table config

alter table temp_config add configvalue memo

update temp_config set configvalue=configvalue2

select configname, configvalue into config from temp_config

drop table temp_config



alter table content add contentformat varchar(10)

alter table content_archive add contentformat varchar(10)

alter table content_public add contentformat varchar(10)



alter table orders add user_id integer



alter table users add content_editor varchar(20)

alter table users add hardcore_language varchar(20)

alter table users add hardcore_upload varchar(10)

alter table users add hardcore_format varchar(10)

alter table users add hardcore_width varchar(10)

alter table users add hardcore_height varchar(10)

alter table users add hardcore_onenter memo

alter table users add hardcore_onctrlenter memo

alter table users add hardcore_onshiftenter memo

alter table users add hardcore_onaltenter memo

alter table users add hardcore_toolbar1 memo

alter table users add hardcore_toolbar2 memo

alter table users add hardcore_toolbar3 memo

alter table users add hardcore_toolbar4 memo

alter table users add hardcore_toolbar5 memo

alter table users add scheduled_publish varchar(20)

alter table users add scheduled_unpublish varchar(20)

alter table users add card_type memo

alter table users add card_number memo

alter table users add card_issuedmonth memo

alter table users add card_issuedyear memo

alter table users add card_expirymonth memo

alter table users add card_expiryyear memo

alter table users add card_name memo

alter table users add card_cvc memo

alter table users add card_issue memo

alter table users add card_postalcode memo

alter table users add delivery_name memo

alter table users add delivery_address memo

alter table users add delivery_postalcode memo

alter table users add delivery_city memo

alter table users add delivery_state memo

alter table users add delivery_country memo

alter table users add delivery_phone memo

alter table users add delivery_fax memo

alter table users add delivery_email memo

alter table users add invoice_name memo

alter table users add invoice_address memo

alter table users add invoice_postalcode memo

alter table users add invoice_city memo

alter table users add invoice_state memo

alter table users add invoice_country memo

alter table users add invoice_phone memo

alter table users add invoice_fax memo

alter table users add invoice_email memo

alter table users add notes memo



create table user2groups (
	user_id integer,
	usergroup varchar(255),
	scheduled_publish varchar(20),
	scheduled_unpublish varchar(20)
)

create index u2g_userid_idx on user2groups(user_id)

create table user2types (
	user_id integer,
	usertype varchar(255),
	scheduled_publish varchar(20),
	scheduled_unpublish varchar(20)
)

create index u2t_userid_idx on user2types(user_id)



update config set configvalue='5.4' where configname='database_version'

