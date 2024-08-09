1. 添加依赖
    ```xml
    <dependency>
        <groupId>com.squareup.okhttp3</groupId>
        <artifactId>mockwebserver</artifactId>
        <version>4.12.0</version>
    </dependency>
    ```
2. 编写测试代码
    ```java
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
    }
    ```