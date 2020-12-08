package rohan.myappcompany.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;

public class acc_Sensor extends AppCompatActivity implements SensorEventListener {
    private TextView xtextview,ytextView,ztextView;
    private SensorManager sensorManager;
    private Sensor AccelerometerSensor;
    private boolean isAccsensorAvaliable,itISNotFirstTime = false;
    private float currentx,currenty,currentz,lastx,lasty,lastz;
    private float xdiff,ydiff,zdiff;
    private float shakeThreshold = 5f;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc__sensor);

        xtextview = findViewById(R.id.textView5);
        ytextView = findViewById(R.id.textView6);
        ztextView = findViewById(R.id.textView7);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            AccelerometerSensor  = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccsensorAvaliable = true;
        }else{
            xtextview.setText("Acceleometer is not avaliable");
            xtextview.setTextColor(Color.RED);
            isAccsensorAvaliable = false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        xtextview.setText(event.values[0] + "m/s2");
        ytextView.setText(event.values[1]+ "m/s2");
        ztextView.setText(event.values[2] + "m/s2");

        currentx = event.values[0];
        currenty = event.values[1];
        currentz = event.values[2];

        if(itISNotFirstTime){
            xdiff = Math.abs(lastx-currentx);
            ydiff = Math.abs(lasty-currenty);
            zdiff = Math.abs(lastz-currentz);

            if((xdiff > shakeThreshold && ydiff > shakeThreshold) ||
            (xdiff > shakeThreshold || zdiff > shakeThreshold) ||
                    (ydiff > shakeThreshold || zdiff > shakeThreshold){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
                }else{
                    vibrator.vibrate(500);
                    // depricated
                }
            }

        }

        lastx = currentx;
        lasty = currenty;
        lastz = currentz;
        itISNotFirstTime = true;




    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isAccsensorAvaliable){
            sensorManager.registerListener(this,AccelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}