/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.golfcartbooking.dataaccess.IMemberDataAccess;
import com.golfcartbooking.dataaccess.MemberSQLDataAccess;
import com.golfcartbooking.util.Utils;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.Map;

public class MemberRequestHandler implements RequestHandler<Map<String, String>, Boolean> {

    @Override
    public Boolean handleRequest(Map<String, String> event, Context context)
    {
        Boolean result = false;
        LambdaLogger logger = context.getLogger();
        Gson gsonObj = Utils.getGsonWithFormatters();
        try {
            IMemberDataAccess memberDataAccess = new MemberSQLDataAccess();
            result =  memberDataAccess.isMember(event.get("fullName"), event.get("membershipId"));
            return result;
        } catch (SQLException e) {
            logger.log(gsonObj.toJson(e));
            return false;
        }
    }
}
