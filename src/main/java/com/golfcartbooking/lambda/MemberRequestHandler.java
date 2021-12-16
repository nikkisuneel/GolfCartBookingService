/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.CognitoUserPoolPreSignUpEvent;
import com.golfcartbooking.dataaccess.IMemberDataAccess;
import com.golfcartbooking.dataaccess.MemberSQLDataAccess;
import com.golfcartbooking.util.Utils;
import com.google.gson.Gson;

import java.sql.SQLException;

public class MemberRequestHandler implements RequestHandler<CognitoUserPoolPreSignUpEvent, CognitoUserPoolPreSignUpEvent> {

    @Override
    public CognitoUserPoolPreSignUpEvent handleRequest(CognitoUserPoolPreSignUpEvent event, Context context)
    {
        Boolean result = false;
        LambdaLogger logger = context.getLogger();
        Gson gsonObj = Utils.getGsonWithFormatters();
        event.getResponse().setAutoConfirmUser(false);

        try {
            IMemberDataAccess memberDataAccess = new MemberSQLDataAccess();
            String fullName = event.getRequest().getUserAttributes().get("name");
            String membershipId = event.getRequest().getUserAttributes().get("custom:custom:membershipId");
            String phoneNumber = event.getRequest().getUserAttributes().get("phone_number");
            result =  memberDataAccess.isMember(fullName, membershipId, phoneNumber);
        } catch (SQLException e) {
            logger.log(gsonObj.toJson(e));
        } finally {
           if (result) {
               return event;
           } else {
               return null;
           }
        }
    }
}
