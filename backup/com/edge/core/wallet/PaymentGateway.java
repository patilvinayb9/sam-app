package com.edge.core.wallet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface PaymentGateway {

    Map<String, String> extractParamMap(HttpServletRequest request);

    String getHtml(Map<String, String> values);

    void printParams(HttpServletRequest request);

    Map<String, String> mapRequest(Map<String, String> reqParams, String email, String phone, String firstname, String profileId) throws ServletException, IOException;

}