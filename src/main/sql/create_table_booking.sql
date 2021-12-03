-- Table: golf_cart_booking.booking

-- DROP TABLE golf_cart_booking.booking;

CREATE TABLE golf_cart_booking.booking (
    id SERIAL,
    membership_id varchar(50) NOT NULL,
    booking_date TIMESTAMP NOT NULL,
    cart_id INTEGER NOT NULL,
    tee_time TIMESTAMP NOT NULL,
    duration DECIMAl(3,1) NOT NULL,
    player_count INTEGER NOT NULL,
    charge DECIMAL(4,2)
CONSTRAINT booking_pkey PRIMARY KEY (id)
)
