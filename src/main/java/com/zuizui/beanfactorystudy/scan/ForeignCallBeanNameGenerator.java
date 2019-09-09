package com.zuizui.beanfactorystudy.scan;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

public class ForeignCallBeanNameGenerator implements BeanNameGenerator {
    @Override
    public String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry beanDefinitionRegistry) {
        String classname = beanDefinition.getBeanClassName();
        return Character.toLowerCase(classname.charAt(0))+classname.substring(1);
    }
}
