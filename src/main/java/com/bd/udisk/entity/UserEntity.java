package com.bd.udisk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author zsj
 * @email zsjemail666@163.com
 * @date 2023-12-08 17:02:41
 */
@Data
@TableName("user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId
	private String id;
	/**
	 * 用户账号
	 */
	private String account;
	/**
	 * 用户别名
	 */
	private String nickname;
	/**
	 * 用户密码
	 */
	private String sign;
	/**
	 * 用户头像地址
	 */
	private String avatar;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 1正常 0删除
	 */
	private Integer status;

}
