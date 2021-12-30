-- Table: golf_cart_rental.rental

--DROP TABLE golf_cart_rental.rental;

-- SEQUENCE: golf_cart_rental.rental_id_seq

--DROP SEQUENCE golf_cart_rental.rental_id_seq;

CREATE SEQUENCE golf_cart_rental.rental_id_seq
INCREMENT 1
START 1
MINVALUE 1
MAXVALUE 2147483647
CACHE 1;

ALTER SEQUENCE golf_cart_rental.rental_id_seq
OWNER TO postgres;

CREATE TABLE golf_cart_rental.rental
(
    id integer NOT NULL DEFAULT nextval('golf_cart_rental.rental_id_seq'::regclass),
    membership_id character varying(50) COLLATE pg_catalog."default" NOT NULL,
    rental_date timestamp without time zone NOT NULL,
    cart_id integer NOT NULL,
    tee_time timestamp without time zone NOT NULL,
    number_of_rounds integer NOT NULL,
    player_count integer NOT NULL,
    charge numeric(8,2),
    CONSTRAINT rental_pkey PRIMARY KEY (id),
    CONSTRAINT "rental_cart_FK" FOREIGN KEY (cart_id)
        REFERENCES golf_cart_rental.cart (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT rental_member_FK FOREIGN KEY (membership_id)
        REFERENCES golf_cart_rental.member (membership_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE golf_cart_rental.rental
    OWNER to postgres;
-- Index: fki_rental_cart_FK

-- DROP INDEX golf_cart_rental."fki_rental_cart_FK";

CREATE INDEX "fki_rental_cart_FK"
    ON golf_cart_rental.rental USING btree
    (cart_id ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: fki_rental_member_fk

-- DROP INDEX golf_cart_rental.fki_rental_member_fk;

CREATE INDEX fki_rental_member_FK
    ON golf_cart_rental.rental USING btree
    (membership_id COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;