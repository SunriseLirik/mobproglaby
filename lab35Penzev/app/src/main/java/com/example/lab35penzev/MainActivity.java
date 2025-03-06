package com.example.lab35penzev;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String myLastName = "Пензев Константин";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLastName("Button 1");
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLastName("Button 2");
            }
        });
    }

    private void showLastName(String buttonName) {
        Toast.makeText(this, "Ваша фамилия: " + myLastName + " (" + buttonName + ")", Toast.LENGTH_SHORT).show();
    }
}
