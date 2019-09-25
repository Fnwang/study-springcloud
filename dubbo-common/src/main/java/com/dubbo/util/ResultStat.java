package com.dubbo.util;

public final class  ResultStat {

   public enum stat{
        ok("200","执行成功"),
        err("500","服务器执行错误");

        private String code;
        private String msg;
        /**
         * 枚举构造方法
         * @param code
         * @param msg
         */
        stat(String code, String msg) {
            this.code = code;
            this.msg  = msg;
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
   }
}
