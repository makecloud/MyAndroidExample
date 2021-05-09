package com.oohlink.wifiprobe.realmdemo.realmentity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gaowen on 2017/9/24.
 */

public class EmergentLayerRealmObject extends RealmObject {

    @PrimaryKey
    private long id;
    private int materialType;
    private String materialUrl;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public int getMaterialType() {
        return materialType;
    }

    public void setMaterialType(int materialType) {
        this.materialType = materialType;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

}
