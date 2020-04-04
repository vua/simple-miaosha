package com.vua.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.vua.entity.User;
import com.vua.utils.JWTUtils;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-27 19:50
 */
@Service
public class AuthService {
    static PublicKey publicKey;
    static PrivateKey privateKey;
    static final String PUB_KEY_PATH = "secret/public.key";
    static final String PRI_KEY_PATH = "secret/private.key";

    static {
        publicKey = getPublicKey(PUB_KEY_PATH);
        privateKey = getPrivateKey(PRI_KEY_PATH);
        //System.out.println(Base64.encodeBase64String(privateKey.getEncoded()));
    }
    static Map<String,Object> token_map=new HashMap(){{
        put("client_id","1531198765");
        put("client_secret","2615246d762013f5d6c7f19b10f583e0");
        put("grant_type","authorization_code");
        put("redirect_uri","http://passport.seckill.com:8889/auth_login");
    }};
    static final String REQUEST_AUTH_CODE_URL="";
    static final String REQUEST_ACCESS_TOKEN_URL="https://api.weibo.com/oauth2/access_token";
    static final String REQUEST_USER_INFO_URL="https://api.weibo.com/2/users/show.json";


    public Map<String,Object> verifyToken(String access_token,String ip) {
        Map<String, Object> map=null;
        try {
            map = JWTUtils.getInfoFromToken(access_token, publicKey);
        }catch (Exception e){
            return null;
        }
        if(map==null) return null;
        if(!ip.equals((String)map.get("ip"))) return null;
        map.forEach((k,v)->{
            System.out.println(k+" : "+v);
        });
        return map;
    }
//    @Autowired
//    UserService userService;
    public String getToken(User user,String ip) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("uid",user.getId());
        map.put("username",user.getName());
        map.put("password",user.getPassword());
        map.put("email",user.getEmail());
        map.put("ip",ip);
        return JWTUtils.generateToken(map,privateKey,5);
    }

    public static PublicKey getPublicKey(String pubKeyPath) {
        try {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(AuthService.class.getClassLoader().getResourceAsStream(pubKeyPath)))
            ) {
                byte[] byteKey = Base64.decodeBase64(reader.readLine().getBytes());
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return keyFactory.generatePublic(x509EncodedKeySpec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static PrivateKey getPrivateKey(String priKeyPath) {
        try {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(AuthService.class.getClassLoader().getResourceAsStream(priKeyPath)))
            ) {
                byte[] byteKey = Base64.decodeBase64(reader.readLine().getBytes());
                PKCS8EncodedKeySpec x509EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return keyFactory.generatePrivate(x509EncodedKeySpec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        /*KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        System.out.println(Base64.encodeBase64String(publicKey.getEncoded()));
        System.out.println(Base64.encodeBase64String(privateKey.getEncoded()));
       */
        AuthService service=new AuthService();
//        String access_token=service.getToken("","","");
//        System.out.println(access_token);
        //service.verifyToken(access_token);
    }
    
    
    public JSONObject getAccessToken(String code) throws HttpProcessException {
        /**
         * @Description 获取微博token
         * @param code
         * @Return com.alibaba.fastjson.JSONObject
         * @Exception 
         */
        token_map.put("code",code);
        // 获取token必须是POST请求
        String info=HttpClientUtil.post(HttpConfig.custom().url(REQUEST_ACCESS_TOKEN_URL).map(token_map));
        System.out.println(info);
        return JSON.parseObject(info);
    }
    public JSONObject getUserInfo(JSONObject o) throws HttpProcessException {
        Map<String,Object> info_map=new HashMap<>();

        System.out.println(o.getString("access_token"));
        System.out.println(o.getString("uid"));
        info_map.put("access_token",o.getString("access_token"));
        info_map.put("uid",o.getString("uid"));
        String info=HttpClientUtil.get(HttpConfig.custom().url(REQUEST_USER_INFO_URL).map(info_map));
        JSONObject json = JSON.parseObject(info);
        return json;
    }
    
}
