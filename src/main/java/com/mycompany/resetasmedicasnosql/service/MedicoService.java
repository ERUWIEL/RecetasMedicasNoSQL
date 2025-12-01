/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resetasmedicasnosql.service;

/**
 *
 * @author Luis Valenzuela
 */
public class MedicoService {

    private IMedicoRepository medicoRepository;

    public MedicoService(IMedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public ObjectId registrarMedico(Medico medico) throws ServiceException {
        try {
            if (medicoRepository.findByEmail(medico.getEmail()).isPresent()) {
                throw new ServiceException("El email ya está registrado");
            }
            if (medicoRepository.findByCedulaProfesional(medico.getCedula_profesional()).isPresent()) {
                throw new ServiceException("La cédula profesional ya está registrada");
            }
            return medicoRepository.create(medico);
        } catch (Exception e) {
            throw new ServiceException("Error al registrar médico", e);
        }
    }

    public Medico autenticarMedico(String email, String contraseña) throws ServiceException {
        try {
            Optional<Medico> medico = medicoRepository.findByEmail(email);
            if (medico.isEmpty()) {
                throw new ServiceException("Médico no encontrado");
            }
            Medico m = medico.get();
            if (!m.isActivo()) {
                throw new ServiceException("Cuenta de médico desactivada");
            }
            if (!m.getContraseña().equals(contraseña)) {
                throw new ServiceException("Contraseña incorrecta");
            }
            return m;
        } catch (Exception e) {
            throw new ServiceException("Error en autenticación", e);
        }
    }

    public Medico obtenerMedico(ObjectId id) throws ServiceException {
        try {
            Optional<Medico> medico = medicoRepository.findById(id);
            if (medico.isEmpty()) {
                throw new ServiceException("Médico no encontrado");
            }
            return medico.get();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener médico", e);
        }
    }

    public List<Medico> obtenerMedicosActivos() throws ServiceException {
        try {
            return medicoRepository.findMedicosActivos();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener médicos activos", e);
        }
    }

    public List<Medico> buscarMedicosPorEspecialidad(String especialidad) throws ServiceException {
        try {
            return medicoRepository.findByEspecialidad(especialidad);
        } catch (Exception e) {
            throw new ServiceException("Error al buscar médicos por especialidad", e);
        }
    }

    public List<Medico> buscarMedicosPorNombre(String nombre) throws ServiceException {
        try {
            return medicoRepository.findByNombre(nombre);
        } catch (Exception e) {
            throw new ServiceException("Error al buscar médicos", e);
        }
    }

    public boolean actualizarMedico(Medico medico) throws ServiceException {
        try {
            return medicoRepository.update(medico);
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar médico", e);
        }
    }

    public boolean desactivarMedico(ObjectId id) throws ServiceException {
        try {
            Optional<Medico> medico = medicoRepository.findById(id);
            if (medico.isEmpty()) {
                throw new ServiceException("Médico no encontrado");
            }
            Medico m = medico.get();
            m.setActivo(false);
            return medicoRepository.update(m);
        } catch (Exception e) {
            throw new ServiceException("Error al desactivar médico", e);
        }
    }
}
