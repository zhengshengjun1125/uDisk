package com.bd.udisk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bd.udisk.dao.UserDao;
import com.bd.udisk.entity.UserEntity;
import com.bd.udisk.service.UserService;
import org.springframework.stereotype.Service;



@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {



}