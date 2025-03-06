package com.example.lab4penzev;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class OrdersActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listViewOrders);

        loadOrders();

        // Обработка клика по элементу списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OrdersActivity.this, EditOrderActivity.class);
                intent.putExtra("ORDER_ID", id); // Передаем ID заказа
                startActivity(intent);
            }
        });
    }

    private void loadOrders() {
        Cursor cursor = dbHelper.getAllOrders();
        String[] from = new String[]{
                DatabaseHelper.COLUMN_ORDER_ISSUE_DESCRIPTION,
                DatabaseHelper.COLUMN_ORDER_STATUS
        };
        int[] to = new int[]{R.id.textViewIssueDescription, R.id.textViewStatus};
        adapter = new SimpleCursorAdapter(this, R.layout.order_item, cursor, from, to, 0);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrders(); // Обновляем список при возвращении на экран
    }
}