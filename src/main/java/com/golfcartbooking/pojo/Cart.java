/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.pojo;

/*
 * A class that defines the attributes of a Cart
 */
public class Cart {
    private int id; // A unique identifier for a Cart
    private int number;
    private String manufacturer;
    private String fuelType;
    private int passengerCount;
    private double rate;

    public Cart() {}

    public Cart(int number, String manufacturer, String fuelType, int passengerCount, double rate) {
        setNumber(number);
        setFuelType(fuelType);
        setPassengerCount(passengerCount);
        setManufacturer(manufacturer);
        setRate(rate);
    }

    public Cart(int id, int number, String manufacturer, String fuelType, int passengerCount, double rate) {
        this(number, manufacturer, fuelType, passengerCount, rate);
        setId(id);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        if (fuelType == null) {
            throw new IllegalArgumentException("Fuel type must be not be null");
        }

        if (!fuelType.equalsIgnoreCase("gas")
                && !fuelType.equalsIgnoreCase("electric")
                && !fuelType.equalsIgnoreCase("lpg")) {
            throw new IllegalArgumentException("Fuel type must be Gas, Electric or LPG");
        }

        this.fuelType = fuelType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        if (manufacturer == null) {
            throw new IllegalArgumentException("Manufacturer must be not be null");
        }

        this.manufacturer = manufacturer;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        if (passengerCount < 1 || passengerCount > 4) {
            throw new IllegalArgumentException("Passenger count must be greater than 1 and less than 4");
        }
        this.passengerCount = passengerCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*
     * An override of the equals method to compare two Carts
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Cart)) {
            return false;
        }
        Cart p = (Cart) obj;

        // Since id is unique, if two Carts have the same id, they must be equal
        if (this.id == p.getId()) {
            return true;
        }
        return false;
    }
}
