package com.khosroabadi.myplantaqua.dataModel.dm.deviceInfo;

import com.google.gson.annotations.SerializedName;
import com.khosroabadi.myplantaqua.dataModel.dm.product.ProductBean;

import java.util.List;

/**
 * Created by Alireza on 1/20/2017.
 */

public class DeviceInfoBean {


    private long id;
    @SerializedName("manufactorer")
    private String manufactorer;
    @SerializedName("device")
    private String device;
    @SerializedName("board")
    private String board;
    @SerializedName("brand")
    private String brand;
    @SerializedName("hardware")
    private String hardware;
    @SerializedName("androidVersion")
    private String androidVersion;
    @SerializedName("apiLevel")
    private int apiLevel;
    @SerializedName("buildNumber")
    private String buildNumber;
    @SerializedName("resolution")
    private String resolution;
    @SerializedName("dentisy")
    private String dentisy;
    @SerializedName("physicalSize")
    private String physicalSize;
    @SerializedName("logicalSize")
    private String logicalSize;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("model")
    private String model;
    @SerializedName("serial")
    private String serial;
    @SerializedName("pushID")
    private String pushID;
    @SerializedName("isActive")
    private Boolean isActive;
    @SerializedName("topic")
    private String topic;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getManufactorer() {
        return manufactorer;
    }

    public void setManufactorer(String manufactorer) {
        this.manufactorer = manufactorer;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public int getApiLevel() {
        return apiLevel;
    }

    public void setApiLevel(int apiLevel) {
        this.apiLevel = apiLevel;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getDentisy() {
        return dentisy;
    }

    public void setDentisy(String dentisy) {
        this.dentisy = dentisy;
    }

    public String getPhysicalSize() {
        return physicalSize;
    }

    public void setPhysicalSize(String physicalSize) {
        this.physicalSize = physicalSize;
    }

    public String getLogicalSize() {
        return logicalSize;
    }

    public void setLogicalSize(String logicalSize) {
        this.logicalSize = logicalSize;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPushID() {
        return pushID;
    }

    public void setPushID(String pushID) {
        this.pushID = pushID;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
