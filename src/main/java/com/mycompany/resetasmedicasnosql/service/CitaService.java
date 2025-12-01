/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resetasmedicasnosql.service;

/**
 *
 * @author Luis Valenzuela
 */
public class CitaService {
    private ICitaRepository citaRepository;
    private IMedicoRepository medicoRepository;

    public CitaService(ICitaRepository citaRepository, IMedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.medicoRepository = medicoRepository;
    }

    public ObjectId crearCita(Cita cita) throws ServiceException {
        try {
            // Validar disponibilidad del médico
            if (citaRepository.findConflictoCita(cita.getId_medico(), cita.getFecha_hora()).isPresent()) {
                throw new ServiceException("El médico ya tiene una cita en ese horario");
            }
            return citaRepository.create(cita);
        } catch (Exception e) {
            throw new ServiceException("Error al crear cita", e);
        }
    }

    public Cita obtenerCita(ObjectId id) throws ServiceException {
        try {
            Optional<Cita> cita = citaRepository.findById(id);
            if (cita.isEmpty()) {
                throw new ServiceException("Cita no encontrada");
            }
            return cita.get();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener cita", e);
        }
    }

    public List<Cita> obtenerCitasPaciente(ObjectId idPaciente) throws ServiceException {
        try {
            return citaRepository.findByIdPaciente(idPaciente);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener citas del paciente", e);
        }
    }

    public List<Cita> obtenerCitasMedico(ObjectId idMedico) throws ServiceException {
        try {
            return citaRepository.findByIdMedico(idMedico);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener citas del médico", e);
        }
    }

    public List<Cita> obtenerCitasPorFecha(Date fecha) throws ServiceException {
        try {
            return citaRepository.findByFechaHora(fecha);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener citas por fecha", e);
        }
    }

    public List<Cita> obtenerCitasMedicoFecha(ObjectId idMedico, Date fecha) throws ServiceException {
        try {
            return citaRepository.findCitasPorMedicoYFecha(idMedico, fecha);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener citas", e);
        }
    }

    public boolean confirmarCita(ObjectId idCita) throws ServiceException {
        try {
            Optional<Cita> cita = citaRepository.findById(idCita);
            if (cita.isEmpty()) {
                throw new ServiceException("Cita no encontrada");
            }
            Cita c = cita.get();
            c.setEstado("confirmada");
            return citaRepository.update(c);
        } catch (Exception e) {
            throw new ServiceException("Error al confirmar cita", e);
        }
    }

    public boolean completarCita(ObjectId idCita) throws ServiceException {
        try {
            Optional<Cita> cita = citaRepository.findById(idCita);
            if (cita.isEmpty()) {
                throw new ServiceException("Cita no encontrada");
            }
            Cita c = cita.get();
            c.setEstado("completada");
            return citaRepository.update(c);
        } catch (Exception e) {
            throw new ServiceException("Error al completar cita", e);
        }
    }

    public boolean cancelarCita(ObjectId idCita) throws ServiceException {
        try {
            Optional<Cita> cita = citaRepository.findById(idCita);
            if (cita.isEmpty()) {
                throw new ServiceException("Cita no encontrada");
            }
            Cita c = cita.get();
            c.setEstado("cancelada");
            c.setFecha_cancelacion(new Date());
            return citaRepository.update(c);
        } catch (Exception e) {
            throw new ServiceException("Error al cancelar cita", e);
        }
    }

    public List<Cita> obtenerCitasConfirmadas() throws ServiceException {
        try {
            return citaRepository.findCitasConfirmadas();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener citas confirmadas", e);
        }
    }
}
