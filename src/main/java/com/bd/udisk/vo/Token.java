package com.bd.udisk.vo;

import lombok.Data;

/**
 * @author <a href="https://gitee.com/zhengshengjun">zsj</a>
 * @date 2023/12/8.
 */
@Data
public class Token {

    private String token;
    private String time;

    public Token(String token) {
        this.token = token;
        this.time = String.valueOf(System.currentTimeMillis());
    }
}
