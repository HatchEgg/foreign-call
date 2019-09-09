package com.zuizui.beanfactorystudy.proxy;


import com.zuizui.beanfactorystudy.annotation.ForeignCall;
import com.zuizui.beanfactorystudy.config.ForeignCallConfig;
import com.zuizui.beanfactorystudy.scan.ForeignCallScanHandle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class ForeignCallProxyBuilder implements BeanFactoryPostProcessor, ApplicationContextAware {
    private ApplicationContext context;
    private static ConfigurableBeanFactory factory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        factory = configurableListableBeanFactory;
        ForeignCallScanHandle.getClasses().forEach(this::proxy);
    }

    public void proxy(String className){
        try {
            Class<?> clazz = Class.forName(className);
            proxy(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void proxy(Class<?> clazz){
        Class<?> proxyClass = Proxy.getProxyClass(BeanDefinitionBuilder.class.getClassLoader(),clazz);
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(proxyClass)
                .addConstructorArgValue(new CallMethodProxy())
                .getBeanDefinition();
        beanDefinition.setAutowireCandidate(true);
        ((DefaultListableBeanFactory)factory).removeBeanDefinition(clazz.getName());
        ((DefaultListableBeanFactory)factory).registerBeanDefinition(clazz.getName(),beanDefinition);
    }

    public static ForeignCallConfig getConfig(Class<? extends ForeignCallConfig> configClass){
        try{
            ForeignCallConfig config = factory.getBean(configClass);
            ((DefaultListableBeanFactory)factory).autowireBean(config);
            return config;
        }catch (BeansException e){
            String name = configClass.getName();
            String className = name.substring(name.lastIndexOf("."));
            String beanName = Character.toLowerCase(className.charAt(0)) + className.substring(1);
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(configClass);
            ((DefaultListableBeanFactory)factory).registerBeanDefinition(beanName,builder.getRawBeanDefinition());
            ForeignCallConfig config = factory.getBean(configClass);
            return config;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
