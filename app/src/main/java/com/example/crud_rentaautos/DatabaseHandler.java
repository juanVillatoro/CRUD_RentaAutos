package com.example.crud_rentaautos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Constructor de base
    public DatabaseHandler(Context context) {

        super(context, "renta.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE auto(codAutomovil TEXT PRIMARY KEY, numPlaca TEXT, modelo TEXT, marca TEXT)"); //creamos la tabla para la base de datos

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS auto");

    }

    //metodos para el CRUD
    public Boolean insertData(String codAutomovil, String numPlaca, String modelo, String marca){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("codAutomovil", codAutomovil);
        contentValues.put("numPlaca", numPlaca);
        contentValues.put("modelo", modelo);
        contentValues.put("marca", marca);
        long results = db.insert("auto", null, contentValues);
        if(results==-1){
            return false;
        }else{
            return true;
        }
    }

    //Obtener los datos ingresados
    public Cursor getData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM auto", null);
        return cursor;
    }

    //Estructura para actualizar los datos
    public Boolean updateData(String codAutomovil, String numPlaca, String modelo, String marca){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("codAutomovil", codAutomovil);
        contentValues.put("numPlaca", numPlaca);
        contentValues.put("modelo", modelo);
        contentValues.put("marca", marca);

        //Buscamos el registro usando el codigo para actualizar
        Cursor cursor = db.rawQuery("SELECT * FROM auto WHERE codAutomovil=?", new String[]{codAutomovil});

        //Evaluamos si el registro existe
        if(cursor.getCount()>0){

            long result = db.update("auto", contentValues, "codAutomovil=?", new String[]{codAutomovil});
            if(result==-1){
                return false;
            }else{
                return true;
            }

        }else{
            return false;
        }

    }

}
