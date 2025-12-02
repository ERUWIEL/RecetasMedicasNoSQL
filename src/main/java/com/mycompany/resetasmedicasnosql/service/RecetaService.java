/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resetasmedicasnosql.service;

import com.mycompany.resetasmedicasnosql.exception.ServiceException;
import com.mycompany.resetasmedicasnosql.model.Medicamento;
import com.mycompany.resetasmedicasnosql.model.Paciente;
import com.mycompany.resetasmedicasnosql.model.Receta;
import com.mycompany.resetasmedicasnosql.repository.IPacienteRepository;
import com.mycompany.resetasmedicasnosql.repository.IRecetaRepository;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

/**
 *
 * @author Luis Valenzuela
 */
public class RecetaService {
    private IRecetaRepository recetaRepository;
    private IPacienteRepository pacienteRepository;

    public RecetaService(IRecetaRepository recetaRepository, IPacienteRepository pacienteRepository) {
        this.recetaRepository = recetaRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public ObjectId emitirReceta(Receta receta) throws ServiceException {
        try {
            // Validar alergias del paciente
            Optional<Paciente> paciente = pacienteRepository.findById(receta.getId_paciente());
            if (paciente.isPresent()) {
                validarAlergias(receta, paciente.get());
            }
            return recetaRepository.create(receta);
        } catch (Exception e) {
            throw new ServiceException("Error al emitir receta", e);
        }
    }

    public Receta obtenerReceta(ObjectId id) throws ServiceException {
        try {
            Optional<Receta> receta = recetaRepository.findById(id);
            if (receta.isEmpty()) {
                throw new ServiceException("Receta no encontrada");
            }
            return receta.get();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener receta", e);
        }
    }

    public List<Receta> obtenerRecetasActivasPaciente(ObjectId idPaciente) throws ServiceException {
        try {
            return recetaRepository.findRecetasPorPacienteActivas(idPaciente);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener recetas activas", e);
        }
    }

    public List<Receta> obtenerRecetasPaciente(ObjectId idPaciente) throws ServiceException {
        try {
            return recetaRepository.findByIdPaciente(idPaciente);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener recetas del paciente", e);
        }
    }

    public List<Receta> obtenerRecetasMedico(ObjectId idMedico) throws ServiceException {
        try {
            return recetaRepository.findByIdMedico(idMedico);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener recetas del médico", e);
        }
    }

    public List<Receta> obtenerRecetasActivas() throws ServiceException {
        try {
            return recetaRepository.findRecetasActivas();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener recetas activas", e);
        }
    }

    public boolean desactivarReceta(ObjectId idReceta) throws ServiceException {
        try {
            Optional<Receta> receta = recetaRepository.findById(idReceta);
            if (receta.isEmpty()) {
                throw new ServiceException("Receta no encontrada");
            }
            Receta r = receta.get();
            r.setActiva(false);
            return recetaRepository.update(r);
        } catch (Exception e) {
            throw new ServiceException("Error al desactivar receta", e);
        }
    }

    public boolean actualizarReceta(Receta receta) throws ServiceException {
        try {
            return recetaRepository.update(receta);
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar receta", e);
        }
    }

    private void validarAlergias(Receta receta, Paciente paciente) throws ServiceException {
        if (paciente.getAlergias() != null && receta.getMedicamentos() != null) {
            for (Medicamento med : receta.getMedicamentos()) {
                for (String alergia : paciente.getAlergias()) {
                    if (med.getNombre().toLowerCase().contains(alergia.toLowerCase())) {
                        throw new ServiceException("¡ALERTA! El medicamento " + med.getNombre() + 
                            " puede causar alergia al paciente");
                    }
                }
            }
        }
    }
}
