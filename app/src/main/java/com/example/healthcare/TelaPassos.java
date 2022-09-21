package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaPassos extends AppCompatActivity implements SensorEventListener {

    TextView dataAtual, dectaPasso;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date data = new Date();
    String dataHoje = sdf.format(data);

    private SensorManager mSensorManager;
    private Sensor mStepDetectorSensor;
    int totalPassos = 0;
//    int requestCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_passos);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        dataAtual = findViewById(R.id.dataAtualPassos);
        dectaPasso = findViewById(R.id.dectaPasso);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

//        if(ContextCompat.checkSelfPermission(this,
//                Manifest.permission.HIGH_SAMPLING_RATE_SENSORS) == PackageManager.PERMISSION_DENIED){
//            //ask for permission
//            requestPermissions(new String[]{Manifest.permission.HIGH_SAMPLING_RATE_SENSORS}, requestCode );
//        }

        setarData();
    }

    public void setarData(){
        String textoPronto = "\uD83C\uDFC3\uD83C\uDFFD " + dataHoje;

        dataAtual.setText(textoPronto);
    }

    public void voltarTelaConteudos(View g){
        Intent voltarTelaConteudos = new Intent(this, TelaConteudos.class);
        startActivity(voltarTelaConteudos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if(sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            //Detectou passo
            totalPassos ++;
            dectaPasso.setText(""+totalPassos);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}