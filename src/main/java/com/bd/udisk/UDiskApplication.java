package com.bd.udisk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 1. 用户管理；
 * 2. 用户可以在云盘上创建目录、上传文件、下载文件、删除文件、移动文件、重命名文件、打开并查看指定目录下的文件列表；
 * 3. 要求开发成一个Web系统或C/S模式的应用程序。
 */
@SpringBootApplication
@CrossOrigin //跨域注解
@MapperScan("com.bd.udisk.dao")
public class UDiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(UDiskApplication.class, args);
    }

}
