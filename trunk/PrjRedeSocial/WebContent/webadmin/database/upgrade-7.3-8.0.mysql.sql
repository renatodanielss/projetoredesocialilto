
alter table elements add parentelement varchar(255);

alter table elements add users_type varchar(255);

alter table elements add users_group varchar(255);

alter table elements add developers_type varchar(255);

alter table elements add developers_group varchar(255);

alter table elements add creators_type varchar(255);

alter table elements add creators_group varchar(255);

alter table elements add editors_type varchar(255);

alter table elements add editors_group varchar(255);

alter table elements add publishers_type varchar(255);

alter table elements add publishers_group varchar(255);

alter table elements add administrators_type varchar(255);

alter table elements add administrators_group varchar(255);

alter table elements add users_users text;

alter table elements add developers_users text;

alter table elements add creators_users text;

alter table elements add editors_users text;

alter table elements add publishers_users text;

alter table elements add administrators_users text;

update elements set parentelement='' where parentelement is null;

update elements set users_type='' where users_type is null;

update elements set users_group='' where users_group is null;

update elements set developers_type='' where developers_type is null;

update elements set developers_group='' where developers_group is null;

update elements set creators_type='' where creators_type is null;

update elements set creators_group='' where creators_group is null;

update elements set editors_type='' where editors_type is null;

update elements set editors_group='' where editors_group is null;

update elements set publishers_type='' where publishers_type is null;

update elements set publishers_group='' where publishers_group is null;

update elements set administrators_type='' where administrators_type is null;

update elements set administrators_group='' where administrators_group is null;

update elements set users_users='' where users_users is null;

update elements set developers_users='' where developers_users is null;

update elements set creators_users='' where creators_users is null;

update elements set editors_users='' where editors_users is null;

update elements set publishers_users='' where publishers_users is null;

update elements set administrators_users='' where administrators_users is null;



alter table versions add parentversion varchar(255);

alter table versions add users_type varchar(255);

alter table versions add users_group varchar(255);

alter table versions add developers_type varchar(255);

alter table versions add developers_group varchar(255);

alter table versions add creators_type varchar(255);

alter table versions add creators_group varchar(255);

alter table versions add editors_type varchar(255);

alter table versions add editors_group varchar(255);

alter table versions add publishers_type varchar(255);

alter table versions add publishers_group varchar(255);

alter table versions add administrators_type varchar(255);

alter table versions add administrators_group varchar(255);

alter table versions add users_users text;

alter table versions add developers_users text;

alter table versions add creators_users text;

alter table versions add editors_users text;

alter table versions add publishers_users text;

alter table versions add administrators_users text;

update versions set parentversion='' where parentversion is null;

update versions set users_type='' where users_type is null;

update versions set users_group='' where users_group is null;

update versions set developers_type='' where developers_type is null;

update versions set developers_group='' where developers_group is null;

update versions set creators_type='' where creators_type is null;

update versions set creators_group='' where creators_group is null;

update versions set editors_type='' where editors_type is null;

update versions set editors_group='' where editors_group is null;

update versions set publishers_type='' where publishers_type is null;

update versions set publishers_group='' where publishers_group is null;

update versions set administrators_type='' where administrators_type is null;

update versions set administrators_group='' where administrators_group is null;

update versions set users_users='' where users_users is null;

update versions set developers_users='' where developers_users is null;

update versions set creators_users='' where creators_users is null;

update versions set editors_users='' where editors_users is null;

update versions set publishers_users='' where publishers_users is null;

update versions set administrators_users='' where administrators_users is null;



alter table hostinggroups add parentgroup varchar(255);

alter table hostinggroups add users_type varchar(255);

alter table hostinggroups add users_group varchar(255);

alter table hostinggroups add developers_type varchar(255);

alter table hostinggroups add developers_group varchar(255);

alter table hostinggroups add creators_type varchar(255);

alter table hostinggroups add creators_group varchar(255);

alter table hostinggroups add editors_type varchar(255);

alter table hostinggroups add editors_group varchar(255);

alter table hostinggroups add publishers_type varchar(255);

alter table hostinggroups add publishers_group varchar(255);

alter table hostinggroups add administrators_type varchar(255);

alter table hostinggroups add administrators_group varchar(255);

alter table hostinggroups add users_users text;

alter table hostinggroups add developers_users text;

alter table hostinggroups add creators_users text;

alter table hostinggroups add editors_users text;

alter table hostinggroups add publishers_users text;

alter table hostinggroups add administrators_users text;

update hostinggroups set parentgroup='' where parentgroup is null;

update hostinggroups set users_type='' where users_type is null;

update hostinggroups set users_group='' where users_group is null;

update hostinggroups set developers_type='' where developers_type is null;

update hostinggroups set developers_group='' where developers_group is null;

update hostinggroups set creators_type='' where creators_type is null;

update hostinggroups set creators_group='' where creators_group is null;

update hostinggroups set editors_type='' where editors_type is null;

update hostinggroups set editors_group='' where editors_group is null;

update hostinggroups set publishers_type='' where publishers_type is null;

update hostinggroups set publishers_group='' where publishers_group is null;

update hostinggroups set administrators_type='' where administrators_type is null;

update hostinggroups set administrators_group='' where administrators_group is null;

update hostinggroups set users_users='' where users_users is null;

update hostinggroups set developers_users='' where developers_users is null;

update hostinggroups set creators_users='' where creators_users is null;

update hostinggroups set editors_users='' where editors_users is null;

update hostinggroups set publishers_users='' where publishers_users is null;

update hostinggroups set administrators_users='' where administrators_users is null;



alter table hostingtypes add parenttype varchar(255);

alter table hostingtypes add users_type varchar(255);

alter table hostingtypes add users_group varchar(255);

alter table hostingtypes add developers_type varchar(255);

alter table hostingtypes add developers_group varchar(255);

alter table hostingtypes add creators_type varchar(255);

alter table hostingtypes add creators_group varchar(255);

alter table hostingtypes add editors_type varchar(255);

alter table hostingtypes add editors_group varchar(255);

alter table hostingtypes add publishers_type varchar(255);

alter table hostingtypes add publishers_group varchar(255);

alter table hostingtypes add administrators_type varchar(255);

alter table hostingtypes add administrators_group varchar(255);

alter table hostingtypes add users_users text;

alter table hostingtypes add developers_users text;

alter table hostingtypes add creators_users text;

alter table hostingtypes add editors_users text;

alter table hostingtypes add publishers_users text;

alter table hostingtypes add administrators_users text;

update hostingtypes set parenttype='' where parenttype is null;

update hostingtypes set users_type='' where users_type is null;

update hostingtypes set users_group='' where users_group is null;

update hostingtypes set developers_type='' where developers_type is null;

update hostingtypes set developers_group='' where developers_group is null;

update hostingtypes set creators_type='' where creators_type is null;

update hostingtypes set creators_group='' where creators_group is null;

update hostingtypes set editors_type='' where editors_type is null;

update hostingtypes set editors_group='' where editors_group is null;

update hostingtypes set publishers_type='' where publishers_type is null;

update hostingtypes set publishers_group='' where publishers_group is null;

update hostingtypes set administrators_type='' where administrators_type is null;

update hostingtypes set administrators_group='' where administrators_group is null;

update hostingtypes set users_users='' where users_users is null;

update hostingtypes set developers_users='' where developers_users is null;

update hostingtypes set creators_users='' where creators_users is null;

update hostingtypes set editors_users='' where editors_users is null;

update hostingtypes set publishers_users='' where publishers_users is null;

update hostingtypes set administrators_users='' where administrators_users is null;



alter table content add unpublished varchar(20);

alter table content add unpublished_by varchar(255);

alter table content_archive add unpublished varchar(20);

alter table content_archive add unpublished_by varchar(255);

alter table content_public add unpublished varchar(20);

alter table content_public add unpublished_by varchar(255);

update content set unpublished='' where unpublished is null;

update content set unpublished_by='' where unpublished_by is null;

update content_archive set unpublished='' where unpublished is null;

update content_archive set unpublished_by='' where unpublished_by is null;

update content_public set unpublished='' where unpublished is null;

update content_public set unpublished_by='' where unpublished_by is null;



alter table content add contentbundle varchar(255);

alter table content_archive add contentbundle varchar(255);

alter table content_public add contentbundle varchar(255);

update content set contentbundle='' where contentbundle is null;

update content_archive set contentbundle='' where contentbundle is null;

update content_public set contentbundle='' where contentbundle is null;



alter table users add index_content text;

alter table users add index_library text;

alter table users add index_product text;

alter table users add index_order text;

alter table users add index_databases text;

alter table users add index_user text;

alter table users add index_hosting text;

alter table users add index_websites text;

alter table users add menu_selection varchar(255);

alter table users add workspace_sections text;

alter table users add statistics_reports text;

update users set index_content='' where index_content is null;

update users set index_library='' where index_library is null;

update users set index_product='' where index_product is null;

update users set index_order='' where index_order is null;

update users set index_databases='' where index_databases is null;

update users set index_user='' where index_user is null;

update users set index_hosting='' where index_hosting is null;

update users set index_websites='' where index_websites is null;

update users set menu_selection='' where menu_selection is null;

update users set workspace_sections='' where workspace_sections is null;

update users set statistics_reports='' where statistics_reports is null;



alter table usagelog add hits int;

update usagelog set hits = 1 where hits is null;



alter table websites add default_page_nonexisting varchar(10);

alter table websites add default_page_unpublished varchar(10);

alter table websites add default_page_expired varchar(10);

alter table websites add default_login varchar(10);

alter table websites add default_searchresults_page varchar(10);

alter table websites add default_searchresults_entry varchar(10);

alter table websites add default_list_entry varchar(10);

alter table websites add default_publish_ready varchar(10);

alter table websites add default_register_confirm_page varchar(10);

alter table websites add default_register_notify_page varchar(10);

alter table websites add default_personal_admin_page varchar(10);

alter table websites add retrieve_password_page varchar(10);

alter table websites add retrieve_password_confirmation varchar(10);

alter table websites add retrieve_password_email varchar(10);

alter table websites add retrieve_password_error varchar(10);

update websites set default_page_nonexisting='' where default_page_nonexisting is null;

update websites set default_page_unpublished='' where default_page_unpublished is null;

update websites set default_page_expired='' where default_page_expired is null;

update websites set default_login='' where default_login is null;

update websites set default_searchresults_page='' where default_searchresults_page is null;

update websites set default_searchresults_entry='' where default_searchresults_entry is null;

update websites set default_list_entry='' where default_list_entry is null;

update websites set default_publish_ready='' where default_publish_ready is null;

update websites set default_register_confirm_page='' where default_register_confirm_page is null;

update websites set default_register_notify_page='' where default_register_notify_page is null;

update websites set default_personal_admin_page='' where default_personal_admin_page is null;

update websites set retrieve_password_page='' where retrieve_password_page is null;

update websites set retrieve_password_confirmation='' where retrieve_password_confirmation is null;

update websites set retrieve_password_email='' where retrieve_password_email is null;

update websites set retrieve_password_error='' where retrieve_password_error is null;



alter table hosting add externalstorage text;

update hosting set externalstorage='' where externalstorage is null;



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



alter table content add futurechar1 varchar(255);

alter table content add futurechar2 varchar(255);

alter table content add futurechar3 varchar(255);

alter table content add futuretext1 text;

alter table content add futuretext2 text;

alter table content add futuretext3 text;

alter table content add futureint1 int;

alter table content add futureint2 int;

alter table content add futureint3 int;

alter table content_archive add futurechar1 varchar(255);

alter table content_archive add futurechar2 varchar(255);

alter table content_archive add futurechar3 varchar(255);

alter table content_archive add futuretext1 text;

alter table content_archive add futuretext2 text;

alter table content_archive add futuretext3 text;

alter table content_archive add futureint1 int;

alter table content_archive add futureint2 int;

alter table content_archive add futureint3 int;

alter table content_public add futurechar1 varchar(255);

alter table content_public add futurechar2 varchar(255);

alter table content_public add futurechar3 varchar(255);

alter table content_public add futuretext1 text;

alter table content_public add futuretext2 text;

alter table content_public add futuretext3 text;

alter table content_public add futureint1 int;

alter table content_public add futureint2 int;

alter table content_public add futureint3 int;



create index c_filename_idx on content (server_filename(250));

create index ca_filename_idx on content_archive (server_filename(250));

create index cp_filename_idx on content_public (server_filename(250));

create index ca_id_idx on content_archive (id);




create index c_page_top_idx on content (page_top);

create index c_page_up_idx on content (page_up);

create index c_page_next_idx on content (page_next);

create index c_page_prev_idx on content (page_previous);

create index c_page_first_idx on content (page_first);

create index c_page_last_idx on content (page_last);

create index cp_page_top_idx on content_public (page_top);

create index cp_page_up_idx on content_public (page_up);

create index cp_page_next_idx on content_public (page_next);

create index cp_page_prev_idx on content_public (page_previous);

create index cp_page_first_idx on content_public (page_first);

create index cp_page_last_idx on content_public (page_last);



update config set configvalue='8.0' where configname='database_version'

