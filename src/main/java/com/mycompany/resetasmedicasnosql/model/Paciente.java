package com.mycompany.resetasmedicasnosql.model;

import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;

public class Paciente {

    private ObjectId id;
    private ObjectId id_usuario;
    private String cedula;
    private Date fecha_nacimiento;
    private String genero;
    private String direccion;
    private List<String> alergias;
    private List<String> condiciones_previas;
    private Date fecha_creacion;

    public Paciente() {
    }

    public Paciente(ObjectId id_usuario, String cedula, Date fecha_nacimiento, String genero, String direccion) {
        this.id_usuario = id_usuario;
        this.cedula = cedula;
        this.fecha_nacimiento = fecha_nacimiento;
        this.genero = genero;
        this.direccion = direccion;
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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<String> getAlergias() {
        return alergias;
    }

    public void setAlergias(List<String> alergias) {
        this.alergias = alergias;
    }

    public List<String> getCondiciones_previas() {
        return condiciones_previas;
    }

    public void setCondiciones_previas(List<String> condiciones_previas) {
        this.condiciones_previas = condiciones_previas;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
}
