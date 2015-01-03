alter table content add product_code memo

alter table content add product_currency memo

alter table content add product_price memo

alter table content add product_stock memo

alter table content add product_comment memo

alter table content add product_email memo

alter table content add product_url memo

alter table content_public add product_code memo

alter table content_public add product_currency memo

alter table content_public add product_price memo

alter table content_public add product_stock memo

alter table content_public add product_comment memo

alter table content_public add product_email memo

alter table content_public add product_url memo

alter table content_archive add product_code memo

alter table content_archive add product_currency memo

alter table content_archive add product_price memo

alter table content_archive add product_stock memo

alter table content_archive add product_comment memo

alter table content_archive add product_email memo

alter table content_archive add product_url memo

create table currencies (
	id counter,
	title varchar(50),
	symbol varchar(50),
	rate memo,
	primary key (id)
)

create index cur_title_idx on currencies(title)

create index cur_symbol_idx on currencies(symbol)

create table orderitems (
	id counter,
	order_id integer,
	product_id integer,
	version memo,
	title memo,
	author memo,
	keywords memo,
	description memo,
	product_code memo,
	product_currency memo,
	product_price memo,
	product_stock memo,
	product_comment memo,
	product_email memo,
	product_url memo,
	item_quantity memo,
	item_total memo,
	primary key (id)
)

create index oi_order_id_idx on orderitems(order_id)

create index oi_product_id_idx on orderitems(product_id)

create table orders (
	id counter,
	created varchar(20),
	updated varchar(20),
	published varchar(20),
	created_by varchar(255),
	updated_by varchar(255),
	published_by varchar(255),
	status memo,
	order_quantity memo,
	order_currency memo,
	order_subtotal memo,
	tax_description memo,
	tax_total memo,
	shipping_description memo,
	shipping_total memo,
	order_total memo,
	card_type memo,
	card_numeric memo,
	card_issuedmonth memo,
	card_issuedyear memo,
	card_expirymonth memo,
	card_expiryyear memo,
	card_name memo,
	card_cvc memo,
	card_postalcode memo,
	delivery_name memo,
	delivery_address memo,
	delivery_postalcode memo,
	delivery_city memo,
	delivery_state memo,
	delivery_country memo,
	delivery_phone memo,
	delivery_fax memo,
	delivery_email memo,
	invoice_name memo,
	invoice_address memo,
	invoice_postalcode memo,
	invoice_city memo,
	invoice_state memo,
	invoice_country memo,
	invoice_phone memo,
	invoice_fax memo,
	invoice_email memo,
	primary key (id)
)

create table productgroups (
	id counter,
	productgroup varchar(50),
	primary key (id)
)

create table producttypes (
	id counter,
	producttype varchar(50),
	primary key (id)
)

create table shipping (
	id counter,
	title varchar(255),
	country varchar(50),
	state varchar(50),
	product_id varchar(50),
	product_group varchar(50),
	product_type varchar(50),
	quantity_from integer,
	quantity_to integer,
	total_currency varchar(50),
	total_from currency,
	total_to currency,
	ship_description varchar(50),
	ship_currency varchar(50),
	ship_order currency,
	ship_item currency,
	ship_percent currency,
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

create table tax (
	id counter,
	title varchar(255),
	country varchar(50),
	state varchar(50),
	product_id varchar(50),
	product_group varchar(50),
	product_type varchar(50),
	quantity_from integer,
	quantity_to integer,
	total_currency varchar(50),
	total_from currency,
	total_to currency,
	tax_description varchar(50),
	tax_currency varchar(50),
	tax_order currency,
	tax_item currency,
	tax_percent currency,
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

