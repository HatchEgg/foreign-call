package com.zuizui.beanfactorystudy.annotation;

import com.zuizui.beanfactorystudy.scan.ForeignCallScanner;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(ForeignCallScanner.class)
@Documented
public @interface ForeignCallScan {
    String[] basePackages() default {};
}
