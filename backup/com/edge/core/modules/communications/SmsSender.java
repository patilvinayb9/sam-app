package com.edge.core.modules.communications;

import com.nexmo.client.NexmoClientException;

import java.io.IOException;

interface SmsSender {

    boolean sendSms(String msgText, String toNumber) throws IOException, NexmoClientException, Exception;

}
