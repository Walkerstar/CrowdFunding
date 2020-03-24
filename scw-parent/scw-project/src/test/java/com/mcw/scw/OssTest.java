package com.mcw.scw;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author mcw 2019\12\14 0014-11:07
 */
public class OssTest {
    public static void main(String[] args) throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "LTAI4FfSzzSnMp7MK1L2apgB";
        String accessKeySecret = "C4RlyDz9E90idcfSEib530maKuIV5E";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = new FileInputStream("E:/product-9.jpg");
        ossClient.putObject("mcwdescw", "picture/product-9.jpg", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
