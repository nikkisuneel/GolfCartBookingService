/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartrental.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/*
 * A class that returns a database connection to a Postgres database
 */
public class DBConnectionManager {
    public static Connection dbConnection;
    private static Logger logger = Logger.getLogger("DBConnectionManager");

    static {
        try {
            Class.forName("org.postgresql.Driver");
            String dbName = "golf_cart_rental";
            String userName = "*******";
            String password = "*******";
            String hostname = "golf-cart-booking.ch1tjgsx0t79.us-east-1.rds.amazonaws.com";
            String port = "5432";
            String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?user="
                    + userName + "&password=" + password;
            logger.info("Getting remote connection with connection string.");
            dbConnection = DriverManager.getConnection(jdbcUrl);
            logger.info("Remote connection successful.");
        }
        catch (ClassNotFoundException e) {
            logger.warn(e.toString());}
        catch (SQLException e) {
            logger.warn(Utils.exceptionStacktraceToString(e));
        }
    }
}
