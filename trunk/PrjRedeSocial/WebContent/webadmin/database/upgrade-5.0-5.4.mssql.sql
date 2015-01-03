create table tempconfig (
        configname varchar(255) not null unique,
        configvalue text,
        primary key (configname)
)

insert into tempconfig select configname, configvalue from config

drop table config

EXEC sp_rename 'tempconfig', 'config'



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

alter table users add hardcore_onenter text

alter table users add hardcore_onctrlenter text

alter table users add hardcore_onshiftenter text

alter table users add hardcore_onaltenter text

alter table users add hardcore_toolbar1 text

alter table users add hardcore_toolbar2 text

alter table users add hardcore_toolbar3 text

alter table users add hardcore_toolbar4 text

alter table users add hardcore_toolbar5 text

alter table users add scheduled_publish varchar(20)

alter table users add scheduled_unpublish varchar(20)

alter table users add card_type text

alter table users add card_number text

alter table users add card_issuedmonth text

alter table users add card_issuedyear text

alter table users add card_expirymonth text

alter table users add card_expiryyear text

alter table users add card_name text

alter table users add card_cvc text

alter table users add card_issue text

alter table users add card_postalcode text

alter table users add delivery_name text

alter table users add delivery_address text

alter table users add delivery_postalcode text

alter table users add delivery_city text

alter table users add delivery_state text

alter table users add delivery_country text

alter table users add delivery_phone text

alter table users add delivery_fax text

alter table users add delivery_email text

alter table users add invoice_name text

alter table users add invoice_address text

alter table users add invoice_postalcode text

alter table users add invoice_city text

alter table users add invoice_state text

alter table users add invoice_country text

alter table users add invoice_phone text

alter table users add invoice_fax text

alter table users add invoice_email text

alter table users add notes text



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

