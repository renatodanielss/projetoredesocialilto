
#The following database commands recreates a number of casts and functions which may have been removed from the PostgreSQL v8.3 and newer database server versions. The database commands may give a number of "already exists" error messages if the casts and functions already exists (if they have already been recreated or if you are using an older PostgreSQL database server version) but such error messages can simply be ignored.

!create function pg_catalog.text(integer) returns text strict immutable language sql as 'select textin(int4out($1));';

!create cast (integer as text) with function pg_catalog.text(integer) as implicit;

!create function pg_catalog.text(smallint) returns text strict immutable language sql as 'select textin(int2out($1));';

!create cast (smallint as text) with function pg_catalog.text(smallint) as implicit;

!create function pg_catalog.text(oid) returns text strict immutable language sql as 'select textin(oidout($1));';

!create cast (oid as text) with function pg_catalog.text(oid) as implicit;

!create function pg_catalog.text(date) returns text strict immutable language sql as 'select textin(date_out($1));';

!create cast (date as text) with function pg_catalog.text(date) as implicit;

!create function pg_catalog.text(double precision) returns text strict immutable language sql as 'select textin(float8out($1));';

!create cast (double precision as text) with function pg_catalog.text(double precision) as implicit;

!create function pg_catalog.text(real) returns text strict immutable language sql as 'select textin(float4out($1));';

!create cast (real as text) with function pg_catalog.text(real) as implicit;

!create function pg_catalog.text(time with time zone) returns text strict immutable language sql as 'select textin(timetz_out($1));';

!create cast (time with time zone as text) with function pg_catalog.text(time with time zone) as implicit;

!create function pg_catalog.text(time without time zone) returns text strict immutable language sql as 'select textin(time_out($1));';

!create cast (time without time zone as text) with function pg_catalog.text(time without time zone) as implicit;

!create function pg_catalog.text(timestamp with time zone) returns text strict immutable language sql as 'select textin(timestamptz_out($1));';

!create cast (timestamp with time zone as text) with function pg_catalog.text(timestamp with time zone) as implicit;

!create function pg_catalog.text(interval) returns text strict immutable language sql as 'select textin(interval_out($1));';

!create cast (interval as text) with function pg_catalog.text(interval) as implicit;

!create function pg_catalog.text(bigint) returns text strict immutable language sql as 'select textin(int8out($1));';

!create cast (bigint as text) with function pg_catalog.text(bigint) as implicit;

!create function pg_catalog.text(numeric) returns text strict immutable language sql as 'select textin(numeric_out($1));';

!create cast (numeric as text) with function pg_catalog.text(numeric) as implicit;

!create function pg_catalog.text(timestamp without time zone) returns text strict immutable language sql as 'select textin(timestamp_out($1));';

!create cast (timestamp without time zone as text) with function pg_catalog.text(timestamp without time zone) as implicit;

