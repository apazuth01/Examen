package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageCapture;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.examen.Contactos.Contactos;
import com.example.examen.Transacciones.Transacciones;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;



public class ActivityContacto extends AppCompatActivity {

    SQLiteConexion conexion;
    ArrayList<String> ListaContactos;
    ArrayList<Contactos> lista;
    ArrayList<String> listas = new ArrayList<String>();
    Spinner pais;
    TextView txtPais;
    EditText nombre;
    EditText telefono;
    EditText nota;
    EditText txtnpais;
    private static final int SELECT_FILE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);




        btn = (ImageButton) findViewById(R.id.bfoto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);


        conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        pais = (Spinner) findViewById(R.id.comboPais);
        txtPais = (TextView) findViewById(R.id.txtPais);
        nombre = (EditText) findViewById(R.id.txtNombre);
        telefono = (EditText) findViewById(R.id.txtTelefono);
        nota = (EditText) findViewById(R.id.txtNota);


        Intent intent = getIntent();
        String  message = intent.getStringExtra(ActivityPaises.EXTRA_MESSAGE);
        TextView txtPais =findViewById(R.id.txtPais);
        txtPais.setText(message);

        // Capture the layout's TextView and set the string as its text
       if (txtPais.getText().toString().isEmpty()) {
            message = "...";
        }else
       {
           message = txtPais.getText().toString();
       }
            //txtPais.setText(message);


        String [] opciones = {"","Belice", "Costa Rica", "El Salvador", "Guatemala", "Honduras", "Nicaragua", "Panamá", message};
        ArrayAdapter<String> adapter = new ArrayAdapter <String> (this, android.R.layout.simple_spinner_item,opciones);
        pais.setAdapter(adapter);


        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        Button btnContactos = (Button) findViewById(R.id.btnContactos);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarContacto();
            }
        });

        btnContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityVerContacto.class);
                startActivity(intent);
            }
        });

    }
  private void AgregarContacto() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();


      try {
          String resu = "";
          //comprobamos que se ha seleccionado alguna de las operaciones a realizar

          if(pais.getSelectedItemPosition()==0){
              TextView errorText=(TextView)pais.getSelectedView();
              errorText.setError("");
              errorText.setTextColor(Color.BLUE);
              errorText.setText("Seleccione un pais");
          }else {
              if (nombre.getText().toString().isEmpty()) {
                  resu = "Ingrese el Nombre del Contacto";
                  Toast.makeText(getApplicationContext(), resu, Toast.LENGTH_LONG).show();
              } else {
                  if (telefono.getText().toString().isEmpty()) {
                      resu = "Ingrese el Numero de Telefono del Contacto";
                      Toast.makeText(getApplicationContext(), resu, Toast.LENGTH_LONG).show();
                  } else {
                      if (nota.getText().toString().isEmpty()) {
                          resu = "Es necesario Agregar una Nota!";
                          Toast.makeText(getApplicationContext(), resu, Toast.LENGTH_LONG).show();
                      } else {
                          ContentValues valores = new ContentValues();
                          valores.put(Transacciones.pais, pais.toString());
                          valores.put(Transacciones.nombre, nombre.getText().toString());
                          valores.put(Transacciones.telefono, telefono.getText().toString());
                          valores.put(Transacciones.nota, nota.getText().toString());
                          Long registro = db.insert(Transacciones.tablacontactos, Transacciones.id, valores);
                          //toast
                          Toast.makeText(getApplicationContext(), " Contacto N- " + registro.toString() + " Ingresado Exitosamente", Toast.LENGTH_LONG).show();
                          //cerrando base de datos
                          db.close();
                          ClearScreen();
                      }
                  }
              }
          }
      } catch (Exception e) {
          //en caso de error se muestra la exception
          System.out.println("Error!! Exception: " + e);
      }
    }

    private void ClearScreen() {
        nombre.setText("");
        telefono.setText("");
        nota.setText("");
    }

    public void clickNew(View view) {
        Intent intent = new Intent(this, ActivityPaises.class);
        startActivity(intent);
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void abrirGaleria(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);
    }



    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath=selectedImage.getPath();
                    if (requestCode == SELECT_FILE) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                            ImageView mImg = (ImageView) findViewById(R.id.imageView);
                            mImg.setImageBitmap(bmp);

                        }
                    }
                }
                break;
        }
    }

}

