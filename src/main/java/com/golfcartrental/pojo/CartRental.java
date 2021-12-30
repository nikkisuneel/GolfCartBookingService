/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartrental.pojo;

import java.time.LocalDateTime;

public class CartRental {
    private int id;
    private String membershipId;
    private LocalDateTime rentalDate;
    private int cartId;
    private LocalDateTime teeTime;
    private int numberOfRounds;
    private int playerCount;
    private double charge;

    public CartRental() {}

    public CartRental(String membershipId,
                      LocalDateTime rentalDate,
                      int cartId,
                      LocalDateTime teeTime,
                      int numberOfRounds,
                      int playerCount,
                      double charge) {
        setMembershipId(membershipId);
        setRentalDate(rentalDate);
        setCartId(cartId);
        setTeeTime(teeTime);
        setNumberOfRounds(numberOfRounds);
        setPlayerCount(playerCount);
        setCharge(charge);
    }

    public CartRental(int id,
                      String membershipId,
                      LocalDateTime rentalDate,
                      int cartId,
                      LocalDateTime teeTime,
                      int numberOfRounds,
                      int playerCount,
                      double charge) {
        this(membershipId, rentalDate, cartId, teeTime, numberOfRounds, playerCount, charge);
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

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
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

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        if (numberOfRounds < 1 || numberOfRounds > 3) {
            throw new IllegalArgumentException("numberofRounds must be between 1 and 3");
        }
        this.numberOfRounds = numberOfRounds;
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

    public String toString() {
        return " membershipId: " + getMembershipId() +
                " cartId: " + getCartId() +
                " number of rounds: " + getNumberOfRounds() +
                " number of players: " + getPlayerCount() +
                " charge: " + getCharge();
    }
}
