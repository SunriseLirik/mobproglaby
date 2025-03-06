package com.example.lab4penzev;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditOrderActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText editTextIssueDescription, editTextStatus;
    private Button buttonSave, buttonDelete;
    private long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        dbHelper = new DatabaseHelper(this);

        editTextIssueDescription = findViewById(R.id.editTextIssueDescription);
        editTextStatus = findViewById(R.id.editTextStatus);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDelete);

        orderId = getIntent().getLongExtra("ORDER_ID", -1);

        if (orderId != -1) {
            loadOrderData();
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrder();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOrder();
            }
        });
    }

    private void loadOrderData() {
        Cursor cursor = dbHelper.getOrderById(orderId);
        if (cursor.moveToFirst()) {
            editTextIssueDescription.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_ISSUE_DESCRIPTION)));
            editTextStatus.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_STATUS)));
        }
        cursor.close();
    }

    private void saveOrder() {
        String issueDescription = editTextIssueDescription.getText().toString();
        String status = editTextStatus.getText().toString();

        if (orderId == -1) {
            dbHelper.addOrder(-1, issueDescription, status, "2023-10-01", "2023-10-01"); // Добавление нового заказа
        } else {
            dbHelper.updateOrder(orderId, issueDescription, status); // Обновление существующего
        }

        Toast.makeText(this, "Заказ сохранен", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteOrder() {
        dbHelper.deleteOrder(orderId);
        Toast.makeText(this, "Заказ удален", Toast.LENGTH_SHORT).show();
        finish();
    }
}