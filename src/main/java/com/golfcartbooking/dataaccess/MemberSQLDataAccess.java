/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.dataaccess;

import com.golfcartbooking.pojo.Member;
import com.golfcartbooking.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberSQLDataAccess implements IMemberDataAccess{

    @Override
    public Member get(String membershipId) throws IllegalArgumentException, SQLException {
        Member result = new Member();

        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT id, full_name, phone, email, membership_id, membership_type, member_since" +
                " FROM golf_cart_booking.member" +
                " WHERE membership_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, membershipId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            result.setId(rs.getInt(1));
            result.setFullName(rs.getString(2));
            result.setPhone(rs.getString(3));
            result.setEmail(rs.getString(4));
            result.setMembershipId(rs.getString(5));
            result.setMembershipType(rs.getString(6));
            result.setMemberSince(rs.getTimestamp(7).toLocalDateTime());
        } else {
            throw new IllegalArgumentException("no cart found for " + membershipId);
        }

        return result;
    }

    @Override
    public boolean isMember(String fullName, String membershipId)
            throws IllegalArgumentException, SQLException {
        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT id, full_name, phone, email, membership_id, membership_type, member_since" +
                " FROM golf_cart_booking.member" +
                " WHERE membership_id = ?" +
                " AND full_name = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, membershipId);
        stmt.setString(2, fullName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return true;
        } else {
            return false;
        }
    }
}
