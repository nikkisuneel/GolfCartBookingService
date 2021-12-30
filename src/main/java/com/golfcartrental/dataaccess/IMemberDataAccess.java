/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartrental.dataaccess;

import com.golfcartrental.pojo.Member;

import java.sql.SQLException;

public interface IMemberDataAccess {
    Member get(String membershipId) throws IllegalArgumentException, SQLException;

    boolean isMember(String fullName, String membershipId, String phoneNumber)
            throws IllegalArgumentException, SQLException;
}
