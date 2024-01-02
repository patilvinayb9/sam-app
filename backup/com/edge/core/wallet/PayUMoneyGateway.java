package com.edge.core.wallet;

import com.edge.core.exception.AppRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//import sun.net.www.http.HttpClient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author root
 */
@Component
public class PayUMoneyGateway implements PaymentGateway {


    private static final Logger logger = LoggerFactory.getLogger(PayUMoneyGateway.class);

    @Value(value = "${wallet.SUCCESS_URL}")
    private String SUCCESS_URL;

    @Value(value = "${wallet.FAILURE_URL}")
    private String FAILURE_URL;

    @Value(value = "${wallet.ALGORITHM}")
    private String ALGORITHM;

    @Value(value = "${wallet.TXN_ALGO}")
    private String TXN_ALGO;

    @Value(value = "${wallet.SERVICE_PROVIDER}")
    private String SERVICE_PROVIDER;

    @Value(value = "${wallet.KEY}")
    private String KEY;

    @Value(value = "${wallet.SALT}")
    private String SALT;

    @Value(value = "${wallet.PAY_U_URL}")
    private String PAY_U_URL;

    @Value(value = "${wallet.MID}")
    private String MID;

    @Value(value = "${wallet.REQ_HASH_FIELDS}")
    private String REQ_HASH_FIELDS;

    @Value(value = "${wallet.OPTIONAL_REQ_FIELDS}")
    private String OPTIONAL_REQ_FIELDS;

    @Value(value = "${wallet.RESPONSE_HASH_FIELDS}")
    private String RESPONSE_HASH_FIELDS;

    public boolean isEmpty(String s) {
        if (s == null || s.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public String hashCal(String algoType, String raw) {
        byte[] rawSeq = raw.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(algoType);
            algorithm.reset();
            algorithm.update(rawSeq);

            byte messageDigest[] = algorithm.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }

        } catch (NoSuchAlgorithmException nsae) {
        }
        return hexString.toString();
    }

    @Override
    public Map<String, String> extractParamMap(HttpServletRequest request) {
        Enumeration paramNames = request.getParameterNames();
        Map<String, String> reqParams = new HashMap<String, String>();

        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            reqParams.put(paramName, paramValue);
        }

        reqParams.put("key", KEY);
        reqParams.put("service_provider", SERVICE_PROVIDER);
        reqParams.put("surl", SUCCESS_URL);
        reqParams.put("furl", FAILURE_URL);

        return reqParams;

    }

    @Override
    public Map<String, String> mapRequest(Map<String, String> reqParams, String email, String phone, String firstname, String profileId)
            throws ServletException, IOException {

        reqParams.put("email", email);
        reqParams.put("phone", phone);
        reqParams.put("firstname", firstname);

        Map<String, String> derivedParams = new HashMap<String, String>();
        String hashString = "";


        String txnid = "";
        if (isEmpty(reqParams.get("txnid"))) {
            Random rand = new Random();
            String rndm = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
            txnid = rndm;
            reqParams.remove("txnid");
            reqParams.put("txnid", txnid);
            txnid = profileId + "-" + hashCal(TXN_ALGO, rndm).substring(0, 20);
        } else {
            txnid = reqParams.get("txnid");
        }

        if (isEmpty(reqParams.get("hash")) && reqParams.size() > 0) {
            if (isEmpty(reqParams.get("key")) || isEmpty(txnid) || isEmpty(reqParams.get("amount")) || isEmpty(reqParams.get("firstname")) || isEmpty(reqParams.get("email")) || isEmpty(reqParams.get("phone")) || isEmpty(reqParams.get("productinfo")) || isEmpty(reqParams.get("surl")) || isEmpty(reqParams.get("furl")) || isEmpty(reqParams.get("service_provider"))) {
                throw new AppRuntimeException(null, "Invalid arguments for Wallet Recharge.");
            } else {

                String[] mandatorySeqArray = REQ_HASH_FIELDS.split("\\|");
                for (String mandatoryPart : mandatorySeqArray) {
                    if (mandatoryPart.equals("txnid")) {
                        hashString = hashString + txnid;
                        derivedParams.put("txnid", txnid);
                    } else {
                        hashString = (isEmpty(reqParams.get(mandatoryPart))) ? hashString.concat("") : hashString.concat(reqParams.get(mandatoryPart).trim());
                        derivedParams.put(mandatoryPart, isEmpty(reqParams.get(mandatoryPart)) ? "" : reqParams.get(mandatoryPart).trim());
                    }
                    hashString = hashString.concat("|");
                }
                hashString = hashString.concat(SALT);
                String hash = hashCal(ALGORITHM, hashString);
                derivedParams.put("hash", hash);

                String[] optionalSeqArray = OPTIONAL_REQ_FIELDS.split("\\|");
                for (String optionalPart : optionalSeqArray) {
                    derivedParams.put(optionalPart, isEmpty(reqParams.get(optionalPart)) ? "" : reqParams.get(optionalPart).trim());
                }

            }
        } else if (!isEmpty(reqParams.get("hash"))) {
            String hash = reqParams.get("hash");
            derivedParams.put("hash", hash);
        }

        derivedParams.put("hashString", hashString);
        derivedParams.put("action", PAY_U_URL);
        return derivedParams;
    }

    public String calcHashForResponse(HttpServletRequest request)
            throws ServletException, IOException {

        String hashString = "";
        hashString = hashString.concat(SALT);

        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, String> params = new HashMap<String, String>();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            params.put(paramName, paramValue);
        }

        String[] hashVarSeq = RESPONSE_HASH_FIELDS.split("\\|");
        for (String part : hashVarSeq) {
            hashString = hashString.concat("|");
            hashString = (isEmpty(params.get(part))) ? hashString.concat("") : hashString.concat(params.get(part).trim());
        }
        return hashCal(ALGORITHM, hashString);
    }

    public void trustSelfSignedSSL() {
        try {
            final SSLContext ctx = SSLContext.getInstance(
                    "TLS");
            final X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(final X509Certificate[] xcs, final String string) throws CertificateException {
                    // do nothing
                }

                @Override
                public void checkServerTrusted(final X509Certificate[] xcs, final String string) throws CertificateException {
                    // do nothing
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLContext.setDefault(ctx);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getSuccessHtml() {
        return "<html> <body> \n"
                + "      \n"
                + "  \n"
                + "  <h1>SUCCESS </h1>\n"
                + "  </body>\n"
                + "</html>";
    }


    public String getFailureHtml() {
        return "<html> <body> \n"
                + "      \n"
                + "  \n"
                + "  <h1>FAILURE </h1>\n"
                + "  </body>\n"
                + "</html>";
    }

    /* (non-Javadoc)
     * @see com.edge.core.wallet.PaymentGateway#getHtml(java.util.Map)
     */
    @Override
    public String getHtml(Map<String, String> values) {
        return "<html> \n"
                + " <style> \n"
                + ".myDiv{\r\n" +
                " 	padding: 20px; \r\n" +
                " 	border-radius: 10px;\r\n "
                + " color:  #425455; \n" +
                "	box-shadow: 0px 2px 10px DarkGreen;	\r\n" +
                "}"
                + " </style> \n"
                + " <body class=\"myDiv\" > "
                + " <div style=\"text-align: center;\"> +"
                + " <H1> "
                + " *************************************************************** "
                + " <br><br>"
                + " You are being redirected to Payment Gateway... "
                + " <br><br>"
                + " Please <B>DO NOT</B> close or refresh this window. "
                + " <br><br> "
                + " Thank You! "
                + " <br><br>"
                + " *************************************************************** "
                + " </H1> "
                + " <span>+</span>"
                + " <br> \n"
                + " <br> \n"
                + " <span style=\"cursor:pointer; \" onclick=\"document.getElementById('payuform').submit();\">"
                + " <i> "
                + " If you are not getting automatically redirected, please click here. "
                + " </i> "
                + " </span>"
                + " <br> \n"
                + " <font style=\"color:white;\"> ** " + values.get("txnid").trim() + " ** </font> "
                + " \n </div> \n"
                + " <div style=\"display: none\"> \n"
                + "      \n"
                + "  \n"
                + "  <h1>PayUForm </h1>\n"
                + "  \n" + "<div>"
                + "      <form id=\"payuform\" action=\"" + values.get("action") + "\"  name=\"payuform\" method=POST >\n"
                + "      <input type=\"hidden\" name=\"key\" value=" + values.get("key").trim() + ">"
                + "      <input type=\"hidden\" name=\"hash\" value=" + values.get("hash").trim() + ">"
                + "      <input type=\"hidden\" name=\"txnid\" value=" + values.get("txnid").trim() + ">"
                + "      <table>\n"
                + "        <tr>\n"
                + "          <td><b>Mandatory Parameters</b></td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "         <td>Amount: </td>\n"
                + "          <td><input name=\"amount\" value=" + values.get("amount").trim() + " /></td>\n"
                + "          <td>First Name: </td>\n"
                + "          <td><input name=\"firstname\" id=\"firstname\" value=" + values.get("firstname").trim() + " /></td>\n"
                + "        <tr>\n"
                + "          <td>Email: </td>\n"
                + "          <td><input name=\"email\" id=\"email\" value=\"" + values.get("email").trim() + "\" /></td>\n"
                + "          <td>Phone: </td>\n"
                + "          <td><input name=\"phone\" value=" + values.get("phone") + " ></td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "          <td>Product Info: </td>\n"
                + "<td><input name=\"productinfo\" value=" + values.get("productinfo").trim() + " ></td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "          <td>Success URI: </td>\n"
                + "          <td colspan=\"3\"><input name=\"surl\"  size=\"64\" value=" + values.get("surl") + "></td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "          <td>Failure URI: </td>\n"
                + "          <td colspan=\"3\"><input name=\"furl\" value=" + values.get("furl") + " size=\"64\" ></td>\n"
                + "        </tr>\n"
                + "\n"
                + "        <tr>\n"
                + "          <td colspan=\"3\"><input type=\"hidden\" name=\"service_provider\" value=\"payu_paisa\" /></td>\n"
                + "        </tr>\n"
                + "             <tr>\n"
                + "          <td><b>Optional Parameters</b></td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "          <td>Last Name: </td>\n"
                + "          <td><input name=\"lastname\" id=\"lastname\" value=" + values.get("lastname") + " ></td>\n"
                + "          <td>Cancel URI: </td>\n"
                + "          <td><input name=\"curl\" value=" + values.get("curl") + " ></td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "          <td>Address1: </td>\n"
                + "          <td><input name=\"address1\" value=" + values.get("address1") + " ></td>\n"
                + "          <td>Address2: </td>\n"
                + "          <td><input name=\"address2\" value=" + values.get("address2") + " ></td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "          <td>City: </td>\n"
                + "          <td><input name=\"city\" value=" + values.get("city") + "></td>\n"
                + "          <td>State: </td>\n"
                + "          <td><input name=\"state\" value=" + values.get("state") + "></td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "          <td>Country: </td>\n"
                + "          <td><input name=\"country\" value=" + values.get("country") + " ></td>\n"
                + "          <td>Zipcode: </td>\n"
                + "          <td><input name=\"zipcode\" value=" + values.get("zipcode") + " ></td>\n"
                + "        </tr>\n"
                + "          <td>UDF1: </td>\n"
                + "          <td><input name=\"udf1\" value=" + values.get("udf1") + "></td>\n"
                + "          <td>UDF2: </td>\n"
                + "          <td><input name=\"udf2\" value=" + values.get("udf2") + "></td>\n"
                + " <td><input name=\"hashString\" value=" + values.get("hashString") + "></td>\n"
                + "          <td>UDF3: </td>\n"
                + "          <td><input name=\"udf3\" value=" + values.get("udf3") + " ></td>\n"
                + "          <td>UDF4: </td>\n"
                + "          <td><input name=\"udf4\" value=" + values.get("udf4") + " ></td>\n"
                + "          <td>UDF5: </td>\n"
                + "          <td><input name=\"udf5\" value=" + values.get("udf5") + " ></td>\n"
                + "          <td>PG: </td>\n"
                + "          <td><input name=\"pg\" value=" + values.get("pg") + " ></td>\n"
                + "        <td colspan=\"4\"><input type=\"submit\" value=\"Submit\"  /></td>\n"
                + "      \n"
                + "    \n"
                + "      </table>\n"
                + "    </form>\n"
                + " <script> "
                + " document.getElementById(\"payuform\").submit(); "
                + " </script> "
                + "       </div>   "
                + "  \n"
                + "  </div>\n"
                + "  </body>\n"
                + "</html>";
    }

    /* (non-Javadoc)
     * @see com.edge.core.wallet.PaymentGateway#printParams(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void printParams(HttpServletRequest request) {

        logger.info("Inside Print Param... \n");
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            logger.info(paramName + " :: " + paramValue + "\n");
        }
        logger.info("Done Print Param... \n");
    }
}
