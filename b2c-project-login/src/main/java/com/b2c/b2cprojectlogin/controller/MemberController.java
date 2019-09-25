package com.b2c.b2cprojectlogin.controller;


import org.springframework.web.bind.annotation.*;

/**
 * 会员API接口层
 *
 */
@RestController
@RequestMapping("/api/member/")
public class MemberController extends MemberBusiness {

    /**
     * 查询会员信息
     * @param usernameKey 参数运行不存在
     *                    当参数不存在时，接收当值默认是空
     * @return
     */
    @GetMapping("list")
    @CrossOrigin
    public String getMemberEntity(@RequestParam(required = false, defaultValue = "") String usernameKey,
                                  @RequestParam(required = false, defaultValue = "0") long page )
    {

        try {
            //long page =
            return super.getMemberEntity(usernameKey,page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
