
alter table content add product_content text null

alter table content add product_contentgroup text null

alter table content add product_contenttype text null

alter table content add product_imagegroup text null

alter table content add product_imagetype text null

alter table content add product_filegroup text null

alter table content add product_filetype text null

alter table content add product_linkgroup text null

alter table content add product_linktype text null

alter table content add product_productgroup text null

alter table content add product_producttype text null

alter table content add product_usergroup text null

alter table content add product_usertype text null


alter table content_archive add product_content text null

alter table content_archive add product_contentgroup text null

alter table content_archive add product_contenttype text null

alter table content_archive add product_imagegroup text null

alter table content_archive add product_imagetype text null

alter table content_archive add product_filegroup text null

alter table content_archive add product_filetype text null

alter table content_archive add product_linkgroup text null

alter table content_archive add product_linktype text null

alter table content_archive add product_productgroup text null

alter table content_archive add product_producttype text null

alter table content_archive add product_usergroup text null

alter table content_archive add product_usertype text null


alter table content_public add product_content text null

alter table content_public add product_contentgroup text null

alter table content_public add product_contenttype text null

alter table content_public add product_imagegroup text null

alter table content_public add product_imagetype text null

alter table content_public add product_filegroup text null

alter table content_public add product_filetype text null

alter table content_public add product_linkgroup text null

alter table content_public add product_linktype text null

alter table content_public add product_productgroup text null

alter table content_public add product_producttype text null

alter table content_public add product_usergroup text null

alter table content_public add product_usertype text null


alter table orderitems add product_content text null

alter table orderitems add product_contentgroup text null

alter table orderitems add product_contenttype text null

alter table orderitems add product_imagegroup text null

alter table orderitems add product_imagetype text null

alter table orderitems add product_filegroup text null

alter table orderitems add product_filetype text null

alter table orderitems add product_linkgroup text null

alter table orderitems add product_linktype text null

alter table orderitems add product_productgroup text null

alter table orderitems add product_producttype text null

alter table orderitems add product_usergroup text null

alter table orderitems add product_usertype text null


alter table contentgroups add title_prefix text null

alter table contentgroups add title_suffix text null

alter table contenttypes add title_prefix text null

alter table contenttypes add title_suffix text null

alter table productgroups add title_prefix text null

alter table productgroups add title_suffix text null

alter table producttypes add title_prefix text null

alter table producttypes add title_suffix text null


alter table users add failed int

update users set failed = 0 where failed is null


update config set configvalue='7.2' where configname='database_version'

