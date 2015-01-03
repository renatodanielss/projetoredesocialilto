create table config (
	configname nvarchar(255) not null unique,
	configvalue nvarchar(max) null,
	primary key clustered (configname)
);

create table content (
	id int not null identity(1,1),
	locked int,
	locked_user int,
	locked_creator int,
	locked_developer int,
	locked_editor int,
	locked_publisher int,
	locked_administrator int,
	locked_schedule int,
	locked_unschedule int,
	created nvarchar(20) null,
	updated nvarchar(20) null,
	published nvarchar(20) null,
	unpublished nvarchar(20) null,
	scheduled_publish nvarchar(20) null,
	scheduled_unpublish nvarchar(20) null,
	requested_publish nvarchar(20) null,
	requested_unpublish nvarchar(20) null,
	status nvarchar(255) null,
	status_by nvarchar(255) null,
	created_by nvarchar(255) null,
	updated_by nvarchar(255) null,
	published_by nvarchar(255) null,
	unpublished_by nvarchar(255) null,
	revision nvarchar(max) null,
	history nvarchar(max) null,
	version nvarchar(255) null,
	version_master int null,
	device nvarchar(255) null,
QQQ	usersegment nvarchar(255) null,
QQQ	usertest nvarchar(255) null,
	heatmap nvarchar(50) null,
	heatmapalign nvarchar(1) null,
	usagelog nvarchar(1) null,
	title nvarchar(255) null,
	searchable nvarchar(3) null,
	menuitem nvarchar(3) null,
	author nvarchar(max) null,
	keywords nvarchar(max) null,
	description nvarchar(max) null,
	metainfo nvarchar(max) null,
	segmentation nvarchar(max) null,
	doctype nvarchar(max) null,
	htmlattributes nvarchar(max) null,
	htmlheader nvarchar(max) null,
	htmlbodyonload nvarchar(max) null,
	url nvarchar(max) null,
	content nvarchar(max) null,
	summary nvarchar(max) null,
	template int null,
	stylesheet int null,
	scripts int null,
	image1 int null,
	image2 int null,
	image3 int null,
	file1 int null,
	file2 int null,
	file3 int null,
	link1 int null,
	link2 int null,
	link3 int null,
	contentformat nvarchar(10) null,
	contentclass nvarchar(255) null,
	contenttype nvarchar(255) null,
	contentgroup nvarchar(255) null,
	contentbundle nvarchar(255) null,
	contentpackage nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	checkedout nvarchar(255) null,
	page_top int null,
	page_up int null,
	page_next int null,
	page_previous int null,
	page_first int null,
	page_last int null,
	related int null,
	upload_filename nvarchar(max) null,
	server_filename nvarchar(255) null,
	product_code nvarchar(255) null,
	product_sku nvarchar(255) null,
	product_currency nvarchar(50) null,
	product_price nvarchar(255) null,
	product_period nvarchar(255) null,
	product_weight nvarchar(255) null,
	product_volume nvarchar(255) null,
	product_width nvarchar(255) null,
	product_height nvarchar(255) null,
	product_depth nvarchar(255) null,
	product_brand nvarchar(255) null,
	product_colour nvarchar(255) null,
	product_size nvarchar(255) null,
	product_count nvarchar(10) null,
	product_tax nvarchar(50) null,
	product_points_cost nvarchar(255) null,
	product_points_reward nvarchar(255) null,
	product_stock nvarchar(255) null,
	product_cost nvarchar(255) null,
	product_location nvarchar(255) null,
	product_stock_amount int,
	product_stock_text nvarchar(255) null,
	product_restocked_amount int,
	product_lowstock_amount int,
	product_lowstock_text nvarchar(255) null,
	product_nostock_order nvarchar(3) null,
	product_nostock_text nvarchar(255) null,
	product_comment nvarchar(max) null,
	product_email int null,
	product_url nvarchar(max) null,
	product_info nvarchar(max) null,
	product_delivery nvarchar(max) null,
	product_user nvarchar(255) null,
	product_page int null,
	product_program nvarchar(max) null,
	product_hosting nvarchar(255) null,
	product_options nvarchar(max) null,
	product_content int null,
	product_contentgroup nvarchar(255) null,
	product_contenttype nvarchar(255) null,
	product_imagegroup nvarchar(255) null,
	product_imagetype nvarchar(255) null,
	product_filegroup nvarchar(255) null,
	product_filetype nvarchar(255) null,
	product_linkgroup nvarchar(255) null,
	product_linktype nvarchar(255) null,
	product_productgroup nvarchar(255) null,
	product_producttype nvarchar(255) null,
	product_usergroup nvarchar(255) null,
	product_usertype nvarchar(255) null,
	custom nvarchar(max) null,
	futurechar1 nvarchar(255) null,
	futurechar2 nvarchar(255) null,
	futurechar3 nvarchar(255) null,
	futuretext1 nvarchar(max) null,
	futuretext2 nvarchar(max) null,
	futuretext3 nvarchar(max) null,
	futureint1 int,
	futureint2 int,
	futureint3 int,
	primary key clustered (id)
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

create index c_filename_idx on content (server_filename);

!alter table content alter column server_filename nvarchar(450) null;


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
	element nvarchar(255) null,
	element_id int,
	element_order int,
	element_params nvarchar(max) null
);

create clustered index ce_index on content_elements (content_id, element);

create index ce_content_id_idx on content_elements (content_id);

create index ce_element_idx on content_elements (element);

create index ce_element_id_idx on content_elements (element_id);

create table content_archive (
	archiveid int not null identity(1,1),
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
	archived nvarchar(20) null,
	archived_by nvarchar(255) null,
	created nvarchar(20) null,
	updated nvarchar(20) null,
	published nvarchar(20) null,
	unpublished nvarchar(20) null,
	scheduled_publish nvarchar(20) null,
	scheduled_unpublish nvarchar(20) null,
	requested_publish nvarchar(20) null,
	requested_unpublish nvarchar(20) null,
	status nvarchar(255) null,
	status_by nvarchar(255) null,
	created_by nvarchar(255) null,
	updated_by nvarchar(255) null,
	published_by nvarchar(255) null,
	unpublished_by nvarchar(255) null,
	revision nvarchar(max) null,
	history nvarchar(max) null,
	version nvarchar(255) null,
	version_master int null,
	device nvarchar(255) null,
QQQ	usersegment nvarchar(255) null,
QQQ	usertest nvarchar(255) null,
	heatmap nvarchar(50) null,
	heatmapalign nvarchar(1) null,
	usagelog nvarchar(1) null,
	title nvarchar(255) null,
	searchable nvarchar(3) null,
	menuitem nvarchar(3) null,
	author nvarchar(max) null,
	keywords nvarchar(max) null,
	description nvarchar(max) null,
	metainfo nvarchar(max) null,
	segmentation nvarchar(max) null,
	doctype nvarchar(max) null,
	htmlattributes nvarchar(max) null,
	htmlheader nvarchar(max) null,
	htmlbodyonload nvarchar(max) null,
	url nvarchar(max) null,
	content nvarchar(max) null,
	summary nvarchar(max) null,
	template int null,
	stylesheet int null,
	scripts int null,
	image1 int null,
	image2 int null,
	image3 int null,
	file1 int null,
	file2 int null,
	file3 int null,
	link1 int null,
	link2 int null,
	link3 int null,
	contentformat nvarchar(10) null,
	contentclass nvarchar(255) null,
	contenttype nvarchar(255) null,
	contentgroup nvarchar(255) null,
	contentbundle nvarchar(255) null,
	contentpackage nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	checkedout nvarchar(255) null,
	page_top int null,
	page_up int null,
	page_next int null,
	page_previous int null,
	page_first int null,
	page_last int null,
	related int null,
	upload_filename nvarchar(max) null,
	server_filename nvarchar(255) null,
	product_code nvarchar(255) null,
	product_sku nvarchar(255) null,
	product_currency nvarchar(50) null,
	product_price nvarchar(255) null,
	product_period nvarchar(255) null,
	product_weight nvarchar(255) null,
	product_volume nvarchar(255) null,
	product_width nvarchar(255) null,
	product_height nvarchar(255) null,
	product_depth nvarchar(255) null,
	product_brand nvarchar(255) null,
	product_colour nvarchar(255) null,
	product_size nvarchar(255) null,
	product_count nvarchar(10) null,
	product_tax nvarchar(50) null,
	product_points_cost nvarchar(255) null,
	product_points_reward nvarchar(255) null,
	product_stock nvarchar(255) null,
	product_cost nvarchar(255) null,
	product_location nvarchar(255) null,
	product_stock_amount int,
	product_stock_text nvarchar(255) null,
	product_restocked_amount int,
	product_lowstock_amount int,
	product_lowstock_text nvarchar(255) null,
	product_nostock_order nvarchar(3) null,
	product_nostock_text nvarchar(255) null,
	product_comment nvarchar(max) null,
	product_email int null,
	product_url nvarchar(max) null,
	product_info nvarchar(max) null,
	product_delivery nvarchar(max) null,
	product_user nvarchar(255) null,
	product_page int null,
	product_program nvarchar(max) null,
	product_hosting nvarchar(255) null,
	product_options nvarchar(max) null,
	product_content int null,
	product_contentgroup nvarchar(255) null,
	product_contenttype nvarchar(255) null,
	product_imagegroup nvarchar(255) null,
	product_imagetype nvarchar(255) null,
	product_filegroup nvarchar(255) null,
	product_filetype nvarchar(255) null,
	product_linkgroup nvarchar(255) null,
	product_linktype nvarchar(255) null,
	product_productgroup nvarchar(255) null,
	product_producttype nvarchar(255) null,
	product_usergroup nvarchar(255) null,
	product_usertype nvarchar(255) null,
	custom nvarchar(max) null,
	futurechar1 nvarchar(255) null,
	futurechar2 nvarchar(255) null,
	futurechar3 nvarchar(255) null,
	futuretext1 nvarchar(max) null,
	futuretext2 nvarchar(max) null,
	futuretext3 nvarchar(max) null,
	futureint1 int,
	futureint2 int,
	futureint3 int,
	primary key clustered (archiveid)
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

create index ca_filename_idx on content_archive (server_filename);

!alter table content_archive alter column server_filename nvarchar(450) null;


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
	element nvarchar(255) null,
	element_id int,
	element_order int,
	element_params nvarchar(max) null
);

create clustered index cae_index on content_archive_elements (content_archiveid, content_id, element);

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
	created nvarchar(20) null,
	updated nvarchar(20) null,
	published nvarchar(20) null,
	unpublished nvarchar(20) null,
	scheduled_publish nvarchar(20) null,
	scheduled_unpublish nvarchar(20) null,
	requested_publish nvarchar(20) null,
	requested_unpublish nvarchar(20) null,
	status nvarchar(255) null,
	status_by nvarchar(255) null,
	created_by nvarchar(255) null,
	updated_by nvarchar(255) null,
	published_by nvarchar(255) null,
	unpublished_by nvarchar(255) null,
	revision nvarchar(max) null,
	history nvarchar(max) null,
	version nvarchar(255) null,
	version_master int null,
	device nvarchar(255) null,
QQQ	usersegment nvarchar(255) null,
QQQ	usertest nvarchar(255) null,
	heatmap nvarchar(50) null,
	heatmapalign nvarchar(1) null,
	usagelog nvarchar(1) null,
	title nvarchar(255) null,
	searchable nvarchar(3) null,
	menuitem nvarchar(3) null,
	author nvarchar(max) null,
	keywords nvarchar(max) null,
	description nvarchar(max) null,
	metainfo nvarchar(max) null,
	segmentation nvarchar(max) null,
	doctype nvarchar(max) null,
	htmlattributes nvarchar(max) null,
	htmlheader nvarchar(max) null,
	htmlbodyonload nvarchar(max) null,
	url nvarchar(max) null,
	content nvarchar(max) null,
	summary nvarchar(max) null,
	template int null,
	stylesheet int null,
	scripts int null,
	image1 int null,
	image2 int null,
	image3 int null,
	file1 int null,
	file2 int null,
	file3 int null,
	link1 int null,
	link2 int null,
	link3 int null,
	contentformat nvarchar(10) null,
	contentclass nvarchar(255) null,
	contenttype nvarchar(255) null,
	contentgroup nvarchar(255) null,
	contentbundle nvarchar(255) null,
	contentpackage nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	checkedout nvarchar(255) null,
	page_top int null,
	page_up int null,
	page_next int null,
	page_previous int null,
	page_first int null,
	page_last int null,
	related int null,
	upload_filename nvarchar(max) null,
	server_filename nvarchar(255) null,
	product_code nvarchar(255) null,
	product_sku nvarchar(255) null,
	product_currency nvarchar(50) null,
	product_price nvarchar(255) null,
	product_period nvarchar(255) null,
	product_weight nvarchar(255) null,
	product_volume nvarchar(255) null,
	product_width nvarchar(255) null,
	product_height nvarchar(255) null,
	product_depth nvarchar(255) null,
	product_brand nvarchar(255) null,
	product_colour nvarchar(255) null,
	product_size nvarchar(255) null,
	product_count nvarchar(10) null,
	product_tax nvarchar(50) null,
	product_points_cost nvarchar(255) null,
	product_points_reward nvarchar(255) null,
	product_stock nvarchar(255) null,
	product_cost nvarchar(255) null,
	product_location nvarchar(255) null,
	product_stock_amount int,
	product_stock_text nvarchar(255) null,
	product_restocked_amount int,
	product_lowstock_amount int,
	product_lowstock_text nvarchar(255) null,
	product_nostock_order nvarchar(3) null,
	product_nostock_text nvarchar(255) null,
	product_comment nvarchar(max) null,
	product_email int null,
	product_url nvarchar(max) null,
	product_info nvarchar(max) null,
	product_delivery nvarchar(max) null,
	product_user nvarchar(255) null,
	product_page int null,
	product_program nvarchar(max) null,
	product_hosting nvarchar(255) null,
	product_options nvarchar(max) null,
	product_content int null,
	product_contentgroup nvarchar(255) null,
	product_contenttype nvarchar(255) null,
	product_imagegroup nvarchar(255) null,
	product_imagetype nvarchar(255) null,
	product_filegroup nvarchar(255) null,
	product_filetype nvarchar(255) null,
	product_linkgroup nvarchar(255) null,
	product_linktype nvarchar(255) null,
	product_productgroup nvarchar(255) null,
	product_producttype nvarchar(255) null,
	product_usergroup nvarchar(255) null,
	product_usertype nvarchar(255) null,
	custom nvarchar(max) null,
	futurechar1 nvarchar(255) null,
	futurechar2 nvarchar(255) null,
	futurechar3 nvarchar(255) null,
	futuretext1 nvarchar(max) null,
	futuretext2 nvarchar(max) null,
	futuretext3 nvarchar(max) null,
	futureint1 int null,
	futureint2 int null,
	futureint3 int null,
	primary key clustered (id)
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

create index cp_filename_idx on content_public (server_filename);

!alter table content_public alter column server_filename nvarchar(450) null;


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
	element nvarchar(255) null,
	element_id int,
	element_order int,
	element_params nvarchar(max) null
);

create clustered index cpe_index on content_public_elements (content_id, element);

create index cpe_content_id_idx on content_public_elements (content_id);

create index cpe_element_idx on content_public_elements (element);

create index cpe_element_id_idx on content_public_elements (element_id);

create table content_dependencies (
	content_id int,
	dependency nvarchar(255) null,
	dependency_id int
);

create clustered index cd_index on content_dependencies (content_id, dependency);

create index cd_content_id_idx on content_dependencies (content_id);

create index cd_dependency_id_idx on content_dependencies (dependency_id);

create table content_public_dependencies (
	content_id int,
	dependency nvarchar(255) null,
	dependency_id int
);

create clustered index cpd_index on content_public_dependencies (content_id, dependency);

create index cpd_content_id_idx on content_public_dependencies (content_id);

create index cpd_dependency_id_idx on content_public_dependencies (dependency_id);

create table contentgroups (
	id int not null identity(1,1),
	contentgroup nvarchar(255) null,
	parentgroup nvarchar(255) null,
	contentclass nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	template int null,
	stylesheet int null,
	title_prefix nvarchar(max) null,
	title_suffix nvarchar(max) null,
	doctype nvarchar(max) null,
	primary key clustered (id)
);

create unique index cg_contentgroup_idx on contentgroups(contentgroup);

create table contenttypes (
	id int not null identity(1,1),
	contenttype nvarchar(255) null,
	parenttype nvarchar(255) null,
	contentclass nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	template int null,
	stylesheet int null,
	title_prefix nvarchar(max) null,
	title_suffix nvarchar(max) null,
	doctype nvarchar(max) null,
	primary key clustered (id)
);

create unique index ct_contenttype_idx on contenttypes(contenttype);

create table elements (
	id int not null identity(1,1),
	element nvarchar(255) null,
	parentelement nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	primary key clustered (id)
);

create unique index e_element_idx on elements(element);

create table fileformats (
	id int not null identity(1,1),
	filenameextension nvarchar(255) null,
	primary key clustered (id)
);

create table filegroups (
	id int not null identity(1,1),
	filegroup nvarchar(255) null,
	parentgroup nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	template int null,
	stylesheet int null,
	primary key clustered (id)
);

create unique index fg_filegroup_idx on filegroups(filegroup);

create table filetypes (
	id int not null identity(1,1),
	filetype nvarchar(255) null,
	parenttype nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	template int null,
	stylesheet int null,
	primary key clustered (id)
);

create unique index ft_filetype_idx on filetypes(filetype);

create table fonts (
	id int not null identity(1,1),
	face nvarchar(255) null,
	primary key clustered (id)
);

create table hosting (
	id int not null identity(1,1),
	user_id int,
	client_address nvarchar(255) not null unique,
	client_urlrootpath nvarchar(255) null,
	client_database nvarchar(255) null,
	personal_license nvarchar(255) null,
	professional_license nvarchar(255) null,
	enterprise_license nvarchar(255) null,
	hosting_license nvarchar(255) null,
	ecommerce_license nvarchar(255) null,
	community_license nvarchar(255) null,
	databases_license nvarchar(255) null,
	statistics_license nvarchar(255) null,
	experience_license nvarchar(255) null,
	superadmin nvarchar(255) null,
	superadmin_password nvarchar(255) null,
	superadmin_email nvarchar(255) null,
	hostingtype nvarchar(255) null,
	hostinggroup nvarchar(255) null,
	scheduled_last nvarchar(20) null,
	scheduled_publish nvarchar(20) null,
	scheduled_notify nvarchar(20) null,
	scheduled_unpublish nvarchar(20) null,
	scheduled_publish_email int null,
	scheduled_notify_email int null,
	scheduled_unpublish_email int null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	externalstorage nvarchar(max) null,
	history nvarchar(max) null,
	custom nvarchar(max) null,
	primary key clustered (id)
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
	id int not null identity(1,1),
	hostinggroup nvarchar(255) null,
	parentgroup nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	primary key clustered (id)
);

create unique index hg_hostinggroup_idx on hostinggroups(hostinggroup);

create table hostingtypes (
	id int not null identity(1,1),
	hostingtype nvarchar(255) null,
	parenttype nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	primary key clustered (id)
);

create unique index ht_hostingtype_idx on hostingtypes(hostingtype);

create table imageformats (
	id int not null identity(1,1),
	filenameextension nvarchar(255) null,
	primary key (id)
);

create table imagegroups (
	id int not null identity(1,1),
	imagegroup nvarchar(255) null,
	parentgroup nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	template int null,
	stylesheet int null,
	primary key clustered (id)
);

create unique index ig_imagegroup_idx on imagegroups(imagegroup);

create table imagetypes (
	id int not null identity(1,1),
	imagetype nvarchar(255) null,
	parenttype nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	template int null,
	stylesheet int null,
	primary key clustered (id)
);

create unique index it_imagetype_idx on imagetypes(imagetype);

create table linkgroups (
	id int not null identity(1,1),
	linkgroup nvarchar(255) null,
	parentgroup nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	template int null,
	stylesheet int null,
	primary key clustered (id)
);

create unique index lg_linkgroup_idx on linkgroups(linkgroup);

create table linktypes (
	id int not null identity(1,1),
	linktype nvarchar(255) null,
	parenttype nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	template int null,
	stylesheet int null,
	primary key clustered (id)
);

create unique index lt_linktype_idx on linktypes(linktype);

create table usergroups (
	id int not null identity(1,1),
	usergroup nvarchar(255) null,
	parentgroup nvarchar(255) null,
	login_page int null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	subscribers_type nvarchar(255) null,
	subscribers_group nvarchar(255) null,
	users_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	subscribers_users int null,
	primary key clustered (id)
);

create unique index ug_usergroup_idx on usergroups(usergroup);

create table usertypes (
	id int not null identity(1,1),
	usertype nvarchar(255) null,
	parenttype nvarchar(255) null,
	login_page int null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	subscribers_type nvarchar(255) null,
	subscribers_group nvarchar(255) null,
	users_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	subscribers_users int null,
	primary key clustered (id)
);

create unique index ut_usertype_idx on usertypes(usertype);

create table usergroups2 (
	usergroup nvarchar(255) null,
	subgroup nvarchar(255)
);

create clustered index ug2_index on usergroups2 (usergroup, subgroup);

create index ug2_usergroup_idx on usergroups2(usergroup);

create table usertypes2 (
	usertype nvarchar(255) null,
	subtype nvarchar(255)
);

create clustered index ut2_index on usertypes2 (usertype, subtype);

create index ut2_usertype_idx on usertypes2(usertype);

create table users_usergroups (
	id int not null identity(1,1),
	locked int,
	created nvarchar(20) null,
	updated nvarchar(20) null,
	created_by nvarchar(255) null,
	updated_by nvarchar(255) null,
	scheduled_publish nvarchar(20) null,
	scheduled_unpublish nvarchar(20) null,
	username nvarchar(255) null,
	usergroup nvarchar(255) null,
	primary key clustered (id)
);

create index u_ug_username_idx on users_usergroups(username);

create index u_ug_usergroup_idx on users_usergroups(usergroup);

create table users_usertypes (
	id int not null identity(1,1),
	locked int,
	created nvarchar(20) null,
	updated nvarchar(20) null,
	created_by nvarchar(255) null,
	updated_by nvarchar(255) null,
	scheduled_publish nvarchar(20) null,
	scheduled_unpublish nvarchar(20) null,
	username nvarchar(255) null,
	usertype nvarchar(255) null,
	primary key clustered (id)
);

create index u_ut_username_idx on users_usertypes(username);

create index u_ut_usertype_idx on users_usertypes(usertype);

create table versions (
	id int not null identity(1,1),
	version nvarchar(255) null,
	currencies nvarchar(50) null,
	default_country nvarchar(255) null,
	default_state nvarchar(255) null,
	default_price nvarchar(max) null,
	parentversion nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	primary key clustered (id)
);

create unique index v_version_idx on versions(version);

create table segments (
	id int not null identity(1,1),
	segmentid nvarchar(255) null,
	segment nvarchar(255) null,
	weight nvarchar(10) null,
	active nvarchar(1) null,
	scheduled nvarchar(20) null,
	unscheduled nvarchar(20) null,
	datetimefull nvarchar(max) null,
	datetimeyear nvarchar(max) null,
	datetimemonth nvarchar(max) null,
	datetimeday nvarchar(max) null,
	datetimeweek nvarchar(max) null,
	datetimeweekday nvarchar(max) null,
	datetimehour nvarchar(max) null,
	datetimemin nvarchar(max) null,
	datetimesec nvarchar(max) null,
	clientaddr nvarchar(max) null,
	clienthost nvarchar(max) null,
	clientuser nvarchar(max) null,
	clientuseragent nvarchar(max) null,
	clientos nvarchar(max) null,
	clientosversion nvarchar(max) null,
	clientbrowser nvarchar(max) null,
	clientversion nvarchar(max) null,
	clientdevice nvarchar(max) null,
	clientdeviceid nvarchar(max) null,
	clientdeviceversion nvarchar(max) null,
	preferredlanguage nvarchar(max) null,
	acceptedlanguages nvarchar(max) null,
	requesthost nvarchar(max) null,
	requestport nvarchar(max) null,
	requestmethod nvarchar(max) null,
	requestpath nvarchar(max) null,
	requestquery nvarchar(max) null,
	requestprotocol nvarchar(max) null,
	requestid nvarchar(max) null,
	requestclass nvarchar(max) null,
	requestdatabase nvarchar(max) null,
	refererid nvarchar(max) null,
	refererclass nvarchar(max) null,
	refererdatabase nvarchar(max) null,
	refererhost nvarchar(max) null,
	refererpath nvarchar(max) null,
	refererquery nvarchar(max) null,
	referersearchengine nvarchar(max) null,
	referersearchquery nvarchar(max) null,
	geoipcountry nvarchar(max) null,
	geoipregion nvarchar(max) null,
	geoipcity nvarchar(max) null,
	geoippostalcode nvarchar(max) null,
	geoiplatitude nvarchar(max) null,
	geoiplongitude nvarchar(max) null,
	geoiptimezone nvarchar(max) null,
	created nvarchar(max) null,
	updated nvarchar(max) null,
	created_by nvarchar(max) null,
	updated_by nvarchar(max) null,
	keywords nvarchar(max) null,
	description nvarchar(max) null,
	birthdate nvarchar(max) null,
	birthyear nvarchar(max) null,
	birthmonth nvarchar(max) null,
	birthday nvarchar(max) null,
	age nvarchar(max) null,
	gender nvarchar(max) null,
	title nvarchar(max) null,
	name nvarchar(max) null,
	organisation nvarchar(max) null,
	username nvarchar(max) null,
	password nvarchar(max) null,
	email nvarchar(max) null,
	userclass nvarchar(max) null,
	usertype nvarchar(max) null,
	usergroup nvarchar(max) null,
	scheduled_last nvarchar(max) null,
	scheduled_publish nvarchar(max) null,
	scheduled_notify nvarchar(max) null,
	scheduled_unpublish nvarchar(max) null,
	scheduled_publish_email nvarchar(max) null,
	scheduled_notify_email nvarchar(max) null,
	scheduled_unpublish_email nvarchar(max) null,
	card_type nvarchar(max) null,
	card_number nvarchar(max) null,
	card_issuedmonth nvarchar(max) null,
	card_issuedyear nvarchar(max) null,
	card_expirymonth nvarchar(max) null,
	card_expiryyear nvarchar(max) null,
	card_name nvarchar(max) null,
	card_cvc nvarchar(max) null,
	card_issue nvarchar(max) null,
	card_postalcode nvarchar(max) null,
	delivery_name nvarchar(max) null,
	delivery_organisation nvarchar(max) null,
	delivery_address nvarchar(max) null,
	delivery_postalcode nvarchar(max) null,
	delivery_city nvarchar(max) null,
	delivery_state nvarchar(max) null,
	delivery_country nvarchar(max) null,
	delivery_phone nvarchar(max) null,
	delivery_fax nvarchar(max) null,
	delivery_email nvarchar(max) null,
	delivery_website nvarchar(max) null,
	invoice_name nvarchar(max) null,
	invoice_organisation nvarchar(max) null,
	invoice_address nvarchar(max) null,
	invoice_postalcode nvarchar(max) null,
	invoice_city nvarchar(max) null,
	invoice_state nvarchar(max) null,
	invoice_country nvarchar(max) null,
	invoice_phone nvarchar(max) null,
	invoice_fax nvarchar(max) null,
	invoice_email nvarchar(max) null,
	invoice_website nvarchar(max) null,
	notes nvarchar(max) null,
	userinfo nvarchar(max) null,
	shopcart nvarchar(max) null,
	wishlist nvarchar(max) null,
	product_points nvarchar(max) null,
	visits nvarchar(max) null,
QQQ	goals nvarchar(max) null,
	custom nvarchar(max) null,
	primary key (id)
);

create unique index s_segmentid_idx on segments(segmentid);

create index segments_active on segments (active);

create index segments_scheduled on segments (scheduled);

create index segments_unscheduled on segments (unscheduled);

create table usertests (
	id int not null identity(1,1),
	usertest nvarchar(125) null,
	description nvarchar(max) null,
	type nvarchar(10) null,
	variants nvarchar(max) null,
	probability nvarchar(10) null,
	visitors nvarchar(3) null,
	visits nvarchar(10) null,
QQQ	goals nvarchar(max) null,
	confidence nvarchar(10) null,
	active nvarchar(1) null,
	scheduled nvarchar(20) null,
	unscheduled nvarchar(20) null,
	primary key (id)
);

create unique index ut_usertest_idx on usertests(usertest);

create index usertests_usertest on usertests (usertest);

create table websites (
	id int not null identity(1,1),
	title nvarchar(255) null,
	domain nvarchar(255) null,
	default_page int null,
	default_page_nonexisting int null,
	default_page_unpublished int null,
	default_page_expired int null,
	default_login int null,
	default_searchresults_page int null,
	default_searchresults_entry int null,
	default_list_entry int null,
	default_publish_ready int null,
	default_register_confirm_page int null,
	default_register_notify_page int null,
	default_personal_admin_page int null,
	retrieve_password_page int null,
	retrieve_password_confirmation int null,
	retrieve_password_email int null,
	retrieve_password_error int null,
	default_template int null,
	default_stylesheet int null,
	default_version nvarchar(255) null,
	default_country nvarchar(255) null,
	default_state nvarchar(255) null,
	default_currency nvarchar(50) null,
	default_price nvarchar(max) null,
	language nvarchar(255) null,
	remote nvarchar(255) null,
	useragent nvarchar(255) null,
	referrer nvarchar(255) null,
	keywords nvarchar(255) null,
	default_doctype nvarchar(max) null,
	primary key clustered (id)
);

create index w_domain_idx on websites (domain) null;

create index w_language_idx on websites (language) null;

create index w_remote_idx on websites (remote) null;

create index w_referrer_idx on websites (referrer) null;

create index w_keywords_idx on websites (keywords) null;

create table workflow (
	id int not null auto_increment,
	title nvarchar(255) null,
	action nvarchar(255) null,
	fromstate nvarchar(255) null,
	tostate nvarchar(255) null,
	usertype nvarchar(max) null,
	usergroup nvarchar(max) null,
	contentclass nvarchar(max) null,
	contenttype nvarchar(max) null,
	contentgroup nvarchar(max) null,
	version nvarchar(max) null,
	notify_email nvarchar(10) null,
	contentchanges nvarchar(max) null,
	workflow_program nvarchar(max) null,
	userrestriction nvarchar(20) null,
	primary key clustered (id)
);

create index w_title_idx on workflow (title);

create index w_action_idx on workflow (action);

create index w_fromstate_idx on workflow (fromstate);

create index w_tostate_idx on workflow (tostate);

create table users (
	id int not null identity(1,1),
	locked int,
	failed int,
	created nvarchar(20) null,
	updated nvarchar(20) null,
	created_by nvarchar(255) null,
	updated_by nvarchar(255) null,
	history nvarchar(max) null,
	keywords nvarchar(max) null,
	description nvarchar(max) null,
	birthdate nvarchar(10) null,
	birthyear nvarchar(4) null,
	birthmonth nvarchar(2) null,
	birthday nvarchar(2) null,
	gender nvarchar(1) null,
	title nvarchar(255) null,
	name nvarchar(255) null,
	organisation nvarchar(max) null,
	username nvarchar(255) null,
	password nvarchar(255) null,
	email nvarchar(255) null,
	userclass nvarchar(255) null,
	usertype nvarchar(255) null,
	usergroup nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	content_editor nvarchar(20) null,
	hardcore_language nvarchar(20) null,
	hardcore_upload nvarchar(10) null,
	hardcore_format nvarchar(10) null,
	hardcore_width nvarchar(10) null,
	hardcore_height nvarchar(10) null,
	hardcore_onenter nvarchar(max) null,
	hardcore_onctrlenter nvarchar(max) null,
	hardcore_onshiftenter nvarchar(max) null,
	hardcore_onaltenter nvarchar(max) null,
	hardcore_toolbar1 nvarchar(max) null,
	hardcore_toolbar2 nvarchar(max) null,
	hardcore_toolbar3 nvarchar(max) null,
	hardcore_toolbar4 nvarchar(max) null,
	hardcore_toolbar5 nvarchar(max) null,
	hardcore_encoding nvarchar(20) null,
	hardcore_formatblock nvarchar(max) null,
	hardcore_fontname nvarchar(max) null,
	hardcore_fontsize nvarchar(max) null,
	hardcore_customscript nvarchar(max) null,
	scheduled_last nvarchar(20) null,
	scheduled_publish nvarchar(20) null,
	scheduled_notify nvarchar(20) null,
	scheduled_unpublish nvarchar(20) null,
	scheduled_publish_email int null,
	scheduled_notify_email int null,
	scheduled_unpublish_email int null,
	card_type nvarchar(255) null,
	card_number nvarchar(255) null,
	card_issuedmonth nvarchar(255) null,
	card_issuedyear nvarchar(255) null,
	card_expirymonth nvarchar(255) null,
	card_expiryyear nvarchar(255) null,
	card_name nvarchar(255) null,
	card_cvc nvarchar(255) null,
	card_issue nvarchar(255) null,
	card_postalcode nvarchar(255) null,
	delivery_name nvarchar(255) null,
	delivery_organisation nvarchar(255) null,
	delivery_address nvarchar(max) null,
	delivery_postalcode nvarchar(255) null,
	delivery_city nvarchar(255) null,
	delivery_state nvarchar(255) null,
	delivery_country nvarchar(255) null,
	delivery_phone nvarchar(255) null,
	delivery_fax nvarchar(255) null,
	delivery_email nvarchar(255) null,
	delivery_website nvarchar(255) null,
	invoice_name nvarchar(255) null,
	invoice_organisation nvarchar(255) null,
	invoice_address nvarchar(max) null,
	invoice_postalcode nvarchar(255) null,
	invoice_city nvarchar(255) null,
	invoice_state nvarchar(255) null,
	invoice_country nvarchar(255) null,
	invoice_phone nvarchar(255) null,
	invoice_fax nvarchar(255) null,
	invoice_email nvarchar(255) null,
	invoice_website nvarchar(255) null,
	notes nvarchar(max) null,
	userinfo nvarchar(max) null,
	index_content nvarchar(max) null,
	index_library nvarchar(max) null,
	index_product nvarchar(max) null,
	index_order nvarchar(max) null,
	index_segments nvarchar(max) null,
	index_usertests nvarchar(max) null,
	index_heatmaps nvarchar(max) null,
	index_search nvarchar(max) null,
	index_searchadvanced nvarchar(max) null,
	index_searchreplace nvarchar(max) null,
	index_databases nvarchar(max) null,
	index_user nvarchar(max) null,
	index_hosting nvarchar(max) null,
	index_websites nvarchar(max) null,
	index_stock nvarchar(max) null,
	index_sales nvarchar(max) null,
	index_workspace nvarchar(max) null,
	menu_selection nvarchar(max) null,
	workspace_sections nvarchar(max) null,
	statistics_reports nvarchar(max) null,
	sales_reports nvarchar(max) null,
	shopcart nvarchar(max) null,
	wishlist nvarchar(max) null,
	product_points int null,
	custom nvarchar(max) null,
	primary key clustered (id)
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
	usergroup nvarchar(255) null,
	scheduled_publish nvarchar(20) null,
	scheduled_unpublish nvarchar(20) null
);

create clustered index u2g_index on user2groups (user_id, usergroup);

create index u2g_userid_idx on user2groups(user_id);

create table user2types (
	user_id int,
	usertype nvarchar(255) null,
	scheduled_publish nvarchar(20) null,
	scheduled_unpublish nvarchar(20) null
);

create clustered index u2t_index on user2types (user_id, usertype);

create index u2t_userid_idx on user2types(user_id);

create table permissions (
	id int not null identity(1,1),
	action nvarchar(50) null,
	resource nvarchar(250) null,
	username nvarchar(255) null,
	userclass nvarchar(255) null,
	usergroup nvarchar(255) null,
	usertype nvarchar(255) null,
	primary key clustered (id)
);

create index perm_action_idx on permissions(action);

create index perm_resource_idx on permissions(resource);

create index perm_username_idx on permissions(username);

create index perm_userclass_idx on permissions(userclass);

create index perm_usergroup_idx on permissions(usergroup);

create index perm_usertype_idx on permissions(usertype);

create table guestbook (
	id int not null identity(1,1),
	created nvarchar(20) null,
	updated nvarchar(20) null,
	published nvarchar(20) null,
	status nvarchar(255) null,
	created_by nvarchar(255) null,
	updated_by nvarchar(255) null,
	published_by nvarchar(255) null,
	title nvarchar(255) null,
	content nvarchar(max) null,
	name nvarchar(255) null,
	email nvarchar(255) null,
	website nvarchar(255) null,
	company nvarchar(255) null,
	primary key clustered (id)
);

create table currencies (
	id int not null identity(1,1),
	title nvarchar(50) null,
	symbol nvarchar(50) null,
	rate nvarchar(max) null,
	primary key clustered (id)
);

create unique index cur_title_idx on currencies(title);

create index cur_symbol_idx on currencies(symbol);

create table orderitems (
	id int not null identity(1,1),
	order_id int,
	product_id int,
	version nvarchar(255) null,
	device nvarchar(255) null,
	usersegment nvarchar(255) null,
	usertest nvarchar(255) null,
	title nvarchar(255) null,
	summary nvarchar(max) null,
	image1 int null,
	image2 int null,
	image3 int null,
	file1 int null,
	file2 int null,
	file3 int null,
	link1 int null,
	link2 int null,
	link3 int null,
	author nvarchar(max) null,
	keywords nvarchar(max) null,
	description nvarchar(max) null,
	product_code nvarchar(255) null,
	product_sku nvarchar(255) null,
	product_currency nvarchar(50) null,
	product_price nvarchar(255) null,
	product_period nvarchar(255) null,
	product_weight nvarchar(255) null,
	product_volume nvarchar(255) null,
	product_width nvarchar(255) null,
	product_height nvarchar(255) null,
	product_depth nvarchar(255) null,
	product_brand nvarchar(255) null,
	product_colour nvarchar(255) null,
	product_size nvarchar(255) null,
	product_count nvarchar(10) null,
	product_tax nvarchar(50) null,
	product_points_cost nvarchar(255) null,
	product_points_reward nvarchar(255) null,
	product_stock nvarchar(255) null,
	product_comment nvarchar(max) null,
	product_email int null,
	product_url nvarchar(max) null,
	product_delivery nvarchar(max) null,
	product_user nvarchar(255) null,
	product_page int null,
	product_program nvarchar(max) null,
	product_hosting nvarchar(255) null,
	product_options nvarchar(max) null,
	product_content int null,
	product_contentgroup nvarchar(255) null,
	product_contenttype nvarchar(255) null,
	product_imagegroup nvarchar(255) null,
	product_imagetype nvarchar(255) null,
	product_filegroup nvarchar(255) null,
	product_filetype nvarchar(255) null,
	product_linkgroup nvarchar(255) null,
	product_linktype nvarchar(255) null,
	product_productgroup nvarchar(255) null,
	product_producttype nvarchar(255) null,
	product_usergroup nvarchar(255) null,
	product_usertype nvarchar(255) null,
	item_quantity int null,
	item_subtotal decimal(20,2) null,
	item_subtotal_base decimal(20,2) null,
	item_total decimal(20,2) null,
	item_total_base decimal(20,2) null,
	discount_description nvarchar(max) null,
	discount_total decimal(20,2) null,
	discount_total_base decimal(20,2) null,
	shipping_description nvarchar(max) null,
	shipping_total decimal(20,2) null,
	shipping_total_base decimal(20,2) null,
	tax_description nvarchar(max) null,
	tax_total decimal(20,2) null,
	tax_total_base decimal(20,2) null,
	primary key clustered (id)
);

create index oi_order_id_idx on orderitems(order_id);

create index oi_product_id_idx on orderitems(product_id);

create table orders (
	id int not null identity(1,1),
	user_id int,
	locked_user int,
	locked_creator int,
	locked_editor int,
	locked_publisher int,
	locked_administrator int,
	created nvarchar(20) null,
	updated nvarchar(20) null,
	published nvarchar(20) null,
	paid nvarchar(20) null,
	payment nvarchar(max) null,
	created_by nvarchar(255) null,
	updated_by nvarchar(255) null,
	published_by nvarchar(255) null,
	status nvarchar(255) null,
	status_by nvarchar(255) null,
	revision nvarchar(max) null,
	history nvarchar(max) null,
	checkedout nvarchar(255) null,
	order_quantity int null,
	order_currency nvarchar(50) null,
	order_currency_base nvarchar(50) null,
	order_subtotal decimal(20,2) null,
	order_subtotal_base decimal(20,2) null,
	tax_description nvarchar(max) null,
	tax_total decimal(20,2) null,
	tax_total_base decimal(20,2) null,
	shipping_description nvarchar(max) null,
	shipping_total decimal(20,2) null,
	shipping_total_base decimal(20,2) null,
	discount_description nvarchar(max) null,
	discount_total decimal(20,2) null,
	discount_total_base decimal(20,2) null,
	order_total decimal(20,2) null,
	order_total_base decimal(20,2) null,
	card_type nvarchar(255) null,
	card_number nvarchar(255) null,
	card_issuedmonth nvarchar(255) null,
	card_issuedyear nvarchar(255) null,
	card_expirymonth nvarchar(255) null,
	card_expiryyear nvarchar(255) null,
	card_name nvarchar(255) null,
	card_cvc nvarchar(255) null,
	card_issue nvarchar(255) null,
	card_postalcode nvarchar(255) null,
	delivery_name nvarchar(255) null,
	delivery_organisation nvarchar(255) null,
	delivery_address nvarchar(max) null,
	delivery_postalcode nvarchar(255) null,
	delivery_city nvarchar(255) null,
	delivery_state nvarchar(255) null,
	delivery_country nvarchar(255) null,
	delivery_phone nvarchar(255) null,
	delivery_fax nvarchar(255) null,
	delivery_email nvarchar(255) null,
	delivery_website nvarchar(255) null,
	invoice_name nvarchar(255) null,
	invoice_organisation nvarchar(255) null,
	invoice_address nvarchar(max) null,
	invoice_postalcode nvarchar(255) null,
	invoice_city nvarchar(255) null,
	invoice_state nvarchar(255) null,
	invoice_country nvarchar(255) null,
	invoice_phone nvarchar(255) null,
	invoice_fax nvarchar(255) null,
	invoice_email nvarchar(255) null,
	invoice_website nvarchar(255) null,
	createdyear int,
	createdmonth int,
	createdday int,
	createdweek int,
	createdweekday int,
	createdhour int,
	createdmin int,
	createdsec int,
	clienthost nvarchar(250) null,
	clienthosttld nvarchar(10) null,
	clientuseragent nvarchar(250) null,
	clientos nvarchar(10) null,
	clientosversion nvarchar(20) null,
	clientbrowser nvarchar(50) null,
	clientversion nvarchar(20) null,
	clientdevice nvarchar(20) null,
	clientdeviceid nvarchar(20) null,
	clientdeviceversion nvarchar(50) null,
	clientusername nvarchar(50) null,
	visitorid nvarchar(50) null,
	visitorduration int,
	sessionid nvarchar(20) null,
	sessionduration int,
	requesthost nvarchar(50) null,
	requestpath nvarchar(250) null,
	requestquery nvarchar(250) null,
	requestid int,
	requestclass nvarchar(10) null,
	requestdatabase nvarchar(50) null,
	refererid int,
	refererclass nvarchar(10) null,
	refererdatabase nvarchar(50) null,
	refererduration int,
	refererhost nvarchar(50) null,
	refererpath nvarchar(250) null,
	refererquery nvarchar(250) null,
	referersearchengine nvarchar(50) null,
	referersearchquery nvarchar(250) null,
	session_entry nvarchar(250) null,
	affiliate nvarchar(250) null,
	usersegments nvarchar(max) null,
	usertests nvarchar(max) null,
	custom nvarchar(max) null,
	primary key clustered (id)
);

create index o_paid_idx on orders(paid);

create table productgroups (
	id int not null identity(1,1),
	productgroup nvarchar(255) null,
	parentgroup nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	template int null,
	stylesheet int null,
	title_prefix nvarchar(max) null,
	title_suffix nvarchar(max) null,
	doctype nvarchar(max) null,
	primary key clustered (id)
);

create unique index pg_productgroup_idx on productgroups(productgroup);

create table producttypes (
	id int not null identity(1,1),
	producttype nvarchar(255) null,
	parenttype nvarchar(255) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	developers_type nvarchar(255) null,
	developers_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	developers_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	template int null,
	stylesheet int null,
	title_prefix nvarchar(max) null,
	title_suffix nvarchar(max) null,
	doctype nvarchar(max) null,
	primary key clustered (id)
);

create unique index pt_producttype_idx on producttypes(producttype);

create table discounts (
	id int not null identity(1,1),
	title nvarchar(255) null,
	country nvarchar(50) null,
	state nvarchar(50) null,
	product_id int null,
	product_group nvarchar(50) null,
	product_type nvarchar(50) null,
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
	total_currency nvarchar(50) null,
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
	discount_description nvarchar(50) null,
	discount_type nvarchar(50) null,
	discount_quantity int,
	discount_quantity2 int,
	discount_products nvarchar(50) null,
	discount_currency nvarchar(50) null,
	discount_amount decimal(20,2),
	discount_orderitems nvarchar(50) null,
	user_username nvarchar(255) null,
	user_type nvarchar(255) null,
	user_group nvarchar(255) null,
	user_code nvarchar(255) null,
	user_points_min int,
	user_points_cost int,
	period_start nvarchar(20) null,
	period_end nvarchar(20) null,
	primary key clustered (id)
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
	id int not null identity(1,1),
	title nvarchar(255) null,
	country nvarchar(50) null,
	state nvarchar(50) null,
	product_id int null,
	product_group nvarchar(255) null,
	product_type nvarchar(255) null,
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
	total_currency nvarchar(50) null,
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
	ship_description nvarchar(50) null,
	ship_currency nvarchar(50) null,
	ship_order decimal(20,2),
	ship_item decimal(20,2),
	ship_percent decimal(20,2),
	ship_total decimal(20,2),
	user_username nvarchar(255) null,
	user_type nvarchar(255) null,
	user_group nvarchar(255) null,
	user_code nvarchar(255) null,
	user_points_min int,
	user_points_cost int,
	period_start nvarchar(20) null,
	period_end nvarchar(20) null,
	primary key clustered (id)
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
	id int not null identity(1,1),
	title nvarchar(255) null,
	country nvarchar(50) null,
	state nvarchar(50) null,
	product_id int null,
	product_group nvarchar(255) null,
	product_type nvarchar(255) null,
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
	total_currency nvarchar(50) null,
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
	tax_description nvarchar(50) null,
	tax_currency nvarchar(50) null,
	tax_order decimal(20,2),
	tax_item decimal(20,2),
	tax_percent decimal(20,2),
	tax_total decimal(20,2),
	primary key clustered (id)
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
	id int not null identity(1,1),
	title nvarchar(50) null,
	content nvarchar(max) null,
	users_type nvarchar(255) null,
	users_group nvarchar(255) null,
	creators_type nvarchar(255) null,
	creators_group nvarchar(255) null,
	editors_type nvarchar(255) null,
	editors_group nvarchar(255) null,
	publishers_type nvarchar(255) null,
	publishers_group nvarchar(255) null,
	administrators_type nvarchar(255) null,
	administrators_group nvarchar(255) null,
	users_users int null,
	creators_users int null,
	editors_users int null,
	publishers_users int null,
	administrators_users int null,
	searchresults int null,
	searchresult int null,
	viewpage int null,
	adminindex nvarchar(max) null,
	custom nvarchar(max) null,
	primary key clustered (id)
);

create unique index d_title_idx on data(title);

create table usagelog (
	datetimefull nvarchar(20) null,
	datetimeyear int,
	datetimemonth int,
	datetimeday int,
	datetimeweek int,
	datetimeweekday int,
	datetimehour int,
	datetimemin int,
	datetimesec int,
	clienthost nvarchar(250) null,
	clienthosttld nvarchar(10) null,
	clientuseragent nvarchar(250) null,
	clientos nvarchar(10) null,
	clientosversion nvarchar(20) null,
	clientbrowser nvarchar(50) null,
	clientversion nvarchar(20) null,
	clientdevice nvarchar(20) null,
	clientdeviceid nvarchar(20) null,
	clientdeviceversion nvarchar(50) null,
	clientusername nvarchar(50) null,
	visitorid nvarchar(50) null,
	visitorduration int,
	sessionid nvarchar(20) null,
	sessionduration int,
	requesthost nvarchar(50) null,
	requestpath nvarchar(250) null,
	requestquery nvarchar(250) null,
	requestid int,
	requestclass nvarchar(10) null,
	requestdatabase nvarchar(50) null,
	refererid int,
	refererclass nvarchar(10) null,
	refererdatabase nvarchar(50) null,
	refererduration int,
	refererhost nvarchar(50) null,
	refererpath nvarchar(250) null,
	refererquery nvarchar(250) null,
	referersearchengine nvarchar(50) null,
	referersearchquery nvarchar(250) null,
	affiliate nvarchar(250) null,
	usersegments nvarchar(max) null,
	usertests nvarchar(max) null,
	hits int,
	pageviews int,
	visits int,
	visitors int,
	clienthosts int,
	clienthostids nvarchar(max) null,
	visitorids nvarchar(max) null,
	sessionids nvarchar(max) null,
	summarised nvarchar(250) null
);

create clustered index u_index on usagelog (datetimefull, sessionid);

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
QQQ	id nvarchar(50) not null,
	logged nvarchar(20) null,
	action nvarchar(5) null,
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
	meta_name nvarchar(255) not null,
	meta_content nvarchar(255) not null,
	primary key clustered (content_id,meta_name)
);

create index cm_content_id_idx on content_meta (content_id);

create index cm_meta_name_idx on content_meta (meta_name);

create index cm_meta_content_idx on content_meta (meta_content);

create index cm_name_idx on content_meta (content_id, meta_name, meta_content);

create index cm_metaname_idx on content_meta (meta_name, meta_content);

create index cm_metacontent_idx on content_meta (meta_content, meta_name);

create table content_public_meta (
QQQ	content_id int not null,
	meta_name nvarchar(255) not null,
	meta_content nvarchar(255) not null,
	primary key clustered (content_id,meta_name)
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

