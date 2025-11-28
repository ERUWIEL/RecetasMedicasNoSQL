package com.mycompany.resetasmedicasnosql.model;

import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;

public class Receta {

    private ObjectId id;
    private ObjectId id_consulta;
    private ObjectId id_paciente;
    private ObjectId id_medico;
    private List<Medicamento> medicamentos;
    private Date fecha_emision;
    private boolean activa;

    public Receta() {
    }

    public Receta(ObjectId id_consulta, ObjectId id_paciente, ObjectId id_medico) {
        this.id_consulta = id_consulta;
        this.id_paciente = id_paciente;
        this.id_medico = id_medico;
        this.fecha_emision = new Date();
        this.activa = true;
    }

    // Getters y Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId_consulta() {
        return id_consulta;
    }

    public void setId_consulta(ObjectId id_consulta) {
        this.id_consulta = id_consulta;
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

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
