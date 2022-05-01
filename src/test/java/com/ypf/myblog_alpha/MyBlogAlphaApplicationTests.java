package com.ypf.myblog_alpha;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tobato.fastdfs.service.*;
import com.ypf.entity.adminEntity;
import com.ypf.entity.commentEntity;
import com.ypf.service.blogService;
import com.ypf.service.impl.redisUtils;
import com.ypf.service.userService;
import com.ypf.utils.*;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class MyBlogAlphaApplicationTests {
    @Autowired
    FastFileStorageClient FastFileStorageClient;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    blogService blogService;
    @ Autowired
    userService userService;
    @Autowired
    com.ypf.service.impl.redisUtils redisUtils;
    @Test
    void contextLoads() {

    }
    @Test
    public void CliamTest () throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        ObjectMapper objectMapper = new ObjectMapper();
        adminEntity aa = new adminEntity();
        aa.setAdmin_id("4");
        aa.setAdmin_name("ypf");
        aa.setAdmin_pwd("123456");
        aa.setCreate_time(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        String aaStr =  objectMapper.writeValueAsString(aa);
        String user = "{\n" +
                "    \"sites\": [\n" +
                "    { \"name\":\"菜鸟教程\" , \"url\":\"www.runoob.com\" }, \n" +
                "    { \"name\":\"google\" , \"url\":\"www.google.com\" }, \n" +
                "    { \"name\":\"微博\" , \"url\":\"www.weibo.com\" }\n" +
                "    ]\n" +
                "}";
        try {
            String token = jwtToken.createToken(aaStr);

            System.out.println("user是:"+jwtToken.getUser(token));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Test
    public void zipTest()  {
   blogService.getArticle("1").forEach(item->{
       String res= null;
       String ures = null;
       try {
          res = deflaterUtils.zipString(item.getArticleList().get(0).getArticle_text());
          ures = deflaterUtils.unzipString(res);
       } catch (Exception e) {
           e.printStackTrace();
       }
       System.out.println(ures);

        });

    }
    @Test
    public  void htmlParseTest() throws Exception {
        System.out.println();
        System.out.println(deflaterUtils.unzipString(blogService.getArticleById("38").getArticle_text())
        );

    }
    @Test
    public void  findComment(){
        List<commentEntity>   list = blogService.getCommentByArticleId("44");
        list.forEach(
                item->{
                    System.out.println(item);
                }
        );
    }
    @Test
    public void jsonTest() throws JsonProcessingException {
        String json = "{ \"body\" : \"body.bmp\", \"canvas\" : \"body.bmp\" }";
        Map<Object,Object> map = new HashMap<>();
        map.put("body","str");
        map.put("canvas","str");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(map));
    }
    @Test
    public void testAssociation (){
        System.out.println(userService.getUserBlogArticle("1"));
    }
    @Test
    public void jedisTest(){

//        JedisPooled jedis = new JedisPooled("1.117.229.165",6381);
//        jedis.sadd("k3","q");
//        System.out.println(jedis.get("k3"));
    }
    @Test
    public void redisTest() throws IOException {
           RBucket bucket = redissonClient.getBucket("k4");


           bucket.set("撒大苏打");
    }
    @Test
    public void jsoupTest() throws IOException {
        Connection connection=Jsoup.newSession();
        connection.url("https://weathernew.pae.baidu.com/weathernew/pc?query=%E7%A6%8F%E5%BB%BA%E6%B3%89%E5%B7%9E%E5%A4%A9%E6%B0%94&srcid=4982");
        Document document = connection.get();
        System.out.println(document.outerHtml());
    }
    @Test
    public void testIPUtils() throws Exception {
        System.out.println( IPUtils.getOutIPV4());
    }
    @Test
    public void getWeatherTest() throws IOException {
        System.out.println(jsoupUtils.getLocalWeather());
    }
    @Test
    public void testRedefine(){
        System.out.println("\\x04>\\x06123123");
    }
    @Test
    public void  IORedisTest(){
//        redisUtils.set("qwe","qweqwe");
    }
    @Test
    public void testListMsg(){
        System.out.println(blogService.findArticleMsg("1",0));
    }
    @Test
    public  void testNullKey(){
        System.out.println(redisUtils.get("asdasd"));
    }
    @Test
    public void dfsUploadTest() throws IOException {


        File file = new File("C:\\Users\\23645\\Desktop\\test.txt");
        InputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test",inputStream);
        System.out.println(FastFileStorageClient.uploadFile(multipartFile.getInputStream(),multipartFile.getSize(),"txt",null));
    }
}
