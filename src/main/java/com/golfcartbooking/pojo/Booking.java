/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.pojo;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private String membershipId;
    private LocalDateTime bookingDate;
    private int cartId;
    private LocalDateTime teeTime;
    private double duration;
    private int playerCount;
    private double charge;

    public Booking() {}

    public Booking(String membershipId,
                   LocalDateTime bookingDate,
                   int cartId,
                   LocalDateTime teeTime,
                   double duration,
                   int playerCount,
                   double charge) {
        setMembershipId(membershipId);
        setBookingDate(bookingDate);
        setCartId(cartId);
        setTeeTime(teeTime);
        setDuration(duration);
        setPlayerCount(playerCount);
        setCharge(charge);
    }

    public Booking(int id,
                   String membershipId,
                   LocalDateTime bookingDate,
                   int cartId,
                   LocalDateTime teeTime,
                   double duration,
                   int playerCount,
                   double charge) {
        this(membershipId, bookingDate, cartId, teeTime, duration, playerCount, charge);
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public LocalDateTime getTeeTime() {
        return teeTime;
    }

    public void setTeeTime(LocalDateTime teeTime) {
        this.teeTime = teeTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        if (duration <1.0 || duration > 12.0) {
            throw new IllegalArgumentException("Duration must be between 1.0 and 12.0 hours");
        }
        this.duration = duration;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        if (playerCount < 1 || playerCount > 4) {
            throw new IllegalArgumentException("Player count must be greater than 0 and less than 5");
        }
        this.playerCount = playerCount;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }
}
