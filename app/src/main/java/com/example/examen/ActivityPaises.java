package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.examen.Contactos.Contactos;
import com.example.examen.Transacciones.Transacciones;
import com.example.examen.Contactos.Contactos;

import java.util.ArrayList;

public class ActivityPaises extends AppCompatActivity {
    public TextView txtInfo;
    public Spinner pais;


    public static final String EXTRA_MESSAGE="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paises);
        SQLiteConexion conexion;

        EditText pais = (EditText) findViewById(R.id.txtPaises);
        EditText codigo = (EditText) findViewById(R.id.txtCodigo);

        Button btn = (Button) findViewById(R.id.btnPasar);
        ImageButton btnatras = (ImageButton)findViewById(R.id.btnatras2) ;

        txtInfo = (TextView) findViewById(R.id.txtInfo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantalla = new Intent(getApplicationContext(), ActivityContacto.class);
                Contactos imp = new Contactos(
                        String.valueOf(pais.getText().toString()),
                        Integer.parseInt(codigo.getText().toString())
                );
                String impPais = imp.setNombre(String.valueOf(pais.getText().toString()));
                Integer impCodigo = imp.setCodigo(Integer.parseInt(codigo.getText().toString()));
               // txtInfo.setText("Pais: " + impPais + " (" + "Codigo: " + ")" + impCodigo);
                txtInfo.setText(impPais);

                String message = txtInfo.getText().toString();
                pantalla.putExtra(EXTRA_MESSAGE, message);
                startActivity(pantalla);


            }
        });
    }
    public void clickNew(View view)
    {
        Intent intent = new Intent(this,ActivityContacto.class);
        startActivity(intent);

    }
    }
