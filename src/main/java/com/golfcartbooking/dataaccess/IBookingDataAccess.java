/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.dataaccess;

import com.golfcartbooking.pojo.Booking;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/*
 * An interface that defines the methods to access Ball Picking Activity data from storage
 */
public interface IBookingDataAccess {
    void create(Booking booking) throws IllegalArgumentException, SQLException;

    Booking getBookingByBookingDate(LocalDateTime bookingDate, String membershipId)
            throws IllegalArgumentException, SQLException;

    List<Booking> getAllBookings(String membershipId)
            throws IllegalArgumentException, SQLException;
}
