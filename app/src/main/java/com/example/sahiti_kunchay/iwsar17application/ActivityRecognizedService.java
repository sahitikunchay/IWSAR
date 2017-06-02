package com.example.sahiti_kunchay.iwsar17application;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Display;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sahiti_Kunchay on 6/1/2017.
 */

public class ActivityRecognizedService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ActivityRecognizedService(String name) {
        super(name);
    }

    public ActivityRecognizedService() {
        super("ActivityRecognizedService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities( result.getProbableActivities() );
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
        for( DetectedActivity activity : probableActivities ) {
            switch( activity.getType() ) {
                case DetectedActivity.IN_VEHICLE: {
                    Log.e( "ActivityRecognition", "In Vehicle: " + activity.getConfidence()+ " "+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                   /* boolean flag = isScreenOn(getApplicationContext());
                   TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
                    Log.e( "ScreenRecognition", "Screen " + flag );*/
                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    Log.e( "ActivityRecognition", "On Bicycle: " + activity.getConfidence() + " "+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    break;
                }
                case DetectedActivity.ON_FOOT: {
                    Log.e( "ActivityRecognition", "On Foot: " + activity.getConfidence() + " "+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    break;
                }
                case DetectedActivity.RUNNING: {
                    Log.e( "ActivityRecognition", "Running: " + activity.getConfidence() + " "+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    break;
                }
                case DetectedActivity.STILL: {
                    Log.e( "ActivityRecognition", "Still: " + activity.getConfidence() + " "+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    break;
                }
                case DetectedActivity.TILTING: {
                    Log.e( "ActivityRecognition", "Tilting: " + activity.getConfidence() + " "+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    break;
                }
                case DetectedActivity.WALKING: {
                    Log.e( "ActivityRecognition", "Walking: " + activity.getConfidence() + " "+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    if( activity.getConfidence() >= 75 ) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText( "Are you walking?" );
                        builder.setSmallIcon( R.mipmap.ic_launcher );
                        builder.setContentTitle( getString( R.string.app_name ) );
                        NotificationManagerCompat.from(this).notify(0, builder.build());
                    }
                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    Log.e( "ActivityRecognition", "Unknown: " + activity.getConfidence() + " "+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    break;
                }
            }
        }
    }

    public boolean isScreenOn(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
            boolean screenOn = false;
            for (Display display : dm.getDisplays()) {
                if (display.getState() != Display.STATE_OFF) {
                    screenOn = true;
                }
            }
            return screenOn;
        } else {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            //noinspection deprecation
            return pm.isScreenOn();
        }
    }


}
