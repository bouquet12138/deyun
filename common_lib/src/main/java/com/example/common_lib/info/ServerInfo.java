package com.example.common_lib.info;

public class ServerInfo {

    //http://101.37.12.131:8080/GroupManager/
    private static final String SERVER_IP = "http://101.37.12.131:8080/";//服务器ip
    private static final String SERVER_PROJECT = SERVER_IP + "GroupManager/";//服务器ip


    public static String getServerAddress(String address) {
        return SERVER_PROJECT + address + ".action";
    }

    /**
     * 得到图片地址
     *
     * @param imageName
     * @return
     */
    public static String getImageAddress(String imageName) {
        //http://47.94.1.12:8080/groupimage/6c155734-04c9-47d2-a298-5608185c2394.png
        return SERVER_IP + "groupimage/" + imageName;
    }

}
