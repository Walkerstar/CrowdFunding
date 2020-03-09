package com.mcw.scw.project.component;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 注入值的方法
 * 1.类上加上@component，加入到IOC容器中---在值上加入@Value("${oss.endpoint}")进行注入
 *
 * 2.类上加上@component，加入到IOC容器中,
 * @author mcw 2019\12\14 0014-11:37
 */
@Slf4j
@Data  //需加入set,get方法，用于属性的注入
@ToString
public class OssTemplate {

    String endpoint ;

    String accessKeyId ;

    String accessKeySecret ;

    String bucket;


    public String upload(String filename,InputStream inputStream)  {
        try {

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            //InputStream inputStream = new FileInputStream("E:/product-9.jpg");
            ossClient.putObject(bucket, "picture/"+filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            String filepath="https://"+bucket+"."+endpoint+"/picture/"+filename;
            log.debug("文件上传成功--{}",filepath);
            return filepath;
        }  catch (Exception e) {
            e.printStackTrace();

            log.debug("文件上传失败--{}",filename);
            return null;
        }
    }
}
