-- Table: golf_cart_booking.member

-- DROP TABLE golf_cart_booking.member;

CREATE TABLE golf_cart_booking.member (
    id SERIAL,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    membership_id VARCHAR(50) NOT NULL,
    membership_type VARCHAR(50) NOT NULL,
    members_since TIMESTAMP NOT NULL
CONSTRAINT member_pkey PRIMARY KEY (id)
)
