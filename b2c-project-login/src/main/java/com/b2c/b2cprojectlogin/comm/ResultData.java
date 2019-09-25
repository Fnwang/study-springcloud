package com.b2c.b2cprojectlogin.comm;


import lombok.Data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

/**
 * 返回页面需要的数据
 */
@Data
public  class ResultData<T> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //错误代码
    public static String CODE_NULL = "";
    public static String CODE_400  = "400";
    public static String CODE_401  = "401";
    public static String CODE_402  = "402";
    public static String CODE_500  = "500";





    /**
     * 固定参数
     * @param data
     * @param code
     * @param msg
     * @return
     * @throws Exception
     */
    public  JSONObject getResultData(List<T> data, String code, String msg)
            throws Exception
    {
        //
        return this.getResultData(data, code, msg, null);
    }

    /**
     * 返回页面需要的数据
     * @param data
     * @param code
     * @param msg
     * @param map
     * @return
     * @throws Exception
     */
    public  JSONObject getResultData(List<T> data, String code, String msg,Map<String,Object> map)
            throws Exception
    {

        long count = (data==null)? 0: data.size();

        JSONObject jsonObject =  getResultData(data,count,code,msg,map);

        return  jsonObject;
    }

    /**
     *
     * @param data
     * @param count
     * @param code
     * @param msg
     * @param map
     * @return
     * @throws Exception
     */
    public  JSONObject getResultData(List<T> data,long count, String code, String msg,Map<String,Object> map)
            throws Exception
    {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("code", code);
        jsonObject.put("msg" , msg);

        jsonObject.put("count", count);
        jsonObject.put("data" , data==null? null : new JSONArray(data.toString()));

        //更多参数
        if(map!=null) {

            map.keySet().forEach(key -> System.out.println("map.get(" + key + ") = " + map.get(key)));
            //
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String k = entry.getKey();
                Object v = entry.getValue();
                jsonObject.put(k, v);
            }
        }

        return  jsonObject;
    }

}
