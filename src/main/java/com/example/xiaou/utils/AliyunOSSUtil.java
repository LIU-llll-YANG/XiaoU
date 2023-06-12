package com.liuyang.smallsystem.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;

public class AliyunOSSUtil {

    // 阿里云OSS的访问地址
    public static final String OSS_ENDPOINT = "oss-cn-beijing.aliyuncs.com";

    // 阿里云OSS的Access Key ID
    public static final String ACCESS_KEY_ID = "LTAI5tPUCBiWujTeiuukYyac";

    // 阿里云OSS的Access Key Secret
    public static final String ACCESS_KEY_SECRET = "zVNp7ADRvdPFoD0DBcPbrB2CDPEDBD";

    // 阿里云OSS的Bucket名称
    public static final String BUCKET_NAME = "studentsystempic";

    // 阿里云OSS的图片存储目录
    public static final String IMAGE_DIRECTORY = "pic/";

    /**
     * 上传头像图片到阿里云OSS
     * @param request HTTP请求对象
     * @return 返回上传后的图片的URL地址
     * @throws ServletException
     * @throws IOException
     */
    public static String uploadAvatarImage(HttpServletRequest request,String imageName) throws ServletException, IOException {
        // 获取上传的文件
        Part filePart = request.getPart(imageName);
        String fileName = filePart.getSubmittedFileName();
        if ("".equals(fileName)){
            return "https://studentsystempic.oss-cn-beijing.aliyuncs.com/pic/7f7dc16ff0e94d99a442f7996ce18631.png";
        }else{
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            // 生成唯一的文件名
            String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExtension;

            // 创建OSS客户端
            OSS ossClient = new OSSClientBuilder().build(OSS_ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

            try {
                // 上传文件流
                InputStream inputStream = filePart.getInputStream();
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(inputStream.available());
                ossClient.putObject(BUCKET_NAME, IMAGE_DIRECTORY + newFileName, inputStream, metadata);
            } catch (IOException e) {
                throw new ServletException("Failed to upload file to OSS", e);
            } finally {
                ossClient.shutdown();
            }
            // 返回上传后的图片的URL地址
            return "https://" + BUCKET_NAME + "." + OSS_ENDPOINT + "/" + IMAGE_DIRECTORY + newFileName;
        }

    }

}
