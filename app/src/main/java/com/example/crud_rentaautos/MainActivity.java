package com.example.crud_rentaautos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText id, placa, modelo, marca;
    Button insert, list, update;
    DatabaseHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.cod);
        placa = findViewById(R.id.placa);
        modelo = findViewById(R.id.modelo);
        marca = findViewById(R.id.marca);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        list = findViewById(R.id.btnViewData);
        DB = new DatabaseHandler(this);

        //Agregamos evento click de los botones
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codAutomovilTXT = id.getText().toString();
                String numPlacaTXT = placa.getText().toString();
                String modeloTXT = modelo.getText().toString();
                String marcaTXT = marca.getText().toString();

                Boolean checkInsert = DB.insertData(codAutomovilTXT,numPlacaTXT,modeloTXT,marcaTXT);
                if(checkInsert==true){

                    Toast.makeText(MainActivity.this, "Se ha insertado su nuevo registro",
                            Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(MainActivity.this, "No se ha podido insertar el registro",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Estructura para mostrar los registros al presionar el botón
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = DB.getData();

                //Evaluamos si ya existe el registro en la tabla
                if(result.getCount()==0){

                    Toast.makeText(MainActivity.this, "Aún no hay registros ingresados",
                            Toast.LENGTH_LONG).show();
                    return;

                }

                StringBuffer buffer = new StringBuffer();

                while(result.moveToNext()){

                    buffer.append("Código: " + result.getString(0) + "\n");
                    buffer.append("Número de placa: " + result.getString(1) + "\n");
                    buffer.append("Modelo: " + result.getString(2) + "\n");
                    buffer.append("Marca: " + result.getString(3) + "\n\n");

                }

                //Mostramos los registros
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Listado de automóviles registrados");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });

        //Estructura para botón actualizar
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codAutomovilTXT = id.getText().toString();
                String numPlacaTXT = placa.getText().toString();
                String modeloTXT = modelo.getText().toString();
                String marcaTXT = marca.getText().toString();

                Boolean checkInsert = DB.updateData(codAutomovilTXT,numPlacaTXT,modeloTXT,marcaTXT); //Llamamos al método UpdateData de la clase Databasehandler

                //Evaluamos si la tabla Data se ha actualizado
                if(checkInsert==true){

                    Toast.makeText(MainActivity.this, "Se ha actualizado el registro con éxito",
                            Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(MainActivity.this, "No se ha podido actualizar el registro",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}