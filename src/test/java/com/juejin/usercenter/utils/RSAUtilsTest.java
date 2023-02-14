package com.juejin.usercenter.utils;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static com.juejin.usercenter.constant.CommonConstant.PRIVATE_KEY;
import static com.juejin.usercenter.constant.CommonConstant.PUBLIC_KEY;
import static org.junit.jupiter.api.Assertions.*;

class RSAUtilsTest {


    @Test
    public void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        //生成的公钥
        String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
        //生成的私钥
        String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        System.out.println(publicKeyString);
        System.out.println();
        System.out.println(privateKeyString);
    }


    @Test
    public void aVoid() throws Exception {
        String a = "123456789";
        String s = RSAUtils.encryptByPublicKey(PUBLIC_KEY, a);
        System.out.println(s);
        String s1 = RSAUtils.decryptByPrivateKey(PRIVATE_KEY, s);
        System.out.println(s1);
    }



}