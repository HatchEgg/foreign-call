package com.zuizui.beanfactorystudy.proxy;

import com.zuizui.beanfactorystudy.annotation.Call;
import com.zuizui.beanfactorystudy.executor.GetExecutor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CallMethodProxy implements InvocationHandler {

    public CallMethodProxy(){

    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return getExecutor(method).executor(args);
    }

    public GetExecutor getExecutor(Method method){
        GetExecutor result = null;
        Call call = method.getAnnotation(Call.class);
        switch (call.method()){
            case GET: result = new GetExecutor(method);
        }
        return result;
    }


}
