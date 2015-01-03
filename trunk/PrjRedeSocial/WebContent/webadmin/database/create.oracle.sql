create table config (
	configname varchar(255) not null,
	configvalue clob,
	primary key (configname)
);

create table content (
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
	revision clob,
	history clob,
	version varchar(255),
	version_master varchar(10),
	device varchar(255),
	usersegment varchar(255),
	usertest varchar(255),
	heatmap varchar(50),
	heatmapalign varchar(1),
	usagelog varchar(1),
	title varchar(255),
	searchable varchar(3),
	menuitem varchar(3),
	author clob,
	keywords clob,
	description clob,
	metainfo clob,
	segmentation clob,
	doctype clob,
	htmlattributes clob,
	htmlheader clob,
	htmlbodyonload clob,
	url clob,
	content clob,
	summary clob,
	template varchar(10),
	stylesheet varchar(255),
	scripts varchar(255),
	image1 varchar(10),
	image2 varchar(10),
	image3 varchar(10),
	file1 varchar(10),
	file2 varchar(10),
	file3 varchar(10),
	link1 varchar(10),
	link2 varchar(10),
	link3 varchar(10),
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	checkedout varchar(255),
	page_top varchar(10),
	page_up varchar(10),
	page_next varchar(10),
	page_previous varchar(10),
	page_first varchar(10),
	page_last varchar(10),
	related clob,
	upload_filename clob,
	server_filename clob,
	product_code varchar(255),
	product_sku varchar(255),
	product_currency varchar(255),
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
	product_comment clob,
	product_email varchar(10),
	product_url clob,
	product_info clob,
	product_delivery clob,
	product_user clob,
	product_page varchar(10),
	product_program clob,
	product_hosting clob,
	product_options clob,
	product_content clob,
	product_contentgroup clob,
	product_contenttype clob,
	product_imagegroup clob,
	product_imagetype clob,
	product_filegroup clob,
	product_filetype clob,
	product_linkgroup clob,
	product_linktype clob,
	product_productgroup clob,
	product_producttype clob,
	product_usergroup clob,
	product_usertype clob,
	custom clob,
	futurechar1 varchar(255),
	futurechar2 varchar(255),
	futurechar3 varchar(255),
	futuretext1 clob,
	futuretext2 clob,
	futuretext3 clob,
	futureint1 int,
	futureint2 int,
	futureint3 int,
	primary key (id)
);

create sequence content_seq;

create or replace trigger content_bir before insert on content referencing old as old new as new for each row
begin
 select content_seq.nextval into :new.id from dual;
end;
/

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

!create index c_filename_idx on content (server_filename);


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
	element_params clob
);

create index ce_content_id_idx on content_elements (content_id);

create index ce_element_idx on content_elements (element);

create index ce_element_id_idx on content_elements (element_id);

create table content_archive (
	archiveid int not null,
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
	revision clob,
	history clob,
	version varchar(255),
	version_master varchar(10),
	device varchar(255),
	usersegment varchar(255),
	usertest varchar(255),
	heatmap varchar(50),
	heatmapalign varchar(1),
	usagelog varchar(1),
	title varchar(255),
	searchable varchar(3),
	menuitem varchar(3),
	author clob,
	keywords clob,
	description clob,
	metainfo clob,
	segmentation clob,
	doctype clob,
	htmlattributes clob,
	htmlheader clob,
	htmlbodyonload clob,
	url clob,
	content clob,
	summary clob,
	template varchar(10),
	stylesheet varchar(255),
	scripts varchar(255),
	image1 varchar(10),
	image2 varchar(10),
	image3 varchar(10),
	file1 varchar(10),
	file2 varchar(10),
	file3 varchar(10),
	link1 varchar(10),
	link2 varchar(10),
	link3 varchar(10),
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	checkedout varchar(255),
	page_top varchar(10),
	page_up varchar(10),
	page_next varchar(10),
	page_previous varchar(10),
	page_first varchar(10),
	page_last varchar(10),
	related clob,
	upload_filename clob,
	server_filename clob,
	product_code varchar(255),
	product_sku varchar(255),
	product_currency varchar(255),
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
	product_comment clob,
	product_email varchar(10),
	product_url clob,
	product_info clob,
	product_delivery clob,
	product_user clob,
	product_page varchar(10),
	product_program clob,
	product_hosting clob,
	product_options clob,
	product_content clob,
	product_contentgroup clob,
	product_contenttype clob,
	product_imagegroup clob,
	product_imagetype clob,
	product_filegroup clob,
	product_filetype clob,
	product_linkgroup clob,
	product_linktype clob,
	product_productgroup clob,
	product_producttype clob,
	product_usergroup clob,
	product_usertype clob,
	custom clob,
	futurechar1 varchar(255),
	futurechar2 varchar(255),
	futurechar3 varchar(255),
	futuretext1 clob,
	futuretext2 clob,
	futuretext3 clob,
	futureint1 int,
	futureint2 int,
	futureint3 int,
	primary key (archiveid)
);

create sequence content_archive_seq;

create or replace trigger content_archive_bir before insert on content_archive referencing old as old new as new for each row
begin
 select content_archive_seq.nextval into :new.archiveid from dual;
end;
/

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

!create index ca_filename_idx on content_archive (server_filename);

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
	element_params clob
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
	revision clob,
	history clob,
	version varchar(255),
	version_master varchar(10),
	device varchar(255),
	usersegment varchar(255),
	usertest varchar(255),
	heatmap varchar(50),
	heatmapalign varchar(1),
	usagelog varchar(1),
	title varchar(255),
	searchable varchar(3),
	menuitem varchar(3),
	author clob,
	keywords clob,
	description clob,
	metainfo clob,
	segmentation clob,
	doctype clob,
	htmlattributes clob,
	htmlheader clob,
	htmlbodyonload clob,
	url clob,
	content clob,
	summary clob,
	template varchar(10),
	stylesheet varchar(255),
	scripts varchar(255),
	image1 varchar(10),
	image2 varchar(10),
	image3 varchar(10),
	file1 varchar(10),
	file2 varchar(10),
	file3 varchar(10),
	link1 varchar(10),
	link2 varchar(10),
	link3 varchar(10),
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	checkedout varchar(255),
	page_top varchar(10),
	page_up varchar(10),
	page_next varchar(10),
	page_previous varchar(10),
	page_first varchar(10),
	page_last varchar(10),
	related clob,
	upload_filename clob,
	server_filename clob,
	product_code varchar(255),
	product_sku varchar(255),
	product_currency varchar(255),
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
	product_comment clob,
	product_email varchar(10),
	product_url clob,
	product_info clob,
	product_delivery clob,
	product_user clob,
	product_page varchar(10),
	product_program clob,
	product_hosting clob,
	product_options clob,
	product_content clob,
	product_contentgroup clob,
	product_contenttype clob,
	product_imagegroup clob,
	product_imagetype clob,
	product_filegroup clob,
	product_filetype clob,
	product_linkgroup clob,
	product_linktype clob,
	product_productgroup clob,
	product_producttype clob,
	product_usergroup clob,
	product_usertype clob,
	custom clob,
	futurechar1 varchar(255),
	futurechar2 varchar(255),
	futurechar3 varchar(255),
	futuretext1 clob,
	futuretext2 clob,
	futuretext3 clob,
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

!create index cp_filename_idx on content_public (server_filename);

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
	element_params clob
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
	id int not null,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	template varchar(10),
	stylesheet varchar(255),
	title_prefix clob,
	title_suffix clob,
	doctype clob,
	primary key (id)
);

create sequence contentgroups_seq;

create or replace trigger contentgroups_bir before insert on contentgroups referencing old as old new as new for each row
begin
 select contentgroups_seq.nextval into :new.id from dual;
end;
/

create table contenttypes (
	id int not null,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	template varchar(10),
	stylesheet varchar(255),
	title_prefix clob,
	title_suffix clob,
	doctype clob,
	primary key (id)
);

create sequence contenttypes_seq;

create or replace trigger contenttypes_bir before insert on contenttypes referencing old as old new as new for each row
begin
 select contenttypes_seq.nextval into :new.id from dual;
end;
/

create table elements (
	id int not null,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	primary key (id)
);

create sequence elements_seq;

create or replace trigger elements_bir before insert on elements referencing old as old new as new for each row
begin
 select elements_seq.nextval into :new.id from dual;
end;
/

create table fileformats (
	id int not null,
	filenameextension varchar(255),
	primary key (id)
);

create sequence fileformats_seq;

create or replace trigger fileformats_bir before insert on fileformats referencing old as old new as new for each row
begin
 select fileformats_seq.nextval into :new.id from dual;
end;
/

create table filegroups (
	id int not null,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	template varchar(10),
	stylesheet varchar(255),
	primary key (id)
);

create sequence filegroups_seq;

create or replace trigger filegroups_bir before insert on filegroups referencing old as old new as new for each row
begin
 select filegroups_seq.nextval into :new.id from dual;
end;
/

create table filetypes (
	id int not null,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	template varchar(10),
	stylesheet varchar(255),
	primary key (id)
);

create sequence filetypes_seq;

create or replace trigger filetypes_bir before insert on filetypes referencing old as old new as new for each row
begin
 select filetypes_seq.nextval into :new.id from dual;
end;
/

create table fonts (
	id int not null,
	face varchar(255),
	primary key (id)
);

create sequence fonts_seq;

create or replace trigger fonts_bir before insert on fonts referencing old as old new as new for each row
begin
 select fonts_seq.nextval into :new.id from dual;
end;
/

create table hosting (
	id int not null,
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
	hostingtype varchar(50),
	hostinggroup varchar(50),
	scheduled_last varchar(20),
	scheduled_publish varchar(20),
	scheduled_notify varchar(20),
	scheduled_unpublish varchar(20),
	scheduled_publish_email varchar(10),
	scheduled_notify_email varchar(10),
	scheduled_unpublish_email varchar(10),
	users_type varchar(50),
	users_group varchar(50),
	creators_type varchar(50),
	creators_group varchar(50),
	editors_type varchar(50),
	editors_group varchar(50),
	publishers_type varchar(50),
	publishers_group varchar(50),
	administrators_type varchar(50),
	administrators_group varchar(50),
	externalstorage clob,
	history clob,
	custom clob,
	primary key (id)
);

create sequence hosting_seq;

create or replace trigger hosting_bir before insert on hosting referencing old as old new as new for each row
begin
 select hosting_seq.nextval into :new.id from dual;
end;
/

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
	id int not null,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	primary key (id)
);

create sequence hostinggroups_seq;

create or replace trigger hostinggroups_bir before insert on hostinggroups referencing old as old new as new for each row
begin
 select hostinggroups_seq.nextval into :new.id from dual;
end;
/

create table hostingtypes (
	id int not null,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	primary key (id)
);

create sequence hostingtypes_seq;

create or replace trigger hostingtypes_bir before insert on hostingtypes referencing old as old new as new for each row
begin
 select hostingtypes_seq.nextval into :new.id from dual;
end;
/

create table imageformats (
	id int not null,
	filenameextension varchar(255),
	primary key (id)
);

create sequence imageformats_seq;

create or replace trigger imageformats_bir before insert on imageformats referencing old as old new as new for each row
begin
 select imageformats_seq.nextval into :new.id from dual;
end;
/

create table imagegroups (
	id int not null,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	template varchar(10),
	stylesheet varchar(255),
	primary key (id)
);

create sequence imagegroups_seq;

create or replace trigger imagegroups_bir before insert on imagegroups referencing old as old new as new for each row
begin
 select imagegroups_seq.nextval into :new.id from dual;
end;
/

create table imagetypes (
	id int not null,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	template varchar(10),
	stylesheet varchar(255),
	primary key (id)
);

create sequence imagetypes_seq;

create or replace trigger imagetypes_bir before insert on imagetypes referencing old as old new as new for each row
begin
 select imagetypes_seq.nextval into :new.id from dual;
end;
/

create table linkgroups (
	id int not null,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	template varchar(10),
	stylesheet varchar(255),
	primary key (id)
);

create sequence linkgroups_seq;

create or replace trigger linkgroups_bir before insert on linkgroups referencing old as old new as new for each row
begin
 select linkgroups_seq.nextval into :new.id from dual;
end;
/

create table linktypes (
	id int not null,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	template varchar(10),
	stylesheet varchar(255),
	primary key (id)
);

create sequence linktypes_seq;

create or replace trigger linktypes_bir before insert on linktypes referencing old as old new as new for each row
begin
 select linktypes_seq.nextval into :new.id from dual;
end;
/

create table usergroups (
	id int not null,
	usergroup varchar(255),
	parentgroup varchar(255),
	login_page varchar(255),
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
	users_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	subscribers_users clob,
	primary key (id)
);

create sequence usergroups_seq;

create or replace trigger usergroups_bir before insert on usergroups referencing old as old new as new for each row
begin
 select usergroups_seq.nextval into :new.id from dual;
end;
/

create table usertypes (
	id int not null,
	usertype varchar(255),
	parenttype varchar(255),
	login_page varchar(255),
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
	users_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	subscribers_users clob,
	primary key (id)
);

create sequence usertypes_seq;

create or replace trigger usertypes_bir before insert on usertypes referencing old as old new as new for each row
begin
 select usertypes_seq.nextval into :new.id from dual;
end;
/

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
	id int not null,
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

create sequence users_usergroups_seq;

create or replace trigger users_usergroups_bir before insert on users_usergroups referencing old as old new as new for each row
begin
 select users_usergroups_seq.nextval into :new.id from dual;
end;
/

create index u_ug_username_idx on users_usergroups(username);

create index u_ug_usergroup_idx on users_usergroups(usergroup);

create table users_usertypes (
	id int not null,
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

create sequence users_usertypes_seq;

create or replace trigger users_usertypes_bir before insert on users_usertypes referencing old as old new as new for each row
begin
 select users_usertypes_seq.nextval into :new.id from dual;
end;
/

create index u_ut_username_idx on users_usertypes(username);

create index u_ut_usertype_idx on users_usertypes(usertype);

create table versions (
	id int not null,
	version varchar(255),
	currencies varchar(50),
	default_country varchar(255),
	default_state varchar(255),
	default_price clob,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	primary key (id)
);

create sequence versions_seq;

create or replace trigger versions_bir before insert on versions referencing old as old new as new for each row
begin
 select versions_seq.nextval into :new.id from dual;
end;
/

create table segments (
	id int not null,
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

create sequence segments_seq;

create or replace trigger segments_bir before insert on segments referencing old as old new as new for each row
begin
 select segments_seq.nextval into :new.id from dual;
end;
/

create index segments_active on segments (active);

create index segments_scheduled on segments (scheduled);

create index segments_unscheduled on segments (unscheduled);

create table usertests (
	id int not null,
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

create sequence usertests_seq;

create or replace trigger usertests_bir before insert on usertests referencing old as old new as new for each row
begin
 select usertests_seq.nextval into :new.id from dual;
end;
/

create index usertests_usertest on usertests (usertest);

create table websites (
	id int not null,
	title varchar(255),
	domain varchar(255),
	default_page varchar(10),
	default_page_nonexisting varchar(10),
	default_page_unpublished varchar(10),
	default_page_expired varchar(10),
	default_login varchar(10),
	default_searchresults_page varchar(10),
	default_searchresults_entry varchar(10),
	default_list_entry varchar(10),
	default_publish_ready varchar(10),
	default_register_confirm_page varchar(10),
	default_register_notify_page varchar(10),
	default_personal_admin_page varchar(10),
	retrieve_password_page varchar(10),
	retrieve_password_confirmation varchar(10),
	retrieve_password_email varchar(10),
	retrieve_password_error varchar(10),
	default_template varchar(10),
	default_stylesheet varchar(10),
	default_version varchar(255),
	default_country varchar(255),
	default_state varchar(255),
	default_currency varchar(50),
	default_price clob,
	language varchar(255),
	remote varchar(255),
	useragent varchar(255),
	referrer varchar(255),
	keywords varchar(255),
	default_doctype clob,
	primary key (id)
);

create sequence websites_seq;

create or replace trigger websites_bir before insert on websites referencing old as old new as new for each row
begin
 select websites_seq.nextval into :new.id from dual;
end;
/

create index w_domain_idx on websites (domain);

create index w_language_idx on websites (language);

create index w_remote_idx on websites (remote);

create index w_referrer_idx on websites (referrer);

create index w_keywords_idx on websites (keywords);

create table workflow (
	id int not null,
	title varchar(255),
	action varchar(255),
	fromstate varchar(255),
	tostate varchar(255),
	usertype clob,
	usergroup clob,
	contentclass clob,
	contenttype clob,
	contentgroup clob,
	version clob,
	notify_email varchar(10),
	contentchanges clob,
	workflow_program clob,
	userrestriction varchar(20),
	primary key (id)
);

create sequence workflow_seq;

create or replace trigger workflow_bir before insert on workflow referencing old as old new as new for each row
begin
 select workflow_seq.nextval into :new.id from dual;
end;
/

create index w_title_idx on workflow (title);

create index w_action_idx on workflow (action);

create index w_fromstate_idx on workflow (fromstate);

create index w_tostate_idx on workflow (tostate);

create table users (
	id int not null,
	locked int,
	failed int,
	created varchar(20),
	updated varchar(20),
	created_by varchar(255),
	updated_by varchar(255),
	history clob,
	keywords clob,
	description clob,
	birthdate varchar(10),
	birthyear varchar(4),
	birthmonth varchar(2),
	birthday varchar(2),
	gender varchar(1),
	title varchar(255),
	name varchar(255),
	organisation clob,
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
	hardcore_onenter clob,
	hardcore_onctrlenter clob,
	hardcore_onshiftenter clob,
	hardcore_onaltenter clob,
	hardcore_toolbar1 clob,
	hardcore_toolbar2 clob,
	hardcore_toolbar3 clob,
	hardcore_toolbar4 clob,
	hardcore_toolbar5 clob,
	hardcore_encoding varchar(20),
	hardcore_formatblock clob,
	hardcore_fontname clob,
	hardcore_fontsize clob,
	hardcore_customscript clob,
	scheduled_last varchar(20),
	scheduled_publish varchar(20),
	scheduled_notify varchar(20),
	scheduled_unpublish varchar(20),
	scheduled_publish_email varchar(10),
	scheduled_notify_email varchar(10),
	scheduled_unpublish_email varchar(10),
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
	delivery_address clob,
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
	invoice_address clob,
	invoice_postalcode varchar(255),
	invoice_city varchar(255),
	invoice_state varchar(255),
	invoice_country varchar(255),
	invoice_phone varchar(255),
	invoice_fax varchar(255),
	invoice_email varchar(255),
	invoice_website varchar(255),
	notes clob,
	userinfo clob,
	index_content clob,
	index_library clob,
	index_product clob,
	index_order clob,
	index_segments clob,
	index_usertests clob,
	index_heatmaps clob,
	index_search clob,
	index_searchadvanced clob,
	index_searchreplace clob,
	index_databases clob,
	index_user clob,
	index_hosting clob,
	index_websites clob,
	index_stock clob,
	index_sales clob,
	index_workspace clob,
	menu_selection clob,
	workspace_sections clob,
	statistics_reports clob,
	sales_reports clob,
	shopcart clob,
	wishlist clob,
	product_points int,
	custom clob,
	primary key (id)
);

create sequence users_seq;

create or replace trigger users_bir before insert on users referencing old as old new as new for each row
begin
 select users_seq.nextval into :new.id from dual;
end;
/

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
	id int not null,
	action varchar(50),
	"resource" varchar(250),
	username varchar(50),
	userclass varchar(50),
	usergroup varchar(50),
	usertype varchar(50),
	primary key (id)
);

create sequence permissions_seq;

create or replace trigger permissions_bir before insert on permissions referencing old as old new as new for each row
begin
 select permissions_seq.nextval into :new.id from dual;
end;
/

create index perm_action_idx on permissions(action);

create index perm_resource_idx on permissions("resource");

create index perm_username_idx on permissions(username);

create index perm_userclass_idx on permissions(userclass);

create index perm_usergroup_idx on permissions(usergroup);

create index perm_usertype_idx on permissions(usertype);

create table guestbook (
	id int not null,
	created varchar(20),
	updated varchar(20),
	published varchar(20),
	status varchar(255),
	created_by varchar(255),
	updated_by varchar(255),
	published_by varchar(255),
	title varchar(255),
	content clob,
	name varchar(255),
	email varchar(255),
	website varchar(255),
	company varchar(255),
	primary key (id)
);

create sequence guestbook_seq;

create or replace trigger guestbook_bir before insert on guestbook referencing old as old new as new for each row
begin
 select guestbook_seq.nextval into :new.id from dual;
end;
/

create table currencies (
	id int not null,
	title varchar(50),
	symbol varchar(50),
	rate clob,
	primary key (id)
);

create sequence currencies_seq;

create or replace trigger currencies_bir before insert on currencies referencing old as old new as new for each row
begin
 select currencies_seq.nextval into :new.id from dual;
end;
/

create index cur_title_idx on currencies(title);

create index cur_symbol_idx on currencies(symbol);

create table orderitems (
	id int not null,
	order_id int,
	product_id int,
	version varchar(255),
	device varchar(255),
	usersegment varchar(255),
	usertest varchar(255),
	title varchar(255),
	summary clob,
	image1 varchar(10),
	image2 varchar(10),
	image3 varchar(10),
	file1 varchar(10),
	file2 varchar(10),
	file3 varchar(10),
	link1 varchar(10),
	link2 varchar(10),
	link3 varchar(10),
	author clob,
	keywords clob,
	description clob,
	product_code varchar(255),
	product_sku varchar(255),
	product_currency varchar(255),
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
	product_comment clob,
	product_email varchar(10),
	product_url clob,
	product_delivery clob,
	product_user clob,
	product_page varchar(10),
	product_program clob,
	product_hosting clob,
	product_options clob,
	product_content clob,
	product_contentgroup clob,
	product_contenttype clob,
	product_imagegroup clob,
	product_imagetype clob,
	product_filegroup clob,
	product_filetype clob,
	product_linkgroup clob,
	product_linktype clob,
	product_productgroup clob,
	product_producttype clob,
	product_usergroup clob,
	product_usertype clob,
	item_quantity int,
	item_subtotal decimal(20,2),
	item_subtotal_base decimal(20,2),
	item_total decimal(20,2),
	item_total_base decimal(20,2),
	discount_description clob,
	discount_total decimal(20,2),
	discount_total_base decimal(20,2),
	shipping_description clob,
	shipping_total decimal(20,2),
	shipping_total_base decimal(20,2),
	tax_description clob,
	tax_total decimal(20,2),
	tax_total_base decimal(20,2),
	primary key (id)
);

create sequence orderitems_seq;

create or replace trigger orderitems_bir before insert on orderitems referencing old as old new as new for each row
begin
 select orderitems_seq.nextval into :new.id from dual;
end;
/

create index oi_order_id_idx on orderitems(order_id);

create index oi_product_id_idx on orderitems(product_id);

create table orders (
	id int not null,
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
	payment clob,
	created_by varchar(255),
	updated_by varchar(255),
	published_by varchar(255),
	status varchar(255),
	status_by varchar(255),
	revision clob,
	history clob,
	checkedout varchar(255),
	order_quantity int,
	order_currency clob,
	order_currency_base clob,
	order_subtotal decimal(20,2),
	order_subtotal_base decimal(20,2),
	tax_description clob,
	tax_total decimal(20,2),
	tax_total_base decimal(20,2),
	shipping_description clob,
	shipping_total decimal(20,2),
	shipping_total_base decimal(20,2),
	discount_description clob,
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
	delivery_address clob,
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
	invoice_address clob,
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
	usersegments clob,
	usertests clob,
	custom clob,
	primary key (id)
);

create sequence orders_seq;

create or replace trigger orders_bir before insert on orders referencing old as old new as new for each row
begin
 select orders_seq.nextval into :new.id from dual;
end;
/

create table productgroups (
	id int not null,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	template varchar(10),
	stylesheet varchar(255),
	title_prefix clob,
	title_suffix clob,
	doctype clob,
	primary key (id)
);

create sequence productgroups_seq;

create or replace trigger productgroups_bir before insert on productgroups referencing old as old new as new for each row
begin
 select productgroups_seq.nextval into :new.id from dual;
end;
/

create table producttypes (
	id int not null,
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
	users_users clob,
	developers_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	template varchar(10),
	stylesheet varchar(255),
	title_prefix clob,
	title_suffix clob,
	doctype clob,
	primary key (id)
);

create sequence producttypes_seq;

create or replace trigger producttypes_bir before insert on producttypes referencing old as old new as new for each row
begin
 select producttypes_seq.nextval into :new.id from dual;
end;
/

create table discounts (
	id int not null,
	title varchar(255),
	country varchar(50),
	state varchar(50),
	product_id varchar(50),
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

create sequence discounts_seq;

create or replace trigger discounts_bir before insert on discounts referencing old as old new as new for each row
begin
 select discounts_seq.nextval into :new.id from dual;
end;
/

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
	id int not null,
	title varchar(255),
	country varchar(50),
	state varchar(50),
	product_id varchar(50),
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

create sequence shipping_seq;

create or replace trigger shipping_bir before insert on shipping referencing old as old new as new for each row
begin
 select shipping_seq.nextval into :new.id from dual;
end;
/

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
	id int not null,
	title varchar(255),
	country varchar(50),
	state varchar(50),
	product_id varchar(50),
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

create sequence tax_seq;

create or replace trigger tax_bir before insert on tax referencing old as old new as new for each row
begin
 select tax_seq.nextval into :new.id from dual;
end;
/

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
	id int not null,
	title varchar(50),
	content clob,
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
	users_users clob,
	creators_users clob,
	editors_users clob,
	publishers_users clob,
	administrators_users clob,
	searchresults varchar(10),
	searchresult varchar(10),
	viewpage varchar(10),
	adminindex clob,
	custom clob,
	primary key (id)
);

create sequence data_seq;

create or replace trigger data_bir before insert on data referencing old as old new as new for each row
begin
 select data_seq.nextval into :new.id from dual;
end;
/

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
	usersegments clob,
	usertests clob,
	hits int,
	pageviews int,
	visits int,
	visitors int,
	clienthosts int,
	clienthostids clob,
	visitorids clob,
	sessionids clob,
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



create table content_meta (
	content_id int not null,
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
	content_id int not null,
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



create table usagedata (
	created varchar(20),
	title varchar(250),
	report varchar(50),
	report_limit varchar(50),
	report_period varchar(50),
	period_start varchar(20),
	period_end varchar(20),
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



insert into config (configname, configvalue) values ('database_version', '9.0');

