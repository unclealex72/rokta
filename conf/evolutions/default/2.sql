# Date and time functions

# --- !Ups

CREATE OR REPLACE FUNCTION year("when" timestamp without time zone)
  RETURNS integer AS
'select cast(extract(year from $1) as integer);;'
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION year(timestamp without time zone)
  OWNER TO rokta;
CREATE OR REPLACE FUNCTION month("when" timestamp without time zone)
  RETURNS integer AS
'select cast(extract(month from $1) as integer);;'
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION month(timestamp without time zone)
  OWNER TO rokta;
CREATE OR REPLACE FUNCTION week("when" timestamp without time zone)
  RETURNS integer AS
'select cast(extract(week from $1) as integer);;'
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION week(timestamp without time zone)
  OWNER TO rokta;
CREATE OR REPLACE FUNCTION day_of_month("when" timestamp without time zone)
  RETURNS integer AS
'select cast(extract(day from $1) as integer);;'
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION day_of_month(timestamp without time zone)
  OWNER TO rokta;

# --- !Downs
DROP FUNCTION year("when" timestamp without time zone);
DROP FUNCTION month("when" timestamp without time zone);
DROP FUNCTION day_of_month("when" timestamp without time zone);
DROP FUNCTION week("when" timestamp without time zone);