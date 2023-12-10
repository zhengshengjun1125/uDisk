package com.bd.udisk.util;

import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author zsj
 * 加密工具
 */
public class Encrypt {


    /**
     * 哈希256加密
     */
    public static String encrypt_hash256(String str) {
        return SHA256(str);
    }


    /**
     * 哈希512加密
     */
    public static String encrypt_hash512(String str) {
        return SHA512(str);
    }

    /**
     * md5加密
     */
    public static String encrypt_md5(String str) {
        return DigestUtils.md5DigestAsHex((str).getBytes());
    }



    /**
     * 获取32位的uuid
     * 去-符号并且小写
     */
    public static String encrypt_uuid_32_low(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-","").trim().toLowerCase();
        return uuid;
    }
    /**
     * 获取32位的uuid
     * 去-符号并且大写
     */
    public static String encrypt_uuid_32_up(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-","").trim().toUpperCase();
        return uuid;
    }

    /**
     * 获取十二位的uuid
     */
    public static String encrypt_uuid_12(){
        String uuid = UUID.randomUUID().toString();
        uuid.replace("-","").trim();
        return uuid.substring(0,12);
    }


    /**
     * 获取十二位的uuid
     */
    public static String encrypt_uuid_6(){
        String uuid = UUID.randomUUID().toString();
        uuid.replace("-","").trim();
        return uuid.substring(0,6);
    }

    /**
     * 传入文本内容，返回 SHA-256 串
     *
     * @param strText
     * @return
     */
    public static String SHA256(final String strText)
    {
        return SHA(strText, "SHA-256");
    }

    /**
     * 传入文本内容，返回 SHA-512 串
     *
     * @param strText
     * @return
     */
    public static String SHA512(final String strText)
    {
        return SHA(strText, "SHA-512");
    }

    /**
     * 字符串 SHA 加密
     *
     * @param strText
     * @return
     */
    private static String SHA(final String strText, final String strType)
    {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0)
        {
            try
            {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++)
                {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1)
                    {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }

        return strResult;
    }
}
