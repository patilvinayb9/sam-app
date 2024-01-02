package com.edge.app.modules.expectations;

import java.text.ParseException;

public interface ExpectationsService {

    String getExpectations(String internalId);

    Expectations loadExpectationsById(String internalId);

    Expectations setExpectations(Expectations expectations) throws ParseException;

    String getOneTimeExpectationsById(Expectations expectations, String internalId) throws ParseException;

    String deriveExpectations(Expectations expectations, String internalId, boolean isExpectation) throws ParseException;

//    boolean checkIfRequestIsPermitted(ProfileDetails profileFrom, ProfileDetails profileTo);

}
