package com.xiezhenlin.odn.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiezhenlin on 2016/2/1.
 */
public class ODNDateTools {
    /**
     * 获取日期字符串。
     *
     * <pre>
     *  日期字符串格式： yyyyMMdd
     *  其中：
     *      yyyy   表示4位年。
     *      MM     表示2位月。
     *      dd     表示2位日。
     * </pre>
     *
     * @return String "yyyyMMdd"格式的日期字符串。
     */
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");

        return formatter.format(new Date());
    }
}
