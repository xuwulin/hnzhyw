package com.swx.ibms.business.common.utils;

import java.util.ResourceBundle;

/**
 * Created by wh on 2017/12/8.
 */
public class XxptMessage {
    //消息平台系统ID
    private static String sys_id = null;
    //访问我方地址
    private static String meg_adree = null;
    //访问消息平台系统地址
    private static String xxpt_dz = null;

    public static void load() {
        ResourceBundle bl = ResourceBundle.getBundle("xxts");
        sys_id = bl.getString("SYS_ID");
        meg_adree = bl.getString("MSG_ADREE");
        xxpt_dz = bl.getString("MSG_PROTOCOL") + "://" + bl.getString("XXPTIP") + ":" + bl.getString("MSG_PORT");
    }

    public static String getSys_id() {
        if (sys_id == null) {
            load();
        }
        return sys_id;
    }

    public static String getMeg_adree() {
        if (meg_adree == null) {
            load();
        }
        return meg_adree;
    }

    public static String getXxpt_dz() {
        if (xxpt_dz == null) {
            load();
        }
        return xxpt_dz;
    }

}
