package com.edge.app.modules.admin;

import com.edge.core.security.Encrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    static SimpleDateFormat simpleDateExtFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    private Encrypter encrypter;


    public static Integer getAsciiSum(String input) {
        Integer result = 0;
        for (Character ch : input.toCharArray()) {
            result += (int) ch;
        }
        return result;
    }
}