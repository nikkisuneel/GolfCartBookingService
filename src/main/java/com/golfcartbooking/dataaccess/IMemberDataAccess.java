/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.dataaccess;

import com.golfcartbooking.pojo.Member;

import java.sql.SQLException;

public interface IMemberDataAccess {
    Member get(String membershipId) throws IllegalArgumentException, SQLException;

    boolean isMember(String fullName, String membershipId, String phoneNumber)
            throws IllegalArgumentException, SQLException;
}
