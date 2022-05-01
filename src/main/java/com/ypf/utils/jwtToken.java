package com.ypf.utils;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class jwtToken {


    /** token秘钥，请勿泄露，请勿随便修改 backups:SECRETKey */
    public static final String SECRET = "123456ypf"; //这里是我随便填的, 用的时候要修改
    /** token 过期时间: 10天 */
    public static final int  CALENDARFIELD = Calendar.DATE;
    public static final int  CALENDARINTERVAL = 10;

    /**
     * JWT生成Token.<br/>
     * JWT构成: header, payload, signature
    * @param user_id
     *	 登录成功后用户user_id, 参数user_id不可传空
     */
    public static String createToken(String userJson) throws Exception {
        userJson = AesEncryption.encrypt(userJson);
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance() ;
        nowTime.add(CALENDARFIELD, CALENDARINTERVAL);
        Date expiresDate = nowTime.getTime();
        // header Map
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP").withClaim("user", null == userJson ? null : userJson)
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature
        return token;
    }
    /**
     *	 解密Token
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            e.printStackTrace();// token 校验失败, 抛出Token验证非法异常
        }
        return jwt.getClaims();
    }

    /**
     * 	根据Token获取user
     * @param token
     * @return user
     * @throws Exception
     */
    public static String getUser(String token) throws Exception {
        Map<String, Claim> claims = verifyToken(token);
        Claim userClaim = claims.get("user");
        if (null == userClaim || StringUtils.isEmpty(userClaim.asString())) {
            new Exception("token 校验失败, Token验证非法异常");
            // token 校验失败, 抛出Token验证非法异常
        }
        return AesEncryption.desencrypt(userClaim.asString());
    }
    public  static  boolean updateToken(HttpServletRequest request , HttpServletResponse response,String tokenName,Object object){
        //                生成jwtToken并写入浏览器
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        PrintWriter out = null;
        try {
            json = objectMapper.writeValueAsString(object);
            response.setContentType("text/html;charset=UTF-8");

            out= response.getWriter();

            out.write("<script>window.localStorage.setItem("+tokenName+",'" + jwtToken.createToken(json) + "')</script>");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            out.close();
        }
        return true;
    }


}
