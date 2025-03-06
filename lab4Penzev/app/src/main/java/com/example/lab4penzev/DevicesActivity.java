package com.example.lab4penzev;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class DevicesActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listViewDevices);

        loadDevices();

        // Обработка клика по элементу списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DevicesActivity.this, EditDeviceActivity.class);
                intent.putExtra("DEVICE_ID", id); // Передаем ID устройства
                startActivity(intent);
            }
        });
    }

    private void loadDevices() {
        Cursor cursor = dbHelper.getAllDevices();
        String[] from = new String[]{
                DatabaseHelper.COLUMN_DEVICE_BRAND,
                DatabaseHelper.COLUMN_DEVICE_MODEL,
                DatabaseHelper.COLUMN_DEVICE_SERIAL_NUMBER
        };
        int[] to = new int[]{R.id.textViewBrand, R.id.textViewModel, R.id.textViewSerialNumber};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.device_item, cursor, from, to, 0);
        listView.setAdapter(adapter);
    }
}