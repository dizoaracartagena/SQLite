package com.dizoaracartagena.sqlite1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView agregar,registros;
    private EditText insertartxt;
    private Button agregarbtn,actualizarbtn,eliminarbtn;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializaComponentes();
        cargaDatos();
    }

    private void cargaDatos() {
        AuxSQLite cnx = new AuxSQLite(this, "bd.sqlite3",null,1);
        SQLiteDatabase db = cnx.getReadableDatabase();

        String query = "SELECT * FROM Alumnos;";
        Cursor c = db.rawQuery(query,null);

        ArrayList<String> datos = new ArrayList<String>();

        if(c.moveToFirst()) {
            do {
                //LOGICA DE LECTURA
                String reg =
                        c.getString(0) + " | " +
                        c.getString(1);
                datos.add(reg);
            } while(c.moveToNext());
        }

        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,datos);
        this.spinner.setAdapter(adapter);
    }

    private void inicializaComponentes() {
        agregar = (TextView)findViewById(R.id.textView);
        registros = (TextView)findViewById(R.id.textView2);
        insertartxt = (EditText)findViewById(R.id.editText);
        agregarbtn = (Button)findViewById(R.id.button);
        actualizarbtn = (Button)findViewById(R.id.button2);
        eliminarbtn = (Button)findViewById(R.id.button3);
        spinner = (Spinner)findViewById(R.id.spinner);
        
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
        agregarbtn.setOnClickListener(v -> {
            agregarbtn_click(v);
        });
        
        actualizarbtn.setOnClickListener(v ->  {
            actualizarbtn_click(v);
        });
        
        eliminarbtn.setOnClickListener(v ->  {
            eliminarbtn_click(v);
        });
    }

    private void agregarbtn_click(View v) {
        String query = "INSERT INTO Alumnos VALUES (NULL,'" +
                insertartxt.getText().toString() + "');";

        AuxSQLite cnx = new AuxSQLite(this, "bd.sqlite3", null, 1);
        SQLiteDatabase db = cnx.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    private void actualizarbtn_click(View v) {
        cargaDatos();
        insertartxt.setText(" ");
    }

    private void eliminarbtn_click(View v) {
        int valor = spinner.getSelectedItemPosition();
        valor = valor + 1;
        //String query = "DELETE FROM Alumnos WHERE AlumnoID = valor - 1;";

        AuxSQLite admin = new AuxSQLite(this,"bd.sqlite3",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        int cantidad = db.delete("Alumnos","AlumnoID=" + valor, null);
        //db.execSQL(query);
        db.close();

        if(cantidad == 1) {
            Toast.makeText(this, "Alumno eliminado", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "No existe el alumno seleccionado",Toast.LENGTH_LONG).show();
        }
    }
}