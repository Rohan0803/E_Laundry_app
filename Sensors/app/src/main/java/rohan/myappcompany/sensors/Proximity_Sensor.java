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

import org.w3c.dom.Text;

public class Proximity_Sensor extends AppCompatActivity implements SensorEventListener {
    private TextView textView;
    private SensorManager sensorManager;
    private Sensor proximitysensor;
    private boolean isproximitysensorAvaliable;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity__sensor);
        textView =findViewById(R.id.textView4);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!= null){
            proximitysensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            isproximitysensorAvaliable = true;

        }else{
            textView.setText("proximity sensor is not avaliable in your device");
            textView.setTextColor(Color.RED);
            isproximitysensorAvaliable = false;
        }


    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        textView.setText(event.values[0]+" cm");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            vibrator.vibrate(500);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isproximitysensorAvaliable){
            sensorManager.registerListener(this,proximitysensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isproximitysensorAvaliable) {
            sensorManager.unregisterListener(this);
        }
    }
}