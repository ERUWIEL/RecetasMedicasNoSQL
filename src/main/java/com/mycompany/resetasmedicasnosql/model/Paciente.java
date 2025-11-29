package com.mycompany.resetasmedicasnosql.model;

import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;

public class Paciente {

    private ObjectId id;
    private String email;
    private String contraseña;
    private String nombre;
    private String apellido;
    private String telefono;
    private Date fecha_registro;
    private boolean activo;

    // Datos específicos del paciente
    private String cedula;
    private Date fecha_nacimiento;
    private String genero;
    private String direccion;
    private List<String> alergias;
    private List<String> condiciones_previas;

    public Paciente() {
    }

    public Paciente(String email, String contraseña, String nombre, String apellido,
            String telefono, String cedula, Date fecha_nacimiento, String genero, String direccion) {
        this.email = email;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.cedula = cedula;
        this.fecha_nacimiento = fecha_nacimiento;
        this.genero = genero;
        this.direccion = direccion;
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
}
