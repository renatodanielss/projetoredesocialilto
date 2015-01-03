
alter table content add product_content clob

alter table content add product_contentgroup clob

alter table content add product_contenttype clob

alter table content add product_imagegroup clob

alter table content add product_imagetype clob

alter table content add product_filegroup clob

alter table content add product_filetype clob

alter table content add product_linkgroup clob

alter table content add product_linktype clob

alter table content add product_productgroup clob

alter table content add product_producttype clob

alter table content add product_usergroup clob

alter table content add product_usertype clob


alter table content_archive add product_content clob

alter table content_archive add product_contentgroup clob

alter table content_archive add product_contenttype clob

alter table content_archive add product_imagegroup clob

alter table content_archive add product_imagetype clob

alter table content_archive add product_filegroup clob

alter table content_archive add product_filetype clob

alter table content_archive add product_linkgroup clob

alter table content_archive add product_linktype clob

alter table content_archive add product_productgroup clob

alter table content_archive add product_producttype clob

alter table content_archive add product_usergroup clob

alter table content_archive add product_usertype clob


alter table content_public add product_content clob

alter table content_public add product_contentgroup clob

alter table content_public add product_contenttype clob

alter table content_public add product_imagegroup clob

alter table content_public add product_imagetype clob

alter table content_public add product_filegroup clob

alter table content_public add product_filetype clob

alter table content_public add product_linkgroup clob

alter table content_public add product_linktype clob

alter table content_public add product_productgroup clob

alter table content_public add product_producttype clob

alter table content_public add product_usergroup clob

alter table content_public add product_usertype clob


alter table orderitems add product_content clob

alter table orderitems add product_contentgroup clob

alter table orderitems add product_contenttype clob

alter table orderitems add product_imagegroup clob

alter table orderitems add product_imagetype clob

alter table orderitems add product_filegroup clob

alter table orderitems add product_filetype clob

alter table orderitems add product_linkgroup clob

alter table orderitems add product_linktype clob

alter table orderitems add product_productgroup clob

alter table orderitems add product_producttype clob

alter table orderitems add product_usergroup clob

alter table orderitems add product_usertype clob


alter table contentgroups add title_prefix clob

alter table contentgroups add title_suffix clob

alter table contenttypes add title_prefix clob

alter table contenttypes add title_suffix clob

alter table productgroups add title_prefix clob

alter table productgroups add title_suffix clob

alter table producttypes add title_prefix clob

alter table producttypes add title_suffix clob


alter table users add failed int

update users set failed = 0 where failed is null


update config set configvalue='7.2' where configname='database_version'

