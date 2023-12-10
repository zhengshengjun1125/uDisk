package com.bd.udisk.hadoop;


import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * @author <a href="https://gitee.com/zhengshengjun">zsj</a>
 * @date 2023/12/8.
 */
@Configuration
@ConditionalOnProperty(name = "hadoop.name_node")
@Slf4j
public class HdfsConnection {

    @Value("${hadoop.name_node}")
    String nameNode;


    @Bean("fileSystem")
    public FileSystem initFileSystem() {
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        configuration.set("fs.defaultFs", nameNode);
        configuration.set("dfs.replication", "1");
        configuration.set("dfs.client.use.datanode.hostname", "true");
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(new URI(nameNode.trim()), configuration, "bigdata");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileSystem;
    }
}
