package com.ypf.controller.common;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class fileController {
    @Resource
    ObjectMapper objectMapper;
    @Resource
    Logger logger;
    @Resource
    FastFileStorageClient fastFileStorageClient;
    private final String urlName = "http://1.117.229.165:8888/";

    @PostMapping("/editormdFileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("editormd-image-file")MultipartFile file) throws JsonProcessingException {

//        private int success;  // 后端是否处理成功 1成功 0失败
//        private String message; // 提示信息
//        private String url; // 成功后的url地址（图片存储在服务器的地址，相对路径）
        Map<Object,Object> map = new HashMap<>();
        map.put("success",0);
        map.put("message",null);
        map.put("url",null);
        String res = null;
        try {
           String extensionName =  file.getOriginalFilename();
            res = fastFileStorageClient.uploadFile(file.getInputStream(),file.getSize(), StringUtils.substring(extensionName,extensionName.lastIndexOf(".")+1) ,null).getFullPath();

        } catch (IOException e) {
            map.put("success",0);
            map.put("message","fail");
            map.put("url",null);
            e.printStackTrace();
            logger.error("文件上传失败!!");
            return objectMapper.writeValueAsString(map);
        }
        map.put("success",1);
        map.put("message","success");
        map.put("url",urlName+res);
        logger.info("文件上传成功  !!");
        return objectMapper.writeValueAsString(map);
    }
    @PostMapping("/deleteFile")
    @ResponseBody
    public void deleteFile (@RequestParam("path")String path){
        fastFileStorageClient.deleteFile(path);

    }
    @PostMapping("/imgUpload")
    @ResponseBody
    public String imgUpload(@RequestParam("img") MultipartFile file){
        String res = null;
        try {
            String extensionName =  file.getOriginalFilename();
            FastImageFile fastImageFile = new FastImageFile(file.getInputStream(),file.getSize(),StringUtils.substring(extensionName,extensionName.lastIndexOf(".")+1),null);
            res =fastFileStorageClient.uploadImage(fastImageFile).getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败，参数错误！";
        }
        return urlName+res;
    }
}
