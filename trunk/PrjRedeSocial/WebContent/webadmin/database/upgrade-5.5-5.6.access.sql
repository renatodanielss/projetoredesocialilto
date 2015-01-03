create table usagelog (
	"datetime" varchar(20),
	datetimeyear integer,
	datetimemonth integer,
	datetimeday integer,
	datetimeweek integer,
	datetimeweekday integer,
	datetimehour integer,
	datetimemin integer,
	datetimesec integer,
	clienthost varchar(50),
	clienthosttld varchar(10),
	clientuseragent varchar(250),
	clientos varchar(10),
	clientosversion varchar(20),
	clientbrowser varchar(20),
	clientversion varchar(20),
	clientusername varchar(50),
	sessionid varchar(20),
	sessionduration integer,
	requesthost varchar(50),
	requestpath varchar(50),
	requestquery varchar(250),
	requestid integer,
	requestclass varchar(10),
	requestdatabase varchar(50),
	refererid integer,
	refererclass varchar(10),
	refererdatabase varchar(50),
	refererduration integer,
	refererhost varchar(50),
	refererpath varchar(250),
	refererquery varchar(250),
	referersearchengine varchar(50),
	referersearchquery varchar(250)
)

create index u_datetime_idx on usagelog (datetime)

create index u_datetimeyear_idx on usagelog (datetimeyear)

create index u_datetimemonth_idx on usagelog (datetimemonth)

create index u_datetimeday_idx on usagelog (datetimeday)

create index u_datetimeweek_idx on usagelog (datetimeweek)

create index u_datetimeweekday_idx on usagelog (datetimeweekday)

create index u_datetimehour_idx on usagelog (datetimehour)

create index u_clienthost_idx on usagelog (clienthost)

create index u_clienthosttld_idx on usagelog (clienthosttld)

create index u_clientos_idx on usagelog (clientos)

create index u_clientosversion_idx on usagelog (clientosversion)

create index u_clientbrowser_idx on usagelog (clientbrowser)

create index u_clientversion_idx on usagelog (clientversion)

create index u_clientusername_idx on usagelog (clientusername)

create index u_sessionid_idx on usagelog (sessionid)

create index u_sessionduration_idx on usagelog (sessionduration)

create index u_requesthost_idx on usagelog (requesthost)

create index u_requestid_idx on usagelog (requestid)

create index u_requestclass_idx on usagelog (requestclass)

create index u_requestdatabase_idx on usagelog (requestdatabase)

create index u_refererid_idx on usagelog (refererid)

create index u_refererclass_idx on usagelog (refererclass)

create index u_refererdatabase_idx on usagelog (refererdatabase)

create index u_refererduration_idx on usagelog (refererduration)

create index u_refererhost_idx on usagelog (refererhost)

create index u_refererpath_idx on usagelog (refererpath)

create index u_referersearchengine_idx on usagelog (referersearchengine)

create index u_referersearchquery_idx on usagelog (referersearchquery)



update config set configvalue='5.6' where configname='database_version'

