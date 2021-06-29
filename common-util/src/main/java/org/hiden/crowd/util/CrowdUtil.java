package org.hiden.crowd.util;


import com.aliyun.api.gateway.demo.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.hiden.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class CrowdUtil {
    public static boolean judgeRequestType(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");
        return (acceptHeader != null && acceptHeader.contains("application/json")
                ||
                xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest"));
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

    public static ResultEntity<String> sendCodeByShortMessageTest(String host, String phoneNum, String appcode, String templateId) {
        try {
            Thread.sleep(500);
            String code = Integer.toString((int) (Math.random() * 1000000));
            return ResultEntity.successWithData(code);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    public static ResultEntity<String> sendCodeByShortMessage(String host, String phoneNum, String appcode, String templateId) {
        String code = Integer.toString((int) (Math.random() * 1000000));

        String path = "/sms/smsSend";
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phoneNum);
        querys.put("param", "**code**:" + code + ",**minute**:5");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", templateId);
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            String reasonPhrase = statusLine.getReasonPhrase();
            if (statusCode == 200) {
                return ResultEntity.successWithData(code);
            }
            return ResultEntity.failed(reasonPhrase);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    public static boolean checkObjPropsNotNull(Object obj) throws IllegalAccessException {
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            if (field.get(obj) == null) return false;
            field.setAccessible(false);
        }
        return true;
    }


}