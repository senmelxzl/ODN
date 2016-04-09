package com.xiezhenlin.odn.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiezhenlin on 2016/2/1.
 */
public class ODNTools {

    public ODNTools(){

    }

    /**
     *get OND date
     * @return String "yyyyMMdd"
     */
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");

        return formatter.format(new Date());
    }

}
