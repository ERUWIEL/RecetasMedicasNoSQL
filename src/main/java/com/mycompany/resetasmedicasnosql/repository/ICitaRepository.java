package com.mycompany.resetasmedicasnosql.repository;

import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Cita;
import java.util.Date;
import org.bson.types.ObjectId;
import java.util.Optional;
import java.util.List;

/**
 *
 * @author gatog
 */
public interface ICitaRepository {

    ObjectId create(Cita entity) throws RepositoryException;

    Optional<Cita> findById(ObjectId _id) throws RepositoryException;

    List<Cita> findAll() throws RepositoryException;

    boolean update(Cita entity) throws RepositoryException;

    boolean deleteById(ObjectId _id) throws RepositoryException;

    long deleteAll() throws RepositoryException;

    List<Cita> findByIdPaciente(ObjectId id_paciente) throws RepositoryException;

    List<Cita> findByIdMedico(ObjectId id_medico) throws RepositoryException;

    List<Cita> findByEstado(String estado) throws RepositoryException;

    List<Cita> findByFechaHora(Date fecha_hora) throws RepositoryException;

    List<Cita> findCitasPorMedicoYFecha(ObjectId id_medico, Date fecha) throws RepositoryException;

    List<Cita> findCitasConfirmadas() throws RepositoryException;

    Optional<Cita> findConflictoCita(ObjectId id_medico, Date fecha_hora) throws RepositoryException;
}
