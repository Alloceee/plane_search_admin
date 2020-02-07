package com.yws.plane.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 返回Json数据处理类
 */
public class JSONData<T> {

    /**
     * {
     * "code": 0
     * ,"msg": ""
     * ,"data": {
     * "src": "http://cdn.layui.com/123.jpg"
     * }
     * }
     * 返回json字符串（图片上传）
     *
     * @param code 0表示成功，1表示失败
     * @param msg  返回信息
     * @param data 返回数据
     * @return 转换后的json字符串
     */
    public static String toJsonString(Integer code, String msg, Object data) {
        JSONObject object = new JSONObject();
        object.put("code", code);
        object.put("msg", msg);
        object.put("data", data);
        return JSON.toJSONString(object,SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * {
     * "status": 0,
     * "message": "",
     * "total": 180,
     * "data": {
     * "item": [{}, {}]
     * }
     * }
     * 返回json格式（数据表格渲染）
     *
     * @param code  状态0成功，其他失败
     * @param msg 返回提示信息
     * @param count   数据长度
     * @param data    数据列表
     * @return 转换后的json字符串
     */
    public static String toJsonString(Integer code, String msg, Integer count, Object data) {
        JSONObject object = new JSONObject();
        object.put("code", code);
        object.put("message", msg);
        object.put("count", count);
        object.put("data", data);
        return JSON.toJSONString(object,SerializerFeature.DisableCircularReferenceDetect);
    }

    public static String toErrorString(Integer code,String msg){
        JSONObject object = new JSONObject();
        object.put("code", code);
        object.put("message", msg);
        return JSON.toJSONString(object,SerializerFeature.DisableCircularReferenceDetect);
    }

    public static JSONObject tokenObject(String token,String username){
        JSONObject object = new JSONObject();
        object.put("token",token);
        object.put("username",username);
        return object;
    }

}
