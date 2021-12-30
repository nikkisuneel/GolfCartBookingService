/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartrental.dataaccess;

import com.golfcartrental.pojo.CartRental;
import com.golfcartrental.util.DBConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*
 * An implementation of the IBallPickingActivityDataAccess interface for activity data access from SQL
 */
public class CartRentalSQLDataAccess implements ICartRentalDataAccess {

    @Override
    public void create(CartRental cartRental) throws IllegalArgumentException, SQLException {
        if (cartRental == null) {
            throw new IllegalArgumentException("cartRental cannot be null");
        }

        Connection conn = DBConnectionManager.dbConnection;
        String insertStatement = "INSERT INTO golf_cart_rental.rental" +
                " (membership_id, rental_date, cart_id, tee_time, number_of_rounds, player_count, charge)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(insertStatement);
        stmt.setString(1, cartRental.getMembershipId());
        stmt.setTimestamp(2, Timestamp.valueOf(cartRental.getRentalDate()));
        stmt.setInt(3, cartRental.getCartId());
        stmt.setTimestamp(4, Timestamp.valueOf(cartRental.getTeeTime()));
        stmt.setInt(5, cartRental.getNumberOfRounds());
        stmt.setInt(6, cartRental.getPlayerCount());
        stmt.setDouble(7, cartRental.getCharge());

        stmt.executeUpdate();
    }

    @Override
    public CartRental getCartRentalByRentalDate(LocalDateTime rentalDate, String membershipId)
            throws IllegalArgumentException, SQLException {
        CartRental result = new CartRental();

        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT id, membership_id, rental_date, cart_id, tee_time, number_of_rounds, " +
                "player_count, charge" +
                " FROM golf_cart_rental.rental" +
                " WHERE rental_date = ?" +
                " AND membership_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setTimestamp(1, Timestamp.valueOf(rentalDate));
        stmt.setString(2, membershipId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            result.setId(rs.getInt(1));
            result.setMembershipId(rs.getString(2));
            result.setRentalDate(rs.getTimestamp(3).toLocalDateTime());
            result.setCartId(rs.getInt(4));
            result.setTeeTime(rs.getTimestamp(5).toLocalDateTime());
            result.setNumberOfRounds(rs.getInt(6));
            result.setPlayerCount(rs.getInt(7));
            result.setCharge(rs.getDouble(8));
        } else {
            throw new IllegalArgumentException("no rentals found for " + rentalDate.format(DateTimeFormatter.ISO_DATE));
        }

        return result;
    }

    @Override
    public List<CartRental> getAllCartRentals(String membershipId)
            throws IllegalArgumentException, SQLException {
        List<CartRental> result = new ArrayList<>();

        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT r.id, r.membership_id, r.rental_date, c.number, r.tee_time, r.number_of_rounds," +
                " r.player_count, r.charge" +
                " FROM golf_cart_rental.rental r, golf_cart_rental.cart c" +
                " WHERE r.membership_id = ?" +
                " AND c.id = r.cart_id";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, membershipId);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            CartRental p = new CartRental(rs.getInt(1),
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
