package com.edge.app.modules.admin;

import lombok.Data;

@Data
public class SendSmsRequest {
    private String profileIds;
    private String smsText;

}
