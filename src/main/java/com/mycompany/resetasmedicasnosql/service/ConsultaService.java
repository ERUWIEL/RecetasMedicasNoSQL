/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resetasmedicasnosql.service;

/**
 *
 * @author Luis Valenzuela
 */
public class ConsultaService {
    private IConsultaRepository consultaRepository;

    public ConsultaService(IConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public ObjectId registrarConsulta(Consulta consulta) throws ServiceException {
        try {
            return consultaRepository.create(consulta);
        } catch (Exception e) {
            throw new ServiceException("Error al registrar consulta", e);
        }
    }

    public Consulta obtenerConsulta(ObjectId id) throws ServiceException {
        try {
            Optional<Consulta> consulta = consultaRepository.findById(id);
            if (consulta.isEmpty()) {
                throw new ServiceException("Consulta no encontrada");
            }
            return consulta.get();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener consulta", e);
        }
    }

    public Consulta obtenerConsultaPorCita(ObjectId idCita) throws ServiceException {
        try {
            Optional<Consulta> consulta = consultaRepository.findByIdCita(idCita);
            if (consulta.isEmpty()) {
                throw new ServiceException("Consulta no encontrada para esa cita");
            }
            return consulta.get();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener consulta", e);
        }
    }

    public List<Consulta> obtenerConsultasPaciente(ObjectId idPaciente) throws ServiceException {
        try {
            return consultaRepository.findByIdPaciente(idPaciente);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener consultas del paciente", e);
        }
    }

    public List<Consulta> obtenerConsultasMedico(ObjectId idMedico) throws ServiceException {
        try {
            return consultaRepository.findByIdMedico(idMedico);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener consultas del médico", e);
        }
    }

    public List<Consulta> obtenerConsultasPorRangoFechas(Date fechaInicio, Date fechaFin) throws ServiceException {
        try {
            return consultaRepository.findConsultasEntrefechas(fechaInicio, fechaFin);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener consultas", e);
        }
    }

    public boolean actualizarConsulta(Consulta consulta) throws ServiceException {
        try {
            return consultaRepository.update(consulta);
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar consulta", e);
        }
    }
}
