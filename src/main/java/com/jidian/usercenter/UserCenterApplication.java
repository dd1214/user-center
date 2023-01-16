package com.jidian.usercenter;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SuppressWarnings({"all"})
@SpringBootApplication
@MapperScan("com.jidian.usercenter.mapper")
public class UserCenterApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserCenterApplication.class, args);

    }

}
