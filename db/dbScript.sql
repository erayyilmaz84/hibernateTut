--Postgre user, db and table create scripts with pgAdmin4

CREATE ROLE apptestuser1 WITH
  LOGIN
  NOSUPERUSER
  INHERIT
  NOCREATEDB
  NOCREATEROLE
  NOREPLICATION
  PASSWORD 'p4ssw0rd';

CREATE DATABASE testdb
    WITH
    OWNER = apptestuser1
    ENCODING = 'UTF8'
    LC_COLLATE = 'Turkish_Turkey.1254'
    LC_CTYPE = 'Turkish_Turkey.1254'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE IF NOT EXISTS public.customer
(
    first_name character varying(30),
    last_name character varying(30) ,
    customer_id integer NOT NULL,
    CONSTRAINT customer_pkey PRIMARY KEY (cust_id)
)

ALTER TABLE public.customer  OWNER to apptestuser1;

COMMENT ON TABLE public.customer  IS 'hibernate sample';
