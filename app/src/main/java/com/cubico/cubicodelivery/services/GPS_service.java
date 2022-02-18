package com.cubico.cubicodelivery.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class GPS_service extends Service {
    private LocationListener listener;
    private LocationManager locationManager;
   // private List<Address> addresses=null;
     @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @SuppressLint("MissingPermission")
    public void onCreate() {
        this.listener = new LocationListener() {
            public void onLocationChanged(Location location) {

                Intent i = new Intent("location_update");
                i.putExtra("Longitud", location.getLongitude());
                i.putExtra("Latitud", location.getLatitude());
                GPS_service.this.sendBroadcast(i);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                GPS_service.this.startActivity(intent);
            }
        };
        this.locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0.0f, this.listener);
        }
    }

    @SuppressLint("MissingPermission")
    public void onDestroy() {
        super.onDestroy();
        LocationManager locationManager2 = this.locationManager;
        if (locationManager2 != null) {
            locationManager2.removeUpdates(this.listener);
        }
    }

}
