package com.edge.core.modules.user;

import lombok.Data;

@Data
public class AdminCreationRequest {

    private String firstName;
    private String lastName;
    private String password;
    private String code;
    private String id;
    private String completeNumber;

    public void deriveValues() {

        firstName = firstName.toLowerCase().trim();
        lastName = lastName.toLowerCase().trim();
        completeNumber = completeNumber.toLowerCase().trim();
        code = code.trim();
        id = firstName + "." + lastName;

        if (!completeNumber.startsWith("+91")) {
            completeNumber = "+91" + completeNumber;
        }
    }

}
