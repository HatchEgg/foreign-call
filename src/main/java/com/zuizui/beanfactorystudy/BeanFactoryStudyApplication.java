package com.zuizui.beanfactorystudy;

import com.zuizui.beanfactorystudy.annotation.ForeignCallScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@ForeignCallScan(basePackages = {"com.zuizui.beanfactorystudy.test"})
@SpringBootApplication
public class BeanFactoryStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeanFactoryStudyApplication.class, args);
    }

}
