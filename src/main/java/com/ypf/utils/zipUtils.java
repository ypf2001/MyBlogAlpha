package com.ypf.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class zipUtils {

public static String zipString(String source){
if(source ==null||source.length()==0){

    return "";
}
byte[] bytes = new byte[1024];
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ZipOutputStream zos = new ZipOutputStream(baos);
    ZipEntry entry = new ZipEntry(source);




    return null;
}

}
