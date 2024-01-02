package com.edge.core.modules.communications;

import com.edge.core.exception.AppRuntimeException;
import com.edge.core.utils.JsonReader;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Component()
class SpringEdgeSender implements SmsSender {

    private final Logger logger = LoggerFactory.getLogger(SpringEdgeSender.class);

    @Value("${sms.seApiKey}")
    private String apiKey;

    @Value("${sms.seSenderId}")
    private String senderId;

    @Override
    public boolean sendSms(String msgText, String toNumber) throws Exception {
        try {
            return process_sms(toNumber, msgText);
        } catch (Exception e) {
            logger.error("Exception while sending SMS - TextLocalSender - ", e);
            e.printStackTrace();
            throw e;
        }
    }

    public Boolean process_sms(String mobileno, String message) throws IOException, KeyManagementException, NoSuchAlgorithmException {

        BufferedReader rd = null;
        try {
            message = URLEncoder.encode(message, "UTF-8");
            String baseurl = "http://instantalerts.co/api/web/send/?apikey=" + apiKey;

            URL url = new URL(baseurl + "&unicode=1&format=json&sender=" + senderId + "&to=" + mobileno + "&message=" + message);
            //System.out.println("url look like " + url );
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.getOutputStream();
            con.getInputStream();

            rd = new BufferedReader(new InputStreamReader(con.getInputStream()));

            JSONObject jsonObject = JsonReader.readJsonFromBufferedReader(rd);

            String error = null;
            try{
                error = "Some error occurred while sending sms - SpringEdgeSender : " + jsonObject.get("error").toString();
            }catch(Exception ex){
                error = "Some error occurred while sending sms - SpringEdgeSender : " + ex.getMessage();
            }
            if (error != null) {
                throw new AppRuntimeException(null, "Error while sending SMS : " + error);
            }
            return true;

        } catch (Exception e) {
            logger.error("Exception while sending SMS :: process_sms - SpringEdgeSender - ", e);
            e.printStackTrace();
            throw e;
        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public static void main(String[] args) throws Exception {
//        SpringEdgeSender sender = new SpringEdgeSender();
//        sender.sendSms("Hi, Verifying the connection", "+918888084629");
//    }


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}