
alter table content add product_content text

alter table content add product_contentgroup text

alter table content add product_contenttype text

alter table content add product_imagegroup text

alter table content add product_imagetype text

alter table content add product_filegroup text

alter table content add product_filetype text

alter table content add product_linkgroup text

alter table content add product_linktype text

alter table content add product_productgroup text

alter table content add product_producttype text

alter table content add product_usergroup text

alter table content add product_usertype text


alter table content_archive add product_content text

alter table content_archive add product_contentgroup text

alter table content_archive add product_contenttype text

alter table content_archive add product_imagegroup text

alter table content_archive add product_imagetype text

alter table content_archive add product_filegroup text

alter table content_archive add product_filetype text

alter table content_archive add product_linkgroup text

alter table content_archive add product_linktype text

alter table content_archive add product_productgroup text

alter table content_archive add product_producttype text

alter table content_archive add product_usergroup text

alter table content_archive add product_usertype text


alter table content_public add product_content text

alter table content_public add product_contentgroup text

alter table content_public add product_contenttype text

alter table content_public add product_imagegroup text

alter table content_public add product_imagetype text

alter table content_public add product_filegroup text

alter table content_public add product_filetype text

alter table content_public add product_linkgroup text

alter table content_public add product_linktype text

alter table content_public add product_productgroup text

alter table content_public add product_producttype text

alter table content_public add product_usergroup text

alter table content_public add product_usertype text


alter table orderitems add product_content text

alter table orderitems add product_contentgroup text

alter table orderitems add product_contenttype text

alter table orderitems add product_imagegroup text

alter table orderitems add product_imagetype text

alter table orderitems add product_filegroup text

alter table orderitems add product_filetype text

alter table orderitems add product_linkgroup text

alter table orderitems add product_linktype text

alter table orderitems add product_productgroup text

alter table orderitems add product_producttype text

alter table orderitems add product_usergroup text

alter table orderitems add product_usertype text


alter table contentgroups add title_prefix text

alter table contentgroups add title_suffix text

alter table contenttypes add title_prefix text

alter table contenttypes add title_suffix text

alter table productgroups add title_prefix text

alter table productgroups add title_suffix text

alter table producttypes add title_prefix text

alter table producttypes add title_suffix text


alter table users add failed int

update users set failed = 0 where failed is null


update config set configvalue='7.2' where configname='database_version'

