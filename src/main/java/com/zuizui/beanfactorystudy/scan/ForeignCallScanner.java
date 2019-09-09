package com.zuizui.beanfactorystudy.scan;

import com.zuizui.beanfactorystudy.annotation.ForeignCall;
import com.zuizui.beanfactorystudy.annotation.ForeignCallScan;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;

import java.util.Set;

public class ForeignCallScanner implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    ResourceLoader resourceLoader = null;

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(ForeignCallScan.class.getName()));
        if (attributes != null) {
            String[] basePackages = attributes.getStringArray("basePackages");
            if (basePackages.length == 0) {
                basePackages = new String[]{((StandardAnnotationMetadata) annotationMetadata).getIntrospectedClass().getPackage().getName()};
            }
            ForeignCallScanHandle handle = new ForeignCallScanHandle(beanDefinitionRegistry, false);
            if (resourceLoader != null) {
                handle.setResourceLoader(resourceLoader);
            }
            handle.setBeanNameGenerator(new ForeignCallBeanNameGenerator());
            handle.doScan(basePackages);
        }
    }
}
