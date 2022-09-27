package com.datpv134.sqlitedemo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLHelper";
    private static final String DB_NAME = "OrderFoods.db";
    private static final String DB_TABLE = "Foods";
    private static final int DB_VERSION = 1;

    private static final String DB_FOODS_ID = "id";
    private static final String DB_FOODS_NAME = "name";
    private static final String DB_FOODS_NUMBER = "number";
    private static final String DB_FOODS_PRICE = "price";

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryCreateTable = "CREATE TABLE " + DB_TABLE + "(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "number INTEGER," +
                "price INTEGER)";

        sqLiteDatabase.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public void addFood(Food food) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_FOODS_NAME, food.getName());
        contentValues.put(DB_FOODS_NUMBER, food.getNumber());
        contentValues.put(DB_FOODS_PRICE, food.getPrice());

        sqLiteDatabase.insert(DB_TABLE, null, contentValues);
    }

    public void onUpdateFoods(String id, Food food) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_FOODS_NAME, food.getName());
        contentValues.put(DB_FOODS_NUMBER, food.getNumber());
        contentValues.put(DB_FOODS_PRICE, food.getPrice());

        sqLiteDatabase.update(DB_TABLE, contentValues, "id=?", new String[]{String.valueOf(id)});
    }

    public void onDeleteAll() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE, null, null);
    }

    public void onDeleteFoods(String id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE, "id=?", new String[]{String.valueOf(id)});
    }

    public List<Food> getAllFood() {
        List<Food> foodList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(false, DB_TABLE,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DB_FOODS_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DB_FOODS_NAME));
            @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(DB_FOODS_NUMBER));
            @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex(DB_FOODS_PRICE));

            foodList.add(new Food(id, name, number, price));
        }

        return foodList;
    }
}
