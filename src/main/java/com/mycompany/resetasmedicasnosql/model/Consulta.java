package com.mycompany.resetasmedicasnosql.model;

import org.bson.types.ObjectId;
import java.util.Date;

public class Consulta {

    private ObjectId id;
    private ObjectId id_cita;
    private ObjectId id_paciente;
    private ObjectId id_medico;
    private Date fecha_consulta;
    private String diagnostico;
    private String observaciones;
    private String tratamiento_recomendado;
    private Date fecha_registro;

    public Consulta() {
    }

    public Consulta(ObjectId id_cita, ObjectId id_paciente, ObjectId id_medico, String diagnostico) {
        this.id_cita = id_cita;
        this.id_paciente = id_paciente;
        this.id_medico = id_medico;
        this.fecha_consulta = new Date();
        this.diagnostico = diagnostico;
        this.fecha_registro = new Date();
    }

    // Getters y Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId_cita() {
        return id_cita;
    }

    public void setId_cita(ObjectId id_cita) {
        this.id_cita = id_cita;
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

    public Date getFecha_consulta() {
        return fecha_consulta;
    }

    public void setFecha_consulta(Date fecha_consulta) {
        this.fecha_consulta = fecha_consulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTratamiento_recomendado() {
        return tratamiento_recomendado;
    }

    public void setTratamiento_recomendado(String tratamiento_recomendado) {
        this.tratamiento_recomendado = tratamiento_recomendado;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
}
