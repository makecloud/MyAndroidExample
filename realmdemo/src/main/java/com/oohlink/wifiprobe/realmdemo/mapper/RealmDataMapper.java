package com.oohlink.wifiprobe.realmdemo.mapper;

import com.oohlink.wifiprobe.realmdemo.entity.EmergentLayer;
import com.oohlink.wifiprobe.realmdemo.realmentity.EmergentLayerRealmObject;

public class RealmDataMapper {
    public static EmergentLayerRealmObject transformEmergentLayerRealmObject(EmergentLayer emergentLayer) {
        EmergentLayerRealmObject emergentLayerRealmObject = new EmergentLayerRealmObject();
        emergentLayerRealmObject.setId(emergentLayer.getId());
        emergentLayerRealmObject.setMaterialType(emergentLayer.getMaterialType());
        emergentLayerRealmObject.setMaterialUrl(emergentLayer.getMaterialUrl());
        return emergentLayerRealmObject;
    }

    public static EmergentLayer transformEmergentLayer(EmergentLayerRealmObject emergentLayerRealmObject) {
        EmergentLayer emergentLayer = new EmergentLayer();
        emergentLayer.setId(emergentLayerRealmObject.getId());
        emergentLayer.setMaterialType(emergentLayerRealmObject.getMaterialType());
        emergentLayer.setMaterialUrl(emergentLayerRealmObject.getMaterialUrl());
        return emergentLayer;
    }

}
