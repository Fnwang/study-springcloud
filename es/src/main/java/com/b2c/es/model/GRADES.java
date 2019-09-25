package com.b2c.es.model;

/**
 * 年级
 */
public enum GRADES {
    GRADES1("1","大一"),
    GRADES2("2","大二"),
    GRADES3("3","大三"),
    GRADES4("4","大四");
    // 成员变量
    private String key;
    private String val;

    GRADES(String key,String val) {
        this.key = key;
        this.val = val;
    }

    /**
     * 取值
     * @return
     */
    public String getVal(){
        return  this.val;
    }

    /**
     * 取key
     * @return
     */
    public String getKey(){
        return  this.key;
    }

    /**
     * 覆盖方法
     */
    @Override
    public String toString() {
        return  this.key+":"+this.val;
    }

}
