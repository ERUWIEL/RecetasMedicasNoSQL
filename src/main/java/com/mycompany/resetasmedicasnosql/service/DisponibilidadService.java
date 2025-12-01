/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resetasmedicasnosql.service;

/**
 *
 * @author Luis Valenzuela
 */
public class DisponibilidadService {
    private IMedicoRepository medicoRepository;

    public DisponibilidadService(IMedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public boolean agregarDisponibilidad(ObjectId idMedico, Disponibilidad disponibilidad) throws ServiceException {
        try {
            Optional<Medico> medico = medicoRepository.findById(idMedico);
            if (medico.isEmpty()) {
                throw new ServiceException("Médico no encontrado");
            }
            Medico m = medico.get();
            if (m.getDisponibilidades() == null) {
                m.setDisponibilidades(new ArrayList<>());
            }
            m.getDisponibilidades().add(disponibilidad);
            return medicoRepository.update(m);
        } catch (Exception e) {
            throw new ServiceException("Error al agregar disponibilidad", e);
        }
    }

    public boolean actualizarDisponibilidad(ObjectId idMedico, ObjectId idDisponibilidad, Disponibilidad disponibilidad) throws ServiceException {
        try {
            Optional<Medico> medico = medicoRepository.findById(idMedico);
            if (medico.isEmpty()) {
                throw new ServiceException("Médico no encontrado");
            }
            Medico m = medico.get();
            if (m.getDisponibilidades() != null) {
                for (int i = 0; i < m.getDisponibilidades().size(); i++) {
                    if (m.getDisponibilidades().get(i).getId_disponibilidad().equals(idDisponibilidad)) {
                        m.getDisponibilidades().set(i, disponibilidad);
                        return medicoRepository.update(m);
                    }
                }
            }
            throw new ServiceException("Disponibilidad no encontrada");
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar disponibilidad", e);
        }
    }

    public boolean desactivarDisponibilidad(ObjectId idMedico, ObjectId idDisponibilidad) throws ServiceException {
        try {
            Optional<Medico> medico = medicoRepository.findById(idMedico);
            if (medico.isEmpty()) {
                throw new ServiceException("Médico no encontrado");
            }
            Medico m = medico.get();
            if (m.getDisponibilidades() != null) {
                for (Disponibilidad d : m.getDisponibilidades()) {
                    if (d.getId_disponibilidad().equals(idDisponibilidad)) {
                        d.setActivo(false);
                        return medicoRepository.update(m);
                    }
                }
            }
            throw new ServiceException("Disponibilidad no encontrada");
        } catch (Exception e) {
            throw new ServiceException("Error al desactivar disponibilidad", e);
        }
    }

    public List<Disponibilidad> obtenerDisponibilidades(ObjectId idMedico) throws ServiceException {
        try {
            Optional<Medico> medico = medicoRepository.findById(idMedico);
            if (medico.isEmpty()) {
                throw new ServiceException("Médico no encontrado");
            }
            Medico m = medico.get();
            return m.getDisponibilidades() != null ? m.getDisponibilidades() : new ArrayList<>();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener disponibilidades", e);
        }
    }
}
