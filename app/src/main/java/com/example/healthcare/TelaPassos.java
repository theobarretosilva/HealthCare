package com.example.healthcare;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.google.firebase.firestore.DocumentReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaPassos extends AppCompatActivity implements SensorEventListener {

    TextView dataAtual, dectaPasso;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date data = new Date();
    String dataHoje = sdf.format(data);

    SimpleDateFormat sdfTraco = new SimpleDateFormat("dd-MM-yyyy");
    Date dataTraco = new Date();
    String dataHojeTraco = sdfTraco.format(dataTraco);

    private SensorManager mSensorManager;
    private Sensor mStepDetectorSensor;
    int totalPassos = 0;

    @RequiresApi(api = Build.VERSION_CODES.Q)
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

        setarData();
        setarQtdPassos();
        verificaPermissaoSensores();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void verificaPermissaoSensores(){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(TelaPassos.this, "Permissão negada.", Toast.LENGTH_SHORT).show();
            }
        };
        showDialogPermissao(permissionListener, new String[]{Manifest.permission.BODY_SENSORS, Manifest.permission.ACTIVITY_RECOGNITION});
    }

    private void showDialogPermissao(PermissionListener listener, String[] permissoes){
        TedPermission.create()
                .setPermissionListener(listener)
                .setDeniedTitle("Permissões")
                .setDeniedMessage("Você negou a permissão para acessar o sensor de movimento do dispositivo, deseja permitir?")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Sim")
                .setPermissions(permissoes)
                .check();
    }

    public void setarData(){
        String textoPronto = "\uD83C\uDFC3\uD83C\uDFFD " + dataHoje;

        dataAtual.setText(textoPronto);
    }

    public void voltarTelaConteudos(View g){
        if (TelaLogin.premium) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
            ActivityCompat.startActivity(TelaPassos.this, new Intent(this, TelaConteudos_Premium.class), activityOptionsCompat.toBundle());
        } else {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
            ActivityCompat.startActivity(TelaPassos.this, new Intent(this, TelaConteudos.class), activityOptionsCompat.toBundle());
        }
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
            if (totalPassos <= 6000){
                totalPassos ++;
                dectaPasso.setText(""+totalPassos);

                Map<String, Object> passosBD = new HashMap<>();
                passosBD.put("Passos dados", totalPassos);

                DocumentReference dr = FirebaseHelper.getFirebaseFirestore()
                        .collection("Usuarios")
                        .document(FirebaseHelper.getUIDUsuario())
                        .collection("Informações pessoais")
                        .document("Registros")
                        .collection("Passos")
                        .document(dataHojeTraco);
                dr.set(passosBD);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setarQtdPassos(){
        DocumentReference dr = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Registros")
                .collection("Passos")
                .document(dataHojeTraco);

        dr.addSnapshotListener((documentSnapshot, error) -> {
            if (documentSnapshot.exists()){
                totalPassos = Math.toIntExact((Long) documentSnapshot.getData().get("Passos dados"));

                if (totalPassos <= 6000){
                    dectaPasso.setText(""+totalPassos);
                }
            }
        });
    }
}