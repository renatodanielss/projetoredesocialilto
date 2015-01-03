alter table content add	product_period memo

alter table content_archive add product_period memo

alter table content_public add product_period memo

alter table orderitems add product_period memo

alter table orders add card_issue memo

alter table orders add delivery_organisation memo

alter table orders add delivery_website memo

alter table orders add invoice_organisation memo

alter table orders add invoice_website memo

alter table users add organisation memo

alter table users add delivery_organisation memo

alter table users add delivery_website memo

alter table users add invoice_organisation memo

alter table users add invoice_website memo

update config set configvalue='6.3' where configname='database_version'

