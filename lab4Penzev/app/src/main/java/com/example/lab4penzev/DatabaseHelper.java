package com.example.lab4penzev;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PhoneRepairService.db";
    public static final int DATABASE_VERSION = 2;

    // Таблица Clients
    public static final String TABLE_CLIENTS = "clients";
    public static final String COLUMN_CLIENT_ID = "id";
    public static final String COLUMN_CLIENT_NAME = "name";
    public static final String COLUMN_CLIENT_PHONE = "phone";
    public static final String COLUMN_CLIENT_EMAIL = "email";

    // Таблица Devices
    public static final String TABLE_DEVICES = "devices";
    public static final String COLUMN_DEVICE_ID = "id";
    public static final String COLUMN_DEVICE_CLIENT_ID = "client_id";
    public static final String COLUMN_DEVICE_BRAND = "brand";
    public static final String COLUMN_DEVICE_MODEL = "model";
    public static final String COLUMN_DEVICE_SERIAL_NUMBER = "serial_number";

    // Таблица Orders
    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ORDER_ID = "id";
    public static final String COLUMN_ORDER_DEVICE_ID = "device_id";
    public static final String COLUMN_ORDER_ISSUE_DESCRIPTION = "issue_description";
    public static final String COLUMN_ORDER_STATUS = "status";
    public static final String COLUMN_ORDER_CREATED_AT = "created_at";
    public static final String COLUMN_ORDER_UPDATED_AT = "updated_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Создание таблицы Clients
            db.execSQL("CREATE TABLE " + TABLE_CLIENTS + "("
                    + COLUMN_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CLIENT_NAME + " TEXT,"
                    + COLUMN_CLIENT_PHONE + " TEXT,"
                    + COLUMN_CLIENT_EMAIL + " TEXT" + ")");

            // Создание таблицы Devices
            db.execSQL("CREATE TABLE " + TABLE_DEVICES + "("
                    + COLUMN_DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DEVICE_CLIENT_ID + " INTEGER,"
                    + COLUMN_DEVICE_BRAND + " TEXT,"
                    + COLUMN_DEVICE_MODEL + " TEXT,"
                    + COLUMN_DEVICE_SERIAL_NUMBER + " TEXT,"
                    + "FOREIGN KEY(" + COLUMN_DEVICE_CLIENT_ID + ") REFERENCES " + TABLE_CLIENTS + "(" + COLUMN_CLIENT_ID + ")" + ")");

            // Создание таблицы Orders
            db.execSQL("CREATE TABLE " + TABLE_ORDERS + "("
                    + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ORDER_DEVICE_ID + " INTEGER,"
                    + COLUMN_ORDER_ISSUE_DESCRIPTION + " TEXT,"
                    + COLUMN_ORDER_STATUS + " TEXT,"
                    + COLUMN_ORDER_CREATED_AT + " TEXT,"
                    + COLUMN_ORDER_UPDATED_AT + " TEXT,"
                    + "FOREIGN KEY(" + COLUMN_ORDER_DEVICE_ID + ") REFERENCES " + TABLE_DEVICES + "(" + COLUMN_DEVICE_ID + ")" + ")");

            Log.d("DatabaseHelper", "Таблицы созданы успешно");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Ошибка при создании таблиц: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    // Методы для работы с таблицей Clients
    public long addClient(String name, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENT_NAME, name);
        values.put(COLUMN_CLIENT_PHONE, phone);
        values.put(COLUMN_CLIENT_EMAIL, email);
        return db.insert(TABLE_CLIENTS, null, values);
    }

    public Cursor getAllClients() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CLIENTS, null);
    }

    // Методы для работы с таблицей Devices
    public long addDevice(int clientId, String brand, String model, String serialNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DEVICE_CLIENT_ID, clientId);
        values.put(COLUMN_DEVICE_BRAND, brand);
        values.put(COLUMN_DEVICE_MODEL, model);
        values.put(COLUMN_DEVICE_SERIAL_NUMBER, serialNumber);
        return db.insert(TABLE_DEVICES, null, values);
    }

    public Cursor getAllDevices() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_DEVICES, null);
    }

    // Методы для работы с таблицей Orders
    public long addOrder(int deviceId, String issueDescription, String status, String createdAt, String updatedAt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_DEVICE_ID, deviceId);
        values.put(COLUMN_ORDER_ISSUE_DESCRIPTION, issueDescription);
        values.put(COLUMN_ORDER_STATUS, status);
        values.put(COLUMN_ORDER_CREATED_AT, createdAt);
        values.put(COLUMN_ORDER_UPDATED_AT, updatedAt);
        return db.insert(TABLE_ORDERS, null, values);
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
    }
    // Методы для работы с устройствами
    public Cursor getDeviceById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_DEVICES, null, COLUMN_DEVICE_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
    }

    public int updateDevice(long id, String brand, String model, String serialNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DEVICE_BRAND, brand);
        values.put(COLUMN_DEVICE_MODEL, model);
        values.put(COLUMN_DEVICE_SERIAL_NUMBER, serialNumber);
        return db.update(TABLE_DEVICES, values, COLUMN_DEVICE_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteDevice(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DEVICES, COLUMN_DEVICE_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Методы для работы с заказами
    public Cursor getOrderById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ORDERS, null, COLUMN_ORDER_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
    }

    public int updateOrder(long id, String issueDescription, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_ISSUE_DESCRIPTION, issueDescription);
        values.put(COLUMN_ORDER_STATUS, status);
        return db.update(TABLE_ORDERS, values, COLUMN_ORDER_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteOrder(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, COLUMN_ORDER_ID + "=?", new String[]{String.valueOf(id)});
    }
}