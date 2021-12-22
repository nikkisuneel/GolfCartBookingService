-- A script to create test data to populate the bookings table

DELETE FROM golf_cart_booking.booking;

INSERT INTO golf_cart_booking.booking
    (membership_id, booking_date, cart_id, tee_time, number_of_rounds, player_count)
SELECT
    'z1000',
	d at time zone 'PST' booking_date,
	(array[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20])[floor(random() * 20 + 1)] cart_id,
	date_trunc('day', d) + '10:00:00' tee_time,
	floor(random() * 3 + 1) number_of_rounds,
	floor(random() * 4 + 1) player_count
FROM generate_series(
  current_timestamp - interval '1 year',
  current_timestamp,
  '1 day'
) d
WHERE CAST(EXTRACT(DAY FROM date_trunc('day', d)) as INTEGER) % 3 = 0;

--
-- Update charge
--

UPDATE golf_cart_booking.booking b
SET charge = (
    SELECT c.rate * b.number_of_rounds + (c.additional_passenger_surcharge * b.number_of_rounds)
    FROM golf_cart_booking.cart_type c
    WHERE c.id = b.cart_id
);