package com.example.crud_rentaautos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)menuInfo;
        c.moveToPosition(info.position);
        menu.setHeaderTitle(c.getString(1));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        //Return super.onContextItemSelected(item);
        switch (item.getItemId()){
            case R.id.edit:
            try{
                String auto[]={c.getString(1), c.getString(2), c.getString(3)};
                Bundle bundle = new Bundle();
                bundle.putString("action", "edit");
                bundle.putString("id", c.getString(0));
                bundle.putStringArray("auto", auto);

                Intent formMain = new Intent(list.this, MainActivity.class);
                formMain.putExtras(bundle);
                startActivity(formMain);
            }catch (Exception e){
                Toast.makeText(list.this, "Error: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
                return true;
            default:
                return super.onContextItemSelected(item);
        }

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

            //Mostrar los registros
            do{
                allData.add(c.getString(1) + " | " + c.getString(2) + " | " + c.getString(3));

            }while (c.moveToNext());
            aData.notifyDataSetChanged();

            //Hacer referencia al listview para que se muestre el menu
            registerForContextMenu(listData);

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