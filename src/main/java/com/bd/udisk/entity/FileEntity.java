package com.bd.udisk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author zsj
 * @email zsjemail666@163.com
 * @date 2023-12-08 18:55:08
 */
@Data
@TableName("file")
public class FileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 文件id
	 */
	@TableId
	private String id;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件在hdfs上的路径
	 */
	private String filePath;
	/**
	 * 文件创建时间
	 */
	private Date createTime;
	/**
	 * 文件的状态 1正常 0 删除
	 */
	private Integer status;
	/**
	 * 所属用户的id
	 */
	private String belong;

}
