package com.zuizui.beanfactorystudy.executor;

import com.zuizui.beanfactorystudy.annotation.CallParam;
import com.zuizui.beanfactorystudy.annotation.ForeignCall;
import com.zuizui.beanfactorystudy.annotation.Call;
import com.zuizui.beanfactorystudy.proxy.ForeignCallProxyBuilder;
import okhttp3.*;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetExecutor {
    private String baseUrl;
    private String api;
    private HttpMethod httpMethod;
    private List<String> paramsName = new ArrayList<>();
    private String result;

    public GetExecutor(Method method) {
        initConfig(method);
    }

    public String executor(Object args[]) {
        HashMap<String, String> params = new HashMap<>();
        for (int i = 0; i < paramsName.size(); i++) {
            params.put(paramsName.get(i), args[i].toString());
        }
        String url = baseUrl + api;
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(url).newBuilder();
        params.forEach((key,value)-> httpUrlBuilder.addQueryParameter(key,value));
        final Request.Builder request = new Request.Builder()
                .url(httpUrlBuilder.build());
        final okhttp3.Call call = okHttpClient.newCall(request.build());
        try {
            Response response = call.execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void initConfig(Method method) {
        ForeignCall foreignCall = method.getDeclaringClass().getDeclaredAnnotation(ForeignCall.class);
        baseUrl = ForeignCallProxyBuilder.getConfig(foreignCall.value()).getBaseUrl();
        Call call = method.getDeclaredAnnotation(Call.class);
        api = call.api();
        httpMethod = call.method();
        for (Parameter parameter : method.getParameters()) {
            paramsName.add(parameter.getDeclaredAnnotation(CallParam.class).value());
        }
    }
}
