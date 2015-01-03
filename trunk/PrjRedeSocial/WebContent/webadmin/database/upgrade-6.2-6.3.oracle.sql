alter table content add	product_period clob

alter table content_archive add product_period clob

alter table content_public add product_period clob

alter table orderitems add product_period clob

alter table orders add card_issue clob

alter table orders add delivery_organisation clob

alter table orders add delivery_website clob

alter table orders add invoice_organisation clob

alter table orders add invoice_website clob

alter table users add organisation clob

alter table users add delivery_organisation clob

alter table users add delivery_website clob

alter table users add invoice_organisation clob

alter table users add invoice_website clob

update config set configvalue='6.3' where configname='database_version'

