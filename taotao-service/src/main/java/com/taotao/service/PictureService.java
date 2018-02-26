package com.taotao.service;

import com.taotao.common.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lenovo on 2018/2/26.
 */
public interface PictureService {

    PictureResult uploadPic(MultipartFile picFile);
}
