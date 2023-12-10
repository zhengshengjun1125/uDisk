package com.bd.udisk.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class BaseUtil {


    //根据格式返回当前时间
    public static String currentTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    //返回当前时间
    public static String currentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    public static String year_month_day() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    //生成长度15的数字串
    public static String randomNumber() {
        return String.valueOf(new Double((Math.random() + 1) * Math.pow(10, 16 - 1)).longValue());
    }

    //指定长度随机数字生成器
    public static String randomNumber(int length) {
        if (length > 19 || length < 0) {
            length = 19;
            log.info("在BaseUtil中的randomNumber传入长度不能大于19或者小于0,已自动改为19");
        }
        return String.valueOf(new Double((Math.random() + 1) * Math.pow(10, length - 1)).longValue());
    }

    //获得一个对象,将其中不为空的属性值返回到一个集合中
    public static Map<String, String> getObjectProperty(Object object) throws IllegalAccessException {
        Map<String, String> ans = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String key = field.getName();
            if (field.get(object) != null) {
                if (!field.get(object).equals("")) {
                    String value = field.get(object).toString();
                    ans.put(key, value);
                }
            }
        }
        return ans;
    }

    public static boolean checkPhoneFormat(String phone) {
        return phone.matches("/^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$/");
    }


}
