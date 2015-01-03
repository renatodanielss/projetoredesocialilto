create table usagelog (
	datetime varchar(20),
	datetimeyear int,
	datetimemonth int,
	datetimeday int,
	datetimeweek int,
	datetimeweekday int,
	datetimehour int,
	datetimemin int,
	datetimesec int,
	clienthost varchar(50),
	clienthosttld varchar(10),
	clientuseragent varchar(250),
	clientos varchar(10),
	clientosversion varchar(20),
	clientbrowser varchar(20),
	clientversion varchar(20),
	clientusername varchar(50),
	sessionid varchar(20),
	sessionduration int,
	requesthost varchar(50),
	requestpath varchar(50),
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
	referersearchquery varchar(250)
)

create index u_date_idx on usagelog (datetime)

create index u_dateyear_idx on usagelog (datetimeyear)

create index u_datemonth_idx on usagelog (datetimemonth)

create index u_dateday_idx on usagelog (datetimeday)

create index u_dateweek_idx on usagelog (datetimeweek)

create index u_dateweekday_idx on usagelog (datetimeweekday)

create index u_datehour_idx on usagelog (datetimehour)

create index u_clihost_idx on usagelog (clienthost)

create index u_clihosttld_idx on usagelog (clienthosttld)

create index u_clios_idx on usagelog (clientos)

create index u_cliosversion_idx on usagelog (clientosversion)

create index u_clibrowser_idx on usagelog (clientbrowser)

create index u_cliversion_idx on usagelog (clientversion)

create index u_cliusername_idx on usagelog (clientusername)

create index u_sesid_idx on usagelog (sessionid)

create index u_sesduration_idx on usagelog (sessionduration)

create index u_reqhost_idx on usagelog (requesthost)

create index u_reqid_idx on usagelog (requestid)

create index u_reqclass_idx on usagelog (requestclass)

create index u_reqdatabase_idx on usagelog (requestdatabase)

create index u_refid_idx on usagelog (refererid)

create index u_refclass_idx on usagelog (refererclass)

create index u_refdatabase_idx on usagelog (refererdatabase)

create index u_refduration_idx on usagelog (refererduration)

create index u_refhost_idx on usagelog (refererhost)

create index u_refpath_idx on usagelog (refererpath)

create index u_refseaengine_idx on usagelog (referersearchengine)

create index u_refseaquery_idx on usagelog (referersearchquery)



update config set configvalue='5.6' where configname='database_version'

