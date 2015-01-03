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
	version_master nvarchar(10) null,
	device nvarchar(255) null,
	usersegment nvarchar(255) null,
	usertest nvarchar(255) null,
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
	template nvarchar(10) null,
	stylesheet nvarchar(255) null,
	scripts nvarchar(255) null,
	image1 nvarchar(10) null,
	image2 nvarchar(10) null,
	image3 nvarchar(10) null,
	file1 nvarchar(10) null,
	file2 nvarchar(10) null,
	file3 nvarchar(10) null,
	link1 nvarchar(10) null,
	link2 nvarchar(10) null,
	link3 nvarchar(10) null,
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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	checkedout nvarchar(255) null,
	page_top nvarchar(10) null,
	page_up nvarchar(10) null,
	page_next nvarchar(10) null,
	page_previous nvarchar(10) null,
	page_first nvarchar(10) null,
	page_last nvarchar(10) null,
	related nvarchar(max) null,
	upload_filename nvarchar(max) null,
	server_filename nvarchar(255) null,
	product_code nvarchar(255) null,
	product_sku nvarchar(255) null,
	product_currency nvarchar(255) null,
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
	product_email nvarchar(10) null,
	product_url nvarchar(max) null,
	product_info nvarchar(max) null,
	product_delivery nvarchar(max) null,
	product_user nvarchar(max) null,
	product_page nvarchar(10) null,
	product_program nvarchar(max) null,
	product_hosting nvarchar(max) null,
	product_options nvarchar(max) null,
	product_content nvarchar(max) null,
	product_contentgroup nvarchar(max) null,
	product_contenttype nvarchar(max) null,
	product_imagegroup nvarchar(max) null,
	product_imagetype nvarchar(max) null,
	product_filegroup nvarchar(max) null,
	product_filetype nvarchar(max) null,
	product_linkgroup nvarchar(max) null,
	product_linktype nvarchar(max) null,
	product_productgroup nvarchar(max) null,
	product_producttype nvarchar(max) null,
	product_usergroup nvarchar(max) null,
	product_usertype nvarchar(max) null,
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
	version_master nvarchar(10) null,
	device nvarchar(255) null,
	usersegment nvarchar(255) null,
	usertest nvarchar(255) null,
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
	template nvarchar(10) null,
	stylesheet nvarchar(255) null,
	scripts nvarchar(255) null,
	image1 nvarchar(10) null,
	image2 nvarchar(10) null,
	image3 nvarchar(10) null,
	file1 nvarchar(10) null,
	file2 nvarchar(10) null,
	file3 nvarchar(10) null,
	link1 nvarchar(10) null,
	link2 nvarchar(10) null,
	link3 nvarchar(10) null,
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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	checkedout nvarchar(255) null,
	page_top nvarchar(10) null,
	page_up nvarchar(10) null,
	page_next nvarchar(10) null,
	page_previous nvarchar(10) null,
	page_first nvarchar(10) null,
	page_last nvarchar(10) null,
	related nvarchar(max) null,
	upload_filename nvarchar(max) null,
	server_filename nvarchar(255) null,
	product_code nvarchar(255) null,
	product_sku nvarchar(255) null,
	product_currency nvarchar(255) null,
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
	product_email nvarchar(10) null,
	product_url nvarchar(max) null,
	product_info nvarchar(max) null,
	product_delivery nvarchar(max) null,
	product_user nvarchar(max) null,
	product_page nvarchar(10) null,
	product_program nvarchar(max) null,
	product_hosting nvarchar(max) null,
	product_options nvarchar(max) null,
	product_content nvarchar(max) null,
	product_contentgroup nvarchar(max) null,
	product_contenttype nvarchar(max) null,
	product_imagegroup nvarchar(max) null,
	product_imagetype nvarchar(max) null,
	product_filegroup nvarchar(max) null,
	product_filetype nvarchar(max) null,
	product_linkgroup nvarchar(max) null,
	product_linktype nvarchar(max) null,
	product_productgroup nvarchar(max) null,
	product_producttype nvarchar(max) null,
	product_usergroup nvarchar(max) null,
	product_usertype nvarchar(max) null,
	custom ntext,
	futurechar1 nvarchar(255) null,
	futurechar2 nvarchar(255) null,
	futurechar3 nvarchar(255) null,
	futuretext1 nvarchar(max) null,
	futuretext2 nvarchar(max) null,
	futuretext3 nvarchar(max) null,
	futureint1 int null,
	futureint2 int null,
	futureint3 int null,
	primary key clustered (archiveid)
);

create index ca_id_idx on content_archive (id);

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
	version_master nvarchar(10) null,
	device nvarchar(255) null,
	usersegment nvarchar(255) null,
	usertest nvarchar(255) null,
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
	template nvarchar(10) null,
	stylesheet nvarchar(255) null,
	scripts nvarchar(255) null,
	image1 nvarchar(10) null,
	image2 nvarchar(10) null,
	image3 nvarchar(10) null,
	file1 nvarchar(10) null,
	file2 nvarchar(10) null,
	file3 nvarchar(10) null,
	link1 nvarchar(10) null,
	link2 nvarchar(10) null,
	link3 nvarchar(10) null,
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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	checkedout nvarchar(255) null,
	page_top nvarchar(10) null,
	page_up nvarchar(10) null,
	page_next nvarchar(10) null,
	page_previous nvarchar(10) null,
	page_first nvarchar(10) null,
	page_last nvarchar(10) null,
	related nvarchar(max) null,
	upload_filename nvarchar(max) null,
	server_filename nvarchar(255) null,
	product_code nvarchar(255) null,
	product_sku nvarchar(255) null,
	product_currency nvarchar(255) null,
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
	product_email nvarchar(10) null,
	product_url nvarchar(max) null,
	product_info nvarchar(max) null,
	product_delivery nvarchar(max) null,
	product_user nvarchar(max) null,
	product_page nvarchar(10) null,
	product_program nvarchar(max) null,
	product_hosting nvarchar(max) null,
	product_options nvarchar(max) null,
	product_content nvarchar(max) null,
	product_contentgroup nvarchar(max) null,
	product_contenttype nvarchar(max) null,
	product_imagegroup nvarchar(max) null,
	product_imagetype nvarchar(max) null,
	product_filegroup nvarchar(max) null,
	product_filetype nvarchar(max) null,
	product_linkgroup nvarchar(max) null,
	product_linktype nvarchar(max) null,
	product_productgroup nvarchar(max) null,
	product_producttype nvarchar(max) null,
	product_usergroup nvarchar(max) null,
	product_usertype nvarchar(max) null,
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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	template nvarchar(10) null,
	stylesheet nvarchar(255) null,
	title_prefix nvarchar(max) null,
	title_suffix nvarchar(max) null,
	doctype nvarchar(max) null,
	primary key clustered (id)
);

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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	template nvarchar(10) null,
	stylesheet nvarchar(255) null,
	title_prefix nvarchar(max) null,
	title_suffix nvarchar(max) null,
	doctype nvarchar(max) null,
	primary key clustered (id)
);

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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	primary key clustered (id)
);

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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	template nvarchar(10) null,
	stylesheet nvarchar(255) null,
	primary key clustered (id)
);

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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	template nvarchar(10) null,
	stylesheet nvarchar(255) null,
	primary key clustered (id)
);

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
	hostingtype nvarchar(50) null,
	hostinggroup nvarchar(50) null,
	scheduled_last nvarchar(20) null,
	scheduled_publish nvarchar(20) null,
	scheduled_notify nvarchar(20) null,
	scheduled_unpublish nvarchar(20) null,
	scheduled_publish_email nvarchar(10) null,
	scheduled_notify_email nvarchar(10) null,
	scheduled_unpublish_email nvarchar(10) null,
	users_type nvarchar(50) null,
	users_group nvarchar(50) null,
	creators_type nvarchar(50) null,
	creators_group nvarchar(50) null,
	editors_type nvarchar(50) null,
	editors_group nvarchar(50) null,
	publishers_type nvarchar(50) null,
	publishers_group nvarchar(50) null,
	administrators_type nvarchar(50) null,
	administrators_group nvarchar(50) null,
	externalstorage nvarchar(max) null,
	history nvarchar(max) null,
	custom nvarchar(max) null,
	primary key clustered (id)
);

create index h_c_address_idx on hosting (client_address);

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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	primary key clustered (id)
);

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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	primary key clustered (id)
);

create table imageformats (
	id int not null identity(1,1),
	filenameextension nvarchar(255) null,
	primary key clustered (id)
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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	template nvarchar(10) null,
	stylesheet nvarchar(255) null,
	primary key clustered (id)
);

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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	template nvarchar(10) null,
	stylesheet nvarchar(255) null,
	primary key clustered (id)
);

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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	template nvarchar(10) null,
	stylesheet nvarchar(255) null,
	primary key clustered (id)
);

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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	template nvarchar(10) null,
	stylesheet nvarchar(255) null,
	primary key clustered (id)
);

create table usergroups (
	id int not null identity(1,1),
	usergroup nvarchar(255) null,
	parentgroup nvarchar(255) null,
	login_page nvarchar(255) null,
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
	users_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	subscribers_users nvarchar(max) null,
	primary key clustered (id)
);

create table usertypes (
	id int not null identity(1,1),
	usertype nvarchar(255) null,
	parenttype nvarchar(255) null,
	login_page nvarchar(255) null,
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
	users_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	subscribers_users nvarchar(max) null,
	primary key clustered (id)
);

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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	primary key clustered (id)
);

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
	goals nvarchar(max) null,
	custom nvarchar(max) null,
	primary key clustered (id)
);

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
	goals nvarchar(max) null,
	confidence nvarchar(10) null,
	active nvarchar(1) null,
	scheduled nvarchar(20) null,
	unscheduled nvarchar(20) null,
	primary key clustered (id)
);

create index usertests_usertest on usertests (usertest);

create table websites (
	id int not null identity(1,1),
	title nvarchar(255) null,
	domain nvarchar(255) null,
	default_page nvarchar(10) null,
	default_page_nonexisting nvarchar(10) null,
	default_page_unpublished nvarchar(10) null,
	default_page_expired nvarchar(10) null,
	default_login nvarchar(10) null,
	default_searchresults_page nvarchar(10) null,
	default_searchresults_entry nvarchar(10) null,
	default_list_entry nvarchar(10) null,
	default_publish_ready nvarchar(10) null,
	default_register_confirm_page nvarchar(10) null,
	default_register_notify_page nvarchar(10) null,
	default_personal_admin_page nvarchar(10) null,
	retrieve_password_page nvarchar(10) null,
	retrieve_password_confirmation nvarchar(10) null,
	retrieve_password_email nvarchar(10) null,
	retrieve_password_error nvarchar(10) null,
	default_template nvarchar(10) null,
	default_stylesheet nvarchar(10) null,
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

create index w_domain_idx on websites (domain);

create index w_language_idx on websites (language);

create index w_remote_idx on websites (remote);

create index w_referrer_idx on websites (referrer);

create index w_keywords_idx on websites (keywords);

create table workflow (
	id int not null identity(1,1),
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
	scheduled_publish_email nvarchar(10) null,
	scheduled_notify_email nvarchar(10) null,
	scheduled_unpublish_email nvarchar(10) null,
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

create index u_username_idx on users (username);

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
	username nvarchar(50) null,
	userclass nvarchar(50) null,
	usergroup nvarchar(50) null,
	usertype nvarchar(50) null,
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

create index cur_title_idx on currencies(title);

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
	image1 nvarchar(10) null,
	image2 nvarchar(10) null,
	image3 nvarchar(10) null,
	file1 nvarchar(10) null,
	file2 nvarchar(10) null,
	file3 nvarchar(10) null,
	link1 nvarchar(10) null,
	link2 nvarchar(10) null,
	link3 nvarchar(10) null,
	author nvarchar(max) null,
	keywords nvarchar(max) null,
	description nvarchar(max) null,
	product_code nvarchar(255) null,
	product_sku nvarchar(255) null,
	product_currency nvarchar(255) null,
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
	product_email nvarchar(10) null,
	product_url nvarchar(max) null,
	product_delivery nvarchar(max) null,
	product_user nvarchar(max) null,
	product_page nvarchar(10) null,
	product_program nvarchar(max) null,
	product_hosting nvarchar(max) null,
	product_options nvarchar(max) null,
	product_content nvarchar(max) null,
	product_contentgroup nvarchar(max) null,
	product_contenttype nvarchar(max) null,
	product_imagegroup nvarchar(max) null,
	product_imagetype nvarchar(max) null,
	product_filegroup nvarchar(max) null,
	product_filetype nvarchar(max) null,
	product_linkgroup nvarchar(max) null,
	product_linktype nvarchar(max) null,
	product_productgroup nvarchar(max) null,
	product_producttype nvarchar(max) null,
	product_usergroup nvarchar(max) null,
	product_usertype nvarchar(max) null,
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
	order_currency nvarchar(max) null,
	order_currency_base nvarchar(max) null,
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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	template nvarchar(10) null,
	stylesheet nvarchar(255) null,
	title_prefix nvarchar(max) null,
	title_suffix nvarchar(max) null,
	doctype nvarchar(max) null,
	primary key clustered (id)
);

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
	users_users nvarchar(max) null,
	developers_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	template nvarchar(10) null,
	stylesheet nvarchar(255) null,
	title_prefix nvarchar(max) null,
	title_suffix nvarchar(max) null,
	doctype nvarchar(max) null,
	primary key clustered (id)
);

create table discounts (
	id int not null identity(1,1),
	title nvarchar(255) null,
	country nvarchar(50) null,
	state nvarchar(50) null,
	product_id nvarchar(50) null,
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
	product_id nvarchar(50) null,
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
	product_id nvarchar(50) null,
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
	users_users nvarchar(max) null,
	creators_users nvarchar(max) null,
	editors_users nvarchar(max) null,
	publishers_users nvarchar(max) null,
	administrators_users nvarchar(max) null,
	searchresults nvarchar(10) null,
	searchresult nvarchar(10) null,
	viewpage nvarchar(10) null,
	adminindex nvarchar(max) null,
	custom nvarchar(max) null,
	primary key clustered (id)
);

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
	id nvarchar(50) not null,
	logged nvarchar(20) null,
	action nvarchar(5) null,
	x int,
	y int,
	w int,
	h int
);

create clustered index heatmap_id_idx on heatmaps (id);

create index heatmap_logged_idx on heatmaps (logged);

create index heatmap_action_idx on heatmaps (action);



create table content_meta (
	content_id int not null,
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
	content_id int not null,
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



create table usagedata (
	created nvarchar(20) null,
	title nvarchar(250) null,
	report nvarchar(50),
	report_limit nvarchar(50),
	report_period nvarchar(50),
	period_start nvarchar(20),
	period_end nvarchar(20),
	details nvarchar(max) null,
	data nvarchar(max) null,
	primary key clustered (report,report_limit,report_period,period_start,period_end)
);

create index udata_title on usagedata (title);

create index udata_report on usagedata (report);

create index udata_limit on usagedata (report_limit);

create index udata_period on usagedata (report_period);

create index udata_start on usagedata (period_start);

create index udata_end on usagedata (period_end);



insert into config (configname, configvalue) values ('database_version', '9.0');

