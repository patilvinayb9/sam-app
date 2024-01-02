package com.edge.app.modules.profile.profileSecure;

import com.edge.core.modules.common.EdgeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecureProfileDetails extends EdgeEntity {

    private String internalId;

    private String profileId;

    private String lastName;

    private String cell;

    private String cellCountry;

    private String completeNumber;

    private String cellOthers;

    public void deriveValues() {
        if(cellCountry == null) cellCountry = "+91";
        if (cellCountry != null && cell != null) {
            String cellCountryCode = cellCountry;
            if (cellCountryCode == null) {
                cellCountryCode = "";
            }
            completeNumber = cellCountryCode.trim() + cell;
        }
    }

}
