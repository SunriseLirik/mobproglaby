package com.example.lab4penzev;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ClientsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listViewClients);

        loadClients();
    }

    private void loadClients() {
        Cursor cursor = dbHelper.getAllClients();
        String[] from = new String[]{DatabaseHelper.COLUMN_CLIENT_NAME, DatabaseHelper.COLUMN_CLIENT_PHONE, DatabaseHelper.COLUMN_CLIENT_EMAIL};
        int[] to = new int[]{R.id.textViewName, R.id.textViewPhone, R.id.textViewEmail};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.client_item, cursor, from, to, 0);
        listView.setAdapter(adapter);
    }
}