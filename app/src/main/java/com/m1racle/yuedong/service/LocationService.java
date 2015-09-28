package com.m1racle.yuedong.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.m1racle.yuedong.base.BaseApplication;
import com.m1racle.yuedong.util.LocationUtil;
import com.m1racle.yuedong.util.LogUtil;

import java.util.List;

/**
 * Location Service
 */
public class LocationService extends Service {

    public LocationClient mLocationClient;

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mLocationClient = ((BaseApplication)getApplication()).mLocationClient;

        LogUtil.log("Location onCreate");
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            //LocationUtil.getLocation(this);
            initLocation();
            mLocationClient.start();
        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void init() {
        /*locationManager = (LocationManager)getSystemService(BaseApplication.getContext().LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "目前暂时无法定位，请检查系统设置", Toast.LENGTH_SHORT).show();
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            showLocation(location);
        }
        locationManager.requestLocationUpdates(locationProvider, 5000, 1, locationListener);*/
    }

    @Override
    public void onDestroy() {
        mLocationClient.stop();
        super.onDestroy();
        LogUtil.log("Location onDestroy");
        /*if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }*/
    }

}
