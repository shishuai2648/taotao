package com.taotao.service.impl;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.FastDFSClient;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

/**
 *  图片上传Service
 * Created by lenovo on 2018/2/26.
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Value("${IMAGE_SERVER_BASE_URL}")
    private String IMAGE_SERVER_BASE_URL;

    @Override
    public PictureResult uploadPic(MultipartFile picFile) {
        PictureResult result = new PictureResult();

        // 判断图片是否为空
        if(picFile.isEmpty()){
            result.setError(1);
            result.setMessage("图片为空");
            return result;
        }

        // 上传到图片服务器
        try {
            // 取图片的扩展名
            String originalFilename = picFile.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            FastDFSClient fastDFSClient = new FastDFSClient("classpath:properties/client.conf");
            String url = fastDFSClient.uploadFile(picFile.getBytes(), ext);

            // 拼接图片服务器的ip地址
            url = IMAGE_SERVER_BASE_URL+url;

            // 将url相应给客户端
            result.setError(0);
            result.setUrl(url);
        } catch (Exception e) {
            result.setError(1);
            result.setMessage("图片上传失败");
            e.printStackTrace();
        }
        return result;
    }
}
