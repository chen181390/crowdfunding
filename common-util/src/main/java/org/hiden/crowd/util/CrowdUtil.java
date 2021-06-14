package org.hiden.crowd.util;


import org.hiden.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrowdUtil {
    public static boolean judgeRequestType(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");
        return (acceptHeader != null && acceptHeader.contains("application/json")
        ||
                xRequestHeader!= null && xRequestHeader.equals("XMLHttpRequest"));
    }

    public static String md5(String source) {
        if (source == null || source.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            byte[] input = source.getBytes();
            byte[] output = messageDigest.digest(input);
            BigInteger bigInteger = new BigInteger(1, output);
            String encoded = bigInteger.toString(16);
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
