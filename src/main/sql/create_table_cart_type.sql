-- Table: golf_cart_booking.cart_type

-- DROP TABLE golf_cart_booking.cart_type;

CREATE TABLE golf_cart_booking.cart_type (
    id SERIAL,
    number INTEGER NOT NULL,
    manufacturer VARCHAR(100) NOT NULL,
    fuel_type VARCHAR(50) NOT NULL,
    passenger_count INTEGER NOT NULL,
    rate DECIMAL(3,2) NOT NULL,
CONSTRAINT cart_type_pkey PRIMARY KEY (id)
)
