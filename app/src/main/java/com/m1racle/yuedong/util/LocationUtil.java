package com.m1racle.yuedong.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.m1racle.yuedong.base.BaseApplication;

import java.util.List;

/**
 * Location Function Util
 * @author sczyh30
 * 目前直接用百度地图API就好，暂时不需要此类
 */
@Deprecated
public class LocationUtil {

    private static Context context;
    private static LocationManager locationManager;
    private static Location location;
    private static String locationProvider;

    public static Location getLocation(Context context) {
        init(context);
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(context, "目前暂时无法定位，请检查系统设置", Toast.LENGTH_SHORT).show();
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            showLocation(location, context);
        }
        locationManager.requestLocationUpdates(locationProvider, 5000, 1, locationListener);
        return location;
    }
    private static void init(Context context0) {
        locationManager = (LocationManager)context0.getSystemService(Context.LOCATION_SERVICE);
        context = context0;
    }


    static LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location, context);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private static void showLocation(Location location, Context context) {
        String currentPosition = "latitude is " + location.getLatitude() + "\n"
                + "longitude is " + location.getLongitude();
        Toast.makeText(context, currentPosition , Toast.LENGTH_SHORT).show();
    }
}
