package com.example.crud_rentaautos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class list extends AppCompatActivity {

    DatabaseHandler DB;
    public Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Mostrar datos en el list
        ShowData();
    }

    private void ShowData(){

        DB = new DatabaseHandler(list.this);
        c = DB.getData();

        //evaluar si existen registros en la tabla
        if(c.moveToFirst()){
            ListView listData = (ListView) findViewById(R.id.listData);

            //Crear un array list
            final ArrayList<String> allData = new ArrayList<String>();

            //Crear array adapter
            final ArrayAdapter<String> aData = new ArrayAdapter<String>(list.this,
                    android.R.layout.simple_expandable_list_item_1, allData);

            //Agregar el adaptador al listview
            listData.setAdapter(aData);

            //Mostrar el adaptador el listview
            listData.setAdapter(aData);

            //Mostrar los registros
            do{
                allData.add(c.getString(1));
            }while (c.moveToNext());

        }else{
            Toast.makeText(list.this, "No hay registros para mostrar",
                    Toast.LENGTH_LONG).show();
            return;
        }

    }

    public void addData(View v){
        Intent add = new Intent(list.this, MainActivity.class);
        startActivity(add);
    }

}