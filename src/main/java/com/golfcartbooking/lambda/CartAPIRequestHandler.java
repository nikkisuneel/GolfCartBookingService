/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.golfcartbooking.dataaccess.CartSQLDataAccess;
import com.golfcartbooking.dataaccess.ICartDataAccess;
import com.golfcartbooking.pojo.Cart;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.Locale;

/*
 * A Lambda handler for processing Cart APIs
 */
public class CartAPIRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent,
        APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request,
                                                      Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = null;
        try {
            switch(request.getHttpMethod().toUpperCase(Locale.ROOT)) {
                case "GET":
                    response = processGet(request, context);
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
        Gson gsonObj = new Gson();

        try {
            ICartDataAccess cartDataAccess = new CartSQLDataAccess();

            if (request.getQueryStringParameters() != null
                   && !request.getQueryStringParameters().isEmpty()) {
                if (request.getQueryStringParameters().containsKey("cartNumber")) {
                    int cartNumber = Integer.parseInt(request.getQueryStringParameters().get("cartNumber"));
                    Cart cart = cartDataAccess.getCartByNumber(cartNumber);
                    logger.log("response body: " + gsonObj.toJson(cart));
                    response.setBody(gsonObj.toJson(cart));
                } else {
                    throw new IllegalArgumentException("If there is a query parameter, it must be cartNumber");
                }
            } else {
                String path = request.getPath();
                Integer id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                Cart cart = cartDataAccess.getCart(id);
                logger.log("response body: " + gsonObj.toJson(cart));
                response.setBody(gsonObj.toJson(cart));
            }
            response.setStatusCode(200);
        } catch (IllegalArgumentException e) {
            logger.log(gsonObj.toJson(e));
            response.setBody(gsonObj.toJson(e.getMessage()));
            response.setStatusCode(400);
        } catch (SQLException e) {
            logger.log(gsonObj.toJson(e));
            response.setBody(gsonObj.toJson(e.getMessage()));
            response.setStatusCode(500);
        } finally {
            return response;
        }
    }
}
