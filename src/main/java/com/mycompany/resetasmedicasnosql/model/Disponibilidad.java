package com.mycompany.resetasmedicasnosql.model;

import org.bson.types.ObjectId;

public class Disponibilidad {

    private ObjectId id_disponibilidad;
    private String dia_semana;
    private String hora_inicio;
    private String hora_fin;
    private boolean activo;

    public Disponibilidad() {
    }

    public Disponibilidad(String dia_semana, String hora_inicio, String hora_fin) {
        this.id_disponibilidad = new ObjectId();
        this.dia_semana = dia_semana;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.activo = true;
    }

    // Getters y Setters
    public ObjectId getId_disponibilidad() {
        return id_disponibilidad;
    }

    public void setId_disponibilidad(ObjectId id_disponibilidad) {
        this.id_disponibilidad = id_disponibilidad;
    }

    public String getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
