package com.example.examen.Transacciones;

public class Transacciones {
    //Creacion de las Tablas
    public  static  final String tablacontactos = "Contactos";
    public  static  final String tablapaises = "Paises";
    /* campos */
    public static final String id = "id";
    public static final String pais = "pais";
    public static final String nombre = "nombre";
    public static final String telefono = "telefono";
    public static final String nota = "nota";
    public static final String codigopais = "codigopais";

    //tablas -CREATE, DROP
    public static final String CreateTableContactos = "CREATE TABLE Contactos( id INTEGER PRIMARY KEY AUTOINCREMENT, pais TEXT, nombre TEXT, telefono INTEGER," +
            "nota TEXT)";

    public static final String DropTableContactos = "DROP TABLE IF EXISTS Contactos";

    //tablas -CREATE, DROP
    public static final String CreateTablePaises = "CREATE TABLE Paises( id INTEGER PRIMARY KEY AUTOINCREMENT, codigopais TEXT, pais TEXT )";

    public static final String DropTablePaises = "DROP TABLE IF EXISTS Paises";

    /* Creacion del nombre de la base de datos */
    public static final String NameDataBase = "DBExamen";
}
