
package com.example.farouk.roomx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomPhoto {

    @SerializedName("id")
    @Expose
    private Integer rpid;
    @SerializedName("photolink")
    @Expose
    private String photolink;
    @SerializedName("room_id")
    @Expose
    private String rooid;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getRpid() {
        return rpid;
    }

    public void setRpid(Integer rpid) {
        this.rpid = rpid;
    }

    public String getPhotolink() {
        return photolink;
    }

    public void setPhotolink(String photolink) {
        this.photolink = photolink;
    }

    public String getRooid() {
        return rooid;
    }

    public void setRooid(String rooid) {
        this.rooid = rooid;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "RoomPhoto{" +
                "rpid=" + rpid +
                ", photolink='" + photolink + '\'' +
                ", rooid='" + rooid + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
