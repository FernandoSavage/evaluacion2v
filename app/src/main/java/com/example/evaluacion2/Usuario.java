package com.example.evaluacion2;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String nombre;
    private String genero;
    private String nacimiento;

    public Usuario(){
        nombre = "";
        genero = "";
        nacimiento = "";
    }

    public Usuario(String nombre, String genero, String nacimiento){
        this.nombre = nombre;
        this.genero = genero;
        this.nacimiento = nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }
}
