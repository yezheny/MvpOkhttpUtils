package mvp.pad.csc.salim.com.mvpokhttputils.model.entity;

import java.io.Serializable;

/**
 * created by yezhengyu on 2018/6/5 19:39
 */

public class ScreenBean implements Serializable {
    private int id;
    private String fileVersion;
    private String fileCode;
    private int fileDuration;
    private String fileAction;
    private String fileUrl;//图片路径
    private String fileName;
    private int fileType;
    private int groupId;
    private String groupUrl;//压缩包
    private String groupTitle;

    @Override
    public String toString() {
        return "ScreenBean{" +
                "id=" + id +
                ", fileVersion='" + fileVersion + '\'' +
                ", fileCode='" + fileCode + '\'' +
                ", fileDuration=" + fileDuration +
                ", fileAction='" + fileAction + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType=" + fileType +
                ", groupId=" + groupId +
                ", groupUrl='" + groupUrl + '\'' +
                ", groupTitle='" + groupTitle + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public int getFileDuration() {
        return fileDuration;
    }

    public void setFileDuration(int fileDuration) {
        this.fileDuration = fileDuration;
    }

    public String getFileAction() {
        return fileAction;
    }

    public void setFileAction(String fileAction) {
        this.fileAction = fileAction;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupUrl() {
        return groupUrl;
    }

    public void setGroupUrl(String groupUrl) {
        this.groupUrl = groupUrl;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }
}
