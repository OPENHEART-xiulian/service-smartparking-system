package com.sp.cjgc;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@EnableAspectJAutoProxy
@MapperScan("com.sp.cjgc.*.mapper")
@SpringBootApplication
public class SmartParkingApplication {

    public static void main(String[] args) throws UnknownHostException {
        log.info("\n=============> 开始启动智慧停车服务 <===================");
        ConfigurableApplicationContext context = SpringApplication.run(SmartParkingApplication.class, args);
        Environment env = context.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("\n----------------------------------------------------------------------\n\t" +
                "==============> 智慧停车服务启动成功 <=================\n\t" +
                "Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------------------");
    }
}

