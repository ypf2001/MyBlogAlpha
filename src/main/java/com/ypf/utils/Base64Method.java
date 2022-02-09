package com.ypf.utils;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
public class Base64Method {
    public static String encryptBase64(String strString) throws Exception {
        Base64 base64 = new Base64();
        String base64str = new String(base64.encode(strString.getBytes("utf-8")), "utf-8");
        base64str = base64str.replace("\n", "").replace("\r", "").replace('+', '-').replace('/', '_');
        return base64str;
    }

    public static String encryptBase64(byte[] bytes) throws Exception {
        Base64 base64 = new Base64();
        String base64str = new String(base64.encode(bytes), "utf-8");
        base64str = base64str.replace("\n", "").replace("\r", "").replace('+', '-').replace('/', '_');
        return base64str;
    }

    public static String decryptBase64(String strString) throws Exception {
        Base64 base64 = new Base64();
        byte[] bytes = base64.decode(strString.replace('-', '+').replace('_', '/').getBytes("utf-8"));
        String str = new String(bytes, "utf-8");
        return str;
    }

    public static byte[] decryptBase64ForByte(String strString) throws Exception {
        Base64 base64 = new Base64();
        byte[] bytes = base64.decode(strString.replace('-', '+').replace('_', '/').getBytes("utf-8"));
        return bytes;
    }

//    public static byte[] safeUrlBase64Decode(final String safeBase64Str) throws IOException {
//        String base64Str = safeBase64Str.replace('-', '+');
//        base64Str = base64Str.replace('_', '/');
//        int mod4 = base64Str.length() % 4;
//        if (mod4 > 0) {
//            base64Str = base64Str + "====".substring(mod4);
//        }
//        return new BASE64Decoder().decodeBuffer(base64Str);
//    }
//
//    public static String safeUrlBase64Encode(byte[] data) {
//        String encodeBase64 = new BASE64Encoder().encode(data);
//        String safeBase64Str = encodeBase64.replace('+', '-');
//        safeBase64Str = safeBase64Str.replace('/', '_');
//        safeBase64Str = safeBase64Str.replaceAll("=", "");
//        return safeBase64Str;
//    }

}
