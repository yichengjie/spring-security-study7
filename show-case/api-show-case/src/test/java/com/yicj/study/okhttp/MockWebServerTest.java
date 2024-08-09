package com.yicj.study.okhttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.study.model.Person;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * <p>
 * MockWebServerTest
 * </p>
 *
 * @author yicj
 * @since 2024/08/09 16:50
 */
@Slf4j
class MockWebServerTest {

    public final MockWebServer server = new MockWebServer();

    @Test
    void execute() throws IOException {
        server.enqueue(new MockResponse().setBody("hello, world!"));
        //
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(server.url("/")).build();
        try(Response response = client.newCall(request).execute()){
            log.info("response : {}", response.body().string());
        }
        // 输出结果: hello, world!
    }

    @Test
    void send() throws IOException, InterruptedException {
        server.enqueue(new MockResponse().setBody("hello, world!"));
        //
        try(HttpClient client = HttpClient.newHttpClient()){
            // 发送http get请求
            HttpRequest request = HttpRequest.newBuilder(server.url("/").uri())
                    .timeout(Duration.ofSeconds(1)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("response : {}", response.body());
        }
        // 输出结果: hello, world!
    }


    @Test
    void send2() throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper() ;
        // 创建一个Person对象
        String json = "{\"firstName\":\"test\",\"lastName\":\"BJS\"}" ;
        server.enqueue(new MockResponse().setBody(json));
        //
        try(HttpClient client = HttpClient.newHttpClient()){
            // 发送http get请求
            HttpRequest request = HttpRequest.newBuilder(server.url("/").uri())
                    .timeout(Duration.ofSeconds(1)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Person person = mapper.readValue(response.body(), Person.class);
            log.info("person : {}", person);
        }
        // 输出结果: Person(firstName=test, lastName=BJS)
    }

}
