alter table content add product_code clob

alter table content add product_currency clob

alter table content add product_price clob

alter table content add product_stock clob

alter table content add product_comment clob

alter table content add product_email clob

alter table content add product_url clob

alter table content_public add product_code clob

alter table content_public add product_currency clob

alter table content_public add product_price clob

alter table content_public add product_stock clob

alter table content_public add product_comment clob

alter table content_public add product_email clob

alter table content_public add product_url clob

alter table content_archive add product_code clob

alter table content_archive add product_currency clob

alter table content_archive add product_price clob

alter table content_archive add product_stock clob

alter table content_archive add product_comment clob

alter table content_archive add product_email clob

alter table content_archive add product_url clob

create table currencies (
	id int not null,
	title varchar(50),
	symbol varchar(50),
	rate clob,
	primary key (id)
)

create sequence currencies_seq

create or replace trigger currencies_bir before insert on currencies referencing old as old new as new for each row
begin
 select currencies_seq.nextval into :new.id from dual;
end;
/

create index cur_title_idx on currencies(title)

create index cur_symbol_idx on currencies(symbol)

create table orderitems (
	id int not null,
	order_id int,
	product_id int,
	version clob,
	title clob,
	author clob,
	keywords clob,
	description clob,
	product_code clob,
	product_currency clob,
	product_price clob,
	product_stock clob,
	product_comment clob,
	product_email clob,
	product_url clob,
	item_quantity clob,
	item_total clob,
	primary key (id)
)

create sequence orderitems_seq

create or replace trigger orderitems_bir before insert on orderitems referencing old as old new as new for each row
begin
 select orderitems_seq.nextval into :new.id from dual;
end;
/

create index oi_order_id_idx on orderitems(order_id)

create index oi_product_id_idx on orderitems(product_id)

create table orders (
	id int not null,
	created varchar(20),
	updated varchar(20),
	published varchar(20),
	created_by varchar(255),
	updated_by varchar(255),
	published_by varchar(255),
	status clob,
	order_quantity clob,
	order_currency clob,
	order_subtotal clob,
	tax_description clob,
	tax_total clob,
	shipping_description clob,
	shipping_total clob,
	order_total clob,
	card_type clob,
	card_number clob,
	card_issuedmonth clob,
	card_issuedyear clob,
	card_expirymonth clob,
	card_expiryyear clob,
	card_name clob,
	card_cvc clob,
	card_postalcode clob,
	delivery_name clob,
	delivery_address clob,
	delivery_postalcode clob,
	delivery_city clob,
	delivery_state clob,
	delivery_country clob,
	delivery_phone clob,
	delivery_fax clob,
	delivery_email clob,
	invoice_name clob,
	invoice_address clob,
	invoice_postalcode clob,
	invoice_city clob,
	invoice_state clob,
	invoice_country clob,
	invoice_phone clob,
	invoice_fax clob,
	invoice_email clob,
	primary key (id)
)

create sequence orders_seq

create or replace trigger orders_bir before insert on orders referencing old as old new as new for each row
begin
 select orders_seq.nextval into :new.id from dual;
end;
/

create table productgroups (
	id int not null,
	productgroup varchar(50),
	primary key (id)
)

create sequence productgroups_seq

create or replace trigger productgroups_bir before insert on productgroups referencing old as old new as new for each row
begin
 select productgroups_seq.nextval into :new.id from dual;
end;
/

create table producttypes (
	id int not null,
	producttype varchar(50),
	primary key (id)
)

create sequence producttypes_seq

create or replace trigger producttypes_bir before insert on producttypes referencing old as old new as new for each row
begin
 select producttypes_seq.nextval into :new.id from dual;
end;
/

create table shipping (
	id int not null,
	title varchar(255),
	country varchar(50),
	state varchar(50),
	product_id varchar(50),
	product_group varchar(50),
	product_type varchar(50),
	quantity_from int,
	quantity_to int,
	total_currency varchar(50),
	total_from decimal(20,2),
	total_to decimal(20,2),
	ship_description varchar(50),
	ship_currency varchar(50),
	ship_order decimal(20,2),
	ship_item decimal(20,2),
	ship_percent decimal(20,2),
	primary key (id)
)

create sequence shipping_seq

create or replace trigger shipping_bir before insert on shipping referencing old as old new as new for each row
begin
 select shipping_seq.nextval into :new.id from dual;
end;
/

create index s_title_idx on shipping(title)

create index s_country_idx on shipping(country)

create index s_state_idx on shipping(state)

create index s_prod_id_idx on shipping(product_id)

create index s_prod_group_idx on shipping(product_group)

create index s_prod_type_idx on shipping(product_type)

create index s_quan_from_idx on shipping(quantity_from)

create index s_quan_to_idx on shipping(quantity_to)

create index s_total_cur_idx on shipping(total_currency)

create index s_total_from_idx on shipping(total_from)

create index s_total_to_idx on shipping(total_to)

create table tax (
	id int not null,
	title varchar(255),
	country varchar(50),
	state varchar(50),
	product_id varchar(50),
	product_group varchar(50),
	product_type varchar(50),
	quantity_from int,
	quantity_to int,
	total_currency varchar(50),
	total_from decimal(20,2),
	total_to decimal(20,2),
	tax_description varchar(50),
	tax_currency varchar(50),
	tax_order decimal(20,2),
	tax_item decimal(20,2),
	tax_percent decimal(20,2),
	primary key (id)
)

create sequence tax_seq

create or replace trigger tax_bir before insert on tax referencing old as old new as new for each row
begin
 select tax_seq.nextval into :new.id from dual;
end;
/

create index t_title_idx on tax(title)

create index t_country_idx on tax(country)

create index t_state_idx on tax(state)

create index t_product_id_idx on tax(product_id)

create index t_prod_group_idx on tax(product_group)

create index t_prod_type_idx on tax(product_type)

create index t_quan_from_idx on tax(quantity_from)

create index t_quan_to_idx on tax(quantity_to)

create index t_total_cur_idx on tax(total_currency)

create index t_total_from_idx on tax(total_from)

create index t_total_to_idx on tax(total_to)

alter table versions add currencies varchar(50)

update config set configvalue='2.2' where configname='database_version'

