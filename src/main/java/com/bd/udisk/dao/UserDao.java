package com.bd.udisk.dao;

import com.bd.udisk.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author zsj
 * @email zsjemail666@163.com
 * @date 2023-12-08 17:02:41
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
}
