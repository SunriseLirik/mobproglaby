package com.example.lab4penzev;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditDeviceActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText editTextBrand, editTextModel, editTextSerialNumber;
    private Button buttonSave, buttonDelete;
    private long deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device);

        dbHelper = new DatabaseHelper(this);

        editTextBrand = findViewById(R.id.editTextBrand);
        editTextModel = findViewById(R.id.editTextModel);
        editTextSerialNumber = findViewById(R.id.editTextSerialNumber);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDelete);

        deviceId = getIntent().getLongExtra("DEVICE_ID", -1);

        if (deviceId != -1) {
            loadDeviceData();
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDevice();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDevice();
            }
        });
    }

    private void loadDeviceData() {
        Cursor cursor = dbHelper.getDeviceById(deviceId);
        if (cursor.moveToFirst()) {
            editTextBrand.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DEVICE_BRAND)));
            editTextModel.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DEVICE_MODEL)));
            editTextSerialNumber.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DEVICE_SERIAL_NUMBER)));
        }
        cursor.close();
    }

    private void saveDevice() {
        String brand = editTextBrand.getText().toString();
        String model = editTextModel.getText().toString();
        String serialNumber = editTextSerialNumber.getText().toString();

        if (deviceId == -1) {
            dbHelper.addDevice(-1, brand, model, serialNumber); // Добавление нового устройства
        } else {
            dbHelper.updateDevice(deviceId, brand, model, serialNumber); // Обновление существующего
        }

        Toast.makeText(this, "Устройство сохранено", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteDevice() {
        dbHelper.deleteDevice(deviceId);
        Toast.makeText(this, "Устройство удалено", Toast.LENGTH_SHORT).show();
        finish();
    }
}