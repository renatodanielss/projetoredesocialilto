
# The following SQL statements may fail on Microsoft SQL Server 2000 and older

# ntext is deprecated in Microsoft SQL Server 2012 and newer - alter to nvarchar(max)

# nvarchar(max) is not available in Microsoft SQL Server 2000 and older

# Existing Microsoft SQL Server 2000 databases can still be used, but new Microsoft SQL Server 2000 databases cannot be created



alter table config alter column configvalue nvarchar(max) null;



alter table content alter column revision nvarchar(max) null;

alter table content alter column history nvarchar(max) null;

alter table content alter column author nvarchar(max) null;

alter table content alter column keywords nvarchar(max) null;

alter table content alter column description nvarchar(max) null;

alter table content alter column metainfo nvarchar(max) null;

alter table content alter column doctype nvarchar(max) null;

alter table content alter column htmlheader nvarchar(max) null;

alter table content alter column htmlbodyonload nvarchar(max) null;

alter table content alter column url nvarchar(max) null;

alter table content alter column content nvarchar(max) null;

alter table content alter column summary nvarchar(max) null;

alter table content alter column users_users nvarchar(max) null;

alter table content alter column developers_users nvarchar(max) null;

alter table content alter column creators_users nvarchar(max) null;

alter table content alter column editors_users nvarchar(max) null;

alter table content alter column publishers_users nvarchar(max) null;

alter table content alter column administrators_users nvarchar(max) null;

alter table content alter column related nvarchar(max) null;

alter table content alter column upload_filename nvarchar(max) null;

alter table content alter column product_code nvarchar(max) null;

alter table content alter column product_currency nvarchar(max) null;

alter table content alter column product_price nvarchar(max) null;

alter table content alter column product_period nvarchar(max) null;

alter table content alter column product_weight nvarchar(max) null;

alter table content alter column product_volume nvarchar(max) null;

alter table content alter column product_width nvarchar(max) null;

alter table content alter column product_height nvarchar(max) null;

alter table content alter column product_depth nvarchar(max) null;

alter table content alter column product_points_cost nvarchar(max) null;

alter table content alter column product_points_reward nvarchar(max) null;

alter table content alter column product_stock nvarchar(max) null;

alter table content alter column product_cost nvarchar(max) null;

alter table content alter column product_comment nvarchar(max) null;

alter table content alter column product_url nvarchar(max) null;

alter table content alter column product_info nvarchar(max) null;

alter table content alter column product_delivery nvarchar(max) null;

alter table content alter column product_user nvarchar(max) null;

alter table content alter column product_program nvarchar(max) null;

alter table content alter column product_hosting nvarchar(max) null;

alter table content alter column product_options nvarchar(max) null;

alter table content alter column product_content nvarchar(max) null;

alter table content alter column product_contentgroup nvarchar(max) null;

alter table content alter column product_contenttype nvarchar(max) null;

alter table content alter column product_imagegroup nvarchar(max) null;

alter table content alter column product_imagetype nvarchar(max) null;

alter table content alter column product_filegroup nvarchar(max) null;

alter table content alter column product_filetype nvarchar(max) null;

alter table content alter column product_linkgroup nvarchar(max) null;

alter table content alter column product_linktype nvarchar(max) null;

alter table content alter column product_productgroup nvarchar(max) null;

alter table content alter column product_producttype nvarchar(max) null;

alter table content alter column product_usergroup nvarchar(max) null;

alter table content alter column product_usertype nvarchar(max) null;

alter table content alter column custom nvarchar(max) null;

alter table content alter column futuretext1 nvarchar(max) null;

alter table content alter column futuretext2 nvarchar(max) null;

alter table content alter column futuretext3 nvarchar(max) null;



alter table content_archive alter column revision nvarchar(max) null;

alter table content_archive alter column history nvarchar(max) null;

alter table content_archive alter column author nvarchar(max) null;

alter table content_archive alter column keywords nvarchar(max) null;

alter table content_archive alter column description nvarchar(max) null;

alter table content_archive alter column metainfo nvarchar(max) null;

alter table content_archive alter column doctype nvarchar(max) null;

alter table content_archive alter column htmlheader nvarchar(max) null;

alter table content_archive alter column htmlbodyonload nvarchar(max) null;

alter table content_archive alter column url nvarchar(max) null;

alter table content_archive alter column content nvarchar(max) null;

alter table content_archive alter column summary nvarchar(max) null;

alter table content_archive alter column users_users nvarchar(max) null;

alter table content_archive alter column developers_users nvarchar(max) null;

alter table content_archive alter column creators_users nvarchar(max) null;

alter table content_archive alter column editors_users nvarchar(max) null;

alter table content_archive alter column publishers_users nvarchar(max) null;

alter table content_archive alter column administrators_users nvarchar(max) null;

alter table content_archive alter column related nvarchar(max) null;

alter table content_archive alter column upload_filename nvarchar(max) null;

alter table content_archive alter column product_code nvarchar(max) null;

alter table content_archive alter column product_currency nvarchar(max) null;

alter table content_archive alter column product_price nvarchar(max) null;

alter table content_archive alter column product_period nvarchar(max) null;

alter table content_archive alter column product_weight nvarchar(max) null;

alter table content_archive alter column product_volume nvarchar(max) null;

alter table content_archive alter column product_width nvarchar(max) null;

alter table content_archive alter column product_height nvarchar(max) null;

alter table content_archive alter column product_depth nvarchar(max) null;

alter table content_archive alter column product_points_cost nvarchar(max) null;

alter table content_archive alter column product_points_reward nvarchar(max) null;

alter table content_archive alter column product_stock nvarchar(max) null;

alter table content_archive alter column product_cost nvarchar(max) null;

alter table content_archive alter column product_comment nvarchar(max) null;

alter table content_archive alter column product_url nvarchar(max) null;

alter table content_archive alter column product_info nvarchar(max) null;

alter table content_archive alter column product_delivery nvarchar(max) null;

alter table content_archive alter column product_user nvarchar(max) null;

alter table content_archive alter column product_program nvarchar(max) null;

alter table content_archive alter column product_hosting nvarchar(max) null;

alter table content_archive alter column product_options nvarchar(max) null;

alter table content_archive alter column product_content nvarchar(max) null;

alter table content_archive alter column product_contentgroup nvarchar(max) null;

alter table content_archive alter column product_contenttype nvarchar(max) null;

alter table content_archive alter column product_imagegroup nvarchar(max) null;

alter table content_archive alter column product_imagetype nvarchar(max) null;

alter table content_archive alter column product_filegroup nvarchar(max) null;

alter table content_archive alter column product_filetype nvarchar(max) null;

alter table content_archive alter column product_linkgroup nvarchar(max) null;

alter table content_archive alter column product_linktype nvarchar(max) null;

alter table content_archive alter column product_productgroup nvarchar(max) null;

alter table content_archive alter column product_producttype nvarchar(max) null;

alter table content_archive alter column product_usergroup nvarchar(max) null;

alter table content_archive alter column product_usertype nvarchar(max) null;

alter table content_archive alter column custom nvarchar(max) null;

alter table content_archive alter column futuretext1 nvarchar(max) null;

alter table content_archive alter column futuretext2 nvarchar(max) null;

alter table content_archive alter column futuretext3 nvarchar(max) null;



alter table content_public alter column revision nvarchar(max) null;

alter table content_public alter column history nvarchar(max) null;

alter table content_public alter column author nvarchar(max) null;

alter table content_public alter column keywords nvarchar(max) null;

alter table content_public alter column description nvarchar(max) null;

alter table content_public alter column metainfo nvarchar(max) null;

alter table content_public alter column doctype nvarchar(max) null;

alter table content_public alter column htmlheader nvarchar(max) null;

alter table content_public alter column htmlbodyonload nvarchar(max) null;

alter table content_public alter column url nvarchar(max) null;

alter table content_public alter column content nvarchar(max) null;

alter table content_public alter column summary nvarchar(max) null;

alter table content_public alter column users_users nvarchar(max) null;

alter table content_public alter column developers_users nvarchar(max) null;

alter table content_public alter column creators_users nvarchar(max) null;

alter table content_public alter column editors_users nvarchar(max) null;

alter table content_public alter column publishers_users nvarchar(max) null;

alter table content_public alter column administrators_users nvarchar(max) null;

alter table content_public alter column related nvarchar(max) null;

alter table content_public alter column upload_filename nvarchar(max) null;

alter table content_public alter column product_code nvarchar(max) null;

alter table content_public alter column product_currency nvarchar(max) null;

alter table content_public alter column product_price nvarchar(max) null;

alter table content_public alter column product_period nvarchar(max) null;

alter table content_public alter column product_weight nvarchar(max) null;

alter table content_public alter column product_volume nvarchar(max) null;

alter table content_public alter column product_width nvarchar(max) null;

alter table content_public alter column product_height nvarchar(max) null;

alter table content_public alter column product_depth nvarchar(max) null;

alter table content_public alter column product_points_cost nvarchar(max) null;

alter table content_public alter column product_points_reward nvarchar(max) null;

alter table content_public alter column product_stock nvarchar(max) null;

alter table content_public alter column product_cost nvarchar(max) null;

alter table content_public alter column product_comment nvarchar(max) null;

alter table content_public alter column product_url nvarchar(max) null;

alter table content_public alter column product_info nvarchar(max) null;

alter table content_public alter column product_delivery nvarchar(max) null;

alter table content_public alter column product_user nvarchar(max) null;

alter table content_public alter column product_program nvarchar(max) null;

alter table content_public alter column product_hosting nvarchar(max) null;

alter table content_public alter column product_options nvarchar(max) null;

alter table content_public alter column product_content nvarchar(max) null;

alter table content_public alter column product_contentgroup nvarchar(max) null;

alter table content_public alter column product_contenttype nvarchar(max) null;

alter table content_public alter column product_imagegroup nvarchar(max) null;

alter table content_public alter column product_imagetype nvarchar(max) null;

alter table content_public alter column product_filegroup nvarchar(max) null;

alter table content_public alter column product_filetype nvarchar(max) null;

alter table content_public alter column product_linkgroup nvarchar(max) null;

alter table content_public alter column product_linktype nvarchar(max) null;

alter table content_public alter column product_productgroup nvarchar(max) null;

alter table content_public alter column product_producttype nvarchar(max) null;

alter table content_public alter column product_usergroup nvarchar(max) null;

alter table content_public alter column product_usertype nvarchar(max) null;

alter table content_public alter column custom nvarchar(max) null;

alter table content_public alter column futuretext1 nvarchar(max) null;

alter table content_public alter column futuretext2 nvarchar(max) null;

alter table content_public alter column futuretext3 nvarchar(max) null;



alter table content_elements alter column element_params nvarchar(max) null;



alter table content_archive_elements alter column element_params nvarchar(max) null;



alter table content_public_elements alter column element_params nvarchar(max) null;



alter table contentgroups alter column users_users nvarchar(max) null;

alter table contentgroups alter column developers_users nvarchar(max) null;

alter table contentgroups alter column creators_users nvarchar(max) null;

alter table contentgroups alter column editors_users nvarchar(max) null;

alter table contentgroups alter column publishers_users nvarchar(max) null;

alter table contentgroups alter column administrators_users nvarchar(max) null;

alter table contentgroups alter column title_prefix nvarchar(max) null;

alter table contentgroups alter column title_suffix nvarchar(max) null;

alter table contentgroups alter column doctype nvarchar(max) null;



alter table contenttypes alter column users_users nvarchar(max) null;

alter table contenttypes alter column developers_users nvarchar(max) null;

alter table contenttypes alter column creators_users nvarchar(max) null;

alter table contenttypes alter column editors_users nvarchar(max) null;

alter table contenttypes alter column publishers_users nvarchar(max) null;

alter table contenttypes alter column administrators_users nvarchar(max) null;

alter table contenttypes alter column title_prefix nvarchar(max) null;

alter table contenttypes alter column title_suffix nvarchar(max) null;

alter table contenttypes alter column doctype nvarchar(max) null;



alter table productgroups alter column users_users nvarchar(max) null;

alter table productgroups alter column developers_users nvarchar(max) null;

alter table productgroups alter column creators_users nvarchar(max) null;

alter table productgroups alter column editors_users nvarchar(max) null;

alter table productgroups alter column publishers_users nvarchar(max) null;

alter table productgroups alter column administrators_users nvarchar(max) null;

alter table productgroups alter column title_prefix nvarchar(max) null;

alter table productgroups alter column title_suffix nvarchar(max) null;

alter table productgroups alter column doctype nvarchar(max) null;



alter table producttypes alter column users_users nvarchar(max) null;

alter table producttypes alter column developers_users nvarchar(max) null;

alter table producttypes alter column creators_users nvarchar(max) null;

alter table producttypes alter column editors_users nvarchar(max) null;

alter table producttypes alter column publishers_users nvarchar(max) null;

alter table producttypes alter column administrators_users nvarchar(max) null;

alter table producttypes alter column title_prefix nvarchar(max) null;

alter table producttypes alter column title_suffix nvarchar(max) null;

alter table producttypes alter column doctype nvarchar(max) null;



alter table elements alter column users_users nvarchar(max) null;

alter table elements alter column developers_users nvarchar(max) null;

alter table elements alter column creators_users nvarchar(max) null;

alter table elements alter column editors_users nvarchar(max) null;

alter table elements alter column publishers_users nvarchar(max) null;

alter table elements alter column administrators_users nvarchar(max) null;



alter table filegroups alter column users_users nvarchar(max) null;

alter table filegroups alter column developers_users nvarchar(max) null;

alter table filegroups alter column creators_users nvarchar(max) null;

alter table filegroups alter column editors_users nvarchar(max) null;

alter table filegroups alter column publishers_users nvarchar(max) null;

alter table filegroups alter column administrators_users nvarchar(max) null;



alter table filetypes alter column users_users nvarchar(max) null;

alter table filetypes alter column developers_users nvarchar(max) null;

alter table filetypes alter column creators_users nvarchar(max) null;

alter table filetypes alter column editors_users nvarchar(max) null;

alter table filetypes alter column publishers_users nvarchar(max) null;

alter table filetypes alter column administrators_users nvarchar(max) null;



alter table hostinggroups alter column users_users nvarchar(max) null;

alter table hostinggroups alter column developers_users nvarchar(max) null;

alter table hostinggroups alter column creators_users nvarchar(max) null;

alter table hostinggroups alter column editors_users nvarchar(max) null;

alter table hostinggroups alter column publishers_users nvarchar(max) null;

alter table hostinggroups alter column administrators_users nvarchar(max) null;



alter table hostingtypes alter column users_users nvarchar(max) null;

alter table hostingtypes alter column developers_users nvarchar(max) null;

alter table hostingtypes alter column creators_users nvarchar(max) null;

alter table hostingtypes alter column editors_users nvarchar(max) null;

alter table hostingtypes alter column publishers_users nvarchar(max) null;

alter table hostingtypes alter column administrators_users nvarchar(max) null;



alter table imagegroups alter column users_users nvarchar(max) null;

alter table imagegroups alter column developers_users nvarchar(max) null;

alter table imagegroups alter column creators_users nvarchar(max) null;

alter table imagegroups alter column editors_users nvarchar(max) null;

alter table imagegroups alter column publishers_users nvarchar(max) null;

alter table imagegroups alter column administrators_users nvarchar(max) null;



alter table imagetypes alter column users_users nvarchar(max) null;

alter table imagetypes alter column developers_users nvarchar(max) null;

alter table imagetypes alter column creators_users nvarchar(max) null;

alter table imagetypes alter column editors_users nvarchar(max) null;

alter table imagetypes alter column publishers_users nvarchar(max) null;

alter table imagetypes alter column administrators_users nvarchar(max) null;



alter table linkgroups alter column users_users nvarchar(max) null;

alter table linkgroups alter column developers_users nvarchar(max) null;

alter table linkgroups alter column creators_users nvarchar(max) null;

alter table linkgroups alter column editors_users nvarchar(max) null;

alter table linkgroups alter column publishers_users nvarchar(max) null;

alter table linkgroups alter column administrators_users nvarchar(max) null;



alter table linktypes alter column users_users nvarchar(max) null;

alter table linktypes alter column developers_users nvarchar(max) null;

alter table linktypes alter column creators_users nvarchar(max) null;

alter table linktypes alter column editors_users nvarchar(max) null;

alter table linktypes alter column publishers_users nvarchar(max) null;

alter table linktypes alter column administrators_users nvarchar(max) null;



alter table hosting alter column externalstorage nvarchar(max) null;

alter table hosting alter column history nvarchar(max) null;

alter table hosting alter column custom nvarchar(max) null;



alter table usergroups alter column users_users nvarchar(max) null;

alter table usergroups alter column creators_users nvarchar(max) null;

alter table usergroups alter column editors_users nvarchar(max) null;

alter table usergroups alter column publishers_users nvarchar(max) null;

alter table usergroups alter column administrators_users nvarchar(max) null;

alter table usergroups alter column subscribers_users nvarchar(max) null;



alter table usertypes alter column users_users nvarchar(max) null;

alter table usertypes alter column creators_users nvarchar(max) null;

alter table usertypes alter column editors_users nvarchar(max) null;

alter table usertypes alter column publishers_users nvarchar(max) null;

alter table usertypes alter column administrators_users nvarchar(max) null;

alter table usertypes alter column subscribers_users nvarchar(max) null;



alter table versions alter column default_price nvarchar(max) null;

alter table versions alter column users_users nvarchar(max) null;

alter table versions alter column developers_users nvarchar(max) null;

alter table versions alter column creators_users nvarchar(max) null;

alter table versions alter column editors_users nvarchar(max) null;

alter table versions alter column publishers_users nvarchar(max) null;

alter table versions alter column administrators_users nvarchar(max) null;



alter table websites alter column default_price nvarchar(max) null;

alter table websites alter column default_doctype nvarchar(max) null;



alter table workflow alter column usertype nvarchar(max) null;

alter table workflow alter column usergroup nvarchar(max) null;

alter table workflow alter column contentclass nvarchar(max) null;

alter table workflow alter column contenttype nvarchar(max) null;

alter table workflow alter column contentgroup nvarchar(max) null;

alter table workflow alter column version nvarchar(max) null;

alter table workflow alter column contentchanges nvarchar(max) null;

alter table workflow alter column workflow_program nvarchar(max) null;



alter table users alter column history nvarchar(max) null;

alter table users alter column keywords nvarchar(max) null;

alter table users alter column description nvarchar(max) null;

alter table users alter column organisation nvarchar(max) null;

alter table users alter column hardcore_onenter nvarchar(max) null;

alter table users alter column hardcore_onctrlenter nvarchar(max) null;

alter table users alter column hardcore_onshiftenter nvarchar(max) null;

alter table users alter column hardcore_onaltenter nvarchar(max) null;

alter table users alter column hardcore_toolbar1 nvarchar(max) null;

alter table users alter column hardcore_toolbar2 nvarchar(max) null;

alter table users alter column hardcore_toolbar3 nvarchar(max) null;

alter table users alter column hardcore_toolbar4 nvarchar(max) null;

alter table users alter column hardcore_toolbar5 nvarchar(max) null;

alter table users alter column hardcore_formatblock nvarchar(max) null;

alter table users alter column hardcore_fontname nvarchar(max) null;

alter table users alter column hardcore_fontsize nvarchar(max) null;

alter table users alter column hardcore_customscript nvarchar(max) null;

alter table users alter column card_type nvarchar(max) null;

alter table users alter column card_number nvarchar(max) null;

alter table users alter column card_issuedmonth nvarchar(max) null;

alter table users alter column card_issuedyear nvarchar(max) null;

alter table users alter column card_expirymonth nvarchar(max) null;

alter table users alter column card_expiryyear nvarchar(max) null;

alter table users alter column card_name nvarchar(max) null;

alter table users alter column card_cvc nvarchar(max) null;

alter table users alter column card_issue nvarchar(max) null;

alter table users alter column card_postalcode nvarchar(max) null;

alter table users alter column delivery_name nvarchar(max) null;

alter table users alter column delivery_organisation nvarchar(max) null;

alter table users alter column delivery_address nvarchar(max) null;

alter table users alter column delivery_postalcode nvarchar(max) null;

alter table users alter column delivery_city nvarchar(max) null;

alter table users alter column delivery_state nvarchar(max) null;

alter table users alter column delivery_country nvarchar(max) null;

alter table users alter column delivery_phone nvarchar(max) null;

alter table users alter column delivery_fax nvarchar(max) null;

alter table users alter column delivery_email nvarchar(max) null;

alter table users alter column delivery_website nvarchar(max) null;

alter table users alter column invoice_name nvarchar(max) null;

alter table users alter column invoice_organisation nvarchar(max) null;

alter table users alter column invoice_address nvarchar(max) null;

alter table users alter column invoice_postalcode nvarchar(max) null;

alter table users alter column invoice_city nvarchar(max) null;

alter table users alter column invoice_state nvarchar(max) null;

alter table users alter column invoice_country nvarchar(max) null;

alter table users alter column invoice_phone nvarchar(max) null;

alter table users alter column invoice_fax nvarchar(max) null;

alter table users alter column invoice_email nvarchar(max) null;

alter table users alter column invoice_website nvarchar(max) null;

alter table users alter column notes nvarchar(max) null;

alter table users alter column userinfo nvarchar(max) null;

alter table users alter column index_content nvarchar(max) null;

alter table users alter column index_library nvarchar(max) null;

alter table users alter column index_product nvarchar(max) null;

alter table users alter column index_order nvarchar(max) null;

alter table users alter column index_databases nvarchar(max) null;

alter table users alter column index_user nvarchar(max) null;

alter table users alter column index_hosting nvarchar(max) null;

alter table users alter column index_websites nvarchar(max) null;

alter table users alter column index_stock nvarchar(max) null;

alter table users alter column index_sales nvarchar(max) null;

alter table users alter column index_workspace nvarchar(max) null;

alter table users alter column menu_selection nvarchar(max) null;

alter table users alter column workspace_sections nvarchar(max) null;

alter table users alter column statistics_reports nvarchar(max) null;

alter table users alter column sales_reports nvarchar(max) null;

alter table users alter column shopcart nvarchar(max) null;

alter table users alter column wishlist nvarchar(max) null;

alter table users alter column custom nvarchar(max) null;



alter table guestbook alter column content nvarchar(max) null;



alter table currencies alter column rate nvarchar(max) null;



alter table orderitems alter column version nvarchar(max) null;

alter table orderitems alter column title nvarchar(max) null;

alter table orderitems alter column summary nvarchar(max) null;

alter table orderitems alter column author nvarchar(max) null;

alter table orderitems alter column keywords nvarchar(max) null;

alter table orderitems alter column description nvarchar(max) null;

alter table orderitems alter column product_code nvarchar(max) null;

alter table orderitems alter column product_currency nvarchar(max) null;

alter table orderitems alter column product_price nvarchar(max) null;

alter table orderitems alter column product_period nvarchar(max) null;

alter table orderitems alter column product_weight nvarchar(max) null;

alter table orderitems alter column product_volume nvarchar(max) null;

alter table orderitems alter column product_width nvarchar(max) null;

alter table orderitems alter column product_height nvarchar(max) null;

alter table orderitems alter column product_depth nvarchar(max) null;

alter table orderitems alter column product_brand nvarchar(max) null;

alter table orderitems alter column product_colour nvarchar(max) null;

alter table orderitems alter column product_size nvarchar(max) null;

alter table orderitems alter column product_points_cost nvarchar(max) null;

alter table orderitems alter column product_points_reward nvarchar(max) null;

alter table orderitems alter column product_stock nvarchar(max) null;

alter table orderitems alter column product_comment nvarchar(max) null;

alter table orderitems alter column product_url nvarchar(max) null;

alter table orderitems alter column product_delivery nvarchar(max) null;

alter table orderitems alter column product_user nvarchar(max) null;

alter table orderitems alter column product_program nvarchar(max) null;

alter table orderitems alter column product_hosting nvarchar(max) null;

alter table orderitems alter column product_options nvarchar(max) null;

alter table orderitems alter column product_content nvarchar(max) null;

alter table orderitems alter column product_contentgroup nvarchar(max) null;

alter table orderitems alter column product_contenttype nvarchar(max) null;

alter table orderitems alter column product_imagegroup nvarchar(max) null;

alter table orderitems alter column product_imagetype nvarchar(max) null;

alter table orderitems alter column product_filegroup nvarchar(max) null;

alter table orderitems alter column product_filetype nvarchar(max) null;

alter table orderitems alter column product_linkgroup nvarchar(max) null;

alter table orderitems alter column product_linktype nvarchar(max) null;

alter table orderitems alter column product_productgroup nvarchar(max) null;

alter table orderitems alter column product_producttype nvarchar(max) null;

alter table orderitems alter column product_usergroup nvarchar(max) null;

alter table orderitems alter column product_usertype nvarchar(max) null;

alter table orderitems alter column discount_description nvarchar(max) null;

alter table orderitems alter column shipping_description nvarchar(max) null;

alter table orderitems alter column tax_description nvarchar(max) null;



alter table orders alter column payment nvarchar(max) null;

alter table orders alter column revision nvarchar(max) null;

alter table orders alter column history nvarchar(max) null;

alter table orders alter column order_currency nvarchar(max) null;

alter table orders alter column order_currency_base nvarchar(max) null;

alter table orders alter column tax_description nvarchar(max) null;

alter table orders alter column shipping_description nvarchar(max) null;

alter table orders alter column discount_description nvarchar(max) null;

alter table orders alter column card_type nvarchar(max) null;

alter table orders alter column card_number nvarchar(max) null;

alter table orders alter column card_issuedmonth nvarchar(max) null;

alter table orders alter column card_issuedyear nvarchar(max) null;

alter table orders alter column card_expirymonth nvarchar(max) null;

alter table orders alter column card_expiryyear nvarchar(max) null;

alter table orders alter column card_name nvarchar(max) null;

alter table orders alter column card_cvc nvarchar(max) null;

alter table orders alter column card_issue nvarchar(max) null;

alter table orders alter column card_postalcode nvarchar(max) null;

alter table orders alter column delivery_name nvarchar(max) null;

alter table orders alter column delivery_organisation nvarchar(max) null;

alter table orders alter column delivery_address nvarchar(max) null;

alter table orders alter column delivery_postalcode nvarchar(max) null;

alter table orders alter column delivery_city nvarchar(max) null;

alter table orders alter column delivery_state nvarchar(max) null;

alter table orders alter column delivery_country nvarchar(max) null;

alter table orders alter column delivery_phone nvarchar(max) null;

alter table orders alter column delivery_fax nvarchar(max) null;

alter table orders alter column delivery_email nvarchar(max) null;

alter table orders alter column delivery_website nvarchar(max) null;

alter table orders alter column invoice_name nvarchar(max) null;

alter table orders alter column invoice_organisation nvarchar(max) null;

alter table orders alter column invoice_address nvarchar(max) null;

alter table orders alter column invoice_postalcode nvarchar(max) null;

alter table orders alter column invoice_city nvarchar(max) null;

alter table orders alter column invoice_state nvarchar(max) null;

alter table orders alter column invoice_country nvarchar(max) null;

alter table orders alter column invoice_phone nvarchar(max) null;

alter table orders alter column invoice_fax nvarchar(max) null;

alter table orders alter column invoice_email nvarchar(max) null;

alter table orders alter column invoice_website nvarchar(max) null;

alter table orders alter column custom nvarchar(max) null;



alter table data alter column content nvarchar(max) null;

alter table data alter column users_users nvarchar(max) null;

alter table data alter column creators_users nvarchar(max) null;

alter table data alter column editors_users nvarchar(max) null;

alter table data alter column publishers_users nvarchar(max) null;

alter table data alter column administrators_users nvarchar(max) null;

alter table data alter column adminindex nvarchar(max) null;

alter table data alter column custom nvarchar(max) null;









update config set configvalue=configvalue;



update content set revision=revision;

update content set history=history;

update content set author=author;

update content set keywords=keywords;

update content set description=description;

update content set metainfo=metainfo;

update content set doctype=doctype;

update content set htmlheader=htmlheader;

update content set htmlbodyonload=htmlbodyonload;

update content set url=url;

update content set content=content;

update content set summary=summary;

update content set users_users=users_users;

update content set developers_users=developers_users;

update content set creators_users=creators_users;

update content set editors_users=editors_users;

update content set publishers_users=publishers_users;

update content set administrators_users=administrators_users;

update content set related=related;

update content set upload_filename=upload_filename;

update content set product_code=product_code;

update content set product_currency=product_currency;

update content set product_price=product_price;

update content set product_period=product_period;

update content set product_weight=product_weight;

update content set product_volume=product_volume;

update content set product_width=product_width;

update content set product_height=product_height;

update content set product_depth=product_depth;

update content set product_points_cost=product_points_cost;

update content set product_points_reward=product_points_reward;

update content set product_stock=product_stock;

update content set product_cost=product_cost;

update content set product_comment=product_comment;

update content set product_url=product_url;

update content set product_info=product_info;

update content set product_delivery=product_delivery;

update content set product_user=product_user;

update content set product_program=product_program;

update content set product_hosting=product_hosting;

update content set product_options=product_options;

update content set product_content=product_content;

update content set product_contentgroup=product_contentgroup;

update content set product_contenttype=product_contenttype;

update content set product_imagegroup=product_imagegroup;

update content set product_imagetype=product_imagetype;

update content set product_filegroup=product_filegroup;

update content set product_filetype=product_filetype;

update content set product_linkgroup=product_linkgroup;

update content set product_linktype=product_linktype;

update content set product_productgroup=product_productgroup;

update content set product_producttype=product_producttype;

update content set product_usergroup=product_usergroup;

update content set product_usertype=product_usertype;

update content set custom=custom;

update content set futuretext1=futuretext1;

update content set futuretext2=futuretext2;

update content set futuretext3=futuretext3;



update content_archive set revision=revision;

update content_archive set history=history;

update content_archive set author=author;

update content_archive set keywords=keywords;

update content_archive set description=description;

update content_archive set metainfo=metainfo;

update content_archive set doctype=doctype;

update content_archive set htmlheader=htmlheader;

update content_archive set htmlbodyonload=htmlbodyonload;

update content_archive set url=url;

update content_archive set content=content;

update content_archive set summary=summary;

update content_archive set users_users=users_users;

update content_archive set developers_users=developers_users;

update content_archive set creators_users=creators_users;

update content_archive set editors_users=editors_users;

update content_archive set publishers_users=publishers_users;

update content_archive set administrators_users=administrators_users;

update content_archive set related=related;

update content_archive set upload_filename=upload_filename;

update content_archive set product_code=product_code;

update content_archive set product_currency=product_currency;

update content_archive set product_price=product_price;

update content_archive set product_period=product_period;

update content_archive set product_weight=product_weight;

update content_archive set product_volume=product_volume;

update content_archive set product_width=product_width;

update content_archive set product_height=product_height;

update content_archive set product_depth=product_depth;

update content_archive set product_points_cost=product_points_cost;

update content_archive set product_points_reward=product_points_reward;

update content_archive set product_stock=product_stock;

update content_archive set product_cost=product_cost;

update content_archive set product_comment=product_comment;

update content_archive set product_url=product_url;

update content_archive set product_info=product_info;

update content_archive set product_delivery=product_delivery;

update content_archive set product_user=product_user;

update content_archive set product_program=product_program;

update content_archive set product_hosting=product_hosting;

update content_archive set product_options=product_options;

update content_archive set product_content=product_content;

update content_archive set product_contentgroup=product_contentgroup;

update content_archive set product_contenttype=product_contenttype;

update content_archive set product_imagegroup=product_imagegroup;

update content_archive set product_imagetype=product_imagetype;

update content_archive set product_filegroup=product_filegroup;

update content_archive set product_filetype=product_filetype;

update content_archive set product_linkgroup=product_linkgroup;

update content_archive set product_linktype=product_linktype;

update content_archive set product_productgroup=product_productgroup;

update content_archive set product_producttype=product_producttype;

update content_archive set product_usergroup=product_usergroup;

update content_archive set product_usertype=product_usertype;

update content_archive set custom=custom;

update content_archive set futuretext1=futuretext1;

update content_archive set futuretext2=futuretext2;

update content_archive set futuretext3=futuretext3;



update content_public set revision=revision;

update content_public set history=history;

update content_public set author=author;

update content_public set keywords=keywords;

update content_public set description=description;

update content_public set metainfo=metainfo;

update content_public set doctype=doctype;

update content_public set htmlheader=htmlheader;

update content_public set htmlbodyonload=htmlbodyonload;

update content_public set url=url;

update content_public set content=content;

update content_public set summary=summary;

update content_public set users_users=users_users;

update content_public set developers_users=developers_users;

update content_public set creators_users=creators_users;

update content_public set editors_users=editors_users;

update content_public set publishers_users=publishers_users;

update content_public set administrators_users=administrators_users;

update content_public set related=related;

update content_public set upload_filename=upload_filename;

update content_public set product_code=product_code;

update content_public set product_currency=product_currency;

update content_public set product_price=product_price;

update content_public set product_period=product_period;

update content_public set product_weight=product_weight;

update content_public set product_volume=product_volume;

update content_public set product_width=product_width;

update content_public set product_height=product_height;

update content_public set product_depth=product_depth;

update content_public set product_points_cost=product_points_cost;

update content_public set product_points_reward=product_points_reward;

update content_public set product_stock=product_stock;

update content_public set product_cost=product_cost;

update content_public set product_comment=product_comment;

update content_public set product_url=product_url;

update content_public set product_info=product_info;

update content_public set product_delivery=product_delivery;

update content_public set product_user=product_user;

update content_public set product_program=product_program;

update content_public set product_hosting=product_hosting;

update content_public set product_options=product_options;

update content_public set product_content=product_content;

update content_public set product_contentgroup=product_contentgroup;

update content_public set product_contenttype=product_contenttype;

update content_public set product_imagegroup=product_imagegroup;

update content_public set product_imagetype=product_imagetype;

update content_public set product_filegroup=product_filegroup;

update content_public set product_filetype=product_filetype;

update content_public set product_linkgroup=product_linkgroup;

update content_public set product_linktype=product_linktype;

update content_public set product_productgroup=product_productgroup;

update content_public set product_producttype=product_producttype;

update content_public set product_usergroup=product_usergroup;

update content_public set product_usertype=product_usertype;

update content_public set custom=custom;

update content_public set futuretext1=futuretext1;

update content_public set futuretext2=futuretext2;

update content_public set futuretext3=futuretext3;



update content_elements set element_params=element_params;



update content_archive_elements set element_params=element_params;



update content_public_elements set element_params=element_params;



update contentgroups set users_users=users_users;

update contentgroups set developers_users=developers_users;

update contentgroups set creators_users=creators_users;

update contentgroups set editors_users=editors_users;

update contentgroups set publishers_users=publishers_users;

update contentgroups set administrators_users=administrators_users;

update contentgroups set title_prefix=title_prefix;

update contentgroups set title_suffix=title_suffix;

update contentgroups set doctype=doctype;



update contenttypes set users_users=users_users;

update contenttypes set developers_users=developers_users;

update contenttypes set creators_users=creators_users;

update contenttypes set editors_users=editors_users;

update contenttypes set publishers_users=publishers_users;

update contenttypes set administrators_users=administrators_users;

update contenttypes set title_prefix=title_prefix;

update contenttypes set title_suffix=title_suffix;

update contenttypes set doctype=doctype;



update productgroups set users_users=users_users;

update productgroups set developers_users=developers_users;

update productgroups set creators_users=creators_users;

update productgroups set editors_users=editors_users;

update productgroups set publishers_users=publishers_users;

update productgroups set administrators_users=administrators_users;

update productgroups set title_prefix=title_prefix;

update productgroups set title_suffix=title_suffix;

update productgroups set doctype=doctype;



update producttypes set users_users=users_users;

update producttypes set developers_users=developers_users;

update producttypes set creators_users=creators_users;

update producttypes set editors_users=editors_users;

update producttypes set publishers_users=publishers_users;

update producttypes set administrators_users=administrators_users;

update producttypes set title_prefix=title_prefix;

update producttypes set title_suffix=title_suffix;

update producttypes set doctype=doctype;



update elements set users_users=users_users;

update elements set developers_users=developers_users;

update elements set creators_users=creators_users;

update elements set editors_users=editors_users;

update elements set publishers_users=publishers_users;

update elements set administrators_users=administrators_users;



update filegroups set users_users=users_users;

update filegroups set developers_users=developers_users;

update filegroups set creators_users=creators_users;

update filegroups set editors_users=editors_users;

update filegroups set publishers_users=publishers_users;

update filegroups set administrators_users=administrators_users;



update filetypes set users_users=users_users;

update filetypes set developers_users=developers_users;

update filetypes set creators_users=creators_users;

update filetypes set editors_users=editors_users;

update filetypes set publishers_users=publishers_users;

update filetypes set administrators_users=administrators_users;



update hostinggroups set users_users=users_users;

update hostinggroups set developers_users=developers_users;

update hostinggroups set creators_users=creators_users;

update hostinggroups set editors_users=editors_users;

update hostinggroups set publishers_users=publishers_users;

update hostinggroups set administrators_users=administrators_users;



update hostingtypes set users_users=users_users;

update hostingtypes set developers_users=developers_users;

update hostingtypes set creators_users=creators_users;

update hostingtypes set editors_users=editors_users;

update hostingtypes set publishers_users=publishers_users;

update hostingtypes set administrators_users=administrators_users;



update imagegroups set users_users=users_users;

update imagegroups set developers_users=developers_users;

update imagegroups set creators_users=creators_users;

update imagegroups set editors_users=editors_users;

update imagegroups set publishers_users=publishers_users;

update imagegroups set administrators_users=administrators_users;



update imagetypes set users_users=users_users;

update imagetypes set developers_users=developers_users;

update imagetypes set creators_users=creators_users;

update imagetypes set editors_users=editors_users;

update imagetypes set publishers_users=publishers_users;

update imagetypes set administrators_users=administrators_users;



update linkgroups set users_users=users_users;

update linkgroups set developers_users=developers_users;

update linkgroups set creators_users=creators_users;

update linkgroups set editors_users=editors_users;

update linkgroups set publishers_users=publishers_users;

update linkgroups set administrators_users=administrators_users;



update linktypes set users_users=users_users;

update linktypes set developers_users=developers_users;

update linktypes set creators_users=creators_users;

update linktypes set editors_users=editors_users;

update linktypes set publishers_users=publishers_users;

update linktypes set administrators_users=administrators_users;



update hosting set externalstorage=externalstorage;

update hosting set history=history;

update hosting set custom=custom;



update usergroups set users_users=users_users;

update usergroups set creators_users=creators_users;

update usergroups set editors_users=editors_users;

update usergroups set publishers_users=publishers_users;

update usergroups set administrators_users=administrators_users;

update usergroups set subscribers_users=subscribers_users;



update usertypes set users_users=users_users;

update usertypes set creators_users=creators_users;

update usertypes set editors_users=editors_users;

update usertypes set publishers_users=publishers_users;

update usertypes set administrators_users=administrators_users;

update usertypes set subscribers_users=subscribers_users;



update versions set default_price=default_price;

update versions set users_users=users_users;

update versions set developers_users=developers_users;

update versions set creators_users=creators_users;

update versions set editors_users=editors_users;

update versions set publishers_users=publishers_users;

update versions set administrators_users=administrators_users;



update websites set default_price=default_price;

update websites set default_doctype=default_doctype;



update workflow set usertype=usertype;

update workflow set usergroup=usergroup;

update workflow set contentclass=contentclass;

update workflow set contenttype=contenttype;

update workflow set contentgroup=contentgroup;

update workflow set version=version;

update workflow set contentchanges=contentchanges;

update workflow set workflow_program=workflow_program;



update users set history=history;

update users set keywords=keywords;

update users set description=description;

update users set organisation=organisation;

update users set hardcore_onenter=hardcore_onenter;

update users set hardcore_onctrlenter=hardcore_onctrlenter;

update users set hardcore_onshiftenter=hardcore_onshiftenter;

update users set hardcore_onaltenter=hardcore_onaltenter;

update users set hardcore_toolbar1=hardcore_toolbar1;

update users set hardcore_toolbar2=hardcore_toolbar2;

update users set hardcore_toolbar3=hardcore_toolbar3;

update users set hardcore_toolbar4=hardcore_toolbar4;

update users set hardcore_toolbar5=hardcore_toolbar5;

update users set hardcore_formatblock=hardcore_formatblock;

update users set hardcore_fontname=hardcore_fontname;

update users set hardcore_fontsize=hardcore_fontsize;

update users set hardcore_customscript=hardcore_customscript;

update users set card_type=card_type;

update users set card_number=card_number;

update users set card_issuedmonth=card_issuedmonth;

update users set card_issuedyear=card_issuedyear;

update users set card_expirymonth=card_expirymonth;

update users set card_expiryyear=card_expiryyear;

update users set card_name=card_name;

update users set card_cvc=card_cvc;

update users set card_issue=card_issue;

update users set card_postalcode=card_postalcode;

update users set delivery_name=delivery_name;

update users set delivery_organisation=delivery_organisation;

update users set delivery_address=delivery_address;

update users set delivery_postalcode=delivery_postalcode;

update users set delivery_city=delivery_city;

update users set delivery_state=delivery_state;

update users set delivery_country=delivery_country;

update users set delivery_phone=delivery_phone;

update users set delivery_fax=delivery_fax;

update users set delivery_email=delivery_email;

update users set delivery_website=delivery_website;

update users set invoice_name=invoice_name;

update users set invoice_organisation=invoice_organisation;

update users set invoice_address=invoice_address;

update users set invoice_postalcode=invoice_postalcode;

update users set invoice_city=invoice_city;

update users set invoice_state=invoice_state;

update users set invoice_country=invoice_country;

update users set invoice_phone=invoice_phone;

update users set invoice_fax=invoice_fax;

update users set invoice_email=invoice_email;

update users set invoice_website=invoice_website;

update users set notes=notes;

update users set userinfo=userinfo;

update users set index_content=index_content;

update users set index_library=index_library;

update users set index_product=index_product;

update users set index_order=index_order;

update users set index_databases=index_databases;

update users set index_user=index_user;

update users set index_hosting=index_hosting;

update users set index_websites=index_websites;

update users set index_stock=index_stock;

update users set index_sales=index_sales;

update users set index_workspace=index_workspace;

update users set menu_selection=menu_selection;

update users set workspace_sections=workspace_sections;

update users set statistics_reports=statistics_reports;

update users set sales_reports=sales_reports;

update users set shopcart=shopcart;

update users set wishlist=wishlist;

update users set custom=custom;



update guestbook set content=content;



update currencies set rate=rate;



update orderitems set version=version;

update orderitems set title=title;

update orderitems set summary=summary;

update orderitems set author=author;

update orderitems set keywords=keywords;

update orderitems set description=description;

update orderitems set product_code=product_code;

update orderitems set product_currency=product_currency;

update orderitems set product_price=product_price;

update orderitems set product_period=product_period;

update orderitems set product_weight=product_weight;

update orderitems set product_volume=product_volume;

update orderitems set product_width=product_width;

update orderitems set product_height=product_height;

update orderitems set product_depth=product_depth;

update orderitems set product_brand=product_brand;

update orderitems set product_colour=product_colour;

update orderitems set product_size=product_size;

update orderitems set product_points_cost=product_points_cost;

update orderitems set product_points_reward=product_points_reward;

update orderitems set product_stock=product_stock;

update orderitems set product_comment=product_comment;

update orderitems set product_url=product_url;

update orderitems set product_delivery=product_delivery;

update orderitems set product_user=product_user;

update orderitems set product_program=product_program;

update orderitems set product_hosting=product_hosting;

update orderitems set product_options=product_options;

update orderitems set product_content=product_content;

update orderitems set product_contentgroup=product_contentgroup;

update orderitems set product_contenttype=product_contenttype;

update orderitems set product_imagegroup=product_imagegroup;

update orderitems set product_imagetype=product_imagetype;

update orderitems set product_filegroup=product_filegroup;

update orderitems set product_filetype=product_filetype;

update orderitems set product_linkgroup=product_linkgroup;

update orderitems set product_linktype=product_linktype;

update orderitems set product_productgroup=product_productgroup;

update orderitems set product_producttype=product_producttype;

update orderitems set product_usergroup=product_usergroup;

update orderitems set product_usertype=product_usertype;

update orderitems set discount_description=discount_description;

update orderitems set shipping_description=shipping_description;

update orderitems set tax_description=tax_description;



update orders set payment=payment;

update orders set revision=revision;

update orders set history=history;

update orders set order_currency=order_currency;

update orders set order_currency_base=order_currency_base;

update orders set tax_description=tax_description;

update orders set shipping_description=shipping_description;

update orders set discount_description=discount_description;

update orders set card_type=card_type;

update orders set card_number=card_number;

update orders set card_issuedmonth=card_issuedmonth;

update orders set card_issuedyear=card_issuedyear;

update orders set card_expirymonth=card_expirymonth;

update orders set card_expiryyear=card_expiryyear;

update orders set card_name=card_name;

update orders set card_cvc=card_cvc;

update orders set card_issue=card_issue;

update orders set card_postalcode=card_postalcode;

update orders set delivery_name=delivery_name;

update orders set delivery_organisation=delivery_organisation;

update orders set delivery_address=delivery_address;

update orders set delivery_postalcode=delivery_postalcode;

update orders set delivery_city=delivery_city;

update orders set delivery_state=delivery_state;

update orders set delivery_country=delivery_country;

update orders set delivery_phone=delivery_phone;

update orders set delivery_fax=delivery_fax;

update orders set delivery_email=delivery_email;

update orders set delivery_website=delivery_website;

update orders set invoice_name=invoice_name;

update orders set invoice_organisation=invoice_organisation;

update orders set invoice_address=invoice_address;

update orders set invoice_postalcode=invoice_postalcode;

update orders set invoice_city=invoice_city;

update orders set invoice_state=invoice_state;

update orders set invoice_country=invoice_country;

update orders set invoice_phone=invoice_phone;

update orders set invoice_fax=invoice_fax;

update orders set invoice_email=invoice_email;

update orders set invoice_website=invoice_website;

update orders set custom=custom;



update data set content=content;

update data set users_users=users_users;

update data set creators_users=creators_users;

update data set editors_users=editors_users;

update data set publishers_users=publishers_users;

update data set administrators_users=administrators_users;

update data set adminindex=adminindex;

update data set custom=custom;




QQQQQQQQQQQQQQQQQQQQQQQQQQ

alter table orders modify column clienthost nvarchar(250) null;

alter table usagelog modify column clienthost nvarchar(250) null;


alter table content modify column product_code nvarchar(255) null;

alter table content modify column product_sku nvarchar(255) null;

alter table content modify column product_location nvarchar(255) null;

alter table content modify column product_stock_text nvarchar(255) null;

alter table content modify column product_lowstock_text nvarchar(255) null;

alter table content modify column product_nostock_text nvarchar(255) null;

alter table content modify column product_price nvarchar(255) null;

alter table content modify column product_currency nvarchar(255) null;

alter table content modify column product_price nvarchar(255) null;

alter table content modify column product_period nvarchar(255) null;

alter table content modify column product_weight nvarchar(255) null;

alter table content modify column product_volume nvarchar(255) null;

alter table content modify column product_width nvarchar(255) null;

alter table content modify column product_height nvarchar(255) null;

alter table content modify column product_depth nvarchar(255) null;

alter table content modify column product_points_cost nvarchar(255) null;

alter table content modify column product_points_reward nvarchar(255) null;

alter table content modify column product_stock nvarchar(255) null;

alter table content modify column product_cost nvarchar(255) null;


alter table content_archive modify column product_code nvarchar(255) null;

alter table content_archive modify column product_sku nvarchar(255) null;

alter table content_archive modify column product_location nvarchar(255) null;

alter table content_archive modify column product_stock_text nvarchar(255) null;

alter table content_archive modify column product_lowstock_text nvarchar(255) null;

alter table content_archive modify column product_nostock_text nvarchar(255) null;

alter table content_archive modify column product_price nvarchar(255) null;

alter table content_archive modify column product_currency nvarchar(255) null;

alter table content_archive modify column product_price nvarchar(255) null;

alter table content_archive modify column product_period nvarchar(255) null;

alter table content_archive modify column product_weight nvarchar(255) null;

alter table content_archive modify column product_volume nvarchar(255) null;

alter table content_archive modify column product_width nvarchar(255) null;

alter table content_archive modify column product_height nvarchar(255) null;

alter table content_archive modify column product_depth nvarchar(255) null;

alter table content_archive modify column product_points_cost nvarchar(255) null;

alter table content_archive modify column product_points_reward nvarchar(255) null;

alter table content_archive modify column product_stock nvarchar(255) null;

alter table content_archive modify column product_cost nvarchar(255) null;


alter table content_public modify column product_code nvarchar(255) null;

alter table content_public modify column product_sku nvarchar(255) null;

alter table content_public modify column product_location nvarchar(255) null;

alter table content_public modify column product_stock_text nvarchar(255) null;

alter table content_public modify column product_lowstock_text nvarchar(255) null;

alter table content_public modify column product_nostock_text nvarchar(255) null;

alter table content_public modify column product_price nvarchar(255) null;

alter table content_public modify column product_currency nvarchar(255) null;

alter table content_public modify column product_price nvarchar(255) null;

alter table content_public modify column product_period nvarchar(255) null;

alter table content_public modify column product_weight nvarchar(255) null;

alter table content_public modify column product_volume nvarchar(255) null;

alter table content_public modify column product_width nvarchar(255) null;

alter table content_public modify column product_height nvarchar(255) null;

alter table content_public modify column product_depth nvarchar(255) null;

alter table content_public modify column product_points_cost nvarchar(255) null;

alter table content_public modify column product_points_reward nvarchar(255) null;

alter table content_public modify column product_stock nvarchar(255) null;

alter table content_public modify column product_cost nvarchar(255) null;



alter table orderitems modify column product_code nvarchar(255) null;

alter table orderitems modify column product_sku nvarchar(255) null;

alter table orderitems modify column product_currency nvarchar(255) null;

alter table orderitems modify column product_price nvarchar(255) null;

alter table orderitems modify column product_period nvarchar(255) null;

alter table orderitems modify column product_weight nvarchar(255) null;

alter table orderitems modify column product_volume nvarchar(255) null;

alter table orderitems modify column product_width nvarchar(255) null;

alter table orderitems modify column product_height nvarchar(255) null;

alter table orderitems modify column product_depth nvarchar(255) null;

alter table orderitems modify column product_brand nvarchar(255) null;

alter table orderitems modify column product_colour nvarchar(255) null;

alter table orderitems modify column product_size nvarchar(255) null;

alter table orderitems modify column product_points_cost nvarchar(255) null;

alter table orderitems modify column product_points_reward nvarchar(255) null;

alter table orderitems modify column product_stock nvarchar(255) null;



alter table orders modify column card_type nvarchar(255) null;

alter table orders modify column card_number nvarchar(255) null;

alter table orders modify column card_issuedmonth nvarchar(255) null;

alter table orders modify column card_issuedyear nvarchar(255) null;

alter table orders modify column card_expirymonth nvarchar(255) null;

alter table orders modify column card_expiryyear nvarchar(255) null;

alter table orders modify column card_name nvarchar(255) null;

alter table orders modify column card_cvc nvarchar(255) null;

alter table orders modify column card_issue nvarchar(255) null;

alter table orders modify column card_postalcode nvarchar(255) null;

alter table orders modify column delivery_name nvarchar(255) null;

alter table orders modify column delivery_organisation nvarchar(255) null;

alter table orders modify column delivery_postalcode nvarchar(255) null;

alter table orders modify column delivery_city nvarchar(255) null;

alter table orders modify column delivery_state nvarchar(255) null;

alter table orders modify column delivery_country nvarchar(255) null;

alter table orders modify column delivery_phone nvarchar(255) null;

alter table orders modify column delivery_fax nvarchar(255) null;

alter table orders modify column delivery_email nvarchar(255) null;

alter table orders modify column delivery_website nvarchar(255) null;

alter table orders modify column invoice_name nvarchar(255) null;

alter table orders modify column invoice_organisation nvarchar(255) null;

alter table orders modify column invoice_postalcode nvarchar(255) null;

alter table orders modify column invoice_city nvarchar(255) null;

alter table orders modify column invoice_state nvarchar(255) null;

alter table orders modify column invoice_country nvarchar(255) null;

alter table orders modify column invoice_phone nvarchar(255) null;

alter table orders modify column invoice_fax nvarchar(255) null;

alter table orders modify column invoice_email nvarchar(255) null;

alter table orders modify column invoice_website nvarchar(255) null;



alter table users modify column card_type nvarchar(255) null;

alter table users modify column card_number nvarchar(255) null;

alter table users modify column card_issuedmonth nvarchar(255) null;

alter table users modify column card_issuedyear nvarchar(255) null;

alter table users modify column card_expirymonth nvarchar(255) null;

alter table users modify column card_expiryyear nvarchar(255) null;

alter table users modify column card_name nvarchar(255) null;

alter table users modify column card_cvc nvarchar(255) null;

alter table users modify column card_issue nvarchar(255) null;

alter table users modify column card_postalcode nvarchar(255) null;

alter table users modify column delivery_name nvarchar(255) null;

alter table users modify column delivery_organisation nvarchar(255) null;

alter table users modify column delivery_postalcode nvarchar(255) null;

alter table users modify column delivery_city nvarchar(255) null;

alter table users modify column delivery_state nvarchar(255) null;

alter table users modify column delivery_country nvarchar(255) null;

alter table users modify column delivery_phone nvarchar(255) null;

alter table users modify column delivery_fax nvarchar(255) null;

alter table users modify column delivery_email nvarchar(255) null;

alter table users modify column delivery_website nvarchar(255) null;

alter table users modify column invoice_name nvarchar(255) null;

alter table users modify column invoice_organisation nvarchar(255) null;

alter table users modify column invoice_postalcode nvarchar(255) null;

alter table users modify column invoice_city nvarchar(255) null;

alter table users modify column invoice_state nvarchar(255) null;

alter table users modify column invoice_country nvarchar(255) null;

alter table users modify column invoice_phone nvarchar(255) null;

alter table users modify column invoice_fax nvarchar(255) null;

alter table users modify column invoice_email nvarchar(255) null;

alter table users modify column invoice_website nvarchar(255) null;



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



alter table content add device nvarchar(255) null;

alter table content_archive add device nvarchar(255) null;

alter table content_public add device nvarchar(255) null;

create index c_device_idx on content (device);

create index ca_device_idx on content_archive (device);

create index cp_device_idx on content_public (device);

update content set device='' where device is null;

update content_archive set device='' where device is null;

update content_public set device='' where device is null;



alter table orderitems add device nvarchar(255) null;

update orderitems set device='' where device is null;

alter table orderitems add usersegment nvarchar(255) null;

update orderitems set usersegment='' where usersegment is null;

alter table orderitems add usertest nvarchar(255) null;

update orderitems set usertest='' where usertest is null;



alter table content add htmlattributes nvarchar(max) null;

alter table content_archive add htmlattributes nvarchar(max) null;

alter table content_public add htmlattributes nvarchar(max) null;

update content set htmlattributes='' where htmlattributes is null;

update content_archive set htmlattributes='' where htmlattributes is null;

update content_public set htmlattributes='' where htmlattributes is null;



alter table orders add clientdevice nvarchar(20) null;

alter table orders add clientdeviceid nvarchar(20) null;

alter table orders add clientdeviceversion nvarchar(50) null;

update orders set clientdevice='' where clientdevice is null;

update orders set clientdeviceid='' where clientdeviceid is null;

update orders set clientdeviceversion='' where clientdeviceversion is null;

alter table usagelog add clientdevice nvarchar(20) null;

alter table usagelog add clientdeviceid nvarchar(20) null;

alter table usagelog add clientdeviceversion nvarchar(50) null;

update usagelog set clientdevice='' where clientdevice is null;

update usagelog set clientdeviceid='' where clientdeviceid is null;

update usagelog set clientdeviceversion='' where clientdeviceversion is null;

create index u_clientdevice_idx on usagelog (clientdevice);

create index u_clientdeviceid_idx on usagelog (clientdeviceid);

create index u_clientdevicev_idx on usagelog (clientdeviceversion);



alter table users add birthdate nvarchar(10) null;

!update users set birthdate=birthday where birthdate is null;

!update users set birthday='' where birthdate=birthday;

alter table users add birthyear nvarchar(4) null;

alter table users add birthmonth nvarchar(2) null;

!alter table users add birthday nvarchar(2) null;

!alter table users modify column birthday nvarchar(2) null;

alter table users add gender nvarchar(1) null;

alter table users add title nvarchar(255) null;

update users set birthdate='' where birthdate is null;

update users set birthyear='' where birthyear is null;

update users set birthmonth='' where birthmonth is null;

update users set birthday='' where birthday is null;

update users set gender='' where gender is null;

update users set title='' where title is null;



alter table users add index_segments nvarchar(max) null;

alter table users add index_usertests nvarchar(max) null;

alter table users add index_heatmaps nvarchar(max) null;

update users set index_segments='' where index_segments is null;

update users set index_usertests='' where index_usertests is null;

update users set index_heatmaps='' where index_heatmaps is null;

alter table users add index_search nvarchar(max) null;

alter table users add index_searchadvanced nvarchar(max) null;

alter table users add index_searchreplace nvarchar(max) null;

update users set index_search='' where index_search is null;

update users set index_searchadvanced='' where index_searchadvanced is null;

update users set index_searchreplace='' where index_searchreplace is null;



alter table hosting add experience_license nvarchar(255) null;

update hosting set experience_license='' where experience_license is null;



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
	primary key (id)
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
	primary key (id)
);

create index usertests_usertest on usertests (usertest);



alter table content add usersegment nvarchar(255) null;

alter table content_archive add usersegment nvarchar(255) null;

alter table content_public add usersegment nvarchar(255) null;

create index c_usersegment on content (usersegment);

create index ca_usersegment on content_archive (usersegment);

create index cp_usersegment on content_public (usersegment);

update content set usersegment = '' where usersegment is null;

update content_archive set usersegment = '' where usersegment is null;

update content_public set usersegment = '' where usersegment is null;



alter table content add usertest nvarchar(255) null;

alter table content_archive add usertest nvarchar(255) null;

alter table content_public add usertest nvarchar(255) null;

create index c_usertest on content (usertest);

create index ca_usertest on content_archive (usertest);

create index cp_usertest on content_public (usertest);

update content set usertest = '' where usertest is null;

update content_archive set usertest = '' where usertest is null;

update content_public set usertest = '' where usertest is null;



alter table content add heatmap nvarchar(50) null;

alter table content add heatmapalign nvarchar(1) null;

alter table content_archive add heatmap nvarchar(50) null;

alter table content_archive add heatmapalign nvarchar(1) null;

alter table content_public add heatmap nvarchar(50) null;

alter table content_public add heatmapalign nvarchar(1) null;

create index c_heatmap on content (heatmap);

create index ca_heatmap on content_archive (heatmap);

create index cp_heatmap on content_public (heatmap);

update content set heatmap = '' where heatmap is null;

update content_archive set heatmap = '' where heatmap is null;

update content_public set heatmap = '' where heatmap is null;



alter table content add usagelog nvarchar(1) null;

alter table content_archive add usagelog nvarchar(1) null;

alter table content_public add usagelog nvarchar(1) null;

create index c_usagelog on content (usagelog);

create index ca_usagelog on content_archive (usagelog);

create index cp_usagelog on content_public (usagelog);

update content set usagelog = '' where usagelog is null;

update content_archive set usagelog = '' where usagelog is null;

update content_public set usagelog = '' where usagelog is null;



alter table content add segmentation nvarchar(max) null;

alter table content_archive add segmentation nvarchar(max) null;

alter table content_public add segmentation nvarchar(max) null;

update content set segmentation = '' where segmentation is null;

update content_archive set segmentation = '' where segmentation is null;

update content_public set segmentation = '' where segmentation is null;



alter table content_archive add archived nvarchar(20) null;

alter table content_archive add archived_by nvarchar(255) null;

update content set archived = '' where archived is null;

update content set archived_by = '' where archived_by is null;

create index ca_archived_idx on content_archive (archived);

create index ca_archived_by_idx on content_archive (archived_by);



alter table orders add usersegments nvarchar(max) null;

alter table orders add usertests nvarchar(max) null;

update orders set usersegments='' where usersegments is null;

update orders set usertests='' where usertests is null;

alter table usagelog add usersegments nvarchar(max) null;

alter table usagelog add usertests nvarchar(max) null;

update usagelog set usersegments='' where usersegments is null;

update usagelog set usertests='' where usertests is null;



alter table orders add visitorid nvarchar(50) null;

alter table orders add visitorduration int;

alter table usagelog add visitorid nvarchar(50) null;

alter table usagelog add visitorduration int;

create index u_visitorid_idx on usagelog (visitorid);

create index u_visitorduration_idx on usagelog (visitorduration);

alter table usagelog add pageviews int;

alter table usagelog add visits int;

alter table usagelog add visitors int;

alter table usagelog add clienthosts int;

alter table usagelog add clienthostids nvarchar(max) null;

alter table usagelog add visitorids nvarchar(max) null;

alter table usagelog add sessionids nvarchar(max) null;

alter table usagelog add summarised nvarchar(250) null;

create index u_summarised on usagelog (summarised);



alter table usagelog add affiliate nvarchar(250) null;

update usagelog set affiliate='' where affiliate is null;

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

create index heatmap_id_idx on heatmaps (id);

create index heatmap_logged_idx on heatmaps (logged);

create index heatmap_action_idx on heatmaps (action);



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
	primary key (report,report_limit,report_period,period_start,period_end)
);

create index udata_title on usagedata (title);

create index udata_report on usagedata (report);

create index udata_limit on usagedata (report_limit);

create index udata_period on usagedata (report_period);

create index udata_start on usagedata (period_start);

create index udata_end on usagedata (period_end);



update config set configvalue='9.0' where configname='database_version';

