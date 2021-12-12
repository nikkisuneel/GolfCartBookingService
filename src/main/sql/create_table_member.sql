-- Table: golf_cart_booking.member

 DROP TABLE golf_cart_booking.member;

 SEQUENCE: golf_cart_booking.member_id_seq

 DROP SEQUENCE golf_cart_booking.member_id_seq;

 CREATE SEQUENCE golf_cart_booking.member_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

 ALTER SEQUENCE golf_cart_booking.member_id_seq
    OWNER TO postgres;

CREATE TABLE golf_cart_booking.member
(
    id integer NOT NULL DEFAULT nextval('golf_cart_booking.member_id_seq'::regclass),
    full_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    phone character varying(50) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    membership_id character varying(50) COLLATE pg_catalog."default" NOT NULL,
    membership_type character varying(50) COLLATE pg_catalog."default" NOT NULL,
    member_since timestamp without time zone NOT NULL,
    CONSTRAINT member_pkey PRIMARY KEY (id),
    CONSTRAINT membership_id_uc UNIQUE (membership_id)
)

TABLESPACE pg_default;

ALTER TABLE golf_cart_booking.member
    OWNER to postgres;