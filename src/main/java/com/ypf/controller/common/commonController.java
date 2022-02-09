package com.ypf.controller.common;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Controller
public class commonController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @GetMapping("/common/kaptcha")
    public void defaultKaptcha (HttpServletRequest request , HttpServletResponse response) throws  Exception{
    byte[]  kaptchaStr = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] kaptchaOutPutStream = null;
        try{
            String verifyCode =defaultKaptcha.createText();
            request.getSession().setAttribute("verifyCode",verifyCode);
            BufferedImage challenge =defaultKaptcha.createImage(verifyCode);
            ImageIO.write(challenge,"jpg",outputStream);


        }catch (IllegalArgumentException e) {

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
            kaptchaOutPutStream = outputStream.toByteArray();
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(kaptchaOutPutStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
