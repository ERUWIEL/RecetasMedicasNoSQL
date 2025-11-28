package com.mycompany.resetasmedicasnosql.model;

import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;

public class Medico {

    private ObjectId id;
    private ObjectId id_usuario;
    private String cedula_profesional;
    private String especialidad;
    private String licencia_medica;
    private int duracion_consulta_minutos;
    private boolean activo;
    private List<Disponibilidad> disponibilidades;
    private Date fecha_creacion;

    public Medico() {
    }

    public Medico(ObjectId id_usuario, String cedula_profesional, String especialidad, String licencia_medica, int duracion_consulta_minutos) {
        this.id_usuario = id_usuario;
        this.cedula_profesional = cedula_profesional;
        this.especialidad = especialidad;
        this.licencia_medica = licencia_medica;
        this.duracion_consulta_minutos = duracion_consulta_minutos;
        this.activo = true;
        this.fecha_creacion = new Date();
    }

    // Getters y Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(ObjectId id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCedula_profesional() {
        return cedula_profesional;
    }

    public void setCedula_profesional(String cedula_profesional) {
        this.cedula_profesional = cedula_profesional;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getLicencia_medica() {
        return licencia_medica;
    }

    public void setLicencia_medica(String licencia_medica) {
        this.licencia_medica = licencia_medica;
    }

    public int getDuracion_consulta_minutos() {
        return duracion_consulta_minutos;
    }

    public void setDuracion_consulta_minutos(int duracion_consulta_minutos) {
        this.duracion_consulta_minutos = duracion_consulta_minutos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Disponibilidad> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(List<Disponibilidad> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
}
