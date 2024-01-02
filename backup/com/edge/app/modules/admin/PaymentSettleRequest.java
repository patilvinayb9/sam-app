package com.edge.app.modules.admin;

import lombok.Data;

@Data
public class PaymentSettleRequest {
    private String paymentId;
    private String paymentRef;
    private String paymentStatus;

}
