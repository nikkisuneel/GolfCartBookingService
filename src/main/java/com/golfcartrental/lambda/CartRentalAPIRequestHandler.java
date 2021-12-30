/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartrental.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.golfcartrental.dataaccess.*;
import com.golfcartrental.pojo.CartRental;
import com.golfcartrental.pojo.Cart;
import com.golfcartrental.pojo.Member;
import com.golfcartrental.util.Utils;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/*
 * A Lambda handler for processing Ball Picking Activity APIs
 */
public class CartRentalAPIRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,
                                                      Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = null;

        try {
            switch(apiGatewayProxyRequestEvent.getHttpMethod().toUpperCase(Locale.ROOT)) {
                case "POST":
                    response = processPost(apiGatewayProxyRequestEvent, context);
                    break;
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

    private APIGatewayProxyResponseEvent processPost(APIGatewayProxyRequestEvent request,
                                                     Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Gson gsonObj = Utils.getGsonWithFormatters();

        try {
            String body = request.getBody();
            CartRental inputCartRental = gsonObj.fromJson(body, CartRental.class);
            String membershipId = Utils.extractMembershipId(request);
            inputCartRental.setMembershipId(membershipId);
            // Calculate charge
            calculateCharge(inputCartRental, membershipId);
            ICartRentalDataAccess cartRentalDataAccess = new CartRentalSQLDataAccess();
            cartRentalDataAccess.create(inputCartRental);
            CartRental createdCartRental = cartRentalDataAccess.getCartRentalByRentalDate(inputCartRental.getRentalDate(),
                    membershipId);
            response.setBody(gsonObj.toJson(createdCartRental));
            response.setStatusCode(201);
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

    private void calculateCharge(CartRental inputCartRental, String membershipId) throws SQLException {
        IMemberDataAccess memberDataAccess = new MemberSQLDataAccess();
        Member member = memberDataAccess.get(membershipId);
        ICartDataAccess cartDataAccess = new CartSQLDataAccess();
        Cart cart = cartDataAccess.getCart(inputCartRental.getCartId());
        double charge = 0;
        if (!member.getMembershipType().equalsIgnoreCase("Lifetime")) {
            charge = (cart.getRate() * inputCartRental.getNumberOfRounds()) +
                    (cart.getAdditionalPassengerSurcharge()
                            * inputCartRental.getNumberOfRounds()
                            * inputCartRental.getPlayerCount());
        }
        inputCartRental.setCharge(charge);
    }

    private APIGatewayProxyResponseEvent processGet(APIGatewayProxyRequestEvent request,
                                                    Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Gson gsonObj = Utils.getGsonWithFormatters();

        try {
            String membershipId = Utils.extractMembershipId(request);
            ICartRentalDataAccess cartRentalDataAccess = new CartRentalSQLDataAccess();
            List<CartRental> cartRentals = cartRentalDataAccess.getAllCartRentals(membershipId);
            response.setBody(gsonObj.toJson(cartRentals));
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
