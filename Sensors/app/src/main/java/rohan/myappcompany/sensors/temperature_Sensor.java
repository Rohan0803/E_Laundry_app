package rohan.myappcompany.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class temperature_Sensor extends AppCompatActivity implements SensorEventListener {
    private TextView textView;
    private SensorManager sensorManager;
    private Sensor Tempsensor;
    private boolean isTempSensorAvaliable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature__sensor);
        textView = findViewById(R.id.textView2);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            Tempsensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTempSensorAvaliable = true;
        }else{
            textView.setText("Temperature Sensor is not avaliable");
            textView.setTextColor(Color.RED);
            isTempSensorAvaliable = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isTempSensorAvaliable){
            sensorManager.registerListener(this,Tempsensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!isTempSensorAvaliable){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textView.setText(event.values[0]+" °C");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}