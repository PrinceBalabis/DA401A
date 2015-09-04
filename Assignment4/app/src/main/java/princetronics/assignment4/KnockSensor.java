package princetronics.assignment4;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Prince on 04-Sep-15.
 */
public class KnockSensor {
    final private String TAG = "KnockSensor";
    /* put this into your activity class */
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private MainActivity mainActivity;
    // How sensitive it is listening for knocks! Make sure this is high enough not to trigger
    // while tapping screen(media buttons)
    private double knockSensitivity = 3;
    private long previousDetectedAccelerationChange = 0;
    private long detectedVibrationDebounceTime = 250; // How long to ignore detected vibrations after last was detected(ms)
    private long lastKnockTimeOut = 500; // How long you have to make another knock before the next knock is considered a new command
    private int amountKnocksDetected = 0; // this keeps count on how many knocks has been detected in a command

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter

            if (mAccel > knockSensitivity) {  // What happens when a vibration is detected
                long timeSincePreviousDetectedVibration = SystemClock.elapsedRealtime() - previousDetectedAccelerationChange;

                // If knock timeout - then run command
                if (lastKnockTimeOut < timeSincePreviousDetectedVibration)
                    if (amountKnocksDetected == 1) { // Play/pause command
                        mainActivity.togglePlayPause();
                        amountKnocksDetected = 0;
                        Log.d(TAG, "Knock-Play/pause command");
                    } else if (amountKnocksDetected == 2) { // Next command
                        mainActivity.playNextSong();
                        amountKnocksDetected = 0;
                        Log.d(TAG, "Knock-Next command");
                    } else if (amountKnocksDetected == 3) { // Previous command
                        mainActivity.playPreviousSong();
                        amountKnocksDetected = 0;
                        Log.d(TAG, "Knock-Previous command");
                    }

                // If last detected vibration was more than the set milliseconds ago then stamp this detection a new knock
                if (detectedVibrationDebounceTime < timeSincePreviousDetectedVibration) {
                    //Log.d(TAG, String.valueOf((SystemClock.elapsedRealtime() - previousDetectedAccelerationChange))); // Uncomment for debug
                    Log.d(TAG, "New knock detected!");

                    if (amountKnocksDetected == 0) {
                        amountKnocksDetected++;
                        Log.d(TAG, "First knock!");
                    }
                    //Count knocks if knock time out has not been reached
                    if (lastKnockTimeOut > timeSincePreviousDetectedVibration) {
                        if (amountKnocksDetected == 1) {
                            amountKnocksDetected++;
                            Log.d(TAG, "Second knock!");
                        } else if (amountKnocksDetected == 2) {
                            amountKnocksDetected++;
                            Log.d(TAG, "Third knock!");
                        }
                    }
                }

                // Remember what time this acceleration change was detected
                previousDetectedAccelerationChange = SystemClock.elapsedRealtime();
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public KnockSensor(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        mSensorManager = (SensorManager) this.mainActivity.getSystemService(this.mainActivity.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

}
