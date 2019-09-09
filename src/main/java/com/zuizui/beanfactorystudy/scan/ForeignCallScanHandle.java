package com.zuizui.beanfactorystudy.scan;

import com.zuizui.beanfactorystudy.annotation.ForeignCall;
import com.zuizui.beanfactorystudy.proxy.CallMethodProxy;
import com.zuizui.beanfactorystudy.proxy.ForeignCallProxyBuilder;
import kotlin.collections.SetsKt;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ForeignCallScanHandle extends ClassPathBeanDefinitionScanner {
    public static List<String> classes = new ArrayList<>();

    public static List<String> getClasses() {
        return classes;
    }

    public ForeignCallScanHandle(BeanDefinitionRegistry beanDefinitionRegistry, boolean useDefaultFilters) {
        super(beanDefinitionRegistry,useDefaultFilters);
    }


    public Set<BeanDefinitionHolder> doScan(String[] basePackages) {
        addIncludeFilter(new AnnotationTypeFilter(ForeignCall.class));
        //调用spring的扫描
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        if(beanDefinitionHolders.size() != 0){
            beanDefinitionHolders.stream()
                    .map(holder->holder.getBeanDefinition().getBeanClassName())
                    .forEach(classes::add);
        }
        return SetsKt.emptySet();
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }
}
