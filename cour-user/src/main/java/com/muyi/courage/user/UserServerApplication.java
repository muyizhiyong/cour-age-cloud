package com.muyi.courage.user;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Hello world!
 *
 */
@ComponentScan("com.muyi.courage")
@SpringCloudApplication
public class UserServerApplication
{
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(UserServerApplication.class, args);
    }
}
