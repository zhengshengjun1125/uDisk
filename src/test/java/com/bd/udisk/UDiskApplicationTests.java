package com.bd.udisk;

import com.bd.udisk.util.Encrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsStatus;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
class UDiskApplicationTests {


    @Autowired
    private FileSystem fileSystem;

    @Test
    void contextLoads() {
        System.out.println(fileSystem);
        boolean mkdirs = false;
        try {
            boolean exists = fileSystem.exists(new Path("/springboot"));
            log.info("文件夹是否存在{}", exists);
            mkdirs = fileSystem.mkdirs(new Path("/springboot"));
            log.info("创建文件是否成功{}", mkdirs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void en() throws IOException {
        boolean rename = fileSystem.rename(new Path("/白板.txt"), new Path("/白板改名.txt"));
        log.info("改名成功{}",rename);
    }

}
