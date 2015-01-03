alter table content add product_code text

alter table content add product_currency text

alter table content add product_price text

alter table content add product_stock text

alter table content add product_comment text

alter table content add product_email text

alter table content add product_url text

alter table content_public add product_code text

alter table content_public add product_currency text

alter table content_public add product_price text

alter table content_public add product_stock text

alter table content_public add product_comment text

alter table content_public add product_email text

alter table content_public add product_url text

alter table content_archive add product_code text

alter table content_archive add product_currency text

alter table content_archive add product_price text

alter table content_archive add product_stock text

alter table content_archive add product_comment text

alter table content_archive add product_email text

alter table content_archive add product_url text

create sequence currencies_seq

create table currencies (
	id int not null default nextval('currencies_seq'),
	title varchar(50),
	symbol varchar(50),
	rate text,
	primary key (id)
)

create index cur_title_idx on currencies(title)

create index cur_symbol_idx on currencies(symbol)

create sequence orderitems_seq

create table orderitems (
	id int not null default nextval('orderitems_seq'),
	order_id int,
	product_id int,
	version text,
	title text,
	author text,
	keywords text,
	description text,
	product_code text,
	product_currency text,
	product_price text,
	product_stock text,
	product_comment text,
	product_email text,
	product_url text,
	item_quantity text,
	item_total text,
	primary key (id)
)

create index oi_order_id_idx on orderitems(order_id)

create index oi_product_id_idx on orderitems(product_id)

create sequence orders_seq

create table orders (
	id int not null default nextval('orders_seq'),
	created varchar(20),
	updated varchar(20),
	published varchar(20),
	created_by varchar(255),
	updated_by varchar(255),
	published_by varchar(255),
	status text,
	order_quantity text,
	order_currency text,
	order_subtotal text,
	tax_description text,
	tax_total text,
	shipping_description text,
	shipping_total text,
	order_total text,
	card_type text,
	card_number text,
	card_issuedmonth text,
	card_issuedyear text,
	card_expirymonth text,
	card_expiryyear text,
	card_name text,
	card_cvc text,
	card_postalcode text,
	delivery_name text,
	delivery_address text,
	delivery_postalcode text,
	delivery_city text,
	delivery_state text,
	delivery_country text,
	delivery_phone text,
	delivery_fax text,
	delivery_email text,
	invoice_name text,
	invoice_address text,
	invoice_postalcode text,
	invoice_city text,
	invoice_state text,
	invoice_country text,
	invoice_phone text,
	invoice_fax text,
	invoice_email text,
	primary key (id)
)

create sequence productgroups_seq

create table productgroups (
	id int not null default nextval('productgroups_seq'),
	productgroup varchar(50),
	primary key (id)
)

create sequence producttypes_seq

create table producttypes (
	id int not null default nextval('producttypes_seq'),
	producttype varchar(50),
	primary key (id)
)

create sequence shipping_seq

create table shipping (
	id int not null default nextval('shipping_seq'),
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

create sequence tax_seq

create table tax (
	id int not null default nextval('tax_seq'),
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

