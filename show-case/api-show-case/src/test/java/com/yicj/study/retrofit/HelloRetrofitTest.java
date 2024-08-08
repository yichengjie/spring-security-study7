package com.yicj.study.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class HelloRetrofitTest {

    @Test
    void hello() throws IOException {
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                //可以接收自定义的Gson，当然也可以不传
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        retrofitApi.postData(null).execute() ;
    }


    @Test
    void hello2(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                // 针对rxjava2.x
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }
}
