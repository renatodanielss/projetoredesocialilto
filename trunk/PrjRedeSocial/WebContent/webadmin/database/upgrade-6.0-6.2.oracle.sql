update config set configname='paypal_email' where configname='paypal_business'

update config set configname='payment_return_success' where configname='paypal_return'

update config set configname='payment_return_cancel' where configname='paypal_cancel_return'

alter table content add new_product_email varchar(10)

update content set new_product_email = to_char(product_email)

alter table content drop column product_email

alter table content rename column new_product_email to product_email

alter table content_archive add new_product_email varchar(10)

update content_archive set new_product_email = to_char(product_email)

alter table content_archive drop column product_email

alter table content_archive rename column new_product_email to product_email

alter table content_public add new_product_email varchar(10)

update content_public set new_product_email = to_char(product_email)

alter table content_public drop column product_email

alter table content_public rename column new_product_email to product_email

alter table orderitems add new_product_email varchar(10)

update orderitems set new_product_email = to_char(product_email)

alter table orderitems drop column product_email

alter table orderitems rename column new_product_email to product_email

alter table usagelog modify requestpath varchar(250)

alter table content add summary clob

alter table content add image1 varchar(10)

alter table content add image2 varchar(10)

alter table content add image3 varchar(10)

alter table content add file1 varchar(10)

alter table content add file2 varchar(10)

alter table content add file3 varchar(10)

alter table content add link1 varchar(10)

alter table content add link2 varchar(10)

alter table content add link3 varchar(10)

alter table content add product_delivery clob

alter table content add product_user clob

alter table content add product_page varchar(10)

alter table content add product_program clob

alter table content add product_hosting clob

alter table content add product_options clob

alter table content_archive add summary clob

alter table content_archive add image1 varchar(10)

alter table content_archive add image2 varchar(10)

alter table content_archive add image3 varchar(10)

alter table content_archive add file1 varchar(10)

alter table content_archive add file2 varchar(10)

alter table content_archive add file3 varchar(10)

alter table content_archive add link1 varchar(10)

alter table content_archive add link2 varchar(10)

alter table content_archive add link3 varchar(10)

alter table content_archive add product_delivery clob

alter table content_archive add product_user clob

alter table content_archive add product_page varchar(10)

alter table content_archive add product_program clob

alter table content_archive add product_hosting clob

alter table content_archive add product_options clob

alter table content_public add summary clob

alter table content_public add image1 varchar(10)

alter table content_public add image2 varchar(10)

alter table content_public add image3 varchar(10)

alter table content_public add file1 varchar(10)

alter table content_public add file2 varchar(10)

alter table content_public add file3 varchar(10)

alter table content_public add link1 varchar(10)

alter table content_public add link2 varchar(10)

alter table content_public add link3 varchar(10)

alter table content_public add product_delivery clob

alter table content_public add product_user clob

alter table content_public add product_page varchar(10)

alter table content_public add product_program clob

alter table content_public add product_hosting clob

alter table content_public add product_options clob

create table hosting (
	id int not null,
	user_id int,
	client_address varchar(255) not null unique,
	client_urlrootpath varchar(255),
	client_database varchar(255),
	personal_license varchar(255),
	professional_license varchar(255),
	enterprise_license varchar(255),
	hosting_license varchar(255),
	ecommerce_license varchar(255),
	community_license varchar(255),
	databases_license varchar(255),
	statistics_license varchar(255),
	superadmin varchar(255),
	superadmin_password varchar(255),
	superadmin_email varchar(255),
	hostingtype varchar(50),
	hostinggroup varchar(50),
	scheduled_last varchar(20),
	scheduled_publish varchar(20),
	scheduled_notify varchar(20),
	scheduled_unpublish varchar(20),
	scheduled_publish_email varchar(10),
	scheduled_notify_email varchar(10),
	scheduled_unpublish_email varchar(10),
	users_type varchar(50),
	users_group varchar(50),
	creators_type varchar(50),
	creators_group varchar(50),
	editors_type varchar(50),
	editors_group varchar(50),
	publishers_type varchar(50),
	publishers_group varchar(50),
	administrators_type varchar(50),
	administrators_group varchar(50),
	primary key (id)
)

create sequence hosting_seq

create or replace trigger hosting_bir before insert on hosting referencing old as old new as new for each row
begin
 select hosting_seq.nextval into :new.id from dual;
end;
/

create index h_c_address_idx on hosting (client_address)

create index h_c_database_idx on hosting (client_database)

create index h_s_email_idx on hosting (superadmin_email)

create index h_hostingtype_idx on hosting (hostingtype)

create index h_hostinggroup_idx on hosting (hostinggroup)

create index h_s_last_idx on hosting (scheduled_last)

create index h_s_publish_idx on hosting (scheduled_publish)

create index h_s_notify_idx on hosting (scheduled_notify)

create index h_s_unpublish_idx on hosting (scheduled_unpublish)

create index h_s_p_email_idx on hosting (scheduled_publish_email)

create index h_s_n_email_idx on hosting (scheduled_notify_email)

create index h_s_u_email_idx on hosting (scheduled_unpublish_email)

create index h_users_type_idx on hosting (users_type)

create index h_users_group_idx on hosting (users_group)

create index h_c_type_idx on hosting (creators_type)

create index h_c_group_idx on hosting (creators_group)

create index h_e_type_idx on hosting (editors_type)

create index h_e_group_idx on hosting (editors_group)

create index h_p_type_idx on hosting (publishers_type)

create index h_p_group_idx on hosting (publishers_group)

create index h_a_group_idx on hosting (administrators_group)

create index h_a_type_idx on hosting (administrators_type)

create table hostinggroups (
	id int not null,
	hostinggroup varchar(255),
	primary key (id)
)

create sequence hostinggroups_seq

create or replace trigger hostinggroups_bir before insert on hostinggroups referencing old as old new as new for each row
begin
 select hostinggroups_seq.nextval into :new.id from dual;
end;
/

create table hostingtypes (
	id int not null,
	hostingtype varchar(255),
	primary key (id)
)

create sequence hostingtypes_seq

create or replace trigger hostingtypes_bir before insert on hostingtypes referencing old as old new as new for each row
begin
 select hostingtypes_seq.nextval into :new.id from dual;
end;
/

alter table users add scheduled_last varchar(20)

alter table users add scheduled_notify varchar(20)

alter table users add scheduled_publish_email varchar(10)

alter table users add scheduled_notify_email varchar(10)

alter table users add scheduled_unpublish_email varchar(10)

create index u_s_last_idx on users (scheduled_last)

create index u_s_publish_idx on users (scheduled_publish)

create index u_s_notify_idx on users (scheduled_notify)

create index u_s_unpublish_idx on users (scheduled_unpublish)

alter table orderitems add summary clob

alter table orderitems add image1 varchar(10)

alter table orderitems add image2 varchar(10)

alter table orderitems add image3 varchar(10)

alter table orderitems add file1 varchar(10)

alter table orderitems add file2 varchar(10)

alter table orderitems add file3 varchar(10)

alter table orderitems add link1 varchar(10)

alter table orderitems add link2 varchar(10)

alter table orderitems add link3 varchar(10)

alter table orderitems add product_delivery clob

alter table orderitems add product_user clob

alter table orderitems add product_page varchar(10)

alter table orderitems add product_program clob

alter table orderitems add product_hosting clob

alter table orderitems add product_options clob

alter table orders add paid varchar(20)

create index o_paid_idx on orders(paid)

update config set configvalue='6.2' where configname='database_version'

