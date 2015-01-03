
alter table orders modify column clienthost varchar(250);

alter table usagelog modify column clienthost varchar(250);


alter table content modify column product_code varchar(255);

alter table content modify column product_sku varchar(255);

alter table content modify column product_location varchar(255);

alter table content modify column product_stock_text varchar(255);

alter table content modify column product_lowstock_text varchar(255);

alter table content modify column product_nostock_text varchar(255);

alter table content modify column product_price varchar(255);

alter table content modify column product_currency varchar(255);

alter table content modify column product_price varchar(255);

alter table content modify column product_period varchar(255);

alter table content modify column product_weight varchar(255);

alter table content modify column product_volume varchar(255);

alter table content modify column product_width varchar(255);

alter table content modify column product_height varchar(255);

alter table content modify column product_depth varchar(255);

alter table content modify column product_points_cost varchar(255);

alter table content modify column product_points_reward varchar(255);

alter table content modify column product_stock varchar(255);

alter table content modify column product_cost varchar(255);


alter table content_archive modify column product_code varchar(255);

alter table content_archive modify column product_sku varchar(255);

alter table content_archive modify column product_location varchar(255);

alter table content_archive modify column product_stock_text varchar(255);

alter table content_archive modify column product_lowstock_text varchar(255);

alter table content_archive modify column product_nostock_text varchar(255);

alter table content_archive modify column product_price varchar(255);

alter table content_archive modify column product_currency varchar(255);

alter table content_archive modify column product_price varchar(255);

alter table content_archive modify column product_period varchar(255);

alter table content_archive modify column product_weight varchar(255);

alter table content_archive modify column product_volume varchar(255);

alter table content_archive modify column product_width varchar(255);

alter table content_archive modify column product_height varchar(255);

alter table content_archive modify column product_depth varchar(255);

alter table content_archive modify column product_points_cost varchar(255);

alter table content_archive modify column product_points_reward varchar(255);

alter table content_archive modify column product_stock varchar(255);

alter table content_archive modify column product_cost varchar(255);


alter table content_public modify column product_code varchar(255);

alter table content_public modify column product_sku varchar(255);

alter table content_public modify column product_location varchar(255);

alter table content_public modify column product_stock_text varchar(255);

alter table content_public modify column product_lowstock_text varchar(255);

alter table content_public modify column product_nostock_text varchar(255);

alter table content_public modify column product_price varchar(255);

alter table content_public modify column product_currency varchar(255);

alter table content_public modify column product_price varchar(255);

alter table content_public modify column product_period varchar(255);

alter table content_public modify column product_weight varchar(255);

alter table content_public modify column product_volume varchar(255);

alter table content_public modify column product_width varchar(255);

alter table content_public modify column product_height varchar(255);

alter table content_public modify column product_depth varchar(255);

alter table content_public modify column product_points_cost varchar(255);

alter table content_public modify column product_points_reward varchar(255);

alter table content_public modify column product_stock varchar(255);

alter table content_public modify column product_cost varchar(255);



alter table orderitems modify column product_code varchar(255);

alter table orderitems modify column product_sku varchar(255);

alter table orderitems modify column product_currency varchar(255);

alter table orderitems modify column product_price varchar(255);

alter table orderitems modify column product_period varchar(255);

alter table orderitems modify column product_weight varchar(255);

alter table orderitems modify column product_volume varchar(255);

alter table orderitems modify column product_width varchar(255);

alter table orderitems modify column product_height varchar(255);

alter table orderitems modify column product_depth varchar(255);

alter table orderitems modify column product_brand varchar(255);

alter table orderitems modify column product_colour varchar(255);

alter table orderitems modify column product_size varchar(255);

alter table orderitems modify column product_points_cost varchar(255);

alter table orderitems modify column product_points_reward varchar(255);

alter table orderitems modify column product_stock varchar(255);



alter table orders modify column card_type varchar(255);

alter table orders modify column card_number varchar(255);

alter table orders modify column card_issuedmonth varchar(255);

alter table orders modify column card_issuedyear varchar(255);

alter table orders modify column card_expirymonth varchar(255);

alter table orders modify column card_expiryyear varchar(255);

alter table orders modify column card_name varchar(255);

alter table orders modify column card_cvc varchar(255);

alter table orders modify column card_issue varchar(255);

alter table orders modify column card_postalcode varchar(255);

alter table orders modify column delivery_name varchar(255);

alter table orders modify column delivery_organisation varchar(255);

alter table orders modify column delivery_postalcode varchar(255);

alter table orders modify column delivery_city varchar(255);

alter table orders modify column delivery_state varchar(255);

alter table orders modify column delivery_country varchar(255);

alter table orders modify column delivery_phone varchar(255);

alter table orders modify column delivery_fax varchar(255);

alter table orders modify column delivery_email varchar(255);

alter table orders modify column delivery_website varchar(255);

alter table orders modify column invoice_name varchar(255);

alter table orders modify column invoice_organisation varchar(255);

alter table orders modify column invoice_postalcode varchar(255);

alter table orders modify column invoice_city varchar(255);

alter table orders modify column invoice_state varchar(255);

alter table orders modify column invoice_country varchar(255);

alter table orders modify column invoice_phone varchar(255);

alter table orders modify column invoice_fax varchar(255);

alter table orders modify column invoice_email varchar(255);

alter table orders modify column invoice_website varchar(255);



alter table users modify column card_type varchar(255);

alter table users modify column card_number varchar(255);

alter table users modify column card_issuedmonth varchar(255);

alter table users modify column card_issuedyear varchar(255);

alter table users modify column card_expirymonth varchar(255);

alter table users modify column card_expiryyear varchar(255);

alter table users modify column card_name varchar(255);

alter table users modify column card_cvc varchar(255);

alter table users modify column card_issue varchar(255);

alter table users modify column card_postalcode varchar(255);

alter table users modify column delivery_name varchar(255);

alter table users modify column delivery_organisation varchar(255);

alter table users modify column delivery_postalcode varchar(255);

alter table users modify column delivery_city varchar(255);

alter table users modify column delivery_state varchar(255);

alter table users modify column delivery_country varchar(255);

alter table users modify column delivery_phone varchar(255);

alter table users modify column delivery_fax varchar(255);

alter table users modify column delivery_email varchar(255);

alter table users modify column delivery_website varchar(255);

alter table users modify column invoice_name varchar(255);

alter table users modify column invoice_organisation varchar(255);

alter table users modify column invoice_postalcode varchar(255);

alter table users modify column invoice_city varchar(255);

alter table users modify column invoice_state varchar(255);

alter table users modify column invoice_country varchar(255);

alter table users modify column invoice_phone varchar(255);

alter table users modify column invoice_fax varchar(255);

alter table users modify column invoice_email varchar(255);

alter table users modify column invoice_website varchar(255);



drop index c_stylesheet_idx on content;

drop index c_template_idx on content;

drop index c_image1_idx on content;

drop index c_image2_idx on content;

drop index c_image3_idx on content;

drop index c_file1_idx on content;

drop index c_file2_idx on content;

drop index c_file3_idx on content;

drop index c_link1_idx on content;

drop index c_link2_idx on content;

drop index c_link3_idx on content;


drop index ca_stylesheet_idx on content_archive;

drop index ca_template_idx on content_archive;

drop index ca_image1_idx on content_archive;

drop index ca_image2_idx on content_archive;

drop index ca_image3_idx on content_archive;

drop index ca_file1_idx on content_archive;

drop index ca_file2_idx on content_archive;

drop index ca_file3_idx on content_archive;

drop index ca_link1_idx on content_archive;

drop index ca_link2_idx on content_archive;

drop index ca_link3_idx on content_archive;


drop index cp_stylesheet_idx on content_public;

drop index cp_template_idx on content_public;

drop index cp_image1_idx on content_public;

drop index cp_image2_idx on content_public;

drop index cp_image3_idx on content_public;

drop index cp_file1_idx on content_public;

drop index cp_file2_idx on content_public;

drop index cp_file3_idx on content_public;

drop index cp_link1_idx on content_public;

drop index cp_link2_idx on content_public;

drop index cp_link3_idx on content_public;



create index c_price_idx on content (product_price);

create index c_filename_idx on content (server_filename);

#create index c_content_idx on content (content);

#create index c_summary_idx on content (summary);

#create index c_author_idx on content (author);

#create index c_description_idx on content (description);

#create index c_keywords_idx on content (keywords);

#create index c_metainfo_idx on content (metainfo);

#create index c_productinfo_idx on content (product_info);


create index ca_page_first_idx on content_archive (page_first);

create index ca_page_last_idx on content_archive (page_last);

create index ca_page_next_idx on content_archive (page_next);

create index ca_page_prev_idx on content_archive (page_previous);

create index ca_page_top_idx on content_archive (page_top);

create index ca_page_up_idx on content_archive (page_up);

create index ca_price_idx on content_archive (product_price);

#create index ca_content_idx on content_archive (content);

#create index ca_summary_idx on content_archive (summary);

#create index ca_author_idx on content_archive (author);

#create index ca_description_idx on content_archive (description);

#create index ca_keywords_idx on content_archive (keywords);

#create index ca_metainfo_idx on content_archive (metainfo);

#create index ca_productinfo_idx on content_archive (product_info);


create index cp_price_idx on content_public (product_price);

#create index cp_content_idx on content_public (content);

#create index cp_summary_idx on content_public (summary);

#create index cp_author_idx on content_public (author);

#create index cp_description_idx on content_public (description);

#create index cp_keywords_idx on content_public (keywords);

#create index cp_metainfo_idx on content_public (metainfo);

#create index cp_productinfo_idx on content_public (product_info);



alter table content add device varchar(255);

alter table content_archive add device varchar(255);

alter table content_public add device varchar(255);

create index c_device_idx on content (device);

create index ca_device_idx on content_archive (device);

create index cp_device_idx on content_public (device);

update content set device='' where device is null;

update content_archive set device='' where device is null;

update content_public set device='' where device is null;



alter table orderitems add device varchar(255);

update orderitems set device='' where device is null;

alter table orderitems add usersegment varchar(255);

update orderitems set usersegment='' where usersegment is null;

alter table orderitems add usertest varchar(255);

update orderitems set usertest='' where usertest is null;



alter table content add htmlattributes clob;

alter table content_archive add htmlattributes clob;

alter table content_public add htmlattributes clob;

update content set htmlattributes='' where htmlattributes is null;

update content_archive set htmlattributes='' where htmlattributes is null;

update content_public set htmlattributes='' where htmlattributes is null;



alter table orders add clientdevice varchar(20);

alter table orders add clientdeviceid varchar(20);

alter table orders add clientdeviceversion varchar(50);

update orders set clientdevice='' where clientdevice is null;

update orders set clientdeviceid='' where clientdeviceid is null;

update orders set clientdeviceversion='' where clientdeviceversion is null;

alter table usagelog add clientdevice varchar(20);

alter table usagelog add clientdeviceid varchar(20);

alter table usagelog add clientdeviceversion varchar(50);

update usagelog set clientdevice='' where clientdevice is null;

update usagelog set clientdeviceid='' where clientdeviceid is null;

update usagelog set clientdeviceversion='' where clientdeviceversion is null;

create index u_clientdevice_idx on usagelog (clientdevice);

create index u_clientdeviceid_idx on usagelog (clientdeviceid);

create index u_clientdevicev_idx on usagelog (clientdeviceversion);



alter table users add birthdate varchar(10);

!update users set birthdate=birthday where birthdate is null;

!update users set birthday='' where birthdate=birthday;

alter table users add birthyear varchar(4);

alter table users add birthmonth varchar(2);

!alter table users add birthday varchar(2);

!alter table users modify column birthday varchar(2);

alter table users add gender varchar(1);

alter table users add title varchar(255);

update users set birthdate='' where birthdate is null;

update users set birthyear='' where birthyear is null;

update users set birthmonth='' where birthmonth is null;

update users set birthday='' where birthday is null;

update users set gender='' where gender is null;

update users set title='' where title is null;



alter table users add index_segments clob;

alter table users add index_usertests clob;

alter table users add index_heatmaps clob;

update users set index_segments='' where index_segments is null;

update users set index_usertests='' where index_usertests is null;

update users set index_heatmaps='' where index_heatmaps is null;

alter table users add index_search clob;

alter table users add index_searchadvanced clob;

alter table users add index_searchreplace clob;

update users set index_search='' where index_search is null;

update users set index_searchadvanced='' where index_searchadvanced is null;

update users set index_searchreplace='' where index_searchreplace is null;



alter table hosting add experience_license varchar(255);

update hosting set experience_license='' where experience_license is null;



create table segments (
	id int generated by default as identity,
	segmentid varchar(255),
	segment varchar(255),
	weight varchar(10),
	active varchar(1),
	scheduled varchar(20),
	unscheduled varchar(20),
	datetimefull clob,
	datetimeyear clob,
	datetimemonth clob,
	datetimeday clob,
	datetimeweek clob,
	datetimeweekday clob,
	datetimehour clob,
	datetimemin clob,
	datetimesec clob,
	clientaddr clob,
	clienthost clob,
	clientuser clob,
	clientuseragent clob,
	clientos clob,
	clientosversion clob,
	clientbrowser clob,
	clientversion clob,
	clientdevice clob,
	clientdeviceid clob,
	clientdeviceversion clob,
	preferredlanguage clob,
	acceptedlanguages clob,
	requesthost clob,
	requestport clob,
	requestmethod clob,
	requestpath clob,
	requestquery clob,
	requestprotocol clob,
	requestid clob,
	requestclass clob,
	requestdatabase clob,
	refererid clob,
	refererclass clob,
	refererdatabase clob,
	refererhost clob,
	refererpath clob,
	refererquery clob,
	referersearchengine clob,
	referersearchquery clob,
	geoipcountry clob,
	geoipregion clob,
	geoipcity clob,
	geoippostalcode clob,
	geoiplatitude clob,
	geoiplongitude clob,
	geoiptimezone clob,
	created clob,
	updated clob,
	created_by clob,
	updated_by clob,
	keywords clob,
	description clob,
	birthdate clob,
	birthyear clob,
	birthmonth clob,
	birthday clob,
	age clob,
	gender clob,
	title clob,
	name clob,
	organisation clob,
	username clob,
	password clob,
	email clob,
	userclass clob,
	usertype clob,
	usergroup clob,
	scheduled_last clob,
	scheduled_publish clob,
	scheduled_notify clob,
	scheduled_unpublish clob,
	scheduled_publish_email clob,
	scheduled_notify_email clob,
	scheduled_unpublish_email clob,
	card_type clob,
	card_number clob,
	card_issuedmonth clob,
	card_issuedyear clob,
	card_expirymonth clob,
	card_expiryyear clob,
	card_name clob,
	card_cvc clob,
	card_issue clob,
	card_postalcode clob,
	delivery_name clob,
	delivery_organisation clob,
	delivery_address clob,
	delivery_postalcode clob,
	delivery_city clob,
	delivery_state clob,
	delivery_country clob,
	delivery_phone clob,
	delivery_fax clob,
	delivery_email clob,
	delivery_website clob,
	invoice_name clob,
	invoice_organisation clob,
	invoice_address clob,
	invoice_postalcode clob,
	invoice_city clob,
	invoice_state clob,
	invoice_country clob,
	invoice_phone clob,
	invoice_fax clob,
	invoice_email clob,
	invoice_website clob,
	notes clob,
	userinfo clob,
	shopcart clob,
	wishlist clob,
	product_points clob,
	visits clob,
	goals clob,
	custom clob,
	primary key (id)
);

create index segments_active on segments (active);

create index segments_scheduled on segments (scheduled);

create index segments_unscheduled on segments (unscheduled);



create table usertests (
	id int generated by default as identity,
	usertest varchar(125),
	description clob,
	type varchar(10),
	variants clob,
	probability varchar(10),
	visitors varchar(3),
	visits varchar(10),
	goals clob,
	confidence varchar(10),
	active varchar(1),
	scheduled varchar(20),
	unscheduled varchar(20),
	primary key (id)
);

create index usertests_usertest on usertests (usertest);



alter table content add usersegment varchar(255);

alter table content_archive add usersegment varchar(255);

alter table content_public add usersegment varchar(255);

create index c_usersegment on content (usersegment);

create index ca_usersegment on content_archive (usersegment);

create index cp_usersegment on content_public (usersegment);

update content set usersegment = '' where usersegment is null;

update content_archive set usersegment = '' where usersegment is null;

update content_public set usersegment = '' where usersegment is null;



alter table content add usertest varchar(255);

alter table content_archive add usertest varchar(255);

alter table content_public add usertest varchar(255);

create index c_usertest on content (usertest);

create index ca_usertest on content_archive (usertest);

create index cp_usertest on content_public (usertest);

update content set usertest = '' where usertest is null;

update content_archive set usertest = '' where usertest is null;

update content_public set usertest = '' where usertest is null;



alter table content add heatmap varchar(50);

alter table content add heatmapalign varchar(1);

alter table content_archive add heatmap varchar(50);

alter table content_archive add heatmapalign varchar(1);

alter table content_public add heatmap varchar(50);

alter table content_public add heatmapalign varchar(1);

create index c_heatmap on content (heatmap);

create index ca_heatmap on content_archive (heatmap);

create index cp_heatmap on content_public (heatmap);

update content set heatmap = '' where heatmap is null;

update content_archive set heatmap = '' where heatmap is null;

update content_public set heatmap = '' where heatmap is null;



alter table content add usagelog varchar(1);

alter table content_archive add usagelog varchar(1);

alter table content_public add usagelog varchar(1);

create index c_usagelog on content (usagelog);

create index ca_usagelog on content_archive (usagelog);

create index cp_usagelog on content_public (usagelog);

update content set usagelog = '' where usagelog is null;

update content_archive set usagelog = '' where usagelog is null;

update content_public set usagelog = '' where usagelog is null;



alter table content add segmentation clob;

alter table content_archive add segmentation clob;

alter table content_public add segmentation clob;

update content set segmentation = '' where segmentation is null;

update content_archive set segmentation = '' where segmentation is null;

update content_public set segmentation = '' where segmentation is null;



alter table content_archive add archived varchar(20);

alter table content_archive add archived_by varchar(255);

update content set archived = '' where archived is null;

update content set archived_by = '' where archived_by is null;

create index ca_archived_idx on content_archive (archived);

create index ca_archived_by_idx on content_archive (archived_by);



alter table orders add usersegments clob;

alter table orders add usertests clob;

update orders set usersegments='' where usersegments is null;

update orders set usertests='' where usertests is null;

alter table usagelog add usersegments clob;

alter table usagelog add usertests clob;

update usagelog set usersegments='' where usersegments is null;

update usagelog set usertests='' where usertests is null;



alter table orders add visitorid varchar(50);

alter table orders add visitorduration int;

alter table usagelog add visitorid varchar(50);

alter table usagelog add visitorduration int;

create index u_visitorid_idx on usagelog (visitorid);

create index u_visitorduration_idx on usagelog (visitorduration);

alter table usagelog add pageviews int;

alter table usagelog add visits int;

alter table usagelog add visitors int;

alter table usagelog add clienthosts int;

alter table usagelog add clienthostids clob;

alter table usagelog add visitorids clob;

alter table usagelog add sessionids clob;

alter table usagelog add summarised varchar(250);

create index u_summarised on usagelog (summarised);



alter table usagelog add affiliate varchar(250);

update usagelog set affiliate='' where affiliate is null;

create index u_affiliate_idx on usagelog (affiliate);



create table heatmaps (
	id varchar(50) not null,
	logged varchar(20),
	action varchar(5),
	x int,
	y int,
	w int,
	h int
);

create index heatmap_id_idx on heatmaps (id);

create index heatmap_logged_idx on heatmaps (logged);

create index heatmap_action_idx on heatmaps (action);



create table usagedata (
	created varchar(20),
	title varchar(250),
	report varchar(50) not null,
	report_limit varchar(50) not null,
	report_period varchar(50) not null,
	period_start varchar(20) not null,
	period_end varchar(20) not null,
	details clob,
	data clob,
	primary key (report,report_limit,report_period,period_start,period_end)
);

create index udata_title on usagedata (title);

create index udata_report on usagedata (report);

create index udata_limit on usagedata (report_limit);

create index udata_period on usagedata (report_period);

create index udata_start on usagedata (period_start);

create index udata_end on usagedata (period_end);



update config set configvalue='9.0' where configname='database_version';

