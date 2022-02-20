package com.example.examen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.examen.Contactos.Contactos;
import com.example.examen.Transacciones.Transacciones;

import java.util.ArrayList;

public class ActivityVerContacto extends AppCompatActivity {
    SQLiteConexion conexion;
    ListView listacontactos;
    ArrayList<Contactos> lista;
    ArrayList<String> ArregloContactos;
   private EditText txtbuscar;
   private  ListView listas;
   EditText tnumero;
    ImageButton btnllamar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_contacto);
        txtbuscar= (EditText)findViewById(R.id.txtbuscar) ;
        listas= (ListView)findViewById(R.id.ListaContactos);
        tnumero = (EditText)findViewById(R.id.tnumero);

        ImageButton btnatras2 = (ImageButton)findViewById(R.id.btnatras2);
        btnatras2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ActivityContacto.class);
                startActivity(intent);
            }
        });


        conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        listacontactos = (ListView) findViewById(R.id.ListaContactos);


        ObtenerListaContactos();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ArregloContactos);
        listacontactos.setAdapter(adp);
        txtbuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            adp.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        listacontactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String informacion = "ID: " + lista.get(position).getId() + "\n";
                informacion += "Nombre: " + lista.get(position).getNombre() + "\n";
                informacion += "Telefono: " + lista.get(position).getTelefono();
                String numero =lista.get(position).getTelefono();
                tnumero.setText(numero);

            }
        });
    }
    private void ObtenerListaContactos(){
        SQLiteDatabase db = conexion.getReadableDatabase();
        Contactos listContactos = null;
        lista = new ArrayList<Contactos>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Transacciones.tablacontactos, null);

        while (cursor.moveToNext()){
            listContactos = new Contactos();
            listContactos.setId(cursor.getInt(0));
            listContactos.setPais(cursor.getString(1));
            listContactos.setNombre(cursor.getString(2));
            listContactos.setTelefono(cursor.getString(3));
            listContactos.setNota(cursor.getString(4));


            lista.add(listContactos);
            fillList();
        }
    }//obtenerListaPersonas

    private void fillList() {
        ArregloContactos = new ArrayList<String>();
        for (int i = 0; i < lista.size(); i++){
            ArregloContactos.add(lista.get(i).getId() + " ||  "
                    +lista.get(i).getNombre() + " || "
                    +lista.get(i).getTelefono());
        }
    }//fillList

    public void onClickLlamada(View v) {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("95030025"));
        startActivity(i);
    }

    public void onClickCompartir(View v) {
        Intent Compartir = new Intent();
        Compartir.setAction(Intent.ACTION_SEND);
        Compartir.putExtra(Intent.EXTRA_TEXT, tnumero.toString());
        Compartir.setType("text/plain");
        Intent share = Intent.createChooser(Compartir, null);
        startActivity(share);
    }

        public void onClick(View v) {
            String num = tnumero.getText().toString(); // Guardamos el numero de telefono en un string
            if(num!="") { // Verificamos si el numero de telefono no esta vacio
                Uri number = Uri.parse("tel:" + num); // Creamos una uri con el numero de telefono
                Intent dial = new Intent(Intent.ACTION_DIAL, number); // Creamos una llamada al Intent de llamadas
                startActivity(dial); // Ejecutamos el Intent
            }else{ // Si el numero esta vacio
                // Mostramos una alerta de que debemos escribir un numero
                String mensaje;
                mensaje="Seleccione un Numero";
                Toast.makeText(getApplicationContext(), mensaje,Toast.LENGTH_LONG).show();
            }
        }

    }
