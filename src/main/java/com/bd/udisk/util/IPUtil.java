package com.bd.udisk.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;


/**
 * @author https://gitee.com/zhengshengjun
 * @date 2023/10/31.
 * ip工具类
 */
@Slf4j
public class IPUtil {


    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String SEPARATOR = ",";

    private static final String IPV6_LOCAL_ADDRESS = "0:0:0:0:0:0:0:1";
    private static final String HEADER_X_FORWARDED_FOR = "x-forwarded-for";
    private static final String HEADER_PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String HEADER_WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    /**
     * 获取真实客户端IP
     *
     * @param serverHttpRequest
     * @return
     */
    public static String getRealIpAddress(ServerHttpRequest serverHttpRequest) {
        String ip = serverHttpRequest.getHeaders().getFirst("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = serverHttpRequest.getHeaders().getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = serverHttpRequest.getRemoteAddress().getAddress().getHostAddress();
        }
//        log.info("the request ip address is {}", ip.equals(IPV6_LOCAL_ADDRESS) ? LOCALHOST : ip);
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
}
