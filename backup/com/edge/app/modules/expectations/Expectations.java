package com.edge.app.modules.expectations;

import com.edge.core.modules.common.EdgeEntity;
import com.edge.core.utils.CoreDateUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@Document(collection = "EXPECTATIONS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Expectations extends EdgeEntity {

    private static final long serialVersionUID = 1L;

    private String gender;

    @Id
    private String internalId;

    private String[] bloodGroups;

    private String[] bodyTypes;

    private String[] skinColors;

    private String[] maritalStatuss;

    private String[] physicalStatuss;

    private String[] manglikStatuss;

    private String[] kundaliNadis;

    private String[] kundaliCharans;

    private String[] kundaliGans;

    private String[] professionalTypes;

    private String[] diet;

    private String[] smoking;

    private String[] drinking;

    private String[] religions;

    private String[] casts;

    private String[] motherTongues;

    private String[] degreeTypes;

    private String[] currentState;

    private String[] currentCountry;

    private String[] familyState;

    private String[] familyCountry;

    private String fromBirthYear;

    private String toBirthYear;

    private Integer heightCompleteFrom;

    private Integer heightCompleteTo;

    private Integer weightFrom;

    private Integer weightTo;

    private Integer earningFrom;

    private Integer earningTo;

    private boolean profilesWithPicOnly = false;

    private String expectations = "";

    private String restrictions = "";


    public BigDecimal getEarningFromInLakh() {
        if (earningFrom != null) return new BigDecimal(earningFrom * 100000);
        return null;
    }

    public BigDecimal getEarningToInLakh() {
        if (earningTo != null) return new BigDecimal(earningTo * 100000);
        return null;
    }

    public Date getFromBirthDate() throws ParseException {
        if(fromBirthYear != null && fromBirthYear != ""){
            return CoreDateUtils.parseStandardDate(fromBirthYear + "-01-01");
        }
        return null;
    }

    public Date getToBirthDate() throws ParseException {
        if(toBirthYear != null && toBirthYear != ""){
            return CoreDateUtils.parseStandardDate(toBirthYear + "-12-31");
        }
        return null;
    }
}
