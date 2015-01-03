alter table content add	product_period text

alter table content_archive add product_period text

alter table content_public add product_period text

alter table orderitems add product_period text

alter table orders add card_issue text

alter table orders add delivery_organisation text

alter table orders add delivery_website text

alter table orders add invoice_organisation text

alter table orders add invoice_website text

alter table users add organisation text

alter table users add delivery_organisation text

alter table users add delivery_website text

alter table users add invoice_organisation text

alter table users add invoice_website text

update config set configvalue='6.3' where configname='database_version'

