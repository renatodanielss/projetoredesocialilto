alter table content add product_brand varchar(255);

alter table content add product_colour varchar(255);

alter table content add product_size varchar(255);

create index c_brand_idx on content (product_brand);

create index c_colour_idx on content (product_colour);

create index c_size_idx on content (product_size);


alter table content_archive add product_brand varchar(255);

alter table content_archive add product_colour varchar(255);

alter table content_archive add product_size varchar(255);

create index ca_brand_idx on content_archive (product_brand);

create index ca_colour_idx on content_archive (product_colour);

create index ca_size_idx on content_archive (product_size);


alter table content_public add product_brand varchar(255);

alter table content_public add product_colour varchar(255);

alter table content_public add product_size varchar(255);

create index cp_brand_idx on content_public (product_brand);

create index cp_colour_idx on content_public (product_colour);

create index cp_size_idx on content_public (product_size);


alter table orderitems add product_brand clob;

alter table orderitems add product_colour clob;

alter table orderitems add product_size clob;



alter table content add product_cost clob;

alter table content add product_location varchar(250);

alter table content add product_stock_amount int;

alter table content add product_stock_text varchar(250);

alter table content add product_restocked_amount int;

alter table content add product_lowstock_amount int;

alter table content add product_lowstock_text varchar(250);

alter table content add product_nostock_order varchar(3);

alter table content add product_nostock_text varchar(250);

update content set product_stock_amount = cast(product_stock as signed integer) where product_stock is not null;

update content set product_stock_amount=0 where product_stock_amount is null;

update content set product_restocked_amount=0 where product_restocked_amount is null;

update content set product_lowstock_amount=0 where product_lowstock_amount is null;

update content set product_stock_text=product_comment where product_stock_text is null;

update content set product_lowstock_text=product_comment where product_lowstock_text is null;

update content set product_nostock_text=product_comment where product_nostock_text is null;

create index c_ps_amount_idx on content (product_stock_amount);

create index c_prs_amount_idx on content (product_restocked_amount);

create index c_pls_amount_idx on content (product_lowstock_amount);


alter table content_archive add product_cost clob;

alter table content_archive add product_location varchar(250);

alter table content_archive add product_stock_amount int;

alter table content_archive add product_stock_text varchar(250);

alter table content_archive add product_restocked_amount int;

alter table content_archive add product_lowstock_amount int;

alter table content_archive add product_lowstock_text varchar(250);

alter table content_archive add product_nostock_order varchar(3);

alter table content_archive add product_nostock_text varchar(250);

update content_archive set product_stock_amount = cast(product_stock as signed integer) where product_stock is not null;

update content_archive set product_stock_amount=0 where product_stock_amount is null;

update content_archive set product_restocked_amount=0 where product_restocked_amount is null;

update content_archive set product_lowstock_amount=0 where product_lowstock_amount is null;

update content_archive set product_stock_text=product_comment where product_stock_text is null;

update content_archive set product_lowstock_text=product_comment where product_lowstock_text is null;

update content_archive set product_nostock_text=product_comment where product_nostock_text is null;

create index ca_ps_amount_idx on content_archive (product_stock_amount);

create index ca_prs_amount_idx on content_archive (product_restocked_amount);

create index ca_pls_amount_idx on content_archive (product_lowstock_amount);


alter table content_public add product_cost clob;

alter table content_public add product_location varchar(250);

alter table content_public add product_stock_amount int;

alter table content_public add product_stock_text varchar(250);

alter table content_public add product_restocked_amount int;

alter table content_public add product_lowstock_amount int;

alter table content_public add product_lowstock_text varchar(250);

alter table content_public add product_nostock_order varchar(3);

alter table content_public add product_nostock_text varchar(250);

update content_public set product_stock_amount = cast(product_stock as signed integer) where product_stock is not null;

update content_public set product_stock_amount=0 where product_stock_amount is null;

update content_public set product_restocked_amount=0 where product_restocked_amount is null;

update content_public set product_lowstock_amount=0 where product_lowstock_amount is null;

update content_public set product_stock_text=product_comment where product_stock_text is null;

update content_public set product_lowstock_text=product_comment where product_lowstock_text is null;

update content_public set product_nostock_text=product_comment where product_nostock_text is null;

create index cp_ps_amount_idx on content_public (product_stock_amount);

create index cp_prs_amount_idx on content_public (product_restocked_amount);

create index cp_pls_amount_idx on content_public (product_lowstock_amount);



alter table content add related clob;

alter table content_archive add related clob;

alter table content_public add related clob;



alter table users add hardcore_encoding varchar(20);

alter table users add hardcore_formatblock clob;

alter table users add hardcore_fontname clob;

alter table users add hardcore_fontsize clob;

alter table users add hardcore_customscript clob;



alter table users add index_stock clob;

alter table users add index_sales clob;

alter table users add sales_reports clob;



alter table users add shopcart clob;

alter table users add wishlist clob;

update users set shopcart = '' where shopcart is null;

update users set wishlist = '' where wishlist is null;



alter table orders add createdyear int;

alter table orders add createdmonth int;

alter table orders add createdday int;

alter table orders add createdweek int;

alter table orders add createdweekday int;

alter table orders add createdhour int;

alter table orders add createdmin int;

alter table orders add createdsec int;

alter table orders add clienthost varchar(50);

alter table orders add clienthosttld varchar(10);

alter table orders add clientuseragent varchar(250);

alter table orders add clientos varchar(10);

alter table orders add clientosversion varchar(20);

alter table orders add clientbrowser varchar(50);

alter table orders add clientversion varchar(20);

alter table orders add clientusername varchar(50);

alter table orders add sessionid varchar(20);

alter table orders add sessionduration int;

alter table orders add requesthost varchar(50);

alter table orders add requestpath varchar(250);

alter table orders add requestquery varchar(250);

alter table orders add requestid int;

alter table orders add requestclass varchar(10);

alter table orders add requestdatabase varchar(50);

alter table orders add refererid int;

alter table orders add refererclass varchar(10);

alter table orders add refererdatabase varchar(50);

alter table orders add refererduration int;

alter table orders add refererhost varchar(50);

alter table orders add refererpath varchar(250);

alter table orders add refererquery varchar(250);

alter table orders add referersearchengine varchar(50);

alter table orders add referersearchquery varchar(250);

alter table orders add revision clob;

update orders set revision = status where revision is null;

alter table orders drop column status;

alter table orders add status varchar(255);

alter table orders add status_by varchar(255);

alter table orders add checkedout varchar(255);

alter table orders add locked_user int;

alter table orders add locked_creator int;

alter table orders add locked_editor int;

alter table orders add locked_publisher int;

alter table orders add locked_administrator int;

alter table orders add session_entry varchar(250);

alter table orders add affiliate varchar(250);

alter table orders add temp_order_quantity int;

update orders set temp_order_quantity = cast(order_quantity as int) where order_quantity is not null;

alter table orders drop column order_quantity;

alter table orders add order_quantity int;

update orders set order_quantity = temp_order_quantity;

alter table orders drop column temp_order_quantity;

alter table orders add order_currency_base clob;

alter table orders add order_subtotal_base decimal(20,2);

alter table orders add temp_order_subtotal decimal(20,2);

update orders set temp_order_subtotal = cast(order_subtotal as decimal(20,2)) where order_subtotal is not null;

alter table orders drop column order_subtotal;

alter table orders add order_subtotal decimal(20,2);

update orders set order_subtotal = temp_order_subtotal;

alter table orders drop column temp_order_subtotal;

alter table orders add discount_total_base decimal(20,2);

alter table orders add temp_discount_total decimal(20,2);

update orders set temp_discount_total = cast(discount_total as decimal(20,2)) where discount_total is not null;

alter table orders drop column discount_total;

alter table orders add discount_total decimal(20,2);

update orders set discount_total = temp_discount_total;

alter table orders drop column temp_discount_total;

alter table orders add shipping_total_base decimal(20,2);

alter table orders add temp_shipping_total decimal(20,2);

update orders set temp_shipping_total = cast(shipping_total as decimal(20,2)) where shipping_total is not null;

alter table orders drop column shipping_total;

alter table orders add shipping_total decimal(20,2);

update orders set shipping_total = temp_shipping_total;

alter table orders drop column temp_shipping_total;

alter table orders add tax_total_base decimal(20,2);

alter table orders add temp_tax_total decimal(20,2);

update orders set temp_tax_total = cast(tax_total as decimal(20,2)) where tax_total is not null;

alter table orders drop column tax_total;

alter table orders add tax_total decimal(20,2);

update orders set tax_total = temp_tax_total;

alter table orders drop column temp_tax_total;

alter table orders add order_total_base decimal(20,2);

alter table orders add temp_order_total decimal(20,2);

update orders set temp_order_total = cast(order_total as decimal(20,2)) where order_total is not null;

alter table orders drop column order_total;

alter table orders add order_total decimal(20,2);

update orders set order_total = temp_order_total;

alter table orders drop column temp_order_total;

alter table orderitems add temp_item_quantity int;

update orderitems set temp_item_quantity = cast(item_quantity as int) where item_quantity is not null;

alter table orderitems drop column item_quantity;

alter table orderitems add item_quantity int;

update orderitems set item_quantity = temp_item_quantity;

alter table orderitems drop column temp_item_quantity;

alter table orderitems add item_subtotal decimal(20,2);

alter table orderitems add item_subtotal_base decimal(20,2);

alter table orderitems add item_total_base decimal(20,2);

alter table orderitems add temp_item_total decimal(20,2);

update orderitems set temp_item_total = cast(item_total as decimal(20,2)) where item_total is not null;

alter table orderitems drop column item_total;

alter table orderitems add item_total decimal(20,2);

update orderitems set item_total = temp_item_total;

alter table orderitems drop column temp_item_total;

alter table orderitems add discount_total_base decimal(20,2);

alter table orderitems add temp_discount_total decimal(20,2);

update orderitems set temp_discount_total = cast(discount_total as decimal(20,2)) where discount_total is not null;

alter table orderitems drop column discount_total;

alter table orderitems add discount_total decimal(20,2);

update orderitems set discount_total = temp_discount_total;

alter table orderitems drop column temp_discount_total;

alter table orderitems add shipping_total_base decimal(20,2);

alter table orderitems add temp_shipping_total decimal(20,2);

update orderitems set temp_shipping_total = cast(shipping_total as decimal(20,2)) where shipping_total is not null;

alter table orderitems drop column shipping_total;

alter table orderitems add shipping_total decimal(20,2);

update orderitems set shipping_total = temp_shipping_total;

alter table orderitems drop column temp_shipping_total;

alter table orderitems add tax_total_base decimal(20,2);

alter table orderitems add temp_tax_total decimal(20,2);

update orderitems set temp_tax_total = cast(tax_total as decimal(20,2)) where tax_total is not null;

alter table orderitems drop column tax_total;

alter table orderitems add tax_total decimal(20,2);

update orderitems set tax_total = temp_tax_total;

alter table orderitems drop column temp_tax_total;

update orders set user_id=0 where user_id is null;

update orders set order_quantity=0 where order_quantity is null;

update orders set order_subtotal=0 where order_subtotal is null;

update orders set discount_total=0 where discount_total is null;

update orders set shipping_total=0 where shipping_total is null;

update orders set tax_total=0 where tax_total is null;

update orders set order_total=0 where order_total is null;

update orderitems set item_quantity=0 where item_quantity is null;

update orderitems set item_total=0 where item_total is null;

update orderitems set discount_total=0 where discount_total is null;

update orderitems set shipping_total=0 where shipping_total is null;

update orderitems set tax_total=0 where tax_total is null;



alter table websites add default_country varchar(255);

alter table websites add default_state varchar(255);

alter table websites add default_currency varchar(50);

alter table websites add default_price clob;



alter table versions add default_country varchar(255);

alter table versions add default_state varchar(255);

alter table versions add default_price clob;



alter table content add custom clob;

alter table content_archive add custom clob;

alter table content_public add custom clob;

alter table data add custom clob;

alter table orders add custom clob;

alter table users add custom clob;

alter table hosting add custom clob;



alter table content add history clob;

alter table content_archive add history clob;

alter table content_public add history clob;

alter table users add history clob;

alter table orders add history clob;

alter table hosting add history clob;



alter table content add product_count varchar(10);

alter table content_archive add product_count varchar(10);

alter table content_public add product_count varchar(10);

alter table orderitems add product_count varchar(10);



alter table content add product_tax varchar(50);

alter table content_archive add product_tax varchar(50);

alter table content_public add product_tax varchar(50);

alter table orderitems add product_tax varchar(50);



alter table content add product_points_cost clob;

alter table content_archive add product_points_cost clob;

alter table content_public add product_points_cost clob;

alter table orderitems add product_points_cost clob;

alter table content add product_points_reward clob;

alter table content_archive add product_points_reward clob;

alter table content_public add product_points_reward clob;

alter table orderitems add product_points_reward clob;

alter table users add product_points int;

alter table discounts add user_points_min int;

alter table discounts add user_points_cost int;

alter table shipping add user_points_min int;

alter table shipping add user_points_cost int;



alter table shipping add user_username varchar(255);

alter table shipping add user_type varchar(255);

alter table shipping add user_group varchar(255);

alter table shipping add user_code varchar(255);

alter table shipping add period_start varchar(20);

alter table shipping add period_end varchar(20);



alter table orders add payment clob;



alter table data add adminindex clob;



alter table users add title varchar(255);

alter table users add birthday varchar(20);

alter table users add gender varchar(1);



alter table websites add title varchar(255);



alter table contentgroups add contentclass clob;

alter table contenttypes add contentclass clob;



alter table content add product_sku varchar(250);

alter table content_archive add product_sku varchar(250);

alter table content_public add product_sku varchar(250);



update config set configvalue='8.1' where configname='database_version'

