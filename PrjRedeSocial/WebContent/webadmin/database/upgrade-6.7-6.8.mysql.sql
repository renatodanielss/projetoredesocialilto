alter table users add userinfo text

alter table websites add referrer varchar(255)

alter table websites add keywords varchar(255)

create index w_referrer_idx on websites (referrer)

create index w_keywords_idx on websites (keywords)

update config set configvalue='6.8' where configname='database_version'

