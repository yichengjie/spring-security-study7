package com.yicj.study.aws.service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * GenShareLinkTest
 * </p>
 *
 * @author yicj
 * @since 2024/08/24 9:29
 */
public class GenShareLinkTest {

    // 带有预签名的 URL 链接，都带有过期时间。
    // v2 签名有效期最长可设置为 30 年。
    // v4 签名有效期最长可设置为 7 天。
    void generatePresignedUrl() throws Exception{
        // 填写存储桶（Bucket）所在地域对应的 endpoint 和 Region。
        // 以华东 - 苏州为例，endpoint 填写 https://eos-wuxi-1.cmecloud.cn，Region 填写 wuxi1。
        String endpoint = "<your-endpoint>";
        String region = "<your-region>";
        // 填写 EOS 账号的认证信息，或者子账号的认证信息。
        String accessKey = "<your-access-key>";
        String secretKey = "<your-secret-access-key>";
        // 填写存储桶名称，例如'example-bucket'。
        String bucketName = "<your-bucket-name>";
        // 填写对象名，例如'object.txt'。
        String objectName = "<your-object-name>";
        // 创建 AmazonS3 实例。
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("S3SignerType");
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        AmazonS3 client = AmazonS3ClientBuilder.standard()
                .withClientConfiguration(clientConfiguration)
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(credentialsProvider).build();
        // 生成共享外链。
        GeneratePresignedUrlRequest request =
                new GeneratePresignedUrlRequest(bucketName, objectName);
        // 设置过期时间，当到达该时间点时，URL 就会过期，其他人不再能访问该对象（Object）。
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date expiration = simpleDateFormat.parse("2022/12/31 23:59:59");
        // 设置 1 小时后过期。
        // Date expiration = new java.util.Date();
        // long expTimeMillis = Instant.now().toEpochMilli();
        // expTimeMillis += 1000 * 60 * 60;
        // expiration.setTime(expTimeMillis);
        // 设置外链的访问方法为 PUT，默认为 GET。
        // request.setMethod(HttpMethod.PUT);
        request.setExpiration(expiration);
        URL url = client.generatePresignedUrl(request);
        System.out.println(url);
        // 关闭 client。
        client.shutdown();
    }


    // 浏览器使用共享外链下载对象时，根据对象类型，可以在浏览器中预览展示，或者直接以对象形式下载。一般来说，图片、视频等可以直接在浏览器中展示。
    // 浏览器能否支持预览与其自身可支持预览的对象类型有关，如果有预览需求，在上传对象时需要指定对象类型 content-type
    // 即使上传的是图片，但 content-type 不是 png、jpg 等对象类型时，浏览器可能无法进行预览对象。
    void generatePresignedUrl2(){
        // 填写存储桶（Bucket）所在地域对应的 endpoint 和 Region。
        // 以华东 - 苏州为例，endpoint 填写 https://eos-wuxi-1.cmecloud.cn，Region 填写 wuxi1。
        String endpoint = "<your-endpoint>";
        String region = "<your-region>";

        // 填写 EOS 账号的认证信息，或者子账号的认证信息。
        String accessKey = "<your-access-key>";
        String secretKey = "<your-secret-access-key>";

        // 填写存储桶名称，例如'example-bucket'。
        String bucketName = "<your-bucket-name>";
        // 填写对象名，例如'object.txt'。
        String objectName = "<your-object-name>";

        // 创建 AmazonS3 实例。
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("S3SignerType");
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        AmazonS3 client = AmazonS3ClientBuilder.standard()
                .withClientConfiguration(clientConfiguration)
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(credentialsProvider).build();

        // 生成可预览的外链。
        GeneratePresignedUrlRequest request =
                new GeneratePresignedUrlRequest(bucketName, objectName);

        // 设置过期时间，当到达该时间点时，URL 就会过期，其他人不再能访问该对象（Object）。
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date expiration = null;
        try {
            expiration = simpleDateFormat.parse("2022/12/31 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request.setExpiration(expiration);

        // 设置返回头
        // 设置为 "inline" 时在浏览器中展示，设置为 "attachment" 时以文件形式下载。
        // 此外设置为 "attachment;filename=\"filename.jpg\"" ，还可以让下载的文件名字重命名为 "filename.jpg"。
        ResponseHeaderOverrides headerOverrides = new ResponseHeaderOverrides();
        headerOverrides.setContentDisposition("inline");
        request.setResponseHeaders(headerOverrides);

        URL url = client.generatePresignedUrl(request);
        System.out.println(url);

        // 关闭 client。
        client.shutdown();
    }

}
