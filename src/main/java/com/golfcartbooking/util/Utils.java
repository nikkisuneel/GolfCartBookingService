/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.util;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Base64;

/*
 * A class with helper methods
 */
public class Utils {
    public static Gson getGsonWithFormatters() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonDeserializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson;
    }

    public static String extractMembershipId(APIGatewayProxyRequestEvent request) {
        Gson gsonObj = Utils.getGsonWithFormatters();
        Base64.Decoder decoder = Base64.getDecoder();
        String tokenPayloadChunk = request.getHeaders().get("x-golf-cart-booking-auth").split("\\.")[1];
        String tokenPayload = new String(decoder.decode(tokenPayloadChunk));
        JsonObject jo = gsonObj.fromJson(tokenPayload, JsonObject.class);
        String membershipId = jo.get("custom:custom:membershipId").getAsString();
        return membershipId;
    }

    public static String exceptionStacktraceToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
