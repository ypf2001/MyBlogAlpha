package com.ypf.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;


//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
public class Base64Method {
    public static String encryptBase64(String strString) throws Exception {
        Base64 base64 = new Base64();
        String base64str = new String(base64.encode(strString.getBytes("utf-8")), "utf-8");
        base64str = base64str.replace("\n", "").replace("\r", "").replace('+', '-').replace('/', '_');
        return base64str;
    }

    public static String encryptBase64(byte[] bytes)  {
        Base64 base64 = new Base64();
        String base64str = new String(base64.encode(bytes), StandardCharsets.UTF_8);
        base64str = base64str.replace('+', '-').replace('/', '_');
        return base64str;
    }

    public static String decryptBase64(String strString) throws Exception {
        Base64 base64 = new Base64();
        byte[] bytes = base64.decode(strString.replace('-', '+').replace('_', '/').getBytes(StandardCharsets.UTF_8));
        String str = new String(bytes, StandardCharsets.UTF_8);
        return str;
    }

    public static byte[] decryptBase64ForByte(String strString) throws Exception {
        Base64 base64 = new Base64();
        byte[] bytes = base64.decode(strString.replace('-', '+').replace('_', '/').getBytes(StandardCharsets.UTF_8));
        return bytes;
    }

    public static boolean base64ToImage(String canvasData, String imagePath) throws IOException {
        if (StringUtils.isEmpty(canvasData)) {
            System.out.println("图片数据为空");
            return false;
        }

        OutputStream out = null;
        try {
            out = new FileOutputStream(imagePath);

            String data = canvasData.substring(canvasData.indexOf(',') + 1);
            byte[] b = Base64.decodeBase64(data);

            out.write(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
            return true;
        }

    }

    public static boolean isBase64(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        } else {
            if (str.length() % 4 != 0) {
                return false;

            }
            char[] strChars = str.toCharArray();
            for (char c : strChars) {
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')
                        || c == '+' || c == '/' || c == '='||c=='_'||c=='-') {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
    }

}
