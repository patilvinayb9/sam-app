package com.edge.app.modules.wallet;

public enum ProductInfoEnum {

    REGISTRATION(0, -1, 0, "REGISTRATION"),
    SILVER(15, 31, 50, "SILVER"),
    GOLD(50, 185, 300, "GOLD"),
    PLATINUM(100, 365, 600, "PLATINUM");

    private int amount;
    private int days;
    private int contacts;
    private String membershipPlan;

    ProductInfoEnum(int amount, int days, int contacts, String membershipPlan) {
        this.amount = amount;
        this.days = days;
        this.contacts = contacts;
        this.membershipPlan = membershipPlan;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getMembershipPlan() {
        return membershipPlan;
    }

    public void setMembershipPlan(String membershipPlan) {
        this.membershipPlan = membershipPlan;
    }

    public int getContacts() {
        return contacts;
    }

    public void setContacts(int contacts) {
        this.contacts = contacts;
    }
}