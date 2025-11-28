package com.mycompany.resetasmedicasnosql.model;

import org.bson.types.ObjectId;
import java.util.Date;

public class Cita {

    private ObjectId id;
    private ObjectId id_paciente;
    private ObjectId id_medico;
    private ObjectId id_disponibilidad;
    private Date fecha_hora;
    private String estado; // pendiente, confirmada, completada, cancelada
    private String motivo_consulta;
    private String notas;
    private Date fecha_creacion;
    private Date fecha_cancelacion;

    public Cita() {
    }

    public Cita(ObjectId id_paciente, ObjectId id_medico, ObjectId id_disponibilidad,
            Date fecha_hora, String motivo_consulta) {
        this.id_paciente = id_paciente;
        this.id_medico = id_medico;
        this.id_disponibilidad = id_disponibilidad;
        this.fecha_hora = fecha_hora;
        this.motivo_consulta = motivo_consulta;
        this.estado = "pendiente";
        this.fecha_creacion = new Date();
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

    public ObjectId getId_medico() {
        return id_medico;
    }

    public void setId_medico(ObjectId id_medico) {
        this.id_medico = id_medico;
    }

    public ObjectId getId_disponibilidad() {
        return id_disponibilidad;
    }

    public void setId_disponibilidad(ObjectId id_disponibilidad) {
        this.id_disponibilidad = id_disponibilidad;
    }

    public Date getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo_consulta() {
        return motivo_consulta;
    }

    public void setMotivo_consulta(String motivo_consulta) {
        this.motivo_consulta = motivo_consulta;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_cancelacion() {
        return fecha_cancelacion;
    }

    public void setFecha_cancelacion(Date fecha_cancelacion) {
        this.fecha_cancelacion = fecha_cancelacion;
    }
}
