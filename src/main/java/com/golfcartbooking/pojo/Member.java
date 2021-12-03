/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfcartbooking.pojo;

import java.time.LocalDateTime;

public class Member {
    int id;
    private String fullName;
    private String phone;
    private String email;
    private String membershipId;
    private String membershipType;
    private LocalDateTime membersSince;

    public Member() {}

    public Member(String fullName,
                  String phone,
                  String email,
                  String membershipId,
                  String membershipType,
                  LocalDateTime memberSince) {
        setFullName(fullName);
        setPhone(phone);
        setEmail(email);
        setMembershipId(membershipId);
        setMembershipType(membershipType);
        setMembersSince(memberSince);
    }

    public Member(int id,
                  String fullName,
                  String phone,
                  String email,
                  String membershipId,
                  String membershipType,
                  LocalDateTime memberSince) {
        this(fullName, phone, email, membershipId, membershipType, memberSince);
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public LocalDateTime getMembersSince() {
        return membersSince;
    }

    public void setMembersSince(LocalDateTime membersSince) {
        this.membersSince = membersSince;
    }
}
