package com.edge.core.utils;

import com.edge.core.config.CoreConstants;
import org.apache.commons.lang3.RandomStringUtils;

public class EdgeUtils {

    private static String regex = ",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))";

    public static String[] clients = new String[]{};


    public static boolean isEmptyString(String inpStr) {
        return inpStr == null || inpStr.trim().length() == 0;
    }

    public static String generateProfileId() {
        String str = RandomStringUtils.randomAlphabetic(CoreConstants.PROFILE_ALPHA_SIZE).toUpperCase();
        String num = RandomStringUtils.randomNumeric(CoreConstants.PROFILE_NUM_SIZE);
        return str.substring(0, 1) + num.charAt(0) + str.substring(1, 3) + num.charAt(1) + str.substring(3);
    }

    public static String appendIndiaCode(String mobileNumber) {
        if (mobileNumber != null && mobileNumber.length() == 10) {
            return CoreConstants.INDIA_COUNTRY_CODE + mobileNumber;
        }
        return null;
    }
}
