package com.mycompany.resetasmedicasnosql.model;

import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;

public class Medico {

    private ObjectId id;
    private String email;
    private String contraseña;
    private String nombre;
    private String apellido;
    private String telefono;
    private Date fecha_registro;
    private boolean activo;

    // Datos específicos del médico
    private String cedula_profesional;
    private String especialidad;
    private String licencia_medica;
    private int duracion_consulta_minutos;
    private List<Disponibilidad> disponibilidades;

    public Medico() {
    }

    public Medico(String email, String contraseña, String nombre, String apellido,
            String telefono, String cedula_profesional, String especialidad, String licencia_medica, int duracion_consulta_minutos) {
        this.email = email;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.cedula_profesional = cedula_profesional;
        this.especialidad = especialidad;
        this.licencia_medica = licencia_medica;
        this.duracion_consulta_minutos = duracion_consulta_minutos;
        this.fecha_registro = new Date();
        this.activo = true;
    }

    // Getters y Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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

    public List<Disponibilidad> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(List<Disponibilidad> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }
}
