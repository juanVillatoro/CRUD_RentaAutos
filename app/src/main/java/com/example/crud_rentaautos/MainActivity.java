package com.example.crud_rentaautos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String idP;
    EditText id, placa, modelo, marca;
    Button insert, list, update, delete;
    DatabaseHandler DB;
    String action="new";

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
        delete = findViewById(R.id.btndelete);
        DB = new DatabaseHandler(this);

        showData();

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

        //Botón eliminar
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String idTXT=id.getText().toString();

                Boolean checkdeleteData=DB.deleteData(idTXT);

                if(checkdeleteData==true){

                    Toast.makeText(MainActivity.this, "El registro se eliminó,",
                            Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this, "El registro no se pudo eliminar",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    //Mostrar los datos recibidos del List
    private void showData() {

        try{

            Bundle bundle = getIntent().getExtras();
            action = bundle.getString("action");
            if(action.equals("edit")){

                //Mostrar/ocultar botones
                update.setVisibility(View.VISIBLE);
                insert.setVisibility(View.GONE);

                idP = bundle.getString("id");
                String auto[]=bundle.getStringArray("auto");
                TextView tempVal = (TextView) findViewById(R.id.cod);
                tempVal.setText(idP);

                tempVal=(EditText) findViewById(R.id.placa);
                tempVal.setText(auto[0].toString());

                tempVal=(EditText) findViewById(R.id.modelo);
                tempVal.setText(auto[1].toString());

                tempVal=(EditText) findViewById(R.id.marca);
                tempVal.setText(auto[2].toString());

            }
        }catch (Exception e){

            Toast.makeText(MainActivity.this, "Error: " +
                    e.getMessage().toString(), Toast.LENGTH_LONG).show();

        }


    }


}