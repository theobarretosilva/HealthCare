package com.example.healthcare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class item_medicamento extends AppCompatActivity {

    Button btnINgerido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_medicamento);

        btnINgerido = findViewById(R.id.ingeridoMedica);
    }

    public void ingerirMedicamento(View a){
        btnINgerido.setText("Ingerido");
        btnINgerido.setEnabled(false);
    }
}
