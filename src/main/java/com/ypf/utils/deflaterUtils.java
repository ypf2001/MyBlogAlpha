package com.ypf.utils;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class deflaterUtils {
    public  static  String zipString(String target)  {
//指定压缩级别
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        deflater.setInput(target.getBytes(StandardCharsets.UTF_8));
        //当被调用时，表示压缩应该以输入缓冲区的当前内容结束。
        deflater.finish();
        byte[] bytes = new byte[521];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bytes.length);
        while(!deflater.finished()){
        int length = deflater.deflate(bytes);
        outputStream.write(bytes,0,length);
        }
        deflater.end();
        return Base64Method.encryptBase64(outputStream.toByteArray());
    }

    public  static String  unzipString(String target) throws Exception {
        if(!Base64Method.isBase64(target))
        {return target;}
         byte[]  decodeBytes = Base64Method.decryptBase64ForByte(target);
        Inflater inflater = new Inflater();
        inflater.setInput(decodeBytes);
        final byte[] bytes =new byte[512];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bytes.length);
        try{
            while (!inflater.finished()){
                int length = inflater.inflate(bytes);
                outputStream.write(bytes,0,length);
            }


        }
        catch (DataFormatException e)
        {
            e.printStackTrace();
            return  null;
        }finally {
            inflater.end();
        }
        try {
          return   outputStream.toString("utf-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }
    }
}
