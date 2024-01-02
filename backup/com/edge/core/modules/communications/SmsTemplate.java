package com.edge.core.modules.communications;

public class SmsTemplate {

    public static final String PLACEHOLDER = "XXXX";

    private String smsText;
    private boolean isUrgent;

    public SmsTemplate(String smsText, boolean isUrgent) {
        this.smsText = smsText;
        this.isUrgent = isUrgent;
    }

    public String getSmsText() {
        return smsText;
    }

    public boolean isUrgent() {
        return isUrgent;
    }
}
