package com.bd.udisk.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author <a href="https://gitee.com/zhengshengjun">zsj</a>
 * @date 2023/12/9.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVo {
    private String path;

    private String label;

    private boolean isDir;

    private List<FileVo> children;

    public FileVo(String label, boolean isDir, String path) {
        this.label = label;
        this.isDir = isDir;
        this.path = path;
    }
}
