package com.example.lab10penzev;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CALENDAR_PERMISSIONS = 100; // Код запроса разрешений

    // Объявление переменных для элементов интерфейса
    private EditText eventTitle; // Поле ввода для названия события
    private Button addEventButton; // Кнопка для добавления события
    private Button viewEventsButton; // Кнопка для просмотра событий
    private TextView eventsList; // Текстовое поле для отображения списка событий

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Вызов метода родителя
        setContentView(R.layout.activity_main); // Установка макета активности

        // Инициализация элементов интерфейса из макета
        eventTitle = findViewById(R.id.eventTitle);
        addEventButton = findViewById(R.id.addEventButton);
        viewEventsButton = findViewById(R.id.viewEventsButton);
        eventsList = findViewById(R.id.eventsList);

        // Установка обработчика клика для кнопки добавления события
        addEventButton.setOnClickListener(v -> {
            // Проверка разрешения на запись в календарь
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                    != PackageManager.PERMISSION_GRANTED) {
                // Если разрешение не предоставлено, запросить его
                requestPermissions();
            } else {
                // Если разрешение есть, добавить событие
                addEvent();
            }
        });

        // Установка обработчика клика для кнопки просмотра событий
        viewEventsButton.setOnClickListener(v -> {
            // Проверка разрешения на чтение календаря
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
                    != PackageManager.PERMISSION_GRANTED) {
                // Если разрешение не предоставлено, запросить его
                requestPermissions();
            } else {
                // Если разрешение есть, отобразить список событий
                displayEvents();
            }
        });
    }

    // Метод для запроса разрешений на чтение и запись в календарь
    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                REQUEST_CALENDAR_PERMISSIONS
        );
    }

    // Метод для добавления события в календарь
    private void addEvent() {
        // Получение названия события из поля ввода
        String title = eventTitle.getText().toString().trim();
        if (title.isEmpty()) {
            // Если название пустое, показать предупреждение
            Toast.makeText(this, "Пожалуйста, введите название события", Toast.LENGTH_SHORT).show();
            return;
        }

        // Время начала события (текущее время)
        long startTime = System.currentTimeMillis();
        // Время завершения события (через 1 час)
        long endTime = startTime + (60 * 60 * 1000);  // 1 час в миллисекундах

        // Создание объекта ContentValues для добавления данных в календарь
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1); // ID календаря
        values.put(CalendarContract.Events.TITLE, title); // Название события
        values.put(CalendarContract.Events.DTSTART, startTime); // Время начала события
        values.put(CalendarContract.Events.DTEND, endTime); // Время завершения события
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "UTC"); // Часовой пояс события

        // Вставка нового события в календарь и получение URI для подтверждения успеха
        Uri uri = getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);

        if (uri != null) {
            // Если вставка прошла успешно, показать сообщение о добавлении события
            Toast.makeText(this, "Событие добавлено", Toast.LENGTH_SHORT).show();
        } else {
            // Если произошла ошибка, показать соответствующее сообщение
            Toast.makeText(this, "Не удалось добавить событие", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод для отображения списка событий из календаря
    private void displayEvents() {
        // URI для получения списка событий
        Uri uri = CalendarContract.Events.CONTENT_URI;
        // Определение, какие данные запрашиваются
        String[] projection = {
                CalendarContract.Events.TITLE, // Название события
                CalendarContract.Events.DTSTART // Время начала события
        };

        // Запрос к календарю для получения событий
        Cursor cursor = getContentResolver().query(
                uri,
                projection,
                null, // Без условий
                null, // Без параметров
                CalendarContract.Events.DTSTART + " ASC"  // Сортировка по времени
        );

        if (cursor == null) {
            // Если запрос не успешен, показать соответствующее сообщение
            Toast.makeText(this, "Не удалось прочитать события", Toast.LENGTH_SHORT).show();
            return;
        }

        // Создание строки для вывода списка событий
        StringBuilder events = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        // Перебор результатов запроса
        while (cursor.moveToNext()) {
            String eventName = cursor.getString(0); // Получение названия события
            long eventTimeMillis = cursor.getLong(1); // Время начала события в миллисекундах

            // Преобразование времени в читаемый формат
            String formattedDate = dateFormat.format(new Date(eventTimeMillis));

            // Добавление информации о событии в строку вывода
            events.append("Событие: ").append(eventName)
                    .append(" | Время: ").append(formattedDate).append("\n");
        }

        // Установка строки вывода в текстовое поле
        eventsList.setText(events.toString());

        // Закрытие курсора после использования
        cursor.close();
    }

    // Метод для обработки результата запроса разрешений
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALENDAR_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Если разрешение предоставлено, показать сообщение
                Toast.makeText(this, "Разрешения предоставлены", Toast.LENGTH_SHORT).show();
            } else {
                // Если разрешение не предоставлено, показать диалог для запроса повторно
                new AlertDialog.Builder(this)
                        .setTitle("Требуются разрешения")
                        .setMessage("Для работы приложения требуются разрешения для доступа к календарю.")
                        .setPositiveButton("OK", (dialog, which) -> requestPermissions()) // Повторный запрос
                        .show();
            }
        }
    }
}