package com.bd.udisk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bd.udisk.entity.UserEntity;
import com.bd.udisk.service.UserService;
import com.bd.udisk.util.Encrypt;
import com.bd.udisk.util.R;
import com.bd.udisk.vo.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @author zsj
 * @email zsjemail666@163.com
 * @date 2023-12-08 17:02:41
 */
@Api("用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    /**
     * TODO
     * 登录
     * 注册(添加)
     * 修改
     * 删除
     */

    @ApiOperation(value = "用户登录", notes = "传入账号密码即可")
    @PostMapping("/login")
    public R login(@ApiParam(value = "账号密码实体对象") @RequestBody UserEntity entity) {
        UserEntity one = userService.getOne(new QueryWrapper<UserEntity>().eq("account", entity.getAccount()));
        if (one != null) {
            if (one.getSign().equals(Encrypt.SHA512(entity.getSign()))) {
                String token = UUID.randomUUID().toString();//随便做一个token
                stringRedisTemplate.opsForValue().set(entity.getAccount(), token, 1, TimeUnit.HOURS);
                stringRedisTemplate.opsForValue().set(token, entity.getAccount(), 1, TimeUnit.HOURS);
                return R.ok("登录成功").put("data", new Token(token));
            }
        }
        return R.ok("登录失败");
    }


    @ApiOperation(value = "用户注册(添加用户)", notes = "传入实体信息")
    @PostMapping("/add")
    public R register() {
        return R.error();
    }


    @ApiOperation(value = "用户信息", notes = "根据token获取用户信息")
    @GetMapping("/userinfo")
    public R userinfo(@RequestHeader("security") String security) {
        String username = stringRedisTemplate.opsForValue().get(security);
        UserEntity one = userService.getOne(new QueryWrapper<UserEntity>().eq("account", username));
        if (one != null) {
            return R.ok().put("data", one);
        }
        return R.error();
    }

    @ApiOperation(value = "获取用户列表分页", notes = "传入实体信息")
    @GetMapping("/list/{cur}/{size}")
    public R list(@ApiParam(value = "当前页") @PathVariable("cur") int cur, @ApiParam(value = "每页条数") @PathVariable("size") int size) {
        return R.ok().put("data", userService.page(new Page<UserEntity>(cur, size)));
    }
}
