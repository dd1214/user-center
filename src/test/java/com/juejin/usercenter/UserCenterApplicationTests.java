package com.juejin.usercenter;

import com.barcke.y.rsa.pojo.KeyInfo;
import com.barcke.y.rsa.util.RSAUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;

@SpringBootTest
class UserCenterApplicationTests {

    @Test
    void contextLoads() throws NoSuchAlgorithmException {
        KeyInfo keyInfo = RSAUtil.genKeyPair();
        String privateKey = keyInfo.getPrivateKey();
        String publicKey = keyInfo.getPublicKey();
        System.out.println(privateKey);
        System.out.println("--------------------------------------------------------");
        System.out.println(publicKey);
    }

}
