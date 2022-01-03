-- A script to create test data to populate the cart_rentals table

DELETE FROM golf_cart_rental.rental;

INSERT INTO golf_cart_rental.rental
    (membership_id, rental_date, cart_id, tee_time, number_of_rounds, player_count)
SELECT
    'z1000',
	d at time zone 'PST' rental_date,
	floor(random() * 41) cart_id,
	date_trunc('day', d) + '10:00:00' tee_time,
	floor(random() * 3 + 1) number_of_rounds,
	floor(random() * 2 + 1) player_count
FROM generate_series(
  current_timestamp - interval '1 year',
  current_timestamp,
  '1 day'
) d
WHERE CAST(EXTRACT(DAY FROM date_trunc('day', d)) as INTEGER) % 3 = 0;

--
-- Update charge
--

UPDATE golf_cart_rental.rental b
SET charge = (
    SELECT ct.rate * b.number_of_rounds + (ct.additional_passenger_surcharge * b.number_of_rounds)
    FROM golf_cart_rental.cart c, golf_cart_rental.cart_type ct
    WHERE c.id = b.cart_id
      AND c.cart_type_id = ct.id
);