package com.example.lab4penzev;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClients = findViewById(R.id.btnClients);
        Button btnDevices = findViewById(R.id.btnDevices);
        Button btnOrders = findViewById(R.id.btnOrders);

        // Переход на ClientsActivity
        btnClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ClientsActivity.class));
            }
        });

        // Переход на DevicesActivity
        btnDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DevicesActivity.class));
            }
        });

        // Переход на OrdersActivity
        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OrdersActivity.class));
            }
        });
    }
}