package com.mycompany.resetasmedicasnosql.repository;

import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Notificacion;
import org.bson.types.ObjectId;
import java.util.Optional;
import java.util.List;

/**
 *
 * @author gatog
 */
public interface INotificacionRepository {

    ObjectId create(Notificacion entity) throws RepositoryException;

    Optional<Notificacion> findById(ObjectId _id) throws RepositoryException;

    List<Notificacion> findAll() throws RepositoryException;

    boolean update(Notificacion entity) throws RepositoryException;

    boolean deleteById(ObjectId _id) throws RepositoryException;

    long deleteAll() throws RepositoryException;

    List<Notificacion> findByIdPaciente(ObjectId id_paciente) throws RepositoryException;

    List<Notificacion> findNotificacionesNoLeidas(ObjectId id_paciente) throws RepositoryException;

    List<Notificacion> findByTipo(String tipo) throws RepositoryException;

    long marcarComoLeida(ObjectId id_notificacion) throws RepositoryException;
}
