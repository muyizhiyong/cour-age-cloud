package com.muyi.courage.gate;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;


@SpringCloudApplication
public class GatewayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }
}
