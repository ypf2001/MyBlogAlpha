package com.ypf.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesEncryption {

    private static final String KEY = "2364555434123456"; // 用的时候要修改
    private static final String IV = "2364555434123456"; // 用的时候要修改

    /**
     * 加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes("UTF-8");
            int plaintextLength = dataBytes.length;
            if ((plaintextLength == 96) || (plaintextLength % blockSize != 0)) {// 96刚好加密大于128位
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64Method.encryptBase64(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String desencrypt(String data) {
        try {
            byte[] encrypted1 = Base64Method.decryptBase64ForByte(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] rawOriginal = cipher.doFinal(encrypted1);
            byte[] original = new String(rawOriginal).replaceAll("\0", "").getBytes();
            String originalString = new String(original, "UTF-8");
            return originalString;
        } catch (Exception e) {
            System.out.println("密码解密异常===================");
            return null;
        }
    }

    //需要的时候用主方法, 看自己的密码加解密后是什么
    public static void main(String[] args) throws Exception {
//		String pwd = "password";
//		String str = AesEncryption.encrypt(pwd);
//		System.out.println("加密后:" + str);
//		System.out.println("解密后:" + AesEncryption.desencrypt(str));
//		System.out.println("MD5加密后:" + MD5Utils.generate(pwd));
    }
}

