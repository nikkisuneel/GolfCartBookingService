/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartrental.dataaccess;

import com.golfcartrental.pojo.CartRental;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/*
 * An interface that defines the methods to access Ball Picking Activity data from storage
 */
public interface ICartRentalDataAccess {
    void create(CartRental cartRental) throws IllegalArgumentException, SQLException;

    CartRental getCartRentalByRentalDate(LocalDateTime rentalDate, String membershipId)
            throws IllegalArgumentException, SQLException;

    List<CartRental> getAllCartRentals(String membershipId)
            throws IllegalArgumentException, SQLException;
}
