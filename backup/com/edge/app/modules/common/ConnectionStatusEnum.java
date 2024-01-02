package com.edge.app.modules.common;

public enum ConnectionStatusEnum {
    Requested("info"), Accepted("success"), Rejected("danger"), Withdrawn("danger"),
    Removed("info"), Shortlisted("info"), ContactViewed("info"), All("all");

    private String category;

    ConnectionStatusEnum(String category) {
        this.setCategory(category);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
