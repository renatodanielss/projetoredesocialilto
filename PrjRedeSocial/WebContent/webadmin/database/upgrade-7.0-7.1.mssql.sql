
alter table websites add useragent varchar(255)

update content set htmlbodyonload = 'onload="' + htmlbodyonload + '"' where htmlbodyonload is not null and htmlbodyonload <> ''

update content_archive set htmlbodyonload = 'onload="' + htmlbodyonload + '"' where htmlbodyonload is not null and htmlbodyonload <> ''

update content_public set htmlbodyonload = 'onload="' + htmlbodyonload + '"' where htmlbodyonload is not null and htmlbodyonload <> ''

alter table content add product_weight text null

alter table content add product_volume text null

alter table content add product_width text null

alter table content add product_height text null

alter table content add product_depth text null

alter table content_archive add product_weight text null

alter table content_archive add product_volume text null

alter table content_archive add product_width text null

alter table content_archive add product_height text null

alter table content_archive add product_depth text null

alter table content_public add product_weight text null

alter table content_public add product_volume text null

alter table content_public add product_width text null

alter table content_public add product_height text null

alter table content_public add product_depth text null

alter table orderitems add product_weight text null

alter table orderitems add product_volume text null

alter table orderitems add product_width text null

alter table orderitems add product_height text null

alter table orderitems add product_depth text null

alter table orderitems add shipping_description text null

alter table orderitems add shipping_total text null

alter table orderitems add tax_description text null

alter table orderitems add tax_total text null

alter table shipping add product_weight_from decimal(20,3)

alter table shipping add product_weight_to decimal(20,3)

alter table shipping add product_volume_from decimal(20,2)

alter table shipping add product_volume_to decimal(20,2)

alter table shipping add product_width_from decimal(20,2)

alter table shipping add product_width_to decimal(20,2)

alter table shipping add product_height_from decimal(20,2)

alter table shipping add product_height_to decimal(20,2)

alter table shipping add product_depth_from decimal(20,2)

alter table shipping add product_depth_to decimal(20,2)

alter table shipping add total_weight_from decimal(20,3)

alter table shipping add total_weight_to decimal(20,3)

alter table shipping add total_volume_from decimal(20,2)

alter table shipping add total_volume_to decimal(20,2)

alter table shipping add total_width_from decimal(20,2)

alter table shipping add total_width_to decimal(20,2)

alter table shipping add total_height_from decimal(20,2)

alter table shipping add total_height_to decimal(20,2)

alter table shipping add total_depth_from decimal(20,2)

alter table shipping add total_depth_to decimal(20,2)

alter table shipping add ship_total decimal(20,2)

create index s_p_weight_from_idx on shipping(product_weight_from)

create index s_p_weight_to_idx on shipping(product_weight_to)

create index s_p_volume_from_idx on shipping(product_volume_from)

create index s_p_volume_to_idx on shipping(product_volume_to)

create index s_p_width_from_idx on shipping(product_width_from)

create index s_p_width_to_idx on shipping(product_width_to)

create index s_p_height_from_idx on shipping(product_height_from)

create index s_p_height_to_idx on shipping(product_height_to)

create index s_p_depth_from_idx on shipping(product_depth_from)

create index s_p_depth_to_idx on shipping(product_depth_to)

create index s_t_weight_from_idx on shipping(total_weight_from)

create index s_t_weight_to_idx on shipping(total_weight_to)

create index s_t_volume_from_idx on shipping(total_volume_from)

create index s_t_volume_to_idx on shipping(total_volume_to)

create index s_t_width_from_idx on shipping(total_width_from)

create index s_t_width_to_idx on shipping(total_width_to)

create index s_t_height_from_idx on shipping(total_height_from)

create index s_t_height_to_idx on shipping(total_height_to)

create index s_t_depth_from_idx on shipping(total_depth_from)

create index s_t_depth_to_idx on shipping(total_depth_to)

alter table tax add product_weight_from decimal(20,3)

alter table tax add product_weight_to decimal(20,3)

alter table tax add product_volume_from decimal(20,2)

alter table tax add product_volume_to decimal(20,2)

alter table tax add product_width_from decimal(20,2)

alter table tax add product_width_to decimal(20,2)

alter table tax add product_height_from decimal(20,2)

alter table tax add product_height_to decimal(20,2)

alter table tax add product_depth_from decimal(20,2)

alter table tax add product_depth_to decimal(20,2)

alter table tax add total_weight_from decimal(20,3)

alter table tax add total_weight_to decimal(20,3)

alter table tax add total_volume_from decimal(20,2)

alter table tax add total_volume_to decimal(20,2)

alter table tax add total_width_from decimal(20,2)

alter table tax add total_width_to decimal(20,2)

alter table tax add total_height_from decimal(20,2)

alter table tax add total_height_to decimal(20,2)

alter table tax add total_depth_from decimal(20,2)

alter table tax add total_depth_to decimal(20,2)

alter table tax add tax_total decimal(20,2)

create index t_p_weight_from_idx on tax(product_weight_from)

create index t_p_weight_to_idx on tax(product_weight_to)

create index t_p_volume_from_idx on tax(product_volume_from)

create index t_p_volume_to_idx on tax(product_volume_to)

create index t_p_width_from_idx on tax(product_width_from)

create index t_p_width_to_idx on tax(product_width_to)

create index t_p_height_from_idx on tax(product_height_from)

create index t_p_height_to_idx on tax(product_height_to)

create index t_p_depth_from_idx on tax(product_depth_from)

create index t_p_depth_to_idx on tax(product_depth_to)

create index t_t_weight_from_idx on tax(total_weight_from)

create index t_t_weight_to_idx on tax(total_weight_to)

create index t_t_volume_from_idx on tax(total_volume_from)

create index t_t_volume_to_idx on tax(total_volume_to)

create index t_t_width_from_idx on tax(total_width_from)

create index t_t_width_to_idx on tax(total_width_to)

create index t_t_height_from_idx on tax(total_height_from)

create index t_t_height_to_idx on tax(total_height_to)

create index t_t_depth_from_idx on tax(total_depth_from)

create index t_t_depth_to_idx on tax(total_depth_to)

create table discounts (
	id int not null identity(1,1),
	title varchar(255) null,
	country varchar(50) null,
	state varchar(50) null,
	product_id varchar(50) null,
	product_group varchar(50) null,
	product_type varchar(50) null,
	product_weight_from decimal(20,3),
	product_weight_to decimal(20,3),
	product_volume_from decimal(20,2),
	product_volume_to decimal(20,2),
	product_width_from decimal(20,2),
	product_width_to decimal(20,2),
	product_height_from decimal(20,2),
	product_height_to decimal(20,2),
	product_depth_from decimal(20,2),
	product_depth_to decimal(20,2),
	quantity_from int,
	quantity_to int,
	total_currency varchar(50) null,
	total_from decimal(20,2),
	total_to decimal(20,2),
	total_weight_from decimal(20,3),
	total_weight_to decimal(20,3),
	total_volume_from decimal(20,2),
	total_volume_to decimal(20,2),
	total_width_from decimal(20,2),
	total_width_to decimal(20,2),
	total_height_from decimal(20,2),
	total_height_to decimal(20,2),
	total_depth_from decimal(20,2),
	total_depth_to decimal(20,2),
	discount_description varchar(50) null,
	discount_type varchar(50) null,
	discount_quantity int,
	discount_quantity2 int,
	discount_products varchar(50) null,
	discount_currency varchar(50) null,
	discount_amount decimal(20,2),
	discount_orderitems varchar(50) null,
	user_username varchar(255) null,
	user_type varchar(255) null,
	user_group varchar(255) null,
	user_code varchar(255) null,
	period_start varchar(20) null,
	period_end varchar(20) null,
	primary key (id)
);

create index d_title_idx on discounts(title);

create index d_country_idx on discounts(country);

create index d_state_idx on discounts(state);

create index d_product_id_idx on discounts(product_id);

create index d_prod_group_idx on discounts(product_group);

create index d_prod_type_idx on discounts(product_type);

create index d_p_weight_from_idx on discounts(product_weight_from);

create index d_p_weight_to_idx on discounts(product_weight_to);

create index d_p_volume_from_idx on discounts(product_volume_from);

create index d_p_volume_to_idx on discounts(product_volume_to);

create index d_p_width_from_idx on discounts(product_width_from);

create index d_p_width_to_idx on discounts(product_width_to);

create index d_p_height_from_idx on discounts(product_height_from);

create index d_p_height_to_idx on discounts(product_height_to);

create index d_p_depth_from_idx on discounts(product_depth_from);

create index d_p_depth_to_idx on discounts(product_depth_to);

create index d_quan_from_idx on discounts(quantity_from);

create index d_quan_to_idx on discounts(quantity_to);

create index d_total_cur_idx on discounts(total_currency);

create index d_total_from_idx on discounts(total_from);

create index d_total_to_idx on discounts(total_to);

create index d_t_weight_from_idx on discounts(total_weight_from);

create index d_t_weight_to_idx on discounts(total_weight_to);

create index d_t_volume_from_idx on discounts(total_volume_from);

create index d_t_volume_to_idx on discounts(total_volume_to);

create index d_t_width_from_idx on discounts(total_width_from);

create index d_t_width_to_idx on discounts(total_width_to);

create index d_t_height_from_idx on discounts(total_height_from);

create index d_t_height_to_idx on discounts(total_height_to);

create index d_t_depth_from_idx on discounts(total_depth_from);

create index d_t_depth_to_idx on discounts(total_depth_to);

create index d_u_username_idx on discounts(user_username);

create index d_u_group_idx on discounts(user_group);

create index d_u_type_idx on discounts(user_type);

create index d_u_code_idx on discounts(user_code);

create index d_p_start_idx on discounts(period_start);

create index d_p_end_idx on discounts(period_end);

alter table orders add discount_description text null;

alter table orders add discount_total text null;

alter table orderitems add discount_description text null;

alter table orderitems add discount_total text null;

update config set configvalue='7.1' where configname='database_version'

