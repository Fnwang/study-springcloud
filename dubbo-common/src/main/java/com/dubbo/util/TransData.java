package com.dubbo.util;
import com.alibaba.fastjson.JSONObject;

/**
 *
 */
public class TransData {
    String code;
    String msg;
    Object data;

    public TransData()
    {

    }
    /**
     * 用构造方法传入数据
     * @param code
     * @param msg
     * @param data
     */
    public TransData(String code , String msg, Object data)
    {
        this.code = code;
        this.msg  = msg;
        this.data = data;
    }

    /**
     * 调用无参方法返回json格式的数据
     * 可返回单条数据，也可以返回list/分页
     * @return
     */
    public  String getResult()
    {
       return this.getResult(null,null);
    }

    /**
     *
     * @param keys
     * @param values
     * @return
     */
    public  String getResult(String[] keys,Object[] values)  {
        JSONObject json = new JSONObject ();
        json.put ("code",this.code);
        json.put ("msg", this.msg);
        json.put ("data",this.data);
        //最近其它参数
        if(keys !=null && values !=null ) {
            int keySize = keys.length;
            int valuesSize = values.length;
            //key、values是同等的
            if(keySize>0  && keySize == valuesSize ) {
               for(int i = 0; i<keySize; i++ ) {
                    json.put (keys[i],values[i]);
               }
            }
        }
        //返回
        return json.toJSONString ();
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
