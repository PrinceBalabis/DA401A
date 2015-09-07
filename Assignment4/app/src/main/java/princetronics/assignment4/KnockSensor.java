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
    private long lastKnockTimeOut = 1000; // How long you have to make another knock before the next knock is considered a new command
    private int amountKnocksDetected = 0; // this keeps count on how many knocks has been detected in a command
    private long timeSincePreviousDetectedThresholdReach = 0;
    private long lastKnockTime = 0;
    private final SensorEventListener mSensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter

            // If knock timeout - then run command
            if (lastKnockTimeOut < (SystemClock.elapsedRealtime() - previousDetectedAccelerationChange))
                if (amountKnocksDetected == 1) { // Play/pause command
                    mainActivity.togglePlayPause();
                    amountKnocksDetected = 0;
                    Log.d(TAG, "Knock-Play/pause command");
                    Log.d(TAG, "--------------------------");
                } else if (amountKnocksDetected == 2) { // Next command
                    mainActivity.playNextSong();
                    amountKnocksDetected = 0;
                    Log.d(TAG, "Knock-Next command");
                    Log.d(TAG, "--------------------------");
                } else if (amountKnocksDetected == 3) { // Previous command
                    mainActivity.playPreviousSong();
                    amountKnocksDetected = 0;
                    Log.d(TAG, "Knock-Previous command");
                    Log.d(TAG, "--------------------------");
                }


            // knock or "knock-echo" detected
            if (mAccel > knockSensitivity) {
                // Calculate how long time ago since knock or knock-echo
                timeSincePreviousDetectedThresholdReach = SystemClock.elapsedRealtime() - previousDetectedAccelerationChange;

                // If last detected knock or "knock-echo" was more than the set milliseconds ago then it's sure its a new knock
                if (detectedVibrationDebounceTime < timeSincePreviousDetectedThresholdReach) {
                    //Log.d(TAG, String.valueOf((SystemClock.elapsedRealtime() - previousDetectedAccelerationChange))); // Uncomment for debug
                    //Log.d(TAG, "New knock detected!");

                    if (amountKnocksDetected == 0) {
                        amountKnocksDetected++;
                        Log.d(TAG, "--------------------------");
                        Log.d(TAG, "First knock!");
                    } else if (lastKnockTimeOut > timeSincePreviousDetectedThresholdReach) {
                        if (amountKnocksDetected == 1) {
                            amountKnocksDetected++;
                            Log.d(TAG, "Second knock!");
                        } else if (amountKnocksDetected == 2) {
                            amountKnocksDetected++;
                            Log.d(TAG, "Third knock!");
                        }
                    }
                } else {
                    //Log.d(TAG, "Just knock-echo!");
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
