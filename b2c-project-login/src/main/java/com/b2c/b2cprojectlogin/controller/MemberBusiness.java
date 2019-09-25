package com.b2c.b2cprojectlogin.controller;

import com.b2c.b2cprojectlogin.comm.PageUtil;
import com.b2c.b2cprojectlogin.comm.ResultData;
import com.b2c.b2cprojectlogin.entity.MemberEntity;
import com.b2c.b2cprojectlogin.service.IMemberService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象可扩张类，处理一定逻辑
 */
public abstract class MemberBusiness extends  AbstractBusiness {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    IMemberService iMemberService;


    /**
     * 查询会员列表
     * @param usernameKey
     * @param indexPage
     * @return
     * @throws Exception
     */
    public String getMemberEntity(String usernameKey,long indexPage)throws Exception {

        JSONObject rs = null;


        ResultData<MemberEntity> resultData = new ResultData();
        try {
            //查询条件
            LambdaQueryWrapper<MemberEntity> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.like(StringUtils.isNotEmpty(usernameKey),MemberEntity::getUsername, usernameKey);
            //TODO
            //调用分页方法
            PageUtil<MemberEntity> pageUtile = new PageUtil(indexPage,iMemberService,lambdaQueryWrapper);
            //调用最全的那个方法
            rs = resultData.getResultData(pageUtile.getRecords(), pageUtile.getTotal(),
                                          ResultData.CODE_NULL, "查询成功",null);

        }catch (Exception e){

            rs = resultData.getResultData(null,ResultData.CODE_500,e.getMessage());
        }

        //返回
        log.info("rs===>>"+rs);
        return rs !=null ?  rs.toString() : null ;
    }

}
