package com.oohlink.wifiprobe.realmdemo;

import android.animation.PropertyValuesHolder;
import android.support.annotation.NonNull;

import com.oohlink.wifiprobe.realmdemo.entity.EmergentLayer;
import com.oohlink.wifiprobe.realmdemo.mapper.RealmDataMapper;
import com.oohlink.wifiprobe.realmdemo.realmentity.EmergentLayerRealmObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * realm数据库 数据源类 demo
 */
public class DatabaseDataSource {
    private static DatabaseDataSource databaseDataSource;

    private DatabaseDataSource() {
    }

    public static DatabaseDataSource getInstance() {
        if (databaseDataSource == null) {
            databaseDataSource = new DatabaseDataSource();
        }
        return databaseDataSource;
    }

    /**
     * 存
     *
     * @param EmergentLayer
     */
    public void saveEmergentLayer(EmergentLayer EmergentLayer) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        //没有主键的结构会报错
        realm.copyToRealmOrUpdate(RealmDataMapper.transformEmergentLayerRealmObject(EmergentLayer));
        //realm.insert(RealmDataMapper.transformEmergentLayerRealmObject(EmergentLayer));
        realm.commitTransaction();
        realm.close();
    }

    /**
     * 条件取
     *
     * @param id
     * @return
     */
    public EmergentLayer getEmergentLayerId(@NonNull long id) {
        Realm realm = Realm.getDefaultInstance();
        EmergentLayerRealmObject result = realm.where(EmergentLayerRealmObject.class).equalTo("id", id).findFirst();
        EmergentLayer emergentLayer = RealmDataMapper.transformEmergentLayer(result);
        realm.close();
        return emergentLayer;
    }

    /**
     * 条件取
     *
     * @param today
     * @return
     */
    public List<EmergentLayer> getEmergentLayerByDate(long today) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<EmergentLayerRealmObject> results = realm.where(EmergentLayerRealmObject.class).findAll();
        List<EmergentLayer> emergentLayers = new ArrayList<>();
        for (EmergentLayerRealmObject layer : results) {
            emergentLayers.add(RealmDataMapper.transformEmergentLayer(layer));
        }
        realm.close();
        return emergentLayers;
    }

    /**
     * 取所有
     *
     * @return
     */
    public List<EmergentLayer> getAllEmergentLayer() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<EmergentLayerRealmObject> results = realm.where(EmergentLayerRealmObject.class).findAll();
        List<EmergentLayer> emergentLayers = new ArrayList<>();
        for (EmergentLayerRealmObject layer : results) {
            emergentLayers.add(RealmDataMapper.transformEmergentLayer(layer));
        }
        realm.close();
        return emergentLayers;

    }
}
