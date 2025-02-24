package com.gk.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.service.UserService;
import com.gk.study.entity.User;
import com.gk.study.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static com.gk.study.controller.UserController.saveAvatar;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    private static String salt = "abcd1234";
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getUserList(String keyword) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(keyword)){
            // like查询
            queryWrapper.like("username", keyword);
        }
        queryWrapper.orderBy(true, false, "create_time");
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public User getAdminUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", user.getPassword());
        queryWrapper.gt("role", "1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User getNormalUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", user.getPassword());
        queryWrapper.eq("role", "1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User createUser(User user) {
        if(user.getId() == null){
            user.setId(UUID.randomUUID().toString());
        }
        String md5Str = DigestUtils.md5DigestAsHex((user.getPassword() + salt).getBytes());
        // 设置密码
        user.setPassword(md5Str);
        md5Str = DigestUtils.md5DigestAsHex((user.getUsername() + salt).getBytes());
        // 设置token
        user.setToken(md5Str);

        try {
            String avatar = saveAvatar(user);
            if(!org.springframework.util.StringUtils.isEmpty(avatar)) {
                user.avatar = avatar;
            }
        }catch (Exception e){}
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        user.setRole(String.valueOf(User.NormalUser));
        userMapper.insert(user);
        return user;
    }

    @Override
    public void deleteUser(String id) {
        userMapper.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Override
    public User getUserByToken(String token) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("token", token);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User getUserByUserName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User getUserDetail(String userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", userId);
        return userMapper.selectOne(queryWrapper);
    }
}
