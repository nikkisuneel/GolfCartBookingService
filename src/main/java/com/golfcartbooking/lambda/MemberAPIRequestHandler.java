/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.golfcartbooking.dataaccess.*;
import com.golfcartbooking.pojo.Member;
import com.golfcartbooking.util.Utils;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.Locale;

/*
 * A Lambda handler for processing Data Trend APIs
 */
public class MemberAPIRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,
                                                      Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = null;

        try {
            switch(apiGatewayProxyRequestEvent.getHttpMethod().toUpperCase(Locale.ROOT)) {
                case "GET":
                    response = processGet(apiGatewayProxyRequestEvent, context);
                    break;
                default:
                    response =  new APIGatewayProxyResponseEvent();
            }
        } catch (Exception e) {
            logger.log(e.getMessage());
            response = new APIGatewayProxyResponseEvent();
            response.setBody(e.getMessage());
            response.setStatusCode(500);
        } finally {
            return response;
        }
    }

    private APIGatewayProxyResponseEvent processGet(APIGatewayProxyRequestEvent request,
                                                    Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Gson gsonObj = Utils.getGsonWithFormatters();

        try {
            IMemberDataAccess memberDataAccess = new MemberSQLDataAccess();
            String membershipId = Utils.extractMembershipId(request);
            Member member = memberDataAccess.get(membershipId);

            response.setBody(gsonObj.toJson(member));

            logger.log(gsonObj.toJson(member));

            response.setStatusCode(200);
        } catch (SQLException e) {
            logger.log(gsonObj.toJson(e));
            response.setBody(gsonObj.toJson(e.getMessage()));
            response.setStatusCode(500);
        } finally {
            return response;
        }
    }
}