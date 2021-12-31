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
    cart_type_id integer NOT NULL,
    CONSTRAINT cart_pkey PRIMARY KEY (id),
    CONSTRAINT "cart_cart_type_FK" FOREIGN KEY (cart_type_id)
            REFERENCES golf_cart_rental.cart_type (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
            NOT VALID,
)

TABLESPACE pg_default;

ALTER TABLE golf_cart_rental.cart
    OWNER to postgres;

-- Index: fki_cart_cart_type_FK

-- DROP INDEX golf_cart_rental."fki_cart_cart_type_FK";

CREATE INDEX "fki_cart_cart_type_FK"
    ON golf_cart_rental.cart USING btree
    (cart_type_id ASC NULLS LAST)
    TABLESPACE pg_default;