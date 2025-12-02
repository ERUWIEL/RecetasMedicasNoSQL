/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resetasmedicasnosql.service;

import com.mycompany.resetasmedicasnosql.exception.ServiceException;
import com.mycompany.resetasmedicasnosql.model.Notificacion;
import com.mycompany.resetasmedicasnosql.repository.INotificacionRepository;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

/**
 *
 * @author Luis Valenzuela
 */
public class NotificacionService {
    private INotificacionRepository notificacionRepository;

    public NotificacionService(INotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public ObjectId crearNotificacion(Notificacion notificacion) throws ServiceException {
        try {
            return notificacionRepository.create(notificacion);
        } catch (Exception e) {
            throw new ServiceException("Error al crear notificación", e);
        }
    }

    public Notificacion obtenerNotificacion(ObjectId id) throws ServiceException {
        try {
            Optional<Notificacion> notificacion = notificacionRepository.findById(id);
            if (notificacion.isEmpty()) {
                throw new ServiceException("Notificación no encontrada");
            }
            return notificacion.get();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener notificación", e);
        }
    }

    public List<Notificacion> obtenerNotificacionesPaciente(ObjectId idPaciente) throws ServiceException {
        try {
            return notificacionRepository.findByIdPaciente(idPaciente);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener notificaciones", e);
        }
    }

    public List<Notificacion> obtenerNotificacionesNoLeidas(ObjectId idPaciente) throws ServiceException {
        try {
            return notificacionRepository.findNotificacionesNoLeidas(idPaciente);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener notificaciones no leídas", e);
        }
    }

    public boolean marcarComoLeida(ObjectId idNotificacion) throws ServiceException {
        try {
            notificacionRepository.marcarComoLeida(idNotificacion);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Error al marcar notificación como leída", e);
        }
    }

    public ObjectId crearRecordatorioCita(ObjectId idPaciente, String detallesCita) throws ServiceException {
        try {
            Notificacion notif = new Notificacion(idPaciente, "recordatorio_cita", 
                "Recordatorio de cita", detallesCita);
            return notificacionRepository.create(notif);
        } catch (Exception e) {
            throw new ServiceException("Error al crear recordatorio", e);
        }
    }

    public ObjectId crearNotificacionCambioCita(ObjectId idPaciente, String detalles) throws ServiceException {
        try {
            Notificacion notif = new Notificacion(idPaciente, "cambio_cita", 
                "Tu cita ha sido reprogramada", detalles);
            return notificacionRepository.create(notif);
        } catch (Exception e) {
            throw new ServiceException("Error al crear notificación de cambio", e);
        }
    }

    public ObjectId crearNotificacionNuevaReceta(ObjectId idPaciente, String detalles) throws ServiceException {
        try {
            Notificacion notif = new Notificacion(idPaciente, "nueva_receta", 
                "Nueva receta disponible", detalles);
            return notificacionRepository.create(notif);
        } catch (Exception e) {
            throw new ServiceException("Error al crear notificación de receta", e);
        }
    }
}
