create table config (
	configname varchar(255) not null unique,
	configvalue text,
	primary key (configname)
);

create table content (
	id int not null auto_increment,
	locked int,
	locked_user int,
	locked_creator int,
	locked_developer int,
	locked_editor int,
	locked_publisher int,
	locked_administrator int,
	locked_schedule int,
	locked_unschedule int,
	created varchar(20),
	updated varchar(20),
	published varchar(20),
	unpublished varchar(20),
	scheduled_publish varchar(20),
	scheduled_unpublish varchar(20),
	requested_publish varchar(20),
	requested_unpublish varchar(20),
	status varchar(255),
	status_by varchar(255),
	created_by varchar(255),
	updated_by varchar(255),
	published_by varchar(255),
	unpublished_by varchar(255),
	revision text,
	history text,
	version varchar(255),
	version_master int,
	device varchar(255),
QQQ	usersegment varchar(255),
QQQ	usertest varchar(255),
	heatmap varchar(50),
	heatmapalign varchar(1),
	usagelog varchar(1),
	title varchar(255),
	searchable varchar(3),
	menuitem varchar(3),
	author text,
	keywords text,
	description text,
	metainfo text,
	segmentation text,
	doctype text,
	htmlattributes text,
	htmlheader text,
	htmlbodyonload text,
	url text,
	content longtext,
	summary longtext,
	template int,
	stylesheet int,
	scripts int,
	image1 int,
	image2 int,
	image3 int,
	file1 int,
	file2 int,
	file3 int,
	link1 int,
	link2 int,
	link3 int,
	contentformat varchar(10),
	contentclass varchar(255),
	contenttype varchar(255),
	contentgroup varchar(255),
	contentbundle varchar(255),
	contentpackage varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	checkedout varchar(255),
	page_top int,
	page_up int,
	page_next int,
	page_previous int,
	page_first int,
	page_last int,
	related int,
	upload_filename text,
	server_filename text,
	product_code varchar(255),
	product_sku varchar(255),
	product_currency varchar(50),
	product_price varchar(255),
	product_period varchar(255),
	product_weight varchar(255),
	product_volume varchar(255),
	product_width varchar(255),
	product_height varchar(255),
	product_depth varchar(255),
	product_brand varchar(255),
	product_colour varchar(255),
	product_size varchar(255),
	product_count varchar(10),
	product_tax varchar(50),
	product_points_cost varchar(255),
	product_points_reward varchar(255),
	product_stock varchar(255),
	product_cost varchar(255),
	product_location varchar(255),
	product_stock_amount int,
	product_stock_text varchar(255),
	product_restocked_amount int,
	product_lowstock_amount int,
	product_lowstock_text varchar(255),
	product_nostock_order varchar(3),
	product_nostock_text varchar(255),
	product_comment text,
	product_email int,
	product_url text,
	product_info text,
	product_delivery text,
	product_user varchar(255),
	product_page int,
	product_program text,
	product_hosting varchar(255),
	product_options text,
	product_content int,
	product_contentgroup varchar(255),
	product_contenttype varchar(255),
	product_imagegroup varchar(255),
	product_imagetype varchar(255),
	product_filegroup varchar(255),
	product_filetype varchar(255),
	product_linkgroup varchar(255),
	product_linktype varchar(255),
	product_productgroup varchar(255),
	product_producttype varchar(255),
	product_usergroup varchar(255),
	product_usertype varchar(255),
	custom text,
	futurechar1 varchar(255),
	futurechar2 varchar(255),
	futurechar3 varchar(255),
	futuretext1 text,
	futuretext2 text,
	futuretext3 text,
	futureint1 int,
	futureint2 int,
	futureint3 int,
	primary key (id)
);

create index c_title_idx on content (title);

!create index c_content_idx on content (content);

!create index c_summary_idx on content (summary);

!create index c_author_idx on content (author);

!create index c_description_idx on content (description);

!create index c_keywords_idx on content (keywords);

create index c_searchable_idx on content (searchable);


create index c_version_idx on content (version);

create index c_v_master_idx on content (version_master);

create index c_device_idx on content (device);

create index c_usersegment_idx on content (usersegment);

create index c_usertest_idx on content (usertest);

create index c_heatmap on content (heatmap);

create index c_usagelog on content (usagelog);

create index c_filename_idx on content (server_filename(250));

create index c_contentclass_idx on content (contentclass);

create index c_contentgroup_idx on content (contentgroup);

create index c_contenttype_idx on content (contenttype);

create index c_cbundle_idx on content (contentbundle);

create index c_cpackage_idx on content (contentpackage);


create index c_created_idx on content (created);

create index c_updated_idx on content (updated);

create index c_published_idx on content (published);

create index c_unpublished_idx on content (unpublished);

create index c_status_idx on content (status);

create index c_created_by_idx on content (created_by);

create index c_updated_by_idx on content (updated_by);

create index c_published_by_idx on content (published_by);

create index c_unpublished_by_idx on content (unpublished_by);

create index c_status_by_idx on content (status_by);

create index c_sched_publish_idx on content (scheduled_publish);

create index c_sched_unpublish_idx on content (scheduled_unpublish);

create index c_req_publish_idx on content (requested_publish);

create index c_req_unpublish_idx on content (requested_unpublish);


create index c_u_type_idx on content (users_type);

create index c_u_group_idx on content (users_group);

create index c_d_type_idx on content (developers_type);

create index c_d_group_idx on content (developers_group);

create index c_c_type_idx on content (creators_type);

create index c_c_group_idx on content (creators_group);

create index c_e_type_idx on content (editors_type);

create index c_e_group_idx on content (editors_group);

create index c_p_type_idx on content (publishers_type);

create index c_p_group_idx on content (publishers_group);

create index c_a_type_idx on content (administrators_type);

create index c_a_group_idx on content (administrators_group);

create index c_cout_idx on content (checkedout);

create index c_l_schedule_idx on content (locked_schedule);

create index c_l_unschedule_idx on content (locked_unschedule);


create index c_page_top_idx on content (page_top);

create index c_page_up_idx on content (page_up);

create index c_page_next_idx on content (page_next);

create index c_page_prev_idx on content (page_previous);

create index c_page_first_idx on content (page_first);

create index c_page_last_idx on content (page_last);


create index c_brand_idx on content (product_brand);

create index c_colour_idx on content (product_colour);

create index c_size_idx on content (product_size);

create index c_price_idx on content (product_price);

create index c_ps_amount_idx on content (product_stock_amount);

create index c_prs_amount_idx on content (product_restocked_amount);

create index c_pls_amount_idx on content (product_lowstock_amount);


!create index c_metainfo_idx on content (metainfo);

!create index c_productinfo_idx on content (product_info);


create table content_elements (
	content_id int,
	element varchar(255),
	element_id int,
	element_order int,
	element_params text
);

create index ce_content_id_idx on content_elements (content_id);

create index ce_element_idx on content_elements (element);

create index ce_element_id_idx on content_elements (element_id);

create table content_archive (
	archiveid int not null auto_increment,
	id int not null,
	locked int,
	locked_user int,
	locked_creator int,
	locked_developer int,
	locked_editor int,
	locked_publisher int,
	locked_administrator int,
	locked_schedule int,
	locked_unschedule int,
	archived varchar(20),
	archived_by varchar(255),
	created varchar(20),
	updated varchar(20),
	published varchar(20),
	unpublished varchar(20),
	scheduled_publish varchar(20),
	scheduled_unpublish varchar(20),
	requested_publish varchar(20),
	requested_unpublish varchar(20),
	status varchar(255),
	status_by varchar(255),
	created_by varchar(255),
	updated_by varchar(255),
	published_by varchar(255),
	unpublished_by varchar(255),
	revision text,
	history text,
	version varchar(255),
	version_master int,
	device varchar(255),
QQQ	usersegment varchar(255),
QQQ	usertest varchar(255),
	heatmap varchar(50),
	heatmapalign varchar(1),
	usagelog varchar(1),
	title varchar(255),
	searchable varchar(3),
	menuitem varchar(3),
	author text,
	keywords text,
	description text,
	metainfo text,
	segmentation text,
	doctype text,
	htmlattributes text,
	htmlheader text,
	htmlbodyonload text,
	url text,
	content longtext,
	summary longtext,
	template int,
	stylesheet int,
	scripts int,
	image1 int,
	image2 int,
	image3 int,
	file1 int,
	file2 int,
	file3 int,
	link1 int,
	link2 int,
	link3 int,
	contentformat varchar(10),
	contentclass varchar(255),
	contenttype varchar(255),
	contentgroup varchar(255),
	contentbundle varchar(255),
	contentpackage varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	checkedout varchar(255),
	page_top int,
	page_up int,
	page_next int,
	page_previous int,
	page_first int,
	page_last int,
	related int,
	upload_filename text,
	server_filename text,
	product_code varchar(255),
	product_sku varchar(255),
	product_currency varchar(50),
	product_price varchar(255),
	product_period varchar(255),
	product_weight varchar(255),
	product_volume varchar(255),
	product_width varchar(255),
	product_height varchar(255),
	product_depth varchar(255),
	product_brand varchar(255),
	product_colour varchar(255),
	product_size varchar(255),
	product_count varchar(10),
	product_tax varchar(50),
	product_points_cost varchar(255),
	product_points_reward varchar(255),
	product_stock varchar(255),
	product_cost varchar(255),
	product_location varchar(255),
	product_stock_amount int,
	product_stock_text varchar(255),
	product_restocked_amount int,
	product_lowstock_amount int,
	product_lowstock_text varchar(255),
	product_nostock_order varchar(3),
	product_nostock_text varchar(255),
	product_comment text,
	product_email int,
	product_url text,
	product_info text,
	product_delivery text,
	product_user varchar(255),
	product_page int,
	product_program text,
	product_hosting varchar(255),
	product_options text,
	product_content int,
	product_contentgroup varchar(255),
	product_contenttype varchar(255),
	product_imagegroup varchar(255),
	product_imagetype varchar(255),
	product_filegroup varchar(255),
	product_filetype varchar(255),
	product_linkgroup varchar(255),
	product_linktype varchar(255),
	product_productgroup varchar(255),
	product_producttype varchar(255),
	product_usergroup varchar(255),
	product_usertype varchar(255),
	custom text,
	futurechar1 varchar(255),
	futurechar2 varchar(255),
	futurechar3 varchar(255),
	futuretext1 text,
	futuretext2 text,
	futuretext3 text,
	futureint1 int,
	futureint2 int,
	futureint3 int,
	primary key (archiveid)
);

create unique index ca_id_idx on content_archive (id);

create index ca_title_idx on content_archive (title);

!create index ca_content_idx on content_archive (content);

!create index ca_summary_idx on content_archive (summary);

!create index ca_author_idx on content_archive (author);

!create index ca_description_idx on content_archive (description);

!create index ca_keywords_idx on content_archive (keywords);

create index ca_searchable_idx on content_archive (searchable);


create index ca_version_idx on content_archive (version);

create index ca_v_master_idx on content_archive (version_master);

create index ca_device_idx on content_archive (device);

create index ca_usersegment_idx on content_archive (usersegment);

create index ca_usertest_idx on content_archive (usertest);

create index ca_heatmap on content_archive (heatmap);

create index ca_usagelog on content_archive (usagelog);

create index ca_filename_idx on content_archive (server_filename(250));

create index ca_cclass_idx on content_archive (contentclass);

create index ca_cgroup_idx on content_archive (contentgroup);

create index ca_ctype_idx on content_archive (contenttype);

create index ca_cbundle_idx on content_archive (contentbundle);

create index ca_cpackage_idx on content_archive (contentpackage);


create index ca_archived_idx on content_archive (archived);

create index ca_archived_by_idx on content_archive (archived_by);

create index ca_created_idx on content_archive (created);

create index ca_updated_idx on content_archive (updated);

create index ca_published_idx on content_archive (published);

create index ca_unpublished_idx on content_archive (unpublished);

create index ca_status_idx on content_archive (status);

create index ca_created_by_idx on content_archive (created_by);

create index ca_updated_by_idx on content_archive (updated_by);

create index ca_published_by_idx on content_archive (published_by);

create index ca_unpublished_by_idx on content_archive (unpublished_by);

create index ca_status_by_idx on content_archive (status_by);

create index ca_sched_publish_idx on content_archive (scheduled_publish);

create index ca_sched_unpublish_idx on content_archive (scheduled_unpublish);

create index ca_req_publish_idx on content_archive (requested_publish);

create index ca_req_unpublish_idx on content_archive (requested_unpublish);


create index ca_u_type_idx on content_archive (users_type);

create index ca_u_group_idx on content_archive (users_group);

create index ca_d_type_idx on content_archive (developers_type);

create index ca_d_group_idx on content_archive (developers_group);

create index ca_c_type_idx on content_archive (creators_type);

create index ca_c_group_idx on content_archive (creators_group);

create index ca_e_type_idx on content_archive (editors_type);

create index ca_e_group_idx on content_archive (editors_group);

create index ca_p_type_idx on content_archive (publishers_type);

create index ca_p_group_idx on content_archive (publishers_group);

create index ca_a_type_idx on content_archive (administrators_type);

create index ca_a_group_idx on content_archive (administrators_group);

create index ca_cout_idx on content_archive (checkedout);

create index ca_l_schedule_idx on content_archive (locked_schedule);

create index ca_l_unschedule_idx on content_archive (locked_unschedule);


create index ca_page_top_idx on content_archive (page_top);

create index ca_page_up_idx on content_archive (page_up);

create index ca_page_next_idx on content_archive (page_next);

create index ca_page_prev_idx on content_archive (page_previous);

create index ca_page_first_idx on content_archive (page_first);

create index ca_page_last_idx on content_archive (page_last);


create index ca_brand_idx on content_archive (product_brand);

create index ca_colour_idx on content_archive (product_colour);

create index ca_size_idx on content_archive (product_size);

create index ca_price_idx on content_archive (product_price);

create index ca_ps_amount_idx on content_archive (product_stock_amount);

create index ca_prs_amount_idx on content_archive (product_restocked_amount);

create index ca_pls_amount_idx on content_archive (product_lowstock_amount);


!create index ca_metainfo_idx on content_archive (metainfo);

!create index ca_productinfo_idx on content_archive (product_info);


create table content_archive_elements (
	content_archiveid int,
	content_id int,
	element varchar(255),
	element_id int,
	element_order int,
	element_params text
);

create index cae_c_aid_idx on content_archive_elements (content_archiveid);

create index cae_content_id_idx on content_archive_elements (content_id);

create index cae_element_idx on content_archive_elements (element);

create index cae_element_id_idx on content_archive_elements (element_id);

create table content_public (
	id int not null,
	locked int,
	locked_user int,
	locked_creator int,
	locked_developer int,
	locked_editor int,
	locked_publisher int,
	locked_administrator int,
	locked_schedule int,
	locked_unschedule int,
	created varchar(20),
	updated varchar(20),
	published varchar(20),
	unpublished varchar(20),
	scheduled_publish varchar(20),
	scheduled_unpublish varchar(20),
	requested_publish varchar(20),
	requested_unpublish varchar(20),
	status varchar(255),
	status_by varchar(255),
	created_by varchar(255),
	updated_by varchar(255),
	published_by varchar(255),
	unpublished_by varchar(255),
	revision text,
	history text,
	version varchar(255),
	version_master int,
	device varchar(255),
QQQ	usersegment varchar(255),
QQQ	usertest varchar(255),
	heatmap varchar(50),
	heatmapalign varchar(1),
	usagelog varchar(1),
	title varchar(255),
	searchable varchar(3),
	menuitem varchar(3),
	author text,
	keywords text,
	description text,
	metainfo text,
	segmentation text,
	doctype text,
	htmlattributes text,
	htmlheader text,
	htmlbodyonload text,
	url text,
	content longtext,
	summary longtext,
	template int,
	stylesheet int,
	scripts int,
	image1 int,
	image2 int,
	image3 int,
	file1 int,
	file2 int,
	file3 int,
	link1 int,
	link2 int,
	link3 int,
	contentformat varchar(10),
	contentclass varchar(255),
	contenttype varchar(255),
	contentgroup varchar(255),
	contentbundle varchar(255),
	contentpackage varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	checkedout varchar(255),
	page_top int,
	page_up int,
	page_next int,
	page_previous int,
	page_first int,
	page_last int,
	related int,
	upload_filename text,
	server_filename text,
	product_code varchar(255),
	product_sku varchar(255),
	product_currency varchar(50),
	product_price varchar(255),
	product_period varchar(255),
	product_weight varchar(255),
	product_volume varchar(255),
	product_width varchar(255),
	product_height varchar(255),
	product_depth varchar(255),
	product_brand varchar(255),
	product_colour varchar(255),
	product_size varchar(255),
	product_count varchar(10),
	product_tax varchar(50),
	product_points_cost varchar(255),
	product_points_reward varchar(255),
	product_stock varchar(255),
	product_cost varchar(255),
	product_location varchar(255),
	product_stock_amount int,
	product_stock_text varchar(255),
	product_restocked_amount int,
	product_lowstock_amount int,
	product_lowstock_text varchar(255),
	product_nostock_order varchar(3),
	product_nostock_text varchar(255),
	product_comment text,
	product_email int,
	product_url text,
	product_info text,
	product_delivery text,
	product_user varchar(255),
	product_page int,
	product_program text,
	product_hosting varchar(255),
	product_options text,
	product_content int,
	product_contentgroup varchar(255),
	product_contenttype varchar(255),
	product_imagegroup varchar(255),
	product_imagetype varchar(255),
	product_filegroup varchar(255),
	product_filetype varchar(255),
	product_linkgroup varchar(255),
	product_linktype varchar(255),
	product_productgroup varchar(255),
	product_producttype varchar(255),
	product_usergroup varchar(255),
	product_usertype varchar(255),
	custom text,
	futurechar1 varchar(255),
	futurechar2 varchar(255),
	futurechar3 varchar(255),
	futuretext1 text,
	futuretext2 text,
	futuretext3 text,
	futureint1 int,
	futureint2 int,
	futureint3 int,
	primary key (id)
);

create index cp_title_idx on content_public (title);

!create index cp_content_idx on content_public (content);

!create index cp_summary_idx on content_public (summary);

!create index cp_author_idx on content_public (author);

!create index cp_description_idx on content_public (description);

!create index cp_keywords_idx on content_public (keywords);

create index cp_searchable_idx on content_public (searchable);


create index cp_version_idx on content_public (version);

create index cp_v_master_idx on content_public (version_master);

create index cp_device_idx on content_public (device);

create index cp_usersegment_idx on content_public (usersegment);

create index cp_usertest_idx on content_public (usertest);

create index cp_heatmap on content_public (heatmap);

create index cp_usagelog on content_public (usagelog);

create index cp_filename_idx on content_public (server_filename(250));

create index cp_cclass_idx on content_public (contentclass);

create index cp_cgroup_idx on content_public (contentgroup);

create index cp_ctype_idx on content_public (contenttype);

create index cp_cbundle_idx on content_public (contentbundle);

create index cp_cpackage_idx on content_public (contentpackage);


create index cp_created_idx on content_public (created);

create index cp_updated_idx on content_public (updated);

create index cp_published_idx on content_public (published);

create index cp_unpublished_idx on content_public (unpublished);

create index cp_status_idx on content_public (status);

create index cp_created_by_idx on content_public (created_by);

create index cp_updated_by_idx on content_public (updated_by);

create index cp_published_by_idx on content_public (published_by);

create index cp_unpublished_by_idx on content_public (unpublished_by);

create index cp_status_by_idx on content_public (status_by);

create index cp_sched_publish_idx on content_public (scheduled_publish);

create index cp_sched_unpublish_idx on content_public (scheduled_unpublish);

create index cp_req_publish_idx on content_public (requested_publish);

create index cp_req_unpublish_idx on content_public (requested_unpublish);


create index cp_u_type_idx on content_public (users_type);

create index cp_u_group_idx on content_public (users_group);

create index cp_d_type_idx on content_public (developers_type);

create index cp_d_group_idx on content_public (developers_group);

create index cp_c_type_idx on content_public (creators_type);

create index cp_c_group_idx on content_public (creators_group);

create index cp_e_type_idx on content_public (editors_type);

create index cp_e_group_idx on content_public (editors_group);

create index cp_p_type_idx on content_public (publishers_type);

create index cp_p_group_idx on content_public (publishers_group);

create index cp_a_type_idx on content_public (administrators_type);

create index cp_a_group_idx on content_public (administrators_group);

create index cp_cout_idx on content_public (checkedout);

create index cp_l_schedule_idx on content_public (locked_schedule);

create index cp_l_unschedule_idx on content_public (locked_unschedule);


create index cp_page_top_idx on content_public (page_top);

create index cp_page_up_idx on content_public (page_up);

create index cp_page_next_idx on content_public (page_next);

create index cp_page_prev_idx on content_public (page_previous);

create index cp_page_first_idx on content_public (page_first);

create index cp_page_last_idx on content_public (page_last);


create index cp_brand_idx on content_public (product_brand);

create index cp_colour_idx on content_public (product_colour);

create index cp_size_idx on content_public (product_size);

create index cp_price_idx on content_public (product_price);

create index cp_ps_amount_idx on content_public (product_stock_amount);

create index cp_prs_amount_idx on content_public (product_restocked_amount);

create index cp_pls_amount_idx on content_public (product_lowstock_amount);

!create index cp_metainfo_idx on content_public (metainfo);

!create index cp_productinfo_idx on content_public (product_info);


create table content_public_elements (
	content_id int,
	element varchar(255),
	element_id int,
	element_order int,
	element_params text
);

create index cpe_content_id_idx on content_public_elements (content_id);

create index cpe_element_idx on content_public_elements (element);

create index cpe_element_id_idx on content_public_elements (element_id);

create table content_dependencies (
	content_id int,
	dependency varchar(255),
	dependency_id int
);

create index cd_content_id_idx on content_dependencies (content_id);

create index cd_dependency_id_idx on content_dependencies (dependency_id);

create table content_public_dependencies (
	content_id int,
	dependency varchar(255),
	dependency_id int
);

create index cpd_content_id_idx on content_public_dependencies (content_id);

create index cpd_dependency_id_idx on content_public_dependencies (dependency_id);

create table contentgroups (
	id int not null auto_increment,
	contentgroup varchar(255),
	parentgroup varchar(255),
	contentclass varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	template int,
	stylesheet int,
	title_prefix text,
	title_suffix text,
	doctype text,
	primary key (id)
);

create unique index cg_contentgroup_idx on contentgroups(contentgroup);

create table contenttypes (
	id int not null auto_increment,
	contenttype varchar(255),
	parenttype varchar(255),
	contentclass varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	template int,
	stylesheet int,
	title_prefix text,
	title_suffix text,
	doctype text,
	primary key (id)
);

create unique index ct_contenttype_idx on contenttypes(contenttype);

create table elements (
	id int not null auto_increment,
	element varchar(255),
	parentelement varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	primary key (id)
);

create unique index e_element_idx on elements(element);

create table fileformats (
	id int not null auto_increment,
	filenameextension varchar(255),
	primary key (id)
);

create table filegroups (
	id int not null auto_increment,
	filegroup varchar(255),
	parentgroup varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	template int,
	stylesheet int,
	primary key (id)
);

create unique index fg_filegroup_idx on filegroups(filegroup);

create table filetypes (
	id int not null auto_increment,
	filetype varchar(255),
	parenttype varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	template int,
	stylesheet int,
	primary key (id)
);

create unique index ft_filetype_idx on filetypes(filetype);

create table fonts (
	id int not null auto_increment,
	face varchar(255),
	primary key (id)
);

create table hosting (
	id int not null auto_increment,
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
	experience_license varchar(255),
	superadmin varchar(255),
	superadmin_password varchar(255),
	superadmin_email varchar(255),
	hostingtype varchar(255),
	hostinggroup varchar(255),
	scheduled_last varchar(20),
	scheduled_publish varchar(20),
	scheduled_notify varchar(20),
	scheduled_unpublish varchar(20),
	scheduled_publish_email int,
	scheduled_notify_email int,
	scheduled_unpublish_email int,
	users_type varchar(255),
	users_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	externalstorage text,
	history text,
	custom text,
	primary key (id)
);

create unique index h_c_address_idx on hosting (client_address);

create index h_c_database_idx on hosting (client_database);

create index h_s_email_idx on hosting (superadmin_email);

create index h_hostingtype_idx on hosting (hostingtype);

create index h_hostinggroup_idx on hosting (hostinggroup);

create index h_s_last_idx on hosting (scheduled_last);

create index h_s_publish_idx on hosting (scheduled_publish);

create index h_s_notify_idx on hosting (scheduled_notify);

create index h_s_unpublish_idx on hosting (scheduled_unpublish);

create index h_s_p_email_idx on hosting (scheduled_publish_email);

create index h_s_n_email_idx on hosting (scheduled_notify_email);

create index h_s_u_email_idx on hosting (scheduled_unpublish_email);

create index h_users_type_idx on hosting (users_type);

create index h_users_group_idx on hosting (users_group);

create index h_c_type_idx on hosting (creators_type);

create index h_c_group_idx on hosting (creators_group);

create index h_e_type_idx on hosting (editors_type);

create index h_e_group_idx on hosting (editors_group);

create index h_p_type_idx on hosting (publishers_type);

create index h_p_group_idx on hosting (publishers_group);

create index h_a_group_idx on hosting (administrators_group);

create index h_a_type_idx on hosting (administrators_type);

create index u_s_p_email_idx on hosting (scheduled_publish_email);

create index u_s_n_email_idx on hosting (scheduled_notify_email);

create index u_s_u_email_idx on hosting (scheduled_unpublish_email);

create table hostinggroups (
	id int not null auto_increment,
	hostinggroup varchar(255),
	parentgroup varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	primary key (id)
);

create unique index hg_hostinggroup_idx on hostinggroups(hostinggroup);

create table hostingtypes (
	id int not null auto_increment,
	hostingtype varchar(255),
	parenttype varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	primary key (id)
);

create unique index ht_hostingtype_idx on hostingtypes(hostingtype);

create table imageformats (
	id int not null auto_increment,
	filenameextension varchar(255),
	primary key (id)
);

create table imagegroups (
	id int not null auto_increment,
	imagegroup varchar(255),
	parentgroup varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	template int,
	stylesheet int,
	primary key (id)
);

create unique index ig_imagegroup_idx on imagegroups(imagegroup);

create table imagetypes (
	id int not null auto_increment,
	imagetype varchar(255),
	parenttype varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	template int,
	stylesheet int,
	primary key (id)
);

create unique index it_imagetype_idx on imagetypes(imagetype);

create table linkgroups (
	id int not null auto_increment,
	linkgroup varchar(255),
	parentgroup varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	template int,
	stylesheet int,
	primary key (id)
);

create unique index lg_linkgroup_idx on linkgroups(linkgroup);

create table linktypes (
	id int not null auto_increment,
	linktype varchar(255),
	parenttype varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	template int,
	stylesheet int,
	primary key (id)
);

create unique index lt_linktype_idx on linktypes(linktype);

create table usergroups (
	id int not null auto_increment,
	usergroup varchar(255),
	parentgroup varchar(255),
	login_page int,
	users_type varchar(255),
	users_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	subscribers_type varchar(255),
	subscribers_group varchar(255),
	users_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	subscribers_users int,
	primary key (id)
);

create unique index ug_usergroup_idx on usergroups(usergroup);

create table usertypes (
	id int not null auto_increment,
	usertype varchar(255),
	parenttype varchar(255),
	login_page int,
	users_type varchar(255),
	users_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	subscribers_type varchar(255),
	subscribers_group varchar(255),
	users_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	subscribers_users int,
	primary key (id)
);

create unique index ut_usertype_idx on usertypes(usertype);

create table usergroups2 (
	usergroup varchar(255),
	subgroup varchar(255)
);

create index ug2_usergroup_idx on usergroups2(usergroup);

create table usertypes2 (
	usertype varchar(255),
	subtype varchar(255)
);

create index ut2_usertype_idx on usertypes2(usertype);

create table users_usergroups (
	id int not null auto_increment,
	locked int,
	created varchar(20),
	updated varchar(20),
	created_by varchar(255),
	updated_by varchar(255),
	scheduled_publish varchar(20),
	scheduled_unpublish varchar(20),
	username varchar(255),
	usergroup varchar(255),
	primary key (id)
);

create index u_ug_username_idx on users_usergroups(username);

create index u_ug_usergroup_idx on users_usergroups(usergroup);

create table users_usertypes (
	id int not null auto_increment,
	locked int,
	created varchar(20),
	updated varchar(20),
	created_by varchar(255),
	updated_by varchar(255),
	scheduled_publish varchar(20),
	scheduled_unpublish varchar(20),
	username varchar(255),
	usertype varchar(255),
	primary key (id)
);

create index u_ut_username_idx on users_usertypes(username);

create index u_ut_usertype_idx on users_usertypes(usertype);

create table versions (
	id int not null auto_increment,
	version varchar(255),
	currencies varchar(50),
	default_country varchar(255),
	default_state varchar(255),
	default_price text,
	parentversion varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	primary key (id)
);

create unique index v_version_idx on versions(version);

create table segments (
	id int not null auto_increment,
	segmentid varchar(255),
	segment varchar(255),
	weight varchar(10),
	active varchar(1),
	scheduled varchar(20),
	unscheduled varchar(20),
	datetimefull text,
	datetimeyear text,
	datetimemonth text,
	datetimeday text,
	datetimeweek text,
	datetimeweekday text,
	datetimehour text,
	datetimemin text,
	datetimesec text,
	clientaddr text,
	clienthost text,
	clientuser text,
	clientuseragent text,
	clientos text,
	clientosversion text,
	clientbrowser text,
	clientversion text,
	clientdevice text,
	clientdeviceid text,
	clientdeviceversion text,
	preferredlanguage text,
	acceptedlanguages text,
	requesthost text,
	requestport text,
	requestmethod text,
	requestpath text,
	requestquery text,
	requestprotocol text,
	requestid text,
	requestclass text,
	requestdatabase text,
	refererid text,
	refererclass text,
	refererdatabase text,
	refererhost text,
	refererpath text,
	refererquery text,
	referersearchengine text,
	referersearchquery text,
	geoipcountry text,
	geoipregion text,
	geoipcity text,
	geoippostalcode text,
	geoiplatitude text,
	geoiplongitude text,
	geoiptimezone text,
	created text,
	updated text,
	created_by text,
	updated_by text,
	keywords text,
	description text,
	birthdate text,
	birthyear text,
	birthmonth text,
	birthday text,
	age text,
	gender text,
	title text,
	name text,
	organisation text,
	username text,
	password text,
	email text,
	userclass text,
	usertype text,
	usergroup text,
	scheduled_last text,
	scheduled_publish text,
	scheduled_notify text,
	scheduled_unpublish text,
	scheduled_publish_email text,
	scheduled_notify_email text,
	scheduled_unpublish_email text,
	card_type text,
	card_number text,
	card_issuedmonth text,
	card_issuedyear text,
	card_expirymonth text,
	card_expiryyear text,
	card_name text,
	card_cvc text,
	card_issue text,
	card_postalcode text,
	delivery_name text,
	delivery_organisation text,
	delivery_address text,
	delivery_postalcode text,
	delivery_city text,
	delivery_state text,
	delivery_country text,
	delivery_phone text,
	delivery_fax text,
	delivery_email text,
	delivery_website text,
	invoice_name text,
	invoice_organisation text,
	invoice_address text,
	invoice_postalcode text,
	invoice_city text,
	invoice_state text,
	invoice_country text,
	invoice_phone text,
	invoice_fax text,
	invoice_email text,
	invoice_website text,
	notes text,
	userinfo text,
	shopcart text,
	wishlist text,
	product_points text,
	visits text,
QQQ	goals text,
	custom text,
	primary key (id)
);

create unique index s_segmentid_idx on segments(segmentid);

create index segments_active on segments (active);

create index segments_scheduled on segments (scheduled);

create index segments_unscheduled on segments (unscheduled);

create table usertests (
	id int not null auto_increment,
	usertest varchar(125),
	description text,
	type varchar(10),
	variants text,
	probability varchar(10),
	visitors varchar(3),
	visits varchar(10),
QQQ	goals text,
	confidence varchar(10),
	active varchar(1),
	scheduled varchar(20),
	unscheduled varchar(20),
	primary key (id)
);

create unique index ut_usertest_idx on usertests(usertest);

create index usertests_usertest on usertests (usertest);

create table websites (
	id int not null auto_increment,
	title varchar(255),
	domain varchar(255),
	default_page int,
	default_page_nonexisting int,
	default_page_unpublished int,
	default_page_expired int,
	default_login int,
	default_searchresults_page int,
	default_searchresults_entry int,
	default_list_entry int,
	default_publish_ready int,
	default_register_confirm_page int,
	default_register_notify_page int,
	default_personal_admin_page int,
	retrieve_password_page int,
	retrieve_password_confirmation int,
	retrieve_password_email int,
	retrieve_password_error int,
	default_template int,
	default_stylesheet int,
	default_version varchar(255),
	default_country varchar(255),
	default_state varchar(255),
	default_currency varchar(50),
	default_price text,
	language varchar(255),
	remote varchar(255),
	useragent varchar(255),
	referrer varchar(255),
	keywords varchar(255),
	default_doctype text,
	primary key (id)
);

create index w_domain_idx on websites (domain);

create index w_language_idx on websites (language);

create index w_remote_idx on websites (remote);

create index w_referrer_idx on websites (referrer);

create index w_keywords_idx on websites (keywords);

create table workflow (
	id int not null auto_increment,
	title varchar(255),
	action varchar(255),
	fromstate varchar(255),
	tostate varchar(255),
	usertype text,
	usergroup text,
	contentclass text,
	contenttype text,
	contentgroup text,
	version text,
	notify_email varchar(10),
	contentchanges text,
	workflow_program text,
	userrestriction varchar(20),
	primary key (id)
);

create index w_title_idx on workflow (title);

create index w_action_idx on workflow (action);

create index w_fromstate_idx on workflow (fromstate);

create index w_tostate_idx on workflow (tostate);

create table users (
	id int not null auto_increment,
	locked int,
	failed int,
	created varchar(20),
	updated varchar(20),
	created_by varchar(255),
	updated_by varchar(255),
	history text,
	keywords text,
	description text,
	birthdate varchar(10),
	birthyear varchar(4),
	birthmonth varchar(2),
	birthday varchar(2),
	gender varchar(1),
	title varchar(255),
	name varchar(255),
	organisation text,
	username varchar(255),
	password varchar(255),
	email varchar(255),
	userclass varchar(255),
	usertype varchar(255),
	usergroup varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	content_editor varchar(20),
	hardcore_language varchar(20),
	hardcore_upload varchar(10),
	hardcore_format varchar(10),
	hardcore_width varchar(10),
	hardcore_height varchar(10),
	hardcore_onenter text,
	hardcore_onctrlenter text,
	hardcore_onshiftenter text,
	hardcore_onaltenter text,
	hardcore_toolbar1 text,
	hardcore_toolbar2 text,
	hardcore_toolbar3 text,
	hardcore_toolbar4 text,
	hardcore_toolbar5 text,
	hardcore_encoding varchar(20),
	hardcore_formatblock text,
	hardcore_fontname text,
	hardcore_fontsize text,
	hardcore_customscript text,
	scheduled_last varchar(20),
	scheduled_publish varchar(20),
	scheduled_notify varchar(20),
	scheduled_unpublish varchar(20),
	scheduled_publish_email int,
	scheduled_notify_email int,
	scheduled_unpublish_email int,
	card_type varchar(255),
	card_number varchar(255),
	card_issuedmonth varchar(255),
	card_issuedyear varchar(255),
	card_expirymonth varchar(255),
	card_expiryyear varchar(255),
	card_name varchar(255),
	card_cvc varchar(255),
	card_issue varchar(255),
	card_postalcode varchar(255),
	delivery_name varchar(255),
	delivery_organisation varchar(255),
	delivery_address text,
	delivery_postalcode varchar(255),
	delivery_city varchar(255),
	delivery_state varchar(255),
	delivery_country varchar(255),
	delivery_phone varchar(255),
	delivery_fax varchar(255),
	delivery_email varchar(255),
	delivery_website varchar(255),
	invoice_name varchar(255),
	invoice_organisation varchar(255),
	invoice_address text,
	invoice_postalcode varchar(255),
	invoice_city varchar(255),
	invoice_state varchar(255),
	invoice_country varchar(255),
	invoice_phone varchar(255),
	invoice_fax varchar(255),
	invoice_email varchar(255),
	invoice_website varchar(255),
	notes text,
	userinfo text,
	index_content text,
	index_library text,
	index_product text,
	index_order text,
	index_segments text,
	index_usertests text,
	index_heatmaps text,
	index_search text,
	index_searchadvanced text,
	index_searchreplace text,
	index_databases text,
	index_user text,
	index_hosting text,
	index_websites text,
	index_stock text,
	index_sales text,
	index_workspace text,
	menu_selection text,
	workspace_sections text,
	statistics_reports text,
	sales_reports text,
	shopcart text,
	wishlist text,
	product_points int,
	custom text,
	primary key (id)
);

create index u_name_idx on users (name);

create unique index u_username_idx on users (username);

create index u_password_idx on users (password);

create index u_email_idx on users (email);

create index u_userclass_idx on users (userclass);

create index u_usergroup_idx on users (usergroup);

create index u_usertype_idx on users (usertype);

create index u_users_type_idx on users (users_type);

create index u_users_group_idx on users (users_group);

create index u_c_type_idx on users (creators_type);

create index u_c_group_idx on users (creators_group);

create index u_e_type_idx on users (editors_type);

create index u_e_group_idx on users (editors_group);

create index u_p_type_idx on users (publishers_type);

create index u_p_group_idx on users (publishers_group);

create index u_a_group_idx on users (administrators_group);

create index u_a_type_idx on users (administrators_type);

create index u_s_last_idx on users (scheduled_last);

create index u_s_publish_idx on users (scheduled_publish);

create index u_s_notify_idx on users (scheduled_notify);

create index u_s_unpublish_idx on users (scheduled_unpublish);

create table user2groups (
	user_id int,
	usergroup varchar(255),
	scheduled_publish varchar(20),
	scheduled_unpublish varchar(20)
);

create index u2g_userid_idx on user2groups(user_id);

create table user2types (
	user_id int,
	usertype varchar(255),
	scheduled_publish varchar(20),
	scheduled_unpublish varchar(20)
);

create index u2t_userid_idx on user2types(user_id);

create table permissions (
	id int not null auto_increment,
	action varchar(50),
	resource varchar(250),
	username varchar(255),
	userclass varchar(255),
	usergroup varchar(255),
	usertype varchar(255),
	primary key (id)
);

create index perm_action_idx on permissions(action);

create index perm_resource_idx on permissions(resource);

create index perm_username_idx on permissions(username);

create index perm_userclass_idx on permissions(userclass);

create index perm_usergroup_idx on permissions(usergroup);

create index perm_usertype_idx on permissions(usertype);

create table guestbook (
	id int not null auto_increment,
	created varchar(20),
	updated varchar(20),
	published varchar(20),
	status varchar(255),
	created_by varchar(255),
	updated_by varchar(255),
	published_by varchar(255),
	title varchar(255),
	content longtext,
	name varchar(255),
	email varchar(255),
	website varchar(255),
	company varchar(255),
	primary key (id)
);

create table currencies (
	id int not null auto_increment,
	title varchar(50),
	symbol varchar(50),
	rate text,
	primary key (id)
);

create unique index cur_title_idx on currencies(title);

create index cur_symbol_idx on currencies(symbol);

create table orderitems (
	id int not null auto_increment,
	order_id int,
	product_id int,
	version varchar(255),
	device varchar(255),
	usersegment varchar(255),
	usertest varchar(255),
	title varchar(255),
	summary longtext,
	image1 int,
	image2 int,
	image3 int,
	file1 int,
	file2 int,
	file3 int,
	link1 int,
	link2 int,
	link3 int,
	author text,
	keywords text,
	description text,
	product_code varchar(255),
	product_sku varchar(255),
	product_currency varchar(50),
	product_price varchar(255),
	product_period varchar(255),
	product_weight varchar(255),
	product_volume varchar(255),
	product_width varchar(255),
	product_height varchar(255),
	product_depth varchar(255),
	product_brand varchar(255),
	product_colour varchar(255),
	product_size varchar(255),
	product_count varchar(10),
	product_tax varchar(50),
	product_points_cost varchar(255),
	product_points_reward varchar(255),
	product_stock varchar(255),
	product_comment text,
	product_email int,
	product_url text,
	product_delivery text,
	product_user varchar(255),
	product_page int,
	product_program text,
	product_hosting varchar(255),
	product_options text,
	product_content int,
	product_contentgroup varchar(255),
	product_contenttype varchar(255),
	product_imagegroup varchar(255),
	product_imagetype varchar(255),
	product_filegroup varchar(255),
	product_filetype varchar(255),
	product_linkgroup varchar(255),
	product_linktype varchar(255),
	product_productgroup varchar(255),
	product_producttype varchar(255),
	product_usergroup varchar(255),
	product_usertype varchar(255),
	item_quantity int,
	item_subtotal decimal(20,2),
	item_subtotal_base decimal(20,2),
	item_total decimal(20,2),
	item_total_base decimal(20,2),
	discount_description text,
	discount_total decimal(20,2),
	discount_total_base decimal(20,2),
	shipping_description text,
	shipping_total decimal(20,2),
	shipping_total_base decimal(20,2),
	tax_description text,
	tax_total decimal(20,2),
	tax_total_base decimal(20,2),
	primary key (id)
);

create index oi_order_id_idx on orderitems(order_id);

create index oi_product_id_idx on orderitems(product_id);

create table orders (
	id int not null auto_increment,
	user_id int,
	locked_user int,
	locked_creator int,
	locked_editor int,
	locked_publisher int,
	locked_administrator int,
	created varchar(20),
	updated varchar(20),
	published varchar(20),
	paid varchar(20),
	payment text,
	created_by varchar(255),
	updated_by varchar(255),
	published_by varchar(255),
	status varchar(255),
	status_by varchar(255),
	revision text,
	history text,
	checkedout varchar(255),
	order_quantity int,
	order_currency varchar(50),
	order_currency_base varchar(50),
	order_subtotal decimal(20,2),
	order_subtotal_base decimal(20,2),
	tax_description text,
	tax_total decimal(20,2),
	tax_total_base decimal(20,2),
	shipping_description text,
	shipping_total decimal(20,2),
	shipping_total_base decimal(20,2),
	discount_description text,
	discount_total decimal(20,2),
	discount_total_base decimal(20,2),
	order_total decimal(20,2),
	order_total_base decimal(20,2),
	card_type varchar(255),
	card_number varchar(255),
	card_issuedmonth varchar(255),
	card_issuedyear varchar(255),
	card_expirymonth varchar(255),
	card_expiryyear varchar(255),
	card_name varchar(255),
	card_cvc varchar(255),
	card_issue varchar(255),
	card_postalcode varchar(255),
	delivery_name varchar(255),
	delivery_organisation varchar(255),
	delivery_address text,
	delivery_postalcode varchar(255),
	delivery_city varchar(255),
	delivery_state varchar(255),
	delivery_country varchar(255),
	delivery_phone varchar(255),
	delivery_fax varchar(255),
	delivery_email varchar(255),
	delivery_website varchar(255),
	invoice_name varchar(255),
	invoice_organisation varchar(255),
	invoice_address text,
	invoice_postalcode varchar(255),
	invoice_city varchar(255),
	invoice_state varchar(255),
	invoice_country varchar(255),
	invoice_phone varchar(255),
	invoice_fax varchar(255),
	invoice_email varchar(255),
	invoice_website varchar(255),
	createdyear int,
	createdmonth int,
	createdday int,
	createdweek int,
	createdweekday int,
	createdhour int,
	createdmin int,
	createdsec int,
	clienthost varchar(250),
	clienthosttld varchar(10),
	clientuseragent varchar(250),
	clientos varchar(10),
	clientosversion varchar(20),
	clientbrowser varchar(50),
	clientversion varchar(20),
	clientdevice varchar(20),
	clientdeviceid varchar(20),
	clientdeviceversion varchar(50),
	clientusername varchar(50),
	visitorid varchar(50),
	visitorduration int,
	sessionid varchar(20),
	sessionduration int,
	requesthost varchar(50),
	requestpath varchar(250),
	requestquery varchar(250),
	requestid int,
	requestclass varchar(10),
	requestdatabase varchar(50),
	refererid int,
	refererclass varchar(10),
	refererdatabase varchar(50),
	refererduration int,
	refererhost varchar(50),
	refererpath varchar(250),
	refererquery varchar(250),
	referersearchengine varchar(50),
	referersearchquery varchar(250),
	session_entry varchar(250),
	affiliate varchar(250),
	usersegments text,
	usertests text,
	custom text,
	primary key (id)
);

create index o_paid_idx on orders(paid);

create table productgroups (
	id int not null auto_increment,
	productgroup varchar(255),
	parentgroup varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	template int,
	stylesheet int,
	title_prefix text,
	title_suffix text,
	doctype text,
	primary key (id)
);

create unique index pg_productgroup_idx on productgroups(productgroup);

create table producttypes (
	id int not null auto_increment,
	producttype varchar(255),
	parenttype varchar(255),
	users_type varchar(255),
	users_group varchar(255),
	developers_type varchar(255),
	developers_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	developers_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	template int,
	stylesheet int,
	title_prefix text,
	title_suffix text,
	doctype text,
	primary key (id)
);

create unique index pt_producttype_idx on producttypes(producttype);

create table discounts (
	id int not null auto_increment,
	title varchar(255),
	country varchar(50),
	state varchar(50),
	product_id int,
	product_group varchar(50),
	product_type varchar(50),
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
	total_currency varchar(50),
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
	discount_description varchar(50),
	discount_type varchar(50),
	discount_quantity int,
	discount_quantity2 int,
	discount_products varchar(50),
	discount_currency varchar(50),
	discount_amount decimal(20,2),
	discount_orderitems varchar(50),
	user_username varchar(255),
	user_type varchar(255),
	user_group varchar(255),
	user_code varchar(255),
	user_points_min int,
	user_points_cost int,
	period_start varchar(20),
	period_end varchar(20),
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

create table shipping (
	id int not null auto_increment,
	title varchar(255),
	country varchar(50),
	state varchar(50),
	product_id int,
	product_group varchar(255),
	product_type varchar(255),
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
	total_currency varchar(50),
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
	ship_description varchar(50),
	ship_currency varchar(50),
	ship_order decimal(20,2),
	ship_item decimal(20,2),
	ship_percent decimal(20,2),
	ship_total decimal(20,2),
	user_username varchar(255),
	user_type varchar(255),
	user_group varchar(255),
	user_code varchar(255),
	user_points_min int,
	user_points_cost int,
	period_start varchar(20),
	period_end varchar(20),
	primary key (id)
);

create index s_title_idx on shipping(title);

create index s_country_idx on shipping(country);

create index s_state_idx on shipping(state);

create index s_prod_id_idx on shipping(product_id);

create index s_prod_group_idx on shipping(product_group);

create index s_prod_type_idx on shipping(product_type);

create index s_p_weight_from_idx on shipping(product_weight_from);

create index s_p_weight_to_idx on shipping(product_weight_to);

create index s_p_volume_from_idx on shipping(product_volume_from);

create index s_p_volume_to_idx on shipping(product_volume_to);

create index s_p_width_from_idx on shipping(product_width_from);

create index s_p_width_to_idx on shipping(product_width_to);

create index s_p_height_from_idx on shipping(product_height_from);

create index s_p_height_to_idx on shipping(product_height_to);

create index s_p_depth_from_idx on shipping(product_depth_from);

create index s_p_depth_to_idx on shipping(product_depth_to);

create index s_quan_from_idx on shipping(quantity_from);

create index s_quan_to_idx on shipping(quantity_to);

create index s_total_cur_idx on shipping(total_currency);

create index s_total_from_idx on shipping(total_from);

create index s_total_to_idx on shipping(total_to);

create index s_t_weight_from_idx on shipping(total_weight_from);

create index s_t_weight_to_idx on shipping(total_weight_to);

create index s_t_volume_from_idx on shipping(total_volume_from);

create index s_t_volume_to_idx on shipping(total_volume_to);

create index s_t_width_from_idx on shipping(total_width_from);

create index s_t_width_to_idx on shipping(total_width_to);

create index s_t_height_from_idx on shipping(total_height_from);

create index s_t_height_to_idx on shipping(total_height_to);

create index s_t_depth_from_idx on shipping(total_depth_from);

create index s_t_depth_to_idx on shipping(total_depth_to);

create table tax (
	id int not null auto_increment,
	title varchar(255),
	country varchar(50),
	state varchar(50),
	product_id int,
	product_group varchar(255),
	product_type varchar(255),
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
	total_currency varchar(50),
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
	tax_description varchar(50),
	tax_currency varchar(50),
	tax_order decimal(20,2),
	tax_item decimal(20,2),
	tax_percent decimal(20,2),
	tax_total decimal(20,2),
	primary key (id)
);

create index t_title_idx on tax(title);

create index t_country_idx on tax(country);

create index t_state_idx on tax(state);

create index t_product_id_idx on tax(product_id);

create index t_prod_group_idx on tax(product_group);

create index t_prod_type_idx on tax(product_type);

create index t_p_weight_from_idx on tax(product_weight_from);

create index t_p_weight_to_idx on tax(product_weight_to);

create index t_p_volume_from_idx on tax(product_volume_from);

create index t_p_volume_to_idx on tax(product_volume_to);

create index t_p_width_from_idx on tax(product_width_from);

create index t_p_width_to_idx on tax(product_width_to);

create index t_p_height_from_idx on tax(product_height_from);

create index t_p_height_to_idx on tax(product_height_to);

create index t_p_depth_from_idx on tax(product_depth_from);

create index t_p_depth_to_idx on tax(product_depth_to);

create index t_quan_from_idx on tax(quantity_from);

create index t_quan_to_idx on tax(quantity_to);

create index t_total_cur_idx on tax(total_currency);

create index t_total_from_idx on tax(total_from);

create index t_total_to_idx on tax(total_to);

create index t_t_weight_from_idx on tax(total_weight_from);

create index t_t_weight_to_idx on tax(total_weight_to);

create index t_t_volume_from_idx on tax(total_volume_from);

create index t_t_volume_to_idx on tax(total_volume_to);

create index t_t_width_from_idx on tax(total_width_from);

create index t_t_width_to_idx on tax(total_width_to);

create index t_t_height_from_idx on tax(total_height_from);

create index t_t_height_to_idx on tax(total_height_to);

create index t_t_depth_from_idx on tax(total_depth_from);

create index t_t_depth_to_idx on tax(total_depth_to);

create table data (
	id int not null auto_increment,
	title varchar(50),
	content longtext,
	users_type varchar(255),
	users_group varchar(255),
	creators_type varchar(255),
	creators_group varchar(255),
	editors_type varchar(255),
	editors_group varchar(255),
	publishers_type varchar(255),
	publishers_group varchar(255),
	administrators_type varchar(255),
	administrators_group varchar(255),
	users_users int,
	creators_users int,
	editors_users int,
	publishers_users int,
	administrators_users int,
	searchresults int,
	searchresult int,
	viewpage int,
	adminindex text,
	custom text,
	primary key (id)
);

create unique index d_title_idx on data(title);

create table usagelog (
	datetimefull varchar(20),
	datetimeyear int,
	datetimemonth int,
	datetimeday int,
	datetimeweek int,
	datetimeweekday int,
	datetimehour int,
	datetimemin int,
	datetimesec int,
	clienthost varchar(250),
	clienthosttld varchar(10),
	clientuseragent varchar(250),
	clientos varchar(10),
	clientosversion varchar(20),
	clientbrowser varchar(50),
	clientversion varchar(20),
	clientdevice varchar(20),
	clientdeviceid varchar(20),
	clientdeviceversion varchar(50),
	clientusername varchar(50),
	visitorid varchar(50),
	visitorduration int,
	sessionid varchar(20),
	sessionduration int,
	requesthost varchar(50),
	requestpath varchar(250),
	requestquery varchar(250),
	requestid int,
	requestclass varchar(10),
	requestdatabase varchar(50),
	refererid int,
	refererclass varchar(10),
	refererdatabase varchar(50),
	refererduration int,
	refererhost varchar(50),
	refererpath varchar(250),
	refererquery varchar(250),
	referersearchengine varchar(50),
	referersearchquery varchar(250),
	affiliate varchar(250),
	usersegments text,
	usertests text,
	hits int,
	pageviews int,
	visits int,
	visitors int,
	clienthosts int,
	clienthostids text,
	visitorids text,
	sessionids text,
	summarised varchar(250)
);

create index u_datetime_idx on usagelog (datetimefull);

create index u_datetimeyear_idx on usagelog (datetimeyear);

create index u_datetimemonth_idx on usagelog (datetimemonth);

create index u_datetimeday_idx on usagelog (datetimeday);

create index u_datetimeweek_idx on usagelog (datetimeweek);

create index u_datetimeweekday_idx on usagelog (datetimeweekday);

create index u_datetimehour_idx on usagelog (datetimehour);

create index u_clienthost_idx on usagelog (clienthost);

create index u_clienthosttld_idx on usagelog (clienthosttld);

create index u_clientos_idx on usagelog (clientos);

create index u_clientosversion_idx on usagelog (clientosversion);

create index u_clientbrowser_idx on usagelog (clientbrowser);

create index u_clientversion_idx on usagelog (clientversion);

create index u_clientdevice_idx on usagelog (clientdevice);

create index u_clientdeviceid_idx on usagelog (clientdeviceid);

create index u_clientdevicev_idx on usagelog (clientdeviceversion);

create index u_clientusername_idx on usagelog (clientusername);

create index u_visitorid_idx on usagelog (visitorid);

create index u_visitorduration_idx on usagelog (visitorduration);

create index u_sessionid_idx on usagelog (sessionid);

create index u_sessionduration_idx on usagelog (sessionduration);

create index u_requesthost_idx on usagelog (requesthost);

create index u_requestid_idx on usagelog (requestid);

create index u_requestclass_idx on usagelog (requestclass);

create index u_requestdatabase_idx on usagelog (requestdatabase);

create index u_refererid_idx on usagelog (refererid);

create index u_refererclass_idx on usagelog (refererclass);

create index u_refererdatabase_idx on usagelog (refererdatabase);

create index u_refererduration_idx on usagelog (refererduration);

create index u_refererhost_idx on usagelog (refererhost);

create index u_refererpath_idx on usagelog (refererpath);

create index u_referersearchengine_idx on usagelog (referersearchengine);

create index u_referersearchquery_idx on usagelog (referersearchquery);

create index u_affiliate_idx on usagelog (affiliate);



create table heatmaps (
QQQ	id varchar(50) not null,
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



create table content_meta (
QQQ	content_id int not null,
	meta_name varchar(255) not null,
	meta_content varchar(255) not null,
	primary key (content_id,meta_name)
);

create index cm_content_id_idx on content_meta (content_id);

create index cm_meta_name_idx on content_meta (meta_name);

create index cm_meta_content_idx on content_meta (meta_content);

create index cm_name_idx on content_meta (content_id, meta_name, meta_content);

create index cm_metaname_idx on content_meta (meta_name, meta_content);

create index cm_metacontent_idx on content_meta (meta_content, meta_name);

create table content_public_meta (
QQQ	content_id int not null,
	meta_name varchar(255) not null,
	meta_content varchar(255) not null,
	primary key (content_id,meta_name)
);

create index cpm_content_id_idx on content_public_meta (content_id);

create index cpm_meta_name_idx on content_public_meta (meta_name);

create index cpm_meta_content_idx on content_public_meta (meta_content);

create index cpm_name_idx on content_public_meta (content_id, meta_name, meta_content);

create index cpm_metaname_idx on content_public_meta (meta_name, meta_content);

create index cpm_metacontent_idx on content_public_meta (meta_content, meta_name);



insert into config (configname, configvalue) values ('database_version', '9.0');



alter table content add constraint fk_c_created_by foreign key (created_by) references users(username);

alter table content add constraint fk_c_updated_by foreign key (updated_by) references users(username);

alter table content add constraint fk_c_published_by foreign key (published_by) references users(username);

alter table content add constraint fk_c_status_by foreign key (status_by) references users(username);

alter table content add constraint fk_c_version foreign key (version) references versions(version);

alter table content add constraint fk_c_version_master foreign key (version_master) references content(id);

alter table content add constraint fk_c_template foreign key (template) references content(id);

alter table content add constraint fk_c_stylesheet foreign key (stylesheet) references content(id);

alter table content add constraint fk_c_scripts foreign key (scripts) references content(id);

alter table content add constraint fk_c_image1 foreign key (image1) references content(id);

alter table content add constraint fk_c_image2 foreign key (image2) references content(id);

alter table content add constraint fk_c_image3 foreign key (image3) references content(id);

alter table content add constraint fk_c_file1 foreign key (file1) references content(id);

alter table content add constraint fk_c_file2 foreign key (file2) references content(id);

alter table content add constraint fk_c_file3 foreign key (file3) references content(id);

alter table content add constraint fk_c_link1 foreign key (link1) references content(id);

alter table content add constraint fk_c_link2 foreign key (link2) references content(id);

alter table content add constraint fk_c_link3 foreign key (link3) references content(id);

alter table content add constraint fk_c_contentgroup foreign key (contentgroup) references contentgroups(contentgroup);

alter table content add constraint fk_c_contenttype foreign key (contenttype) references contenttypes(contenttype);

alter table content add constraint fk_c_users_group foreign key (users_group) references usergroups(usergroup);

alter table content add constraint fk_c_users_type foreign key (users_type) references usertypes(usertype);

alter table content add constraint fk_c_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table content add constraint fk_c_developers_type foreign key (developers_type) references usertypes(usertype);

alter table content add constraint fk_c_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table content add constraint fk_c_creators_type foreign key (creators_type) references usertypes(usertype);

alter table content add constraint fk_c_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table content add constraint fk_c_editors_type foreign key (editors_type) references usertypes(usertype);

alter table content add constraint fk_c_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table content add constraint fk_c_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table content add constraint fk_c_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table content add constraint fk_c_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table content add constraint fk_c_users_users foreign key (users_users) references users(id);

alter table content add constraint fk_c_developers_users foreign key (developers_users) references users(id);

alter table content add constraint fk_c_creators_users foreign key (creators_users) references users(id);

alter table content add constraint fk_c_editors_users foreign key (editors_users) references users(id);

alter table content add constraint fk_c_publishers_users foreign key (publishers_users) references users(id);

alter table content add constraint fk_c_administrators_users foreign key (administrators_users) references users(id);

alter table content add constraint fk_c_checkedout foreign key (checkedout) references users(username);

alter table content add constraint fk_c_page_top foreign key (page_top) references content(id);

alter table content add constraint fk_c_page_up foreign key (page_up) references content(id);

alter table content add constraint fk_c_page_next foreign key (page_next) references content(id);

alter table content add constraint fk_c_page_previous foreign key (page_previous) references content(id);

alter table content add constraint fk_c_page_first foreign key (page_first) references content(id);

alter table content add constraint fk_c_page_last foreign key (page_last) references content(id);

alter table content add constraint fk_c_related foreign key (related) references content(id);

alter table content add constraint fk_c_product_currency foreign key (product_currency) references currencies(title);

alter table content add constraint fk_c_product_email foreign key (product_email) references content(id);

alter table content add constraint fk_c_product_user foreign key (product_user) references users(username);

alter table content add constraint fk_c_product_page foreign key (product_page) references content(id);

alter table content add constraint fk_c_product_hosting foreign key (product_hosting) references hosting(client_address);

alter table content add constraint fk_c_status foreign key (status) references workflow(fromstate);

alter table content add constraint fk_c_status foreign key (status) references workflow(tostate);

alter table content add constraint fk_c_product_content foreign key (product_content) references content(id);

alter table content add constraint fk_c_product_contentgroup foreign key (product_contentgroup) references contentgroups(contentgroup);

alter table content add constraint fk_c_product_contenttype foreign key (product_contenttype) references contenttypes(contenttype);

alter table content add constraint fk_c_product_imagegroup foreign key (product_imagegroup) references imagegroups(imagegroup);

alter table content add constraint fk_c_product_imagetype foreign key (product_imagetype) references imagetypes(imagetype);

alter table content add constraint fk_c_product_filegroup foreign key (product_filegroup) references filegroups(filegroup);

alter table content add constraint fk_c_product_filetype foreign key (product_filetype) references filetypes(filetype);

alter table content add constraint fk_c_product_linkgroup foreign key (product_linkgroup) references linkgroups(linkgroup);

alter table content add constraint fk_c_product_linktype foreign key (product_linktype) references linktypes(linktype);

alter table content add constraint fk_c_product_productgroup foreign key (product_productgroup) references productgroups(productgroup);

alter table content add constraint fk_c_product_producttype foreign key (product_producttype) references producttypes(producttype);

alter table content add constraint fk_c_product_usergroup foreign key (product_usergroup) references usergroups(usergroup);

alter table content add constraint fk_c_product_usertype foreign key (product_usertype) references usertypes(usertype);



alter table content_elements add constraint fk_e_content_id foreign key (content_id) references content(id);

alter table content_elements add constraint fk_e_element foreign key (element) references elements(element);

alter table content_elements add constraint fk_e_element_id foreign key (element_id) references content(id);



alter table content_archive add constraint fk_ca_id foreign key (id) references content(id);

alter table content_archive add constraint fk_ca_created_by foreign key (created_by) references users(username);

alter table content_archive add constraint fk_ca_updated_by foreign key (updated_by) references users(username);

alter table content_archive add constraint fk_ca_published_by foreign key (published_by) references users(username);

alter table content_archive add constraint fk_ca_status_by foreign key (status_by) references users(username);

alter table content_archive add constraint fk_ca_version foreign key (version) references versions(version);

alter table content_archive add constraint fk_ca_version_master foreign key (version_master) references content(id);

alter table content_archive add constraint fk_ca_template foreign key (template) references content(id);

alter table content_archive add constraint fk_ca_stylesheet foreign key (stylesheet) references content(id);

alter table content_archive add constraint fk_ca_scripts foreign key (scripts) references content(id);

alter table content_archive add constraint fk_ca_image1 foreign key (image1) references content(id);

alter table content_archive add constraint fk_ca_image2 foreign key (image2) references content(id);

alter table content_archive add constraint fk_ca_image3 foreign key (image3) references content(id);

alter table content_archive add constraint fk_ca_file1 foreign key (file1) references content(id);

alter table content_archive add constraint fk_ca_file2 foreign key (file2) references content(id);

alter table content_archive add constraint fk_ca_file3 foreign key (file3) references content(id);

alter table content_archive add constraint fk_ca_link1 foreign key (link1) references content(id);

alter table content_archive add constraint fk_ca_link2 foreign key (link2) references content(id);

alter table content_archive add constraint fk_ca_link3 foreign key (link3) references content(id);

alter table content_archive add constraint fk_ca_contentgroup foreign key (contentgroup) references contentgroups(contentgroup);

alter table content_archive add constraint fk_ca_contenttype foreign key (contenttype) references contenttypes(contenttype);

alter table content_archive add constraint fk_ca_users_group foreign key (users_group) references usergroups(usergroup);

alter table content_archive add constraint fk_ca_users_type foreign key (users_type) references usertypes(usertype);

alter table content_archive add constraint fk_ca_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table content_archive add constraint fk_ca_developers_type foreign key (developers_type) references usertypes(usertype);

alter table content_archive add constraint fk_ca_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table content_archive add constraint fk_ca_creators_type foreign key (creators_type) references usertypes(usertype);

alter table content_archive add constraint fk_ca_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table content_archive add constraint fk_ca_editors_type foreign key (editors_type) references usertypes(usertype);

alter table content_archive add constraint fk_ca_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table content_archive add constraint fk_ca_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table content_archive add constraint fk_ca_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table content_archive add constraint fk_ca_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table content_archive add constraint fk_ca_users_users foreign key (users_users) references users(id);

alter table content_archive add constraint fk_ca_developers_users foreign key (developers_users) references users(id);

alter table content_archive add constraint fk_ca_creators_users foreign key (creators_users) references users(id);

alter table content_archive add constraint fk_ca_editors_users foreign key (editors_users) references users(id);

alter table content_archive add constraint fk_ca_publishers_users foreign key (publishers_users) references users(id);

alter table content_archive add constraint fk_ca_administrators_users foreign key (administrators_users) references users(id);

alter table content_archive add constraint fk_ca_checkedout foreign key (checkedout) references users(username);

alter table content_archive add constraint fk_ca_page_top foreign key (page_top) references content(id);

alter table content_archive add constraint fk_ca_page_up foreign key (page_up) references content(id);

alter table content_archive add constraint fk_ca_page_next foreign key (page_next) references content(id);

alter table content_archive add constraint fk_ca_page_previous foreign key (page_previous) references content(id);

alter table content_archive add constraint fk_ca_page_first foreign key (page_first) references content(id);

alter table content_archive add constraint fk_ca_page_last foreign key (page_last) references content(id);

alter table content_archive add constraint fk_ca_related foreign key (related) references content(id);

alter table content_archive add constraint fk_ca_product_currency foreign key (product_currency) references currencies(title);

alter table content_archive add constraint fk_ca_product_email foreign key (product_email) references content(id);

alter table content_archive add constraint fk_ca_product_user foreign key (product_user) references users(username);

alter table content_archive add constraint fk_ca_product_page foreign key (product_page) references content(id);

alter table content_archive add constraint fk_ca_product_hosting foreign key (product_hosting) references hosting(client_address);

alter table content_archive add constraint fk_ca_status foreign key (status) references workflow(fromstate);

alter table content_archive add constraint fk_ca_status foreign key (status) references workflow(tostate);

alter table content_archive add constraint fk_ca_product_content foreign key (product_content) references content(id);

alter table content_archive add constraint fk_ca_product_contentgroup foreign key (product_contentgroup) references contentgroups(contentgroup);

alter table content_archive add constraint fk_ca_product_contenttype foreign key (product_contenttype) references contenttypes(contenttype);

alter table content_archive add constraint fk_ca_product_imagegroup foreign key (product_imagegroup) references imagegroups(imagegroup);

alter table content_archive add constraint fk_ca_product_imagetype foreign key (product_imagetype) references imagetypes(imagetype);

alter table content_archive add constraint fk_ca_product_filegroup foreign key (product_filegroup) references filegroups(filegroup);

alter table content_archive add constraint fk_ca_product_filetype foreign key (product_filetype) references filetypes(filetype);

alter table content_archive add constraint fk_ca_product_linkgroup foreign key (product_linkgroup) references linkgroups(linkgroup);

alter table content_archive add constraint fk_ca_product_linktype foreign key (product_linktype) references linktypes(linktype);

alter table content_archive add constraint fk_ca_product_productgroup foreign key (product_productgroup) references productgroups(productgroup);

alter table content_archive add constraint fk_ca_product_producttype foreign key (product_producttype) references producttypes(producttype);

alter table content_archive add constraint fk_ca_product_usergroup foreign key (product_usergroup) references usergroups(usergroup);

alter table content_archive add constraint fk_ca_product_usertype foreign key (product_usertype) references usertypes(usertype);



alter table content_archive_elements add constraint fk_cae_content_archiveid foreign key (content_archiveid) references content_archive(archiveid);

alter table content_archive_elements add constraint fk_cae_content_id foreign key (content_id) references content_archive(id);

alter table content_archive_elements add constraint fk_cae_element foreign key (element) references elements(element);

alter table content_archive_elements add constraint fk_cae_element_id foreign key (element_id) references content_archive(id);



alter table content_public add constraint fk_cp_id foreign key (id) references content(id);

alter table content_public add constraint fk_cp_created_by foreign key (created_by) references users(username);

alter table content_public add constraint fk_cp_updated_by foreign key (updated_by) references users(username);

alter table content_public add constraint fk_cp_published_by foreign key (published_by) references users(username);

alter table content_public add constraint fk_cp_status_by foreign key (status_by) references users(username);

alter table content_public add constraint fk_cp_version foreign key (version) references versions(version);

alter table content_public add constraint fk_cp_version_master foreign key (version_master) references content(id);

alter table content_public add constraint fk_cp_template foreign key (template) references content(id);

alter table content_public add constraint fk_cp_stylesheet foreign key (stylesheet) references content(id);

alter table content_public add constraint fk_cp_scripts foreign key (scripts) references content(id);

alter table content_public add constraint fk_cp_image1 foreign key (image1) references content(id);

alter table content_public add constraint fk_cp_image2 foreign key (image2) references content(id);

alter table content_public add constraint fk_cp_image3 foreign key (image3) references content(id);

alter table content_public add constraint fk_cp_file1 foreign key (file1) references content(id);

alter table content_public add constraint fk_cp_file2 foreign key (file2) references content(id);

alter table content_public add constraint fk_cp_file3 foreign key (file3) references content(id);

alter table content_public add constraint fk_cp_link1 foreign key (link1) references content(id);

alter table content_public add constraint fk_cp_link2 foreign key (link2) references content(id);

alter table content_public add constraint fk_cp_link3 foreign key (link3) references content(id);

alter table content_public add constraint fk_cp_contentgroup foreign key (contentgroup) references contentgroups(contentgroup);

alter table content_public add constraint fk_cp_contenttype foreign key (contenttype) references contenttypes(contenttype);

alter table content_public add constraint fk_cp_users_group foreign key (users_group) references usergroups(usergroup);

alter table content_public add constraint fk_cp_users_type foreign key (users_type) references usertypes(usertype);

alter table content_public add constraint fk_cp_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table content_public add constraint fk_cp_developers_type foreign key (developers_type) references usertypes(usertype);

alter table content_public add constraint fk_cp_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table content_public add constraint fk_cp_creators_type foreign key (creators_type) references usertypes(usertype);

alter table content_public add constraint fk_cp_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table content_public add constraint fk_cp_editors_type foreign key (editors_type) references usertypes(usertype);

alter table content_public add constraint fk_cp_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table content_public add constraint fk_cp_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table content_public add constraint fk_cp_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table content_public add constraint fk_cp_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table content_public add constraint fk_cp_users_users foreign key (users_users) references users(id);

alter table content_public add constraint fk_cp_developers_users foreign key (developers_users) references users(id);

alter table content_public add constraint fk_cp_creators_users foreign key (creators_users) references users(id);

alter table content_public add constraint fk_cp_editors_users foreign key (editors_users) references users(id);

alter table content_public add constraint fk_cp_publishers_users foreign key (publishers_users) references users(id);

alter table content_public add constraint fk_cp_administrators_users foreign key (administrators_users) references users(id);

alter table content_public add constraint fk_cp_checkedout foreign key (checkedout) references users(username);

alter table content_public add constraint fk_cp_page_top foreign key (page_top) references content(id);

alter table content_public add constraint fk_cp_page_up foreign key (page_up) references content(id);

alter table content_public add constraint fk_cp_page_next foreign key (page_next) references content(id);

alter table content_public add constraint fk_cp_page_previous foreign key (page_previous) references content(id);

alter table content_public add constraint fk_cp_page_first foreign key (page_first) references content(id);

alter table content_public add constraint fk_cp_page_last foreign key (page_last) references content(id);

alter table content_public add constraint fk_cp_related foreign key (related) references content(id);

alter table content_public add constraint fk_cp_product_currency foreign key (product_currency) references currencies(title);

alter table content_public add constraint fk_cp_product_email foreign key (product_email) references content(id);

alter table content_public add constraint fk_cp_product_user foreign key (product_user) references users(username);

alter table content_public add constraint fk_cp_product_page foreign key (product_page) references content(id);

alter table content_public add constraint fk_cp_product_hosting foreign key (product_hosting) references hosting(client_address);

alter table content_public add constraint fk_cp_status foreign key (status) references workflow(fromstate);

alter table content_public add constraint fk_cp_status foreign key (status) references workflow(tostate);

alter table content_public add constraint fk_cp_product_content foreign key (product_content) references content(id);

alter table content_public add constraint fk_cp_product_contentgroup foreign key (product_contentgroup) references contentgroups(contentgroup);

alter table content_public add constraint fk_cp_product_contenttype foreign key (product_contenttype) references contenttypes(contenttype);

alter table content_public add constraint fk_cp_product_imagegroup foreign key (product_imagegroup) references imagegroups(imagegroup);

alter table content_public add constraint fk_cp_product_imagetype foreign key (product_imagetype) references imagetypes(imagetype);

alter table content_public add constraint fk_cp_product_filegroup foreign key (product_filegroup) references filegroups(filegroup);

alter table content_public add constraint fk_cp_product_filetype foreign key (product_filetype) references filetypes(filetype);

alter table content_public add constraint fk_cp_product_linkgroup foreign key (product_linkgroup) references linkgroups(linkgroup);

alter table content_public add constraint fk_cp_product_linktype foreign key (product_linktype) references linktypes(linktype);

alter table content_public add constraint fk_cp_product_productgroup foreign key (product_productgroup) references productgroups(productgroup);

alter table content_public add constraint fk_cp_product_producttype foreign key (product_producttype) references producttypes(producttype);

alter table content_public add constraint fk_cp_product_usergroup foreign key (product_usergroup) references usergroups(usergroup);

alter table content_public add constraint fk_cp_product_usertype foreign key (product_usertype) references usertypes(usertype);



alter table content_public_elements add constraint fk_cpe_content_id foreign key (content_id) references content_public(id);

alter table content_public_elements add constraint fk_cpe_element foreign key (element) references elements(element);

alter table content_public_elements add constraint fk_cpe_element_id foreign key (element_id) references content_public(id);



alter table content_dependencies add constraint fk_cd_content_id foreign key (content_id) references content(id);

alter table content_dependencies add constraint fk_cd_dependency_id foreign key (dependency_id) references content(id);



alter table content_public_dependencies add constraint fk_cd_content_id foreign key (content_id) references content_public(id);

alter table content_public_dependencies add constraint fk_cd_dependency_id foreign key (dependency_id) references content_public(id);



alter table contentgroups add constraint fk_cg_users_group foreign key (users_group) references usergroups(usergroup);

alter table contentgroups add constraint fk_cg_users_type foreign key (users_type) references usertypes(usertype);

alter table contentgroups add constraint fk_cg_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table contentgroups add constraint fk_cg_developers_type foreign key (developers_type) references usertypes(usertype);

alter table contentgroups add constraint fk_cg_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table contentgroups add constraint fk_cg_creators_type foreign key (creators_type) references usertypes(usertype);

alter table contentgroups add constraint fk_cg_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table contentgroups add constraint fk_cg_editors_type foreign key (editors_type) references usertypes(usertype);

alter table contentgroups add constraint fk_cg_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table contentgroups add constraint fk_cg_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table contentgroups add constraint fk_cg_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table contentgroups add constraint fk_cg_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table contentgroups add constraint fk_cg_users_users foreign key (users_users) references users(id);

alter table contentgroups add constraint fk_cg_developers_users foreign key (developers_users) references users(id);

alter table contentgroups add constraint fk_cg_creators_users foreign key (creators_users) references users(id);

alter table contentgroups add constraint fk_cg_editors_users foreign key (editors_users) references users(id);

alter table contentgroups add constraint fk_cg_publishers_users foreign key (publishers_users) references users(id);

alter table contentgroups add constraint fk_cg_administrators_users foreign key (administrators_users) references users(id);

alter table contentgroups add constraint fk_cg_template foreign key (template) references content(id);

alter table contentgroups add constraint fk_cg_stylesheet foreign key (stylesheet) references content(id);

alter table contentgroups add constraint fk_cg_contentclass foreign key (contentclass) references elements(element);



alter table contenttypes add constraint fk_ct_users_group foreign key (users_group) references usergroups(usergroup);

alter table contenttypes add constraint fk_ct_users_type foreign key (users_type) references usertypes(usertype);

alter table contenttypes add constraint fk_ct_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table contenttypes add constraint fk_ct_developers_type foreign key (developers_type) references usertypes(usertype);

alter table contenttypes add constraint fk_ct_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table contenttypes add constraint fk_ct_creators_type foreign key (creators_type) references usertypes(usertype);

alter table contenttypes add constraint fk_ct_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table contenttypes add constraint fk_ct_editors_type foreign key (editors_type) references usertypes(usertype);

alter table contenttypes add constraint fk_ct_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table contenttypes add constraint fk_ct_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table contenttypes add constraint fk_ct_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table contenttypes add constraint fk_ct_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table contenttypes add constraint fk_ct_users_users foreign key (users_users) references users(id);

alter table contenttypes add constraint fk_ct_developers_users foreign key (developers_users) references users(id);

alter table contenttypes add constraint fk_ct_creators_users foreign key (creators_users) references users(id);

alter table contenttypes add constraint fk_ct_editors_users foreign key (editors_users) references users(id);

alter table contenttypes add constraint fk_ct_publishers_users foreign key (publishers_users) references users(id);

alter table contenttypes add constraint fk_ct_administrators_users foreign key (administrators_users) references users(id);

alter table contenttypes add constraint fk_ct_template foreign key (template) references content(id);

alter table contenttypes add constraint fk_ct_stylesheet foreign key (stylesheet) references content(id);

alter table contenttypes add constraint fk_ct_contentclass foreign key (contentclass) references elements(element);



alter table filegroups add constraint fk_fg_users_group foreign key (users_group) references usergroups(usergroup);

alter table filegroups add constraint fk_fg_users_type foreign key (users_type) references usertypes(usertype);

alter table filegroups add constraint fk_fg_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table filegroups add constraint fk_fg_developers_type foreign key (developers_type) references usertypes(usertype);

alter table filegroups add constraint fk_fg_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table filegroups add constraint fk_fg_creators_type foreign key (creators_type) references usertypes(usertype);

alter table filegroups add constraint fk_fg_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table filegroups add constraint fk_fg_editors_type foreign key (editors_type) references usertypes(usertype);

alter table filegroups add constraint fk_fg_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table filegroups add constraint fk_fg_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table filegroups add constraint fk_fg_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table filegroups add constraint fk_fg_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table filegroups add constraint fk_fg_users_users foreign key (users_users) references users(id);

alter table filegroups add constraint fk_fg_developers_users foreign key (developers_users) references users(id);

alter table filegroups add constraint fk_fg_creators_users foreign key (creators_users) references users(id);

alter table filegroups add constraint fk_fg_editors_users foreign key (editors_users) references users(id);

alter table filegroups add constraint fk_fg_publishers_users foreign key (publishers_users) references users(id);

alter table filegroups add constraint fk_fg_administrators_users foreign key (administrators_users) references users(id);

alter table filegroups add constraint fk_fg_template foreign key (template) references content(id);

alter table filegroups add constraint fk_fg_stylesheet foreign key (stylesheet) references content(id);



alter table filetypes add constraint fk_ft_users_group foreign key (users_group) references usergroups(usergroup);

alter table filetypes add constraint fk_ft_users_type foreign key (users_type) references usertypes(usertype);

alter table filetypes add constraint fk_ft_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table filetypes add constraint fk_ft_developers_type foreign key (developers_type) references usertypes(usertype);

alter table filetypes add constraint fk_ft_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table filetypes add constraint fk_ft_creators_type foreign key (creators_type) references usertypes(usertype);

alter table filetypes add constraint fk_ft_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table filetypes add constraint fk_ft_editors_type foreign key (editors_type) references usertypes(usertype);

alter table filetypes add constraint fk_ft_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table filetypes add constraint fk_ft_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table filetypes add constraint fk_ft_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table filetypes add constraint fk_ft_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table filetypes add constraint fk_ft_users_users foreign key (users_users) references users(id);

alter table filetypes add constraint fk_ft_developers_users foreign key (developers_users) references users(id);

alter table filetypes add constraint fk_ft_creators_users foreign key (creators_users) references users(id);

alter table filetypes add constraint fk_ft_editors_users foreign key (editors_users) references users(id);

alter table filetypes add constraint fk_ft_publishers_users foreign key (publishers_users) references users(id);

alter table filetypes add constraint fk_ft_administrators_users foreign key (administrators_users) references users(id);

alter table filetypes add constraint fk_ft_template foreign key (template) references content(id);

alter table filetypes add constraint fk_ft_stylesheet foreign key (stylesheet) references content(id);



alter table hosting add constraint fk_h_user_id foreign key (user_id) references users(id);

alter table hosting add constraint fk_h_hostinggroup foreign key (hostinggroup) references hostinggroups(hostinggroup);

alter table hosting add constraint fk_h_hostingtype foreign key (hostingtype) references hostingtypes(hostingtype);

alter table hosting add constraint fk_h_scheduled_publish_email foreign key (scheduled_publish_email) references content(id);

alter table hosting add constraint fk_h_scheduled_notify_email foreign key (scheduled_notify_email) references content(id);

alter table hosting add constraint fk_h_scheduled_unpublish_email foreign key (scheduled_unpublish_email) references content(id);

alter table hosting add constraint fk_h_users_group foreign key (users_group) references usergroups(usergroup);

alter table hosting add constraint fk_h_users_type foreign key (users_type) references usertypes(usertype);

alter table hosting add constraint fk_h_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table hosting add constraint fk_h_creators_type foreign key (creators_type) references usertypes(usertype);

alter table hosting add constraint fk_h_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table hosting add constraint fk_h_editors_type foreign key (editors_type) references usertypes(usertype);

alter table hosting add constraint fk_h_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table hosting add constraint fk_h_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table hosting add constraint fk_h_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table hosting add constraint fk_h_administrators_type foreign key (administrators_type) references usertypes(usertype);



alter table hostinggroups add constraint fk_hg_users_group foreign key (users_group) references usergroups(usergroup);

alter table hostinggroups add constraint fk_hg_users_type foreign key (users_type) references usertypes(usertype);

alter table hostinggroups add constraint fk_hg_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table hostinggroups add constraint fk_hg_developers_type foreign key (developers_type) references usertypes(usertype);

alter table hostinggroups add constraint fk_hg_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table hostinggroups add constraint fk_hg_creators_type foreign key (creators_type) references usertypes(usertype);

alter table hostinggroups add constraint fk_hg_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table hostinggroups add constraint fk_hg_editors_type foreign key (editors_type) references usertypes(usertype);

alter table hostinggroups add constraint fk_hg_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table hostinggroups add constraint fk_hg_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table hostinggroups add constraint fk_hg_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table hostinggroups add constraint fk_hg_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table hostinggroups add constraint fk_hg_users_users foreign key (users_users) references users(id);

alter table hostinggroups add constraint fk_hg_developers_users foreign key (developers_users) references users(id);

alter table hostinggroups add constraint fk_hg_creators_users foreign key (creators_users) references users(id);

alter table hostinggroups add constraint fk_hg_editors_users foreign key (editors_users) references users(id);

alter table hostinggroups add constraint fk_hg_publishers_users foreign key (publishers_users) references users(id);

alter table hostinggroups add constraint fk_hg_administrators_users foreign key (administrators_users) references users(id);



alter table hostingtypes add constraint fk_ht_users_group foreign key (users_group) references usergroups(usergroup);

alter table hostingtypes add constraint fk_ht_users_type foreign key (users_type) references usertypes(usertype);

alter table hostingtypes add constraint fk_ht_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table hostingtypes add constraint fk_ht_developers_type foreign key (developers_type) references usertypes(usertype);

alter table hostingtypes add constraint fk_ht_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table hostingtypes add constraint fk_ht_creators_type foreign key (creators_type) references usertypes(usertype);

alter table hostingtypes add constraint fk_ht_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table hostingtypes add constraint fk_ht_editors_type foreign key (editors_type) references usertypes(usertype);

alter table hostingtypes add constraint fk_ht_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table hostingtypes add constraint fk_ht_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table hostingtypes add constraint fk_ht_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table hostingtypes add constraint fk_ht_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table hostingtypes add constraint fk_ht_users_users foreign key (users_users) references users(id);

alter table hostingtypes add constraint fk_ht_developers_users foreign key (developers_users) references users(id);

alter table hostingtypes add constraint fk_ht_creators_users foreign key (creators_users) references users(id);

alter table hostingtypes add constraint fk_ht_editors_users foreign key (editors_users) references users(id);

alter table hostingtypes add constraint fk_ht_publishers_users foreign key (publishers_users) references users(id);

alter table hostingtypes add constraint fk_ht_administrators_users foreign key (administrators_users) references users(id);



alter table imagegroups add constraint fk_ig_users_group foreign key (users_group) references usergroups(usergroup);

alter table imagegroups add constraint fk_ig_users_type foreign key (users_type) references usertypes(usertype);

alter table imagegroups add constraint fk_ig_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table imagegroups add constraint fk_ig_developers_type foreign key (developers_type) references usertypes(usertype);

alter table imagegroups add constraint fk_ig_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table imagegroups add constraint fk_ig_creators_type foreign key (creators_type) references usertypes(usertype);

alter table imagegroups add constraint fk_ig_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table imagegroups add constraint fk_ig_editors_type foreign key (editors_type) references usertypes(usertype);

alter table imagegroups add constraint fk_ig_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table imagegroups add constraint fk_ig_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table imagegroups add constraint fk_ig_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table imagegroups add constraint fk_ig_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table imagegroups add constraint fk_ig_users_users foreign key (users_users) references users(id);

alter table imagegroups add constraint fk_ig_developers_users foreign key (developers_users) references users(id);

alter table imagegroups add constraint fk_ig_creators_users foreign key (creators_users) references users(id);

alter table imagegroups add constraint fk_ig_editors_users foreign key (editors_users) references users(id);

alter table imagegroups add constraint fk_ig_publishers_users foreign key (publishers_users) references users(id);

alter table imagegroups add constraint fk_ig_administrators_users foreign key (administrators_users) references users(id);

alter table imagegroups add constraint fk_ig_template foreign key (template) references content(id);

alter table imagegroups add constraint fk_ig_stylesheet foreign key (stylesheet) references content(id);



alter table imagetypes add constraint fk_it_users_group foreign key (users_group) references usergroups(usergroup);

alter table imagetypes add constraint fk_it_users_type foreign key (users_type) references usertypes(usertype);

alter table imagetypes add constraint fk_it_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table imagetypes add constraint fk_it_developers_type foreign key (developers_type) references usertypes(usertype);

alter table imagetypes add constraint fk_it_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table imagetypes add constraint fk_it_creators_type foreign key (creators_type) references usertypes(usertype);

alter table imagetypes add constraint fk_it_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table imagetypes add constraint fk_it_editors_type foreign key (editors_type) references usertypes(usertype);

alter table imagetypes add constraint fk_it_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table imagetypes add constraint fk_it_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table imagetypes add constraint fk_it_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table imagetypes add constraint fk_it_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table imagetypes add constraint fk_it_users_users foreign key (users_users) references users(id);

alter table imagetypes add constraint fk_it_developers_users foreign key (developers_users) references users(id);

alter table imagetypes add constraint fk_it_creators_users foreign key (creators_users) references users(id);

alter table imagetypes add constraint fk_it_editors_users foreign key (editors_users) references users(id);

alter table imagetypes add constraint fk_it_publishers_users foreign key (publishers_users) references users(id);

alter table imagetypes add constraint fk_it_administrators_users foreign key (administrators_users) references users(id);

alter table imagetypes add constraint fk_it_template foreign key (template) references content(id);

alter table imagetypes add constraint fk_it_stylesheet foreign key (stylesheet) references content(id);



alter table linkgroups add constraint fk_lg_users_group foreign key (users_group) references usergroups(usergroup);

alter table linkgroups add constraint fk_lg_users_type foreign key (users_type) references usertypes(usertype);

alter table linkgroups add constraint fk_lg_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table linkgroups add constraint fk_lg_developers_type foreign key (developers_type) references usertypes(usertype);

alter table linkgroups add constraint fk_lg_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table linkgroups add constraint fk_lg_creators_type foreign key (creators_type) references usertypes(usertype);

alter table linkgroups add constraint fk_lg_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table linkgroups add constraint fk_lg_editors_type foreign key (editors_type) references usertypes(usertype);

alter table linkgroups add constraint fk_lg_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table linkgroups add constraint fk_lg_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table linkgroups add constraint fk_lg_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table linkgroups add constraint fk_lg_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table linkgroups add constraint fk_lg_users_users foreign key (users_users) references users(id);

alter table linkgroups add constraint fk_lg_developers_users foreign key (developers_users) references users(id);

alter table linkgroups add constraint fk_lg_creators_users foreign key (creators_users) references users(id);

alter table linkgroups add constraint fk_lg_editors_users foreign key (editors_users) references users(id);

alter table linkgroups add constraint fk_lg_publishers_users foreign key (publishers_users) references users(id);

alter table linkgroups add constraint fk_lg_administrators_users foreign key (administrators_users) references users(id);

alter table linkgroups add constraint fk_lg_template foreign key (template) references content(id);

alter table linkgroups add constraint fk_lg_stylesheet foreign key (stylesheet) references content(id);



alter table linktypes add constraint fk_lt_users_group foreign key (users_group) references usergroups(usergroup);

alter table linktypes add constraint fk_lt_users_type foreign key (users_type) references usertypes(usertype);

alter table linktypes add constraint fk_lt_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table linktypes add constraint fk_lt_developers_type foreign key (developers_type) references usertypes(usertype);

alter table linktypes add constraint fk_lt_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table linktypes add constraint fk_lt_creators_type foreign key (creators_type) references usertypes(usertype);

alter table linktypes add constraint fk_lt_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table linktypes add constraint fk_lt_editors_type foreign key (editors_type) references usertypes(usertype);

alter table linktypes add constraint fk_lt_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table linktypes add constraint fk_lt_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table linktypes add constraint fk_lt_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table linktypes add constraint fk_lt_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table linktypes add constraint fk_lt_users_users foreign key (users_users) references users(id);

alter table linktypes add constraint fk_lt_developers_users foreign key (developers_users) references users(id);

alter table linktypes add constraint fk_lt_creators_users foreign key (creators_users) references users(id);

alter table linktypes add constraint fk_lt_editors_users foreign key (editors_users) references users(id);

alter table linktypes add constraint fk_lt_publishers_users foreign key (publishers_users) references users(id);

alter table linktypes add constraint fk_lt_administrators_users foreign key (administrators_users) references users(id);

alter table linktypes add constraint fk_lt_template foreign key (template) references content(id);

alter table linktypes add constraint fk_lt_stylesheet foreign key (stylesheet) references content(id);



alter table usergroups add constraint fk_ug_login_page foreign key (login_page) references content(id);

alter table usergroups add constraint fk_ug_users_group foreign key (users_group) references usergroups(usergroup);

alter table usergroups add constraint fk_ug_users_type foreign key (users_type) references usertypes(usertype);

alter table usergroups add constraint fk_ug_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table usergroups add constraint fk_ug_creators_type foreign key (creators_type) references usertypes(usertype);

alter table usergroups add constraint fk_ug_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table usergroups add constraint fk_ug_editors_type foreign key (editors_type) references usertypes(usertype);

alter table usergroups add constraint fk_ug_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table usergroups add constraint fk_ug_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table usergroups add constraint fk_ug_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table usergroups add constraint fk_ug_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table usergroups add constraint fk_ug_subscribers_group foreign key (subscribers_group) references usergroups(usergroup);

alter table usergroups add constraint fk_ug_subscribers_type foreign key (subscribers_type) references usertypes(usertype);

alter table usergroups add constraint fk_ug_users_users foreign key (users_users) references users(id);

alter table usergroups add constraint fk_ug_creators_users foreign key (creators_users) references users(id);

alter table usergroups add constraint fk_ug_editors_users foreign key (editors_users) references users(id);

alter table usergroups add constraint fk_ug_publishers_users foreign key (publishers_users) references users(id);

alter table usergroups add constraint fk_ug_administrators_users foreign key (administrators_users) references users(id);

alter table usergroups add constraint fk_ug_subscribers_users foreign key (subscribers_users) references users(id);



alter table usertypes add constraint fk_ut_login_page foreign key (login_page) references content(id);

alter table usertypes add constraint fk_ut_users_group foreign key (users_group) references usergroups(usergroup);

alter table usertypes add constraint fk_ut_users_type foreign key (users_type) references usertypes(usertype);

alter table usertypes add constraint fk_ut_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table usertypes add constraint fk_ut_creators_type foreign key (creators_type) references usertypes(usertype);

alter table usertypes add constraint fk_ut_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table usertypes add constraint fk_ut_editors_type foreign key (editors_type) references usertypes(usertype);

alter table usertypes add constraint fk_ut_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table usertypes add constraint fk_ut_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table usertypes add constraint fk_ut_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table usertypes add constraint fk_ut_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table usertypes add constraint fk_ut_subscribers_group foreign key (subscribers_group) references usergroups(usergroup);

alter table usertypes add constraint fk_ut_subscribers_type foreign key (subscribers_type) references usertypes(usertype);

alter table usertypes add constraint fk_ut_users_users foreign key (users_users) references users(id);

alter table usertypes add constraint fk_ut_creators_users foreign key (creators_users) references users(id);

alter table usertypes add constraint fk_ut_editors_users foreign key (editors_users) references users(id);

alter table usertypes add constraint fk_ut_publishers_users foreign key (publishers_users) references users(id);

alter table usertypes add constraint fk_ut_administrators_users foreign key (administrators_users) references users(id);

alter table usertypes add constraint fk_ut_subscribers_users foreign key (subscribers_users) references users(id);



alter table usergroups2 add constraint fk_ug2_usergroup foreign key (usergroup) references usergroups(usergroup);

alter table usergroups2 add constraint fk_ug2_subgroup foreign key (subgroup) references usergroups(usergroup);



alter table usertypes2 add constraint fk_ut2_usertype foreign key (usertype) references usertypes(usertype);

alter table usertypes2 add constraint fk_ut2_subtype foreign key (subtype) references usertypes(usertype);



alter table users_usergroups add constraint fk_uug_created_by foreign key (created_by) references users(username);

alter table users_usergroups add constraint fk_uug_updated_by foreign key (updated_by) references users(username);

alter table users_usergroups add constraint fk_uug_username foreign key (username) references users(username);

alter table users_usergroups add constraint fk_uug_usergroup foreign key (usergroup) references usergroups(usergroup);



alter table users_usertypes add constraint fk_uut_created_by foreign key (created_by) references users(username);

alter table users_usertypes add constraint fk_uut_updated_by foreign key (updated_by) references users(username);

alter table users_usertypes add constraint fk_uut_username foreign key (username) references users(username);

alter table users_usertypes add constraint fk_uut_usertype foreign key (usertype) references usertypes(usertype);



alter table versions add constraint fk_v_currencies foreign key (currencies) references currencies(title);

alter table versions add constraint fk_v_users_group foreign key (users_group) references usergroups(usergroup);

alter table versions add constraint fk_v_users_type foreign key (users_type) references usertypes(usertype);

alter table versions add constraint fk_v_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table versions add constraint fk_v_developers_type foreign key (developers_type) references usertypes(usertype);

alter table versions add constraint fk_v_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table versions add constraint fk_v_creators_type foreign key (creators_type) references usertypes(usertype);

alter table versions add constraint fk_v_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table versions add constraint fk_v_editors_type foreign key (editors_type) references usertypes(usertype);

alter table versions add constraint fk_v_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table versions add constraint fk_v_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table versions add constraint fk_v_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table versions add constraint fk_v_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table versions add constraint fk_v_users_users foreign key (users_users) references users(id);

alter table versions add constraint fk_v_developers_users foreign key (developers_users) references users(id);

alter table versions add constraint fk_v_creators_users foreign key (creators_users) references users(id);

alter table versions add constraint fk_v_editors_users foreign key (editors_users) references users(id);

alter table versions add constraint fk_v_publishers_users foreign key (publishers_users) references users(id);

alter table versions add constraint fk_v_administrators_users foreign key (administrators_users) references users(id);


alter table elements add constraint fk_e_currencies foreign key (currencies) references currencies(title);

alter table elements add constraint fk_e_users_group foreign key (users_group) references usergroups(usergroup);

alter table elements add constraint fk_e_users_type foreign key (users_type) references usertypes(usertype);

alter table elements add constraint fk_e_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table elements add constraint fk_e_developers_type foreign key (developers_type) references usertypes(usertype);

alter table elements add constraint fk_e_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table elements add constraint fk_e_creators_type foreign key (creators_type) references usertypes(usertype);

alter table elements add constraint fk_e_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table elements add constraint fk_e_editors_type foreign key (editors_type) references usertypes(usertype);

alter table elements add constraint fk_e_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table elements add constraint fk_e_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table elements add constraint fk_e_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table elements add constraint fk_e_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table elements add constraint fk_e_users_users foreign key (users_users) references users(id);

alter table elements add constraint fk_e_developers_users foreign key (developers_users) references users(id);

alter table elements add constraint fk_e_creators_users foreign key (creators_users) references users(id);

alter table elements add constraint fk_e_editors_users foreign key (editors_users) references users(id);

alter table elements add constraint fk_e_publishers_users foreign key (publishers_users) references users(id);

alter table elements add constraint fk_e_administrators_users foreign key (administrators_users) references users(id);



alter table websites add constraint fk_w_default_page foreign key (default_page) references content(id);

alter table websites add constraint fk_w_default_template foreign key (default_template) references content(id);

alter table websites add constraint fk_w_default_stylesheet foreign key (default_stylesheet) references content(id);

alter table websites add constraint fk_w_default_version foreign key (default_version) references versions(version);

alter table websites add constraint fk_w_default_currency foreign key (default_currency) references currencies(title);

alter table websites add constraint fk_w_default_country_d foreign key (default_country) references discounts(country);

alter table websites add constraint fk_w_default_country_s foreign key (default_country) references shipping(country);

alter table websites add constraint fk_w_default_country_t foreign key (default_country) references tax(country);

alter table websites add constraint fk_w_default_state_d foreign key (default_state) references discounts(state);

alter table websites add constraint fk_w_default_state_s foreign key (default_state) references shipping(state);

alter table websites add constraint fk_w_default_state_t foreign key (default_state) references tax(state);



alter table workflow add constraint fk_w_usertype foreign key (usertype) references usertypes(usertype);

alter table workflow add constraint fk_w_usergroup foreign key (usergroup) references usergroups(usergroup);

alter table workflow add constraint fk_w_contentclass foreign key (contentclass) references elements(element);

alter table workflow add constraint fk_w_contenttype foreign key (contenttype) references contenttypes(contenttype);

alter table workflow add constraint fk_w_contentgroup foreign key (contentgroup) references contentgroups(contentgroup);

alter table workflow add constraint fk_w_version foreign key (version) references versions(version);

alter table workflow add constraint fk_w_notify_email foreign key (notify_email) references content(id);



alter table users add constraint fk_u_created_by foreign key (created_by) references users(username);

alter table users add constraint fk_u_updated_by foreign key (updated_by) references users(username);

alter table users add constraint fk_u_usergroup foreign key (usergroup) references usergroups(usergroup);

alter table users add constraint fk_u_usertype foreign key (usertype) references usertypes(usertype);

alter table users add constraint fk_u_users_group foreign key (users_group) references usergroups(usergroup);

alter table users add constraint fk_u_users_type foreign key (users_type) references usertypes(usertype);

alter table users add constraint fk_u_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table users add constraint fk_u_creators_type foreign key (creators_type) references usertypes(usertype);

alter table users add constraint fk_u_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table users add constraint fk_u_editors_type foreign key (editors_type) references usertypes(usertype);

alter table users add constraint fk_u_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table users add constraint fk_u_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table users add constraint fk_u_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table users add constraint fk_u_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table users add constraint fk_u_scheduled_publish_email foreign key (scheduled_publish_email) references content(id);

alter table users add constraint fk_u_scheduled_notify_email foreign key (scheduled_notify_email) references content(id);

alter table users add constraint fk_u_scheduled_unpublish_email foreign key (scheduled_unpublish_email) references content(id);



alter table user2groups add constraint fk_u2g_usergroup foreign key (usergroup) references usergroups(usergroup);



alter table user2types add constraint fk_u2t_usertype foreign key (usertype) references usertypes(usertype);



alter table permissions add constraint fk_p_username foreign key (username) references users(username);

alter table permissions add constraint fk_p_usergroup foreign key (usergroup) references usergroups(usergroup);

alter table permissions add constraint fk_p_usertype foreign key (usertype) references usertypes(usertype);



alter table guestbook add constraint fk_g_created_by foreign key (created_by) references users(username);

alter table guestbook add constraint fk_g_updated_by foreign key (updated_by) references users(username);

alter table guestbook add constraint fk_g_published_by foreign key (published_by) references users(username);



alter table orderitems add constraint fk_oi_order_id foreign key (order_id) references orders(id);

alter table orderitems add constraint fk_oi_product_id foreign key (product_id) references content(id);

alter table orderitems add constraint fk_oi_version foreign key (version) references versions(version);

alter table orderitems add constraint fk_oi_image1 foreign key (image1) references content(id);

alter table orderitems add constraint fk_oi_image2 foreign key (image2) references content(id);

alter table orderitems add constraint fk_oi_image3 foreign key (image3) references content(id);

alter table orderitems add constraint fk_oi_file1 foreign key (file1) references content(id);

alter table orderitems add constraint fk_oi_file2 foreign key (file2) references content(id);

alter table orderitems add constraint fk_oi_file3 foreign key (file3) references content(id);

alter table orderitems add constraint fk_oi_link1 foreign key (link1) references content(id);

alter table orderitems add constraint fk_oi_link2 foreign key (link2) references content(id);

alter table orderitems add constraint fk_oi_link3 foreign key (link3) references content(id);

alter table orderitems add constraint fk_oi_product_currency foreign key (product_currency) references currencies(title);

alter table orderitems add constraint fk_oi_product_email foreign key (product_email) references content(id);

alter table orderitems add constraint fk_oi_product_user foreign key (product_user) references users(username);

alter table orderitems add constraint fk_oi_product_page foreign key (product_page) references content(id);

alter table orderitems add constraint fk_oi_product_hosting foreign key (product_hosting) references hosting(client_address);

alter table orderitems add constraint fk_oi_product_content foreign key (product_content) references content(id);

alter table orderitems add constraint fk_oi_product_contentgroup foreign key (product_contentgroup) references contentgroups(contentgroup);

alter table orderitems add constraint fk_oi_product_contenttype foreign key (product_contenttype) references contenttypes(contenttype);

alter table orderitems add constraint fk_oi_product_imagegroup foreign key (product_imagegroup) references imagegroups(imagegroup);

alter table orderitems add constraint fk_oi_product_imagetype foreign key (product_imagetype) references imagetypes(imagetype);

alter table orderitems add constraint fk_oi_product_filegroup foreign key (product_filegroup) references filegroups(filegroup);

alter table orderitems add constraint fk_oi_product_filetype foreign key (product_filetype) references filetypes(filetype);

alter table orderitems add constraint fk_oi_product_linkgroup foreign key (product_linkgroup) references linkgroups(linkgroup);

alter table orderitems add constraint fk_oi_product_linktype foreign key (product_linktype) references linktypes(linktype);

alter table orderitems add constraint fk_oi_product_productgroup foreign key (product_productgroup) references productgroups(productgroup);

alter table orderitems add constraint fk_oi_product_producttype foreign key (product_producttype) references producttypes(producttype);

alter table orderitems add constraint fk_oi_product_usergroup foreign key (product_usergroup) references usergroups(usergroup);

alter table orderitems add constraint fk_oi_product_usertype foreign key (product_usertype) references usertypes(usertype);



alter table orders add constraint fk_o_user_id foreign key (user_id) references users(id);

alter table orders add constraint fk_o_created_by foreign key (created_by) references users(username);

alter table orders add constraint fk_o_updated_by foreign key (updated_by) references users(username);

alter table orders add constraint fk_o_published_by foreign key (published_by) references users(username);

alter table orders add constraint fk_o_checkedout foreign key (checkedout) references users(username);

alter table orders add constraint fk_o_order_currency foreign key (order_currency) references currencies(title);

alter table orders add constraint fk_o_order_currency_base foreign key (order_currency_base) references currencies(title);

alter table orders add constraint fk_o_affiliate foreign key (affiliate) references users(username);



alter table productgroups add constraint fk_pg_users_group foreign key (users_group) references usergroups(usergroup);

alter table productgroups add constraint fk_pg_users_type foreign key (users_type) references usertypes(usertype);

alter table productgroups add constraint fk_pg_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table productgroups add constraint fk_pg_developers_type foreign key (developers_type) references usertypes(usertype);

alter table productgroups add constraint fk_pg_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table productgroups add constraint fk_pg_creators_type foreign key (creators_type) references usertypes(usertype);

alter table productgroups add constraint fk_pg_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table productgroups add constraint fk_pg_editors_type foreign key (editors_type) references usertypes(usertype);

alter table productgroups add constraint fk_pg_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table productgroups add constraint fk_pg_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table productgroups add constraint fk_pg_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table productgroups add constraint fk_pg_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table productgroups add constraint fk_pg_users_users foreign key (users_users) references users(id);

alter table productgroups add constraint fk_pg_developers_users foreign key (developers_users) references users(id);

alter table productgroups add constraint fk_pg_creators_users foreign key (creators_users) references users(id);

alter table productgroups add constraint fk_pg_editors_users foreign key (editors_users) references users(id);

alter table productgroups add constraint fk_pg_publishers_users foreign key (publishers_users) references users(id);

alter table productgroups add constraint fk_pg_administrators_users foreign key (administrators_users) references users(id);

alter table productgroups add constraint fk_pg_template foreign key (template) references content(id);

alter table productgroups add constraint fk_pg_stylesheet foreign key (stylesheet) references content(id);



alter table producttypes add constraint fk_pt_users_group foreign key (users_group) references usergroups(usergroup);

alter table producttypes add constraint fk_pt_users_type foreign key (users_type) references usertypes(usertype);

alter table producttypes add constraint fk_pt_developers_group foreign key (developers_group) references usergroups(usergroup);

alter table producttypes add constraint fk_pt_developers_type foreign key (developers_type) references usertypes(usertype);

alter table producttypes add constraint fk_pt_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table producttypes add constraint fk_pt_creators_type foreign key (creators_type) references usertypes(usertype);

alter table producttypes add constraint fk_pt_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table producttypes add constraint fk_pt_editors_type foreign key (editors_type) references usertypes(usertype);

alter table producttypes add constraint fk_pt_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table producttypes add constraint fk_pt_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table producttypes add constraint fk_pt_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table producttypes add constraint fk_pt_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table producttypes add constraint fk_pt_users_users foreign key (users_users) references users(id);

alter table producttypes add constraint fk_pt_developers_users foreign key (developers_users) references users(id);

alter table producttypes add constraint fk_pt_creators_users foreign key (creators_users) references users(id);

alter table producttypes add constraint fk_pt_editors_users foreign key (editors_users) references users(id);

alter table producttypes add constraint fk_pt_publishers_users foreign key (publishers_users) references users(id);

alter table producttypes add constraint fk_pt_administrators_users foreign key (administrators_users) references users(id);

alter table producttypes add constraint fk_pt_template foreign key (template) references content(id);

alter table producttypes add constraint fk_pt_stylesheet foreign key (stylesheet) references content(id);



alter table discounts add constraint fk_d_product_id foreign key (product_id) references content(id);

alter table discounts add constraint fk_d_product_group foreign key (product_group) references productgroups(productgroup);

alter table discounts add constraint fk_d_product_type foreign key (product_type) references producttypes(producttype);

alter table discounts add constraint fk_d_total_currency foreign key (total_currency) references currencies(title);

alter table discounts add constraint fk_d_discount_currency foreign key (discount_currency) references currencies(title);

alter table discounts add constraint fk_d_user_username foreign key (user_username) references users(username);

alter table discounts add constraint fk_d_user_group foreign key (user_group) references usergroups(usergroup);

alter table discounts add constraint fk_d_user_type foreign key (user_type) references usertypes(usertype);



alter table shipping add constraint fk_s_product_id foreign key (product_id) references content(id);

alter table shipping add constraint fk_s_product_group foreign key (product_group) references productgroups(productgroup);

alter table shipping add constraint fk_s_product_type foreign key (product_type) references producttypes(producttype);

alter table shipping add constraint fk_s_total_currency foreign key (total_currency) references currencies(title);

alter table shipping add constraint fk_s_ship_currency foreign key (ship_currency) references currencies(title);

alter table shipping add constraint fk_s_user_username foreign key (user_username) references users(username);

alter table shipping add constraint fk_s_user_group foreign key (user_group) references usergroups(usergroup);

alter table shipping add constraint fk_s_user_type foreign key (user_type) references usertypes(usertype);



alter table tax add constraint fk_t_product_id foreign key (product_id) references content(id);

alter table tax add constraint fk_t_product_group foreign key (product_group) references productgroups(productgroup);

alter table tax add constraint fk_t_product_type foreign key (product_type) references producttypes(producttype);

alter table tax add constraint fk_t_total_currency foreign key (total_currency) references currencies(title);

alter table tax add constraint fk_t_tax_currency foreign key (tax_currency) references currencies(title);



alter table data add constraint fk_d_users_group foreign key (users_group) references usergroups(usergroup);

alter table data add constraint fk_d_users_type foreign key (users_type) references usertypes(usertype);

alter table data add constraint fk_d_creators_group foreign key (creators_group) references usergroups(usergroup);

alter table data add constraint fk_d_creators_type foreign key (creators_type) references usertypes(usertype);

alter table data add constraint fk_d_editors_group foreign key (editors_group) references usergroups(usergroup);

alter table data add constraint fk_d_editors_type foreign key (editors_type) references usertypes(usertype);

alter table data add constraint fk_d_publishers_group foreign key (publishers_group) references usergroups(usergroup);

alter table data add constraint fk_d_publishers_type foreign key (publishers_type) references usertypes(usertype);

alter table data add constraint fk_d_administrators_group foreign key (administrators_group) references usergroups(usergroup);

alter table data add constraint fk_d_administrators_type foreign key (administrators_type) references usertypes(usertype);

alter table data add constraint fk_d_users_users foreign key (users_users) references users(id);

alter table data add constraint fk_d_creators_users foreign key (creators_users) references users(id);

alter table data add constraint fk_d_editors_users foreign key (editors_users) references users(id);

alter table data add constraint fk_d_publishers_users foreign key (publishers_users) references users(id);

alter table data add constraint fk_d_administrators_users foreign key (administrators_users) references users(id);

alter table data add constraint fk_d_searchresults foreign key (searchresults) references content(id);

alter table data add constraint fk_d_searchresult foreign key (searchresult) references content(id);

alter table data add constraint fk_d_viewpage foreign key (viewpage) references content(id);



alter table usagelog add constraint fk_ul_requestid foreign key (requestid) references content(id);

alter table usagelog add constraint fk_ul_requestdatabase foreign key (requestdatabase) references data(title);

