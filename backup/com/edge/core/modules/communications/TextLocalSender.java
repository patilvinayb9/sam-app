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

@Component()
class TextLocalSender implements SmsSender {

    private final Logger logger = LoggerFactory.getLogger(TextLocalSender.class);

    @Value("${sms.tlApiKey}")
    private String apiKey;

    @Value("${sms.tlSenderId}")
    private String senderId;

    @Override
    public boolean sendSms(String msgText, String toNumber) throws Exception {
        BufferedReader rd = null;
        try {
            msgText += "  #  ";
            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String apiKey = "apikey=" + getApiKey();
            String sender = "&sender=" + getSenderId();
            String numbers = "&numbers=" + toNumber;
            String message = "&message=" + msgText;

            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            JSONObject jsonObject = JsonReader.readJsonFromBufferedReader(rd);

            if (jsonObject.get("status").toString().equalsIgnoreCase("failure")) {
                String reason = " ";

                if (jsonObject.optJSONArray("errors") != null) {
                    reason += jsonObject.optJSONArray("errors").getJSONObject(0).get("message").toString() + ". ";
                }

                if (jsonObject.optJSONArray("warnings") != null) {
                    reason += jsonObject.optJSONArray("warnings").getJSONObject(0).get("message").toString() + ". ";
                }

                throw new AppRuntimeException(null, "Error while sending SMS : " + reason);
            }
            return true;
        } catch (Exception e) {
            logger.error("Exception while sending SMS - TextLocalSender - ", e);
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