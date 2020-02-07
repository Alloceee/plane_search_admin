package com.yws.plane.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * 文件上传工具类
 */
public class FileUploadUtils {

    /**
     * 文件上传到七牛云
     *
     * @param bytes 字节数组
     * @return 上传后的文件名
     */
    public static String fileUpload(byte[] bytes) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "vfjcfpQ6vfXv08Gyp9YxCUYicudA8Zp1W5ZzQUiF";
        String secretKey = "v5kUNHUM4SJSX9b9TCzpp3-il-wmTE9e72ilaoh4";
        String bucket = "img-home";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.hash;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println("r.to"+r.toString());
            try {
                System.err.println("r.body"+r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
                ex2.printStackTrace();
            }
        }
        return null;
    }
}
