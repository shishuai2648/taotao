package com.taotao.common.utils;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;


/**
 * Created by lenovo on 2018/2/26.
 */
public class FastDFSClient {
    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient storageClient = null;
    public FastDFSClient(String conf) throws Exception{
        if(conf.contains("classpath:")){
            conf = conf.replace("classpath:",this.getClass().getResource("/").getPath());
        }
        ClientGlobal.init(conf);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient(trackerServer,storageServer);
    }

    /**
     * 文件上传方法
     * @param fileName  文件全路径
     * @param extName   文件扩展名
     * @param metas     文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception{
        String[] result = storageClient.upload_file(fileName,extName,metas);
        return result[0]+"/"+result[1];
    }
    public String uploadFile(String fileName,String extName) throws Exception{
        return uploadFile(fileName,extName,null);
    }
    public String uploadFile(String fileName) throws Exception{
        return uploadFile(fileName,null,null);
    }
    /**
     * 文件上传方法
     * @param fileContent   文件byte数组
     * @param extName       文件扩展名
     * @param metas         文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent,String extName,NameValuePair[] metas) throws Exception{
        String[] result = storageClient.upload_file(fileContent,extName,metas);
        return result[0]+"/"+result[1];
    }
    public String uploadFile(byte[] fileContext,String extName) throws Exception{
        return uploadFile(fileContext,extName,null);
    }
    public String uploadFile(byte[] fileContext) throws Exception{
        return uploadFile(fileContext,null,null);
    }
}
