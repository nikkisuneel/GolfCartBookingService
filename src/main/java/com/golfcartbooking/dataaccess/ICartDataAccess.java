/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.dataaccess;

import com.golfcartbooking.pojo.Cart;

import java.sql.SQLException;

/*
 * An interface that defines the methods to access Cart information from storage
 */
public interface ICartDataAccess {
    Cart getCart(int id) throws IllegalArgumentException, SQLException;

    Cart getCartByNumber(int cartNumber) throws IllegalArgumentException, SQLException;
}
