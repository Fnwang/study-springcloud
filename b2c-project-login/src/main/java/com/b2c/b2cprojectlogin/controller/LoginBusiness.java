package com.b2c.b2cprojectlogin.controller;

import com.b2c.b2cprojectlogin.comm.ResultData;
import com.b2c.b2cprojectlogin.entity.UserEntity;
import com.b2c.b2cprojectlogin.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 用户登录业务
 */
public abstract class LoginBusiness extends  AbstractBusiness {



    @Autowired
    IUserService iUserService;

    //标示是否登录成功。
    protected boolean loginSuccess = false;


    /**
     * 通过用户名、账号查找
     * 1、先只用用户名找，没这个名字先提醒没这个人；
     * 2、当有这个人，输入密码错误，在提示密码错误；
     * @param userEntity
     * @return
     */
    public  String findUserByNameAndPassWord(UserEntity userEntity,String url) throws Exception {

        JSONObject jsonObject = null ;

        ResultData<UserEntity> resultData = new ResultData();

        if(userEntity!=null){
            if (StringUtils.isNotEmpty(userEntity.getUserName())) {
                LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper();
                //先查询账号
                lambdaQueryWrapper.eq(UserEntity::getUserName, userEntity.getUserName());
                //查询账号
                List<UserEntity> list = iUserService.list(lambdaQueryWrapper);

                //账号存在
                if(list.size()>0){
                    //已经输入了密码
                    if (StringUtils.isNotEmpty(userEntity.getPassWord())) {
                        //账号、密码都查询
                        lambdaQueryWrapper.eq(UserEntity::getPassWord, userEntity.getPassWord());
                        list = iUserService.list(lambdaQueryWrapper);
                        //账号、密码都正确
                        if(list.size()>0){
                            // 正确查询到用户
                            jsonObject = resultData.getResultData(list,ResultData.CODE_NULL,"正确查询数据！");
                            loginSuccess = true;
                        }else {
                            //密码错误
                            jsonObject = resultData.getResultData(null,ResultData.CODE_400,"["+userEntity.getUserName()+"]的密码错误！");
                        }

                    }else {
                        //密码为空
                        jsonObject = resultData.getResultData(null,ResultData.CODE_400,"密码不能为空！");
                    }

                }else{
                    //找不到这个账号
                    jsonObject = resultData.getResultData(null,ResultData.CODE_400,"找不到["+userEntity.getUserName()+"]这个账号！");
                }

            }else {
                //账号为空
                jsonObject = resultData.getResultData(null,ResultData.CODE_400,"账号不能为空！");
            }
        }

        jsonObject.put("url", url);
        //返回
        log.info("rs===>>"+jsonObject);

        return jsonObject !=null ?  jsonObject.toString() : null ;
    }
}
