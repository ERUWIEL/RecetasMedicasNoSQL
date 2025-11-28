package com.mycompany.resetasmedicasnosql.model;

public class Medicamento {

    private String nombre;
    private String dosis;
    private String frecuencia;
    private int dias_duracion;
    private String indicaciones;

    public Medicamento() {
    }

    public Medicamento(String nombre, String dosis, String frecuencia, int dias_duracion, String indicaciones) {
        this.nombre = nombre;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.dias_duracion = dias_duracion;
        this.indicaciones = indicaciones;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getDias_duracion() {
        return dias_duracion;
    }

    public void setDias_duracion(int dias_duracion) {
        this.dias_duracion = dias_duracion;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }
}
