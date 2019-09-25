package com.b2c.ucenter.service.impl;

import com.b2c.ucenter.comment.PasswordEncoderComment;
import com.b2c.ucenter.entity.RoleEntity;
import com.b2c.ucenter.entity.UserEntity;
import com.b2c.ucenter.repository.UserRepository;
import com.b2c.ucenter.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoderComment passwordEncoderComment;

    /**
     * 重写方法
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //通过账号查询数据库，必须保证查询出唯一。
        //如username是电话号码、身份证、微信账号、qq账号等。保证唯一
        UserEntity user = userRepository.findByUsername (userName);

        if(user!=null) {
            System.out.println ("UserEntity===>>>" + user.toString ());
            //获取角色
            List<SimpleGrantedAuthority> authorities = new ArrayList<> ();
            for (RoleEntity role : user.getRoles ()) {
                authorities.add (new SimpleGrantedAuthority (role.getName ()));
            }
            //通过用户名、密码查询。返回security用户信息
            UserDetails userDetails = new User (user.getUsername (), user.getPassword (), authorities);
            System.out.println ("userDetails===>>>" + userDetails.toString ());
            return userDetails;
        }else{
            System.out.println ("不存在这个用户！");
            return null;
        }
    }

    /**
     * 注册
     * @param user
     * @return
     */
    public String registry(UserEntity user) {
        String rs = null;
        //先检查是否有这个用户
        UserEntity userEntity = userRepository.findByUsername (user.getUsername ());
        //如果还没有，则注册
        if(userEntity ==null){
            userRepository.save(new UserEntity (user.getUsername(), passwordEncoderComment.getPasswordEncoder ().encode(user.getPassword())));
            rs = "注册成功！";
        }else {
            rs = "'"+user.getUsername ()+"'已经存在！";
        }
        return rs;
    }
}
