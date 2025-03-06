package com.example.lab9penzev;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private View circleView;
    private RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов интерфейса
        circleView = findViewById(R.id.circleView);
        mainLayout = findViewById(R.id.mainLayout);

        // Регистрация контекстного меню для circleView
        registerForContextMenu(circleView);
    }

    // Создание главного меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Обработка выбора пунктов главного меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Изменение размера круга
        if (id == R.id.smallCircle) {
            resizeCircle(100);
            return true;
        } else if (id == R.id.mediumCircle) {
            resizeCircle(150);
            return true;
        } else if (id == R.id.largeCircle) {
            resizeCircle(200);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Метод для изменения размера круга
    private void resizeCircle(int size) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) circleView.getLayoutParams();
        params.width = size;
        params.height = size;
        circleView.setLayoutParams(params);
    }

    // Создание контекстного меню
    @Override
    public void onCreateContextMenu(android.view.ContextMenu menu, View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    // Обработка выбора пунктов контекстного меню
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Перемещение круга
        if (id == R.id.moveLeft) {
            moveCircle(-50);
            return true;
        } else if (id == R.id.moveRight) {
            moveCircle(50);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    // Метод для перемещения круга
    private void moveCircle(int offset) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) circleView.getLayoutParams();
        params.leftMargin += offset;
        circleView.setLayoutParams(params);
    }
}