package com.pcjz.http.okhttp.server.download;


/**
 * Created by jiangtinming on 2016/7/11.
 */
public class DownloadPathInfo {

    private String url;           //文件URL

    private String targetFolder;  //保存文件夹

    //private String targetPath;    //保存文件地址


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargetFolder() {
        return targetFolder;
    }

    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }
}
