package com.example.pc.aprendien2app.GpsImplementation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;


public class GPSAdapter {
    private LocationManager mlocManager;
    private GPSListener mlocListener;
    private Context con;
    private UsabilityGPS act;
    private boolean connected;


    public GPSAdapter(Context context,UsabilityGPS activity) {
        connected = false;
        con = context;
        act = activity;
        init();
    }


    public void init() {

        if (ActivityCompat.checkSelfPermission(con, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           connected = false;
        }

        else {
            mlocManager = (LocationManager)con.getSystemService(Context.LOCATION_SERVICE);
            mlocListener = new GPSListener(act);
            connected = true;
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) mlocListener);
        }
    }

    public boolean getStatus() {
        return connected;
    }

}
