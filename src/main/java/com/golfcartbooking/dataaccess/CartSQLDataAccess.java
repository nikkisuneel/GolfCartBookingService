/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.golfcartbooking.pojo.Cart;
import com.golfcartbooking.util.DBConnectionManager;

/*
 * An implementation of the ICartDataAccess interface for Cart data access from SQL
 */
public class CartSQLDataAccess implements ICartDataAccess {

    @Override
    public Cart getCart(int id) throws IllegalArgumentException, SQLException {
        Cart result = new Cart();

        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT id, number, manufacturer, fuel_type, passenger_count, rate, " +
                "additional_passenger_surcharge" +
                " FROM golf_cart_booking.cart_type" +
                " WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            result.setId(rs.getInt(1));
            result.setNumber(rs.getInt(2));
            result.setManufacturer(rs.getString(3));
            result.setFuelType(rs.getString(4));
            result.setPassengerCount(rs.getInt(5));
            result.setRate(rs.getDouble(6));
            result.setAdditionalPassengerSurcharge(rs.getDouble(7));
        } else {
            throw new IllegalArgumentException("no cart found for " + id);
        }

        return result;
    }

    @Override
    public Cart getCartByNumber(int number) throws IllegalArgumentException, SQLException {
        Cart result = new Cart();

        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT id, number, manufacturer, fuel_type, passenger_count, rate, " +
                "additional_passenger_surcharge" +
                " FROM golf_cart_booking.cart_type" +
                " WHERE number = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, number);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            result.setId(rs.getInt(1));
            result.setNumber(rs.getInt(2));
            result.setManufacturer(rs.getString(3));
            result.setFuelType(rs.getString(4));
            result.setPassengerCount(rs.getInt(5));
            result.setRate(rs.getDouble(6));
            result.setAdditionalPassengerSurcharge(rs.getDouble(7));
        }

        return result;
    }
}
