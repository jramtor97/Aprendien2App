package com.example.pc.aprendien2app.GpsImplementation;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Pc on 14/10/2017.
 */

public class GPSListener implements LocationListener {

    private UsabilityGPS mainActivity;


    public GPSListener(UsabilityGPS act) {
        mainActivity = act;
    }


    @Override
    public void onLocationChanged(Location loc) {
        mainActivity.useGPS(loc);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
