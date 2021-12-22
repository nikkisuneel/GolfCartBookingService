/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.dataaccess;

import com.golfcartbooking.pojo.Booking;
import com.golfcartbooking.util.DBConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*
 * An implementation of the IBallPickingActivityDataAccess interface for activity data access from SQL
 */
public class BookingSQLDataAccess implements IBookingDataAccess {

    @Override
    public void create(Booking booking) throws IllegalArgumentException, SQLException {
        if (booking == null) {
            throw new IllegalArgumentException("booking cannot be null");
        }

        Connection conn = DBConnectionManager.dbConnection;
        String insertStatement = "INSERT INTO golf_cart_booking.booking" +
                " (membership_id, booking_date, cart_id, tee_time, number_of_rounds, player_count, charge)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(insertStatement);
        stmt.setString(1, booking.getMembershipId());
        stmt.setTimestamp(2, Timestamp.valueOf(booking.getBookingDate()));
        stmt.setInt(3, booking.getCartId());
        stmt.setTimestamp(4, Timestamp.valueOf(booking.getTeeTime()));
        stmt.setInt(5, booking.getNumberOfRounds());
        stmt.setInt(6, booking.getPlayerCount());
        stmt.setDouble(7, booking.getCharge());

        stmt.executeUpdate();
    }

    @Override
    public Booking getBookingByBookingDate(LocalDateTime bookingDate, String membershipId)
            throws IllegalArgumentException, SQLException {
        Booking result = new Booking();

        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT id, membership_id, booking_date, cart_id, tee_time, number_of_rounds, " +
                "player_count, charge" +
                " FROM golf_cart_booking.booking" +
                " WHERE booking_date = ?" +
                " AND membership_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setTimestamp(1, Timestamp.valueOf(bookingDate));
        stmt.setString(2, membershipId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            result.setId(rs.getInt(1));
            result.setMembershipId(rs.getString(2));
            result.setBookingDate(rs.getTimestamp(3).toLocalDateTime());
            result.setCartId(rs.getInt(4));
            result.setTeeTime(rs.getTimestamp(5).toLocalDateTime());
            result.setNumberOfRounds(rs.getInt(6));
            result.setPlayerCount(rs.getInt(7));
            result.setCharge(rs.getDouble(8));
        } else {
            throw new IllegalArgumentException("no booking found for " + bookingDate.format(DateTimeFormatter.ISO_DATE));
        }

        return result;
    }

    @Override
    public List<Booking> getAllBookings(String membershipId)
            throws IllegalArgumentException, SQLException {
        List<Booking> result = new ArrayList<>();

        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT b.id, b.membership_id, b.booking_date, c.number, b.tee_time, b.number_of_rounds," +
                " player_count, charge" +
                " FROM golf_cart_booking.booking b, golf_cart_booking.cart_type c" +
                " WHERE b.membership_id = ?" +
                " AND c.id = b.cart_id";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, membershipId);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            Booking p = new Booking(rs.getInt(1),
                    rs.getString(2),
                    rs.getTimestamp(3).toLocalDateTime(),
                    rs.getInt(4),
                    rs.getTimestamp(5).toLocalDateTime(),
                    rs.getInt(6),
                    rs.getInt(7),
                    rs.getDouble(8)
            );

            result.add(p);
        }

        return result;
    }
}
