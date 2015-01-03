alter table content add product_brand nvarchar(255) null;

alter table content add product_colour nvarchar(255) null;

alter table content add product_size nvarchar(255) null;

create index c_brand_idx on content (product_brand);

create index c_colour_idx on content (product_colour);

create index c_size_idx on content (product_size);


alter table content_archive add product_brand nvarchar(255) null;

alter table content_archive add product_colour nvarchar(255) null;

alter table content_archive add product_size nvarchar(255) null;

create index ca_brand_idx on content_archive (product_brand);

create index ca_colour_idx on content_archive (product_colour);

create index ca_size_idx on content_archive (product_size);


alter table content_public add product_brand nvarchar(255) null;

alter table content_public add product_colour nvarchar(255) null;

alter table content_public add product_size nvarchar(255) null;

create index cp_brand_idx on content_public (product_brand);

create index cp_colour_idx on content_public (product_colour);

create index cp_size_idx on content_public (product_size);


alter table orderitems add product_brand ntext null;

alter table orderitems add product_colour ntext null;

alter table orderitems add product_size ntext null;



alter table content add product_cost ntext null;

alter table content add product_location nvarchar(250) null;

alter table content add product_stock_amount int null;

alter table content add product_stock_text nvarchar(250) null;

alter table content add product_restocked_amount int null;

alter table content add product_lowstock_amount int null;

alter table content add product_lowstock_text nvarchar(250) null;

alter table content add product_nostock_order nvarchar(3) null;

alter table content add product_nostock_text nvarchar(250) null;

update content set product_stock_amount = cast(cast(product_stock as varchar(20)) as integer) where product_stock is not null and product_stock not like '';

update content set product_stock_amount=0 where product_stock_amount is null;

update content set product_restocked_amount=0 where product_restocked_amount is null;

update content set product_lowstock_amount=0 where product_lowstock_amount is null;

update content set product_stock_text=product_comment where product_stock_text is null;

update content set product_lowstock_text=product_comment where product_lowstock_text is null;

update content set product_nostock_text=product_comment where product_nostock_text is null;

create index c_ps_amount_idx on content (product_stock_amount);

create index c_prs_amount_idx on content (product_restocked_amount);

create index c_pls_amount_idx on content (product_lowstock_amount);


alter table content_archive add product_cost ntext null;

alter table content_archive add product_location nvarchar(250) null;

alter table content_archive add product_stock_amount int null;

alter table content_archive add product_stock_text nvarchar(250) null;

alter table content_archive add product_restocked_amount int null;

alter table content_archive add product_lowstock_amount int null;

alter table content_archive add product_lowstock_text nvarchar(250) null;

alter table content_archive add product_nostock_order nvarchar(3) null;

alter table content_archive add product_nostock_text nvarchar(250) null;

update content_archive set product_stock_amount = cast(cast(product_stock as varchar(20)) as integer) where product_stock is not null and product_stock not like '';

update content_archive set product_stock_amount=0 where product_stock_amount is null;

update content_archive set product_restocked_amount=0 where product_restocked_amount is null;

update content_archive set product_lowstock_amount=0 where product_lowstock_amount is null;

update content_archive set product_stock_text=product_comment where product_stock_text is null;

update content_archive set product_lowstock_text=product_comment where product_lowstock_text is null;

update content_archive set product_nostock_text=product_comment where product_nostock_text is null;

create index ca_ps_amount_idx on content_archive (product_stock_amount);

create index ca_prs_amount_idx on content_archive (product_restocked_amount);

create index ca_pls_amount_idx on content_archive (product_lowstock_amount);


alter table content_public add product_cost ntext null;

alter table content_public add product_location nvarchar(250) null;

alter table content_public add product_stock_amount int null;

alter table content_public add product_stock_text nvarchar(250) null;

alter table content_public add product_restocked_amount int null;

alter table content_public add product_lowstock_amount int null;

alter table content_public add product_lowstock_text nvarchar(250) null;

alter table content_public add product_nostock_order nvarchar(3) null;

alter table content_public add product_nostock_text nvarchar(250) null;

update content_public set product_stock_amount = cast(cast(product_stock as varchar(20)) as integer) where product_stock is not null and product_stock not like '';

update content_public set product_stock_amount=0 where product_stock_amount is null;

update content_public set product_restocked_amount=0 where product_restocked_amount is null;

update content_public set product_lowstock_amount=0 where product_lowstock_amount is null;

update content_public set product_stock_text=product_comment where product_stock_text is null;

update content_public set product_lowstock_text=product_comment where product_lowstock_text is null;

update content_public set product_nostock_text=product_comment where product_nostock_text is null;

create index cp_ps_amount_idx on content_public (product_stock_amount);

create index cp_prs_amount_idx on content_public (product_restocked_amount);

create index cp_pls_amount_idx on content_public (product_lowstock_amount);



alter table content add related ntext null;

alter table content_archive add related ntext null;

alter table content_public add related ntext null;



alter table users add hardcore_encoding nvarchar(20) null;

alter table users add hardcore_formatblock ntext null;

alter table users add hardcore_fontname ntext null;

alter table users add hardcore_fontsize ntext null;

alter table users add hardcore_customscript ntext null;



alter table users add index_stock ntext null;

alter table users add index_sales ntext null;

alter table users add sales_reports ntext null;



alter table users add shopcart ntext null;

alter table users add wishlist ntext null;

update users set shopcart = '' where shopcart is null;

update users set wishlist = '' where wishlist is null;



alter table orders add createdyear int null;

alter table orders add createdmonth int null;

alter table orders add createdday int null;

alter table orders add createdweek int null;

alter table orders add createdweekday int null;

alter table orders add createdhour int null;

alter table orders add createdmin int null;

alter table orders add createdsec int null;

alter table orders add clienthost nvarchar(50) null;

alter table orders add clienthosttld nvarchar(10) null;

alter table orders add clientuseragent nvarchar(250) null;

alter table orders add clientos nvarchar(10) null;

alter table orders add clientosversion nvarchar(20) null;

alter table orders add clientbrowser nvarchar(50) null;

alter table orders add clientversion nvarchar(20) null;

alter table orders add clientusername nvarchar(50) null;

alter table orders add sessionid nvarchar(20) null;

alter table orders add sessionduration int null;

alter table orders add requesthost nvarchar(50) null;

alter table orders add requestpath nvarchar(250) null;

alter table orders add requestquery nvarchar(250) null;

alter table orders add requestid int null;

alter table orders add requestclass nvarchar(10) null;

alter table orders add requestdatabase nvarchar(50) null;

alter table orders add refererid int null;

alter table orders add refererclass nvarchar(10) null;

alter table orders add refererdatabase nvarchar(50) null;

alter table orders add refererduration int null;

alter table orders add refererhost nvarchar(50) null;

alter table orders add refererpath nvarchar(250) null;

alter table orders add refererquery nvarchar(250) null;

alter table orders add referersearchengine nvarchar(50) null;

alter table orders add referersearchquery nvarchar(250) null;

alter table orders add revision ntext null;

update orders set revision = status where revision is null;

alter table orders drop column status;

alter table orders add status nvarchar(255) null;

alter table orders add status_by nvarchar(255) null;

alter table orders add checkedout nvarchar(255) null;

alter table orders add locked_user int null;

alter table orders add locked_creator int null;

alter table orders add locked_editor int null;

alter table orders add locked_publisher int null;

alter table orders add locked_administrator int null;

alter table orders add session_entry nvarchar(250) null;

alter table orders add affiliate nvarchar(250) null;

alter table orders add temp_order_quantity int null;

update orders set temp_order_quantity = cast(cast(order_quantity as varchar(20)) as integer) where order_quantity is not null and order_quantity not like '';

alter table orders drop column order_quantity;

alter table orders add order_quantity int null;

update orders set order_quantity = temp_order_quantity;

alter table orders drop column temp_order_quantity;

alter table orders add order_currency_base ntext null;

alter table orders add order_subtotal_base decimal(20,2) null;

alter table orders add temp_order_subtotal decimal(20,2) null;

update orders set temp_order_subtotal = cast(cast(order_subtotal as varchar(20)) as decimal(20,2)) where order_subtotal is not null and order_subtotal not like '';

alter table orders drop column order_subtotal;

alter table orders add order_subtotal decimal(20,2) null;

update orders set order_subtotal = temp_order_subtotal;

alter table orders drop column temp_order_subtotal;

alter table orders add discount_total_base decimal(20,2) null;

alter table orders add temp_discount_total decimal(20,2) null;

update orders set temp_discount_total = cast(cast(discount_total as varchar(20)) as decimal(20,2)) where discount_total is not null and discount_total not like '';

alter table orders drop column discount_total;

alter table orders add discount_total decimal(20,2) null;

update orders set discount_total = temp_discount_total;

alter table orders drop column temp_discount_total;

alter table orders add shipping_total_base decimal(20,2) null;

alter table orders add temp_shipping_total decimal(20,2) null;

update orders set temp_shipping_total = cast(cast(shipping_total as varchar(20)) as decimal(20,2)) where shipping_total is not null and shipping_total not like '';

alter table orders drop column shipping_total;

alter table orders add shipping_total decimal(20,2) null;

update orders set shipping_total = temp_shipping_total;

alter table orders drop column temp_shipping_total;

alter table orders add tax_total_base decimal(20,2) null;

alter table orders add temp_tax_total decimal(20,2) null;

update orders set temp_tax_total = cast(cast(tax_total as varchar(20)) as decimal(20,2)) where tax_total is not null and tax_total not like '';

alter table orders drop column tax_total;

alter table orders add tax_total decimal(20,2) null;

update orders set tax_total = temp_tax_total;

alter table orders drop column temp_tax_total;

alter table orders add order_total_base decimal(20,2) null;

alter table orders add temp_order_total decimal(20,2) null;

update orders set temp_order_total = cast(cast(order_total as varchar(20)) as decimal(20,2)) where order_total is not null and order_total not like '';

alter table orders drop column order_total;

alter table orders add order_total decimal(20,2) null;

update orders set order_total = temp_order_total;

alter table orders drop column temp_order_total;

alter table orderitems add temp_item_quantity int null;

update orderitems set temp_item_quantity = cast(cast(item_quantity as varchar(20)) as integer) where item_quantity is not null and item_quantity not like '';

alter table orderitems drop column item_quantity;

alter table orderitems add item_quantity int null;

update orderitems set item_quantity = temp_item_quantity;

alter table orderitems drop column temp_item_quantity;

alter table orderitems add item_subtotal decimal(20,2) null;

alter table orderitems add item_subtotal_base decimal(20,2) null;

alter table orderitems add item_total_base decimal(20,2) null;

alter table orderitems add temp_item_total decimal(20,2) null;

update orderitems set temp_item_total = cast(cast(item_total as varchar(20)) as decimal(20,2)) where item_total is not null and item_total not like '';

alter table orderitems drop column item_total;

alter table orderitems add item_total decimal(20,2) null;

update orderitems set item_total = temp_item_total;

alter table orderitems drop column temp_item_total;

alter table orderitems add discount_total_base decimal(20,2) null;

alter table orderitems add temp_discount_total decimal(20,2) null;

update orderitems set temp_discount_total = cast(cast(discount_total as varchar(20)) as decimal(20,2)) where discount_total is not null and discount_total not like '';

alter table orderitems drop column discount_total;

alter table orderitems add discount_total decimal(20,2) null;

update orderitems set discount_total = temp_discount_total;

alter table orderitems drop column temp_discount_total;

alter table orderitems add shipping_total_base decimal(20,2) null;

alter table orderitems add temp_shipping_total decimal(20,2) null;

update orderitems set temp_shipping_total = cast(cast(shipping_total as varchar(20)) as decimal(20,2)) where shipping_total is not null and shipping_total not like '';

alter table orderitems drop column shipping_total;

alter table orderitems add shipping_total decimal(20,2) null;

update orderitems set shipping_total = temp_shipping_total;

alter table orderitems drop column temp_shipping_total;

alter table orderitems add tax_total_base decimal(20,2) null;

alter table orderitems add temp_tax_total decimal(20,2) null;

update orderitems set temp_tax_total = cast(cast(tax_total as varchar(20)) as decimal(20,2)) where tax_total is not null and tax_total not like '';

alter table orderitems drop column tax_total;

alter table orderitems add tax_total decimal(20,2) null;

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



alter table websites add default_country nvarchar(255) null;

alter table websites add default_state nvarchar(255) null;

alter table websites add default_currency nvarchar(50) null;

alter table websites add default_price ntext null;



alter table versions add default_country nvarchar(255) null;

alter table versions add default_state nvarchar(255) null;

alter table versions add default_price ntext null;



alter table content add custom ntext null;

alter table content_archive add custom ntext null;

alter table content_public add custom ntext null;

alter table data add custom ntext null;

alter table orders add custom ntext null;

alter table users add custom ntext null;

alter table hosting add custom ntext null;



alter table content add history ntext null;

alter table content_archive add history ntext null;

alter table content_public add history ntext null;

alter table users add history ntext null;

alter table orders add history ntext null;

alter table hosting add history ntext null;



alter table content add product_count nvarchar(10) null;

alter table content_archive add product_count nvarchar(10) null;

alter table content_public add product_count nvarchar(10) null;

alter table orderitems add product_count nvarchar(10) null;



alter table content add product_tax nvarchar(50) null;

alter table content_archive add product_tax nvarchar(50) null;

alter table content_public add product_tax nvarchar(50) null;

alter table orderitems add product_tax nvarchar(50) null;



alter table content add product_points_cost ntext null;

alter table content_archive add product_points_cost ntext null;

alter table content_public add product_points_cost ntext null;

alter table orderitems add product_points_cost ntext null;

alter table content add product_points_reward ntext null;

alter table content_archive add product_points_reward ntext null;

alter table content_public add product_points_reward ntext null;

alter table orderitems add product_points_reward ntext null;

alter table users add product_points int;

alter table discounts add user_points_min int null;

alter table discounts add user_points_cost int null;

alter table shipping add user_points_min int null;

alter table shipping add user_points_cost int null;



alter table shipping add user_username nvarchar(255);

alter table shipping add user_type nvarchar(255);

alter table shipping add user_group nvarchar(255);

alter table shipping add user_code nvarchar(255);

alter table shipping add period_start nvarchar(20);

alter table shipping add period_end nvarchar(20);



alter table orders add payment ntext null;



alter table data add adminindex ntext null;



alter table users add title nvarchar(255) null;

alter table users add birthday nvarchar(20) null;

alter table users add gender nvarchar(1) null;



alter table websites add title nvarchar(255) null;



alter table contentgroups add contentclass ntext null;

alter table contenttypes add contentclass ntext null;



alter table content add product_sku nvarchar(250);

alter table content_archive add product_sku nvarchar(250);

alter table content_public add product_sku nvarchar(250);



update config set configvalue='8.1' where configname='database_version'

