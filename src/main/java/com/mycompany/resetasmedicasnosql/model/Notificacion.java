package com.mycompany.resetasmedicasnosql.model;

import org.bson.types.ObjectId;
import java.util.Date;

public class Notificacion {

    private ObjectId id;
    private ObjectId id_paciente;
    private String tipo; // recordatorio_cita, cambio_cita, nueva_receta
    private String titulo;
    private String mensaje;
    private Date fecha_envio;
    private boolean leida;

    public Notificacion() {
    }

    public Notificacion(ObjectId id_paciente, String tipo, String titulo, String mensaje) {
        this.id_paciente = id_paciente;
        this.tipo = tipo;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fecha_envio = new Date();
        this.leida = false;
    }

    // Getters y Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(ObjectId id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(Date fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }
}
