package net.braingang.mellow.hound.app;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.braingang.houndlib.service.GeoLocService;
import net.braingang.mellow.hound.R;


public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getName();

    private static final int BASE_REQUEST = 1234;
    private static final int COARSE_LOCATION_REQUEST = BASE_REQUEST;
    private static final int FINE_LOCATION_REQUEST = BASE_REQUEST+1;
    private static final int EXTERNAL_STORAGE_REQUEST = BASE_REQUEST+2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (canAccessCoarseLocation()) {
            Log.i(LOG_TAG, "coarse already granted");
        } else {
            Log.i(LOG_TAG, "coarse must granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION_REQUEST);
        }

        if (canAccessFineLocation()) {
            Log.i(LOG_TAG, "fine already granted");
        } else {
            Log.i(LOG_TAG, "fine must granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUEST);
        }

        if (canAccessExternalStorage()) {
            Log.i(LOG_TAG, "storage already granted");
        } else {
            Log.i(LOG_TAG, "storage must granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i(LOG_TAG, "onRequestPermissions:" + requestCode + ":" + permissions.length + ":" + grantResults.length);

        if (canAccessCoarseLocation() && canAccessFineLocation()) {
            //GeoLocService.startGeoLoc(this);
            GeoLocService.startGeoLoc(this, 2000L);
        } else {
            Log.i(LOG_TAG, "unable to start geoloc");
        }
    }

    public static final float GEO_MIN_DISTANCE = 1000L;
    public static final long GEO_MIN_TIME = 60 * 1000L;

    private boolean canAccessCoarseLocation() {
        boolean flag = (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        return flag;
    }

    private boolean canAccessFineLocation() {
        boolean flag = (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        return flag;
    }

    private boolean canAccessExternalStorage() {
        boolean flag = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        return flag;
    }
}
