package com.icode.cms;

import com.icode.cms.common.listener.ApplicationInitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IcodeCmsApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(IcodeCmsApplication.class);
        springApplication.addListeners(new ApplicationInitListener());
        springApplication.run(args);
    }

}
