package com.yicj.study.retrofit;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {

    @POST("admin/login/")  // POST注解 表明http的请求方法，后面参数和baseurl组合成一个完整的请求路径
    @Headers({"Content-Type: application/json;charset=UTF-8"})   // 添加请求头参数
    Call<ResponseBody> postData(@Body RequestBody data);  // 请求体为 RequestBody 类型
 }