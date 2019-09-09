package com.zuizui.beanfactorystudy.test;

import com.zuizui.beanfactorystudy.config.ForeignCallConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestConfig implements ForeignCallConfig {
    @Value("${foreign.url.base}")
    private String baseUrl;
    @Override
    public String getBaseUrl() {
        return baseUrl;
    }
}
