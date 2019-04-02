package mvp.pad.csc.salim.com.mvpokhttputils.model.entity;

/**
 * created by yezhengyu on 2018/6/13 10:36
 */

public class DeviceInfo {
    private int id;
    private String deviceId;
    private String startTime;
    private String endTime;
    private int shutTime;
    private int screenDegree;
    private int screenIsLight;
    private String screenGroupId;
    private int launcherId;
    private String eventType;

    private String msg;

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", shutTime=" + shutTime +
                ", screenDegree=" + screenDegree +
                ", screenIsLight=" + screenIsLight +
                ", screenGroupId='" + screenGroupId + '\'' +
                ", launcherId=" + launcherId +
                ", eventType='" + eventType + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getShutTime() {
        return shutTime;
    }

    public void setShutTime(int shutTime) {
        this.shutTime = shutTime;
    }

    public int getScreenDegree() {
        return screenDegree;
    }

    public void setScreenDegree(int screenDegree) {
        this.screenDegree = screenDegree;
    }

    public int getScreenIsLight() {
        return screenIsLight;
    }

    public void setScreenIsLight(int screenIsLight) {
        this.screenIsLight = screenIsLight;
    }

    public String getScreenGroupId() {
        return screenGroupId;
    }

    public void setScreenGroupId(String screenGroupId) {
        this.screenGroupId = screenGroupId;
    }

    public int getLauncherId() {
        return launcherId;
    }

    public void setLauncherId(int launcherId) {
        this.launcherId = launcherId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
