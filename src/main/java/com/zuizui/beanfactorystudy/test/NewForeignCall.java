package com.zuizui.beanfactorystudy.test;

import com.zuizui.beanfactorystudy.annotation.Call;
import com.zuizui.beanfactorystudy.annotation.CallParam;
import com.zuizui.beanfactorystudy.annotation.ForeignCall;
import org.springframework.http.HttpMethod;

@ForeignCall(TestConfig.class)
public interface NewForeignCall {
    @Call(method = HttpMethod.GET, api = "welcome/sayHello")
    String sayHello(@CallParam("user") String user);

    @Call(method = HttpMethod.GET, api = "welcome/hai")
    String welcome();

    @Call(method = HttpMethod.GET, api = "welcome/bye")
    String bye();
}
