package com.dubbo.reader.controller;

import com.dubbo.util.ResultStat;
import com.dubbo.util.TransData;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 抽象服务
 */
public abstract class AbsController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 统一数据处理(方法1)
     * @param callBack
     * @return
     */
    public String getData(CallBack callBack) {
        TransData transData = new TransData ();
        try {
            //正确信息
            transData.setCode (""+response.getStatus ());
            transData.setMsg (ResultStat.stat.ok.getMsg ());
            //真正执行数据
            System.out.println ("开始执行回调方法：");
            transData.setData (callBack.call ());
            System.out.println ("回调方法执行完毕！");
        }catch (Exception e){
            transData.setCode (""+e.hashCode ());
            transData.setMsg (e.getMessage ());
        }
        //返回结果
        return transData.getResult ();
    }

    /**
     *
     * @param data
     * @return
     */
    public String getData(Object data) {

        TransData transData = new TransData ("200","执行成功",data);

        //返回结果
        return transData.getResult ();
    }
}
