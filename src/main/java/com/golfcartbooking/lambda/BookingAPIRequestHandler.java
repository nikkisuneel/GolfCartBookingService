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
import com.golfcartbooking.pojo.Booking;
import com.golfcartbooking.pojo.Cart;
import com.golfcartbooking.pojo.Member;
import com.golfcartbooking.util.Utils;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/*
 * A Lambda handler for processing Ball Picking Activity APIs
 */
public class BookingAPIRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
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
            Booking inputBooking = gsonObj.fromJson(body, Booking.class);
            String membershipId = Utils.extractMembershipId(request);
            inputBooking.setMembershipId(membershipId);
            // Calculate charge
            calculateCharge(inputBooking, membershipId);
            IBookingDataAccess bookingDataAccess = new BookingSQLDataAccess();
            bookingDataAccess.create(inputBooking);
            Booking createdBooking = bookingDataAccess.getBookingByBookingDate(inputBooking.getBookingDate(),
                    membershipId);
            response.setBody(gsonObj.toJson(createdBooking));
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

    private void calculateCharge(Booking inputBooking, String membershipId) throws SQLException {
        IMemberDataAccess memberDataAccess = new MemberSQLDataAccess();
        Member member = memberDataAccess.get(membershipId);
        ICartDataAccess cartDataAccess = new CartSQLDataAccess();
        Cart cart = cartDataAccess.getCart(inputBooking.getCartId());
        double charge = 0;
        if (!member.getMembershipType().equalsIgnoreCase("Lifetime")) {
            charge = (cart.getRate() * inputBooking.getNumberOfRounds()) +
                    (cart.getAdditionalPassengerSurcharge()
                            * inputBooking.getNumberOfRounds()
                            * inputBooking.getPlayerCount());
        }
        inputBooking.setCharge(charge);
    }

    private APIGatewayProxyResponseEvent processGet(APIGatewayProxyRequestEvent request,
                                                    Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Gson gsonObj = Utils.getGsonWithFormatters();

        try {
            String membershipId = Utils.extractMembershipId(request);
            IBookingDataAccess bookingDataAccess = new BookingSQLDataAccess();
            List<Booking> bookings = bookingDataAccess.getAllBookings(membershipId);
            response.setBody(gsonObj.toJson(bookings));
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
