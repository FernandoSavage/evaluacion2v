package com.example.evaluacion2;

import java.io.Serializable;

public class Persona implements Serializable {
    public String nombre;
    public String contraseña;

    public Persona(){
        nombre = "test";
        contraseña = "test";
    }

    public Persona(String nombre, String contraseña){
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
