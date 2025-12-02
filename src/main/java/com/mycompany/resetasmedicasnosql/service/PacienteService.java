/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resetasmedicasnosql.service;

import com.mycompany.resetasmedicasnosql.exception.ServiceException;
import com.mycompany.resetasmedicasnosql.model.Paciente;
import com.mycompany.resetasmedicasnosql.repository.IPacienteRepository;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

/**
 *
 * @author Luis Valenzuela
 */
public class PacienteService {

    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public ObjectId registrarPaciente(Paciente paciente) throws ServiceException {
        try {
            if (pacienteRepository.findByEmail(paciente.getEmail()).isPresent()) {
                throw new ServiceException("El email ya está registrado");
            }
            return pacienteRepository.create(paciente);
        } catch (Exception e) {
            throw new ServiceException("Error al registrar paciente", e);
        }
    }

    public Paciente autenticarPaciente(String email, String contraseña) throws ServiceException {
        try {
            Optional<Paciente> paciente = pacienteRepository.findByEmail(email);
            if (paciente.isEmpty()) {
                throw new ServiceException("Paciente no encontrado");
            }
            Paciente p = paciente.get();
            if (!p.isActivo()) {
                throw new ServiceException("Cuenta de paciente desactivada");
            }
            if (!p.getContraseña().equals(contraseña)) {
                throw new ServiceException("Contraseña incorrecta");
            }
            return p;
        } catch (Exception e) {
            throw new ServiceException("Error en autenticación", e);
        }
    }

    public Paciente obtenerPaciente(ObjectId id) throws ServiceException {
        try {
            Optional<Paciente> paciente = pacienteRepository.findById(id);
            if (paciente.isEmpty()) {
                throw new ServiceException("Paciente no encontrado");
            }
            return paciente.get();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener paciente", e);
        }
    }

    public List<Paciente> obtenerTodosPacientes() throws ServiceException {
        try {
            return pacienteRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener pacientes", e);
        }
    }

    public boolean actualizarPaciente(Paciente paciente) throws ServiceException {
        try {
            return pacienteRepository.update(paciente);
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar paciente", e);
        }
    }

    public boolean desactivarPaciente(ObjectId id) throws ServiceException {
        try {
            Optional<Paciente> paciente = pacienteRepository.findById(id);
            if (paciente.isEmpty()) {
                throw new ServiceException("Paciente no encontrado");
            }
            Paciente p = paciente.get();
            p.setActivo(false);
            return pacienteRepository.update(p);
        } catch (Exception e) {
            throw new ServiceException("Error al desactivar paciente", e);
        }
    }

    public Paciente buscarPacientePorCedula(String cedula) throws ServiceException {
        try {
            Optional<Paciente> paciente = pacienteRepository.findByCedula(cedula);
            if (paciente.isEmpty()) {
                throw new ServiceException("Paciente con cédula no encontrado");
            }
            return paciente.get();
        } catch (Exception e) {
            throw new ServiceException("Error al buscar paciente", e);
        }
    }

    public List<Paciente> buscarPacientesPorNombre(String nombre) throws ServiceException {
        try {
            return pacienteRepository.findByNombre(nombre);
        } catch (Exception e) {
            throw new ServiceException("Error al buscar pacientes", e);
        }
    }
}
