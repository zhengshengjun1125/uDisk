package com.bd.udisk.util;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author <a href="https://gitee.com/zhengshengjun">zsj</a>
 * @date 2023/12/8.
 */
@Component
public class HdfsUtil {

    @Autowired
    public FileSystem fileSystem;

    /**
     * 创建文件<br/>
     * @param path 文件路径和文件名称 <br/>
     * @return true为成功 false失败<br/>
     */
    public boolean createFile(String path) {
        Path road = new Path(path);
        boolean exists = false;
        try {
            exists = fileSystem.exists(road);
            if (exists) return false;
            else {
                fileSystem.create(road);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}
