package com.zuizui.beanfactorystudy;

import com.zuizui.beanfactorystudy.annotation.ForeignCallScan;
import com.zuizui.beanfactorystudy.proxy.ForeignCallProxyBuilder;
import com.zuizui.beanfactorystudy.test.NewForeignCall;
import com.zuizui.beanfactorystudy.test.TestConfig;
import com.zuizui.beanfactorystudy.test.TestForeignCall;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ForeignCallScan(basePackages = {"com.zuizui.beanfactorystudy.test"})
@SpringBootTest
public class BeanFactoryStudyApplicationTests {
    @Autowired
    ForeignCallProxyBuilder builder;
    @Autowired
    TestForeignCall foreignCall;
    @Autowired
    NewForeignCall newForeignCall;
    @Autowired
    TestConfig config;
    @Test
    public void contextLoads() {
        System.out.println(foreignCall.welcome());
        System.out.println(foreignCall.sayHello("zuizui"));
        System.out.println(foreignCall.bye());

        System.out.println(newForeignCall.welcome());
        System.out.println(newForeignCall.sayHello("zuizui"));
        System.out.println(newForeignCall.bye());
    }
}
