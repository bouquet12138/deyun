package com.example.common_lib.info;

public class ServerInfo {

    private static final String SERVER_IP = "http://47.94.1.12:9090/";//服务器ip
    private static final String SERVER_PROJECT = SERVER_IP + "";//服务器ip


    public static String getServerAddress(String address) {
        return SERVER_PROJECT + address;
    }

    /**
     * 得到图片地址
     *
     * @param imageName
     * @return
     */
    public static String getImageAddress(String imageName) {
        //http://47.94.1.12:8080/groupimage/6c155734-04c9-47d2-a298-5608185c2394.png
        return SERVER_IP + "" + imageName;
    }

}
