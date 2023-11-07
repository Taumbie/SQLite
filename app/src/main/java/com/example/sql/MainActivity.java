package com.example.sql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText ID, Nombre, Area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ID = findViewById(R.id.txtIdUsuario);
        Nombre = findViewById(R.id.txtNombreUsuario);
        Area = findViewById(R.id.txtAreaUsuario);
    }

    public void RegistrarUsuario(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Produccion", null, 1);
        //Definimos el nombre de la base de datos hecha en la otra clase
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();
        String IDUsuario = ID.getText().toString();
        String NombreUsuario = Nombre.getText().toString();
        String AreaUsuario = Area.getText().toString();

        //Validaciones
        if (IDUsuario.isEmpty() && NombreUsuario.isEmpty() && AreaUsuario.isEmpty()) {
            ContentValues DatosUsuario = new ContentValues();
            DatosUsuario.put("idUsuario", IDUsuario);
            DatosUsuario.put("NombreUsuario", IDUsuario);
            DatosUsuario.put("AreaUsuario", IDUsuario);
            BaseDatos.insert("Usuarios", null, DatosUsuario);
            BaseDatos.close();

            //Limpiamos Pantalla
            ID.setText("");
            Nombre.setText("");
            Area.setText("");

            //Creamos una alerta para que sepa que ya esta registerado
            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            //Creamos una alerta para que sepa que se equivocó
            Toast.makeText(this, "No pueden haber campos vacíos", Toast.LENGTH_SHORT).show();
        }

    }

    public void BuscarUsuario(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Produccion", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();
        String IDUsuario = ID.getText().toString();

        if (!IDUsuario.isEmpty()) {
            Cursor fila = BaseDatos.rawQuery("SELECT NombreUsuario," + "AreaUsuario, from Usuarios where idUsuario=+" + IDUsuario, null);
            if (fila.moveToFirst()) {
                Nombre.setText(fila.getString(0));
                Area.setText(fila.getString(1));
                BaseDatos.close();


            }else {
                Toast.makeText(this, "No se encontro el ID ingresado", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Campo no puede estar vacio", Toast.LENGTH_SHORT).show();
    }
}
    public void actualizarUsuario(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Produccion", null, 1);
        //Definimos el nombre de la base de datos hecha en la otra clase
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();
        BaseDatos.isOpen();
        String IDUsuario = ID.getText().toString();
        String NombreUsuario = Nombre.getText().toString();
        String AreaUsuario = Area.getText().toString();
        if (IDUsuario.isEmpty() && NombreUsuario.isEmpty() && AreaUsuario.isEmpty()) {
            ContentValues DatosUsuario = new ContentValues();
            DatosUsuario.put("NombreUsuario", NombreUsuario);
            DatosUsuario.put("AreaUsuario", AreaUsuario);
            BaseDatos.insert("Usuarios", null, DatosUsuario);
            int cantidad = BaseDatos.update("Usuarios", DatosUsuario, "idUsuario=" + IDUsuario, null);

            BaseDatos.close();
            if (cantidad == 1){
                Toast.makeText(this, "El registro se actualizó correctamente", Toast.LENGTH_SHORT).show();
                ID.setText("");
                Nombre.setText("");
                Area.setText("");
            }else{
                Toast.makeText(this, "No se actualizó el registro correctamente", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Campo no puede estar vacio", Toast.LENGTH_SHORT).show();
        }
    }
    public void eliminarUsuario (View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Produccion", null, 1);
        //Definimos el nombre de la base de datos hecha en la otra clase
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();
        String IDUsuario = ID.getText().toString();
        if (!IDUsuario.isEmpty()) {
            int Eliminar = BaseDatos.delete("Usuarios", "idUsuario=" + IDUsuario, null);
            BaseDatos.close();
        }
        if (Eliminar == 1) {
            Toast.makeText(this, "El Registro se elimino correctamente", Toast.LENGTH_SHORT).show();
            ID.setText("");
            Nombre.setText("");
            Area.setText("");
        } else {
            Toast.makeText(this, "El id que intenta eliminar no existe", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "El campo id no puede estar vacio", Toast.LENGTH_SHORT).show();
        }
    }}

