-- Table: golf_cart_rental.cart

-- DROP TABLE golf_cart_rental.cart CASCADE;

-- DROP SEQUENCE golf_cart_rental.cart_id_seq;

 CREATE SEQUENCE golf_cart_rental.cart_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

 ALTER SEQUENCE golf_cart_rental.cart_id_seq
    OWNER TO postgres;

CREATE TABLE golf_cart_rental.cart
(
    id integer NOT NULL DEFAULT nextval('golf_cart_rental.cart_id_seq'::regclass),
    "number" integer NOT NULL,
    manufacturer character varying(100) COLLATE pg_catalog."default" NOT NULL,
    fuel_type character varying(50) COLLATE pg_catalog."default" NOT NULL,
    passenger_count integer NOT NULL,
    rate numeric(4,2) NOT NULL,
    additional_passenger_surcharge numeric(4,2) NOT NULL,
    CONSTRAINT cart_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE golf_cart_rental.cart
    OWNER to postgres;