package com.mycompany.resetasmedicasnosql.repository;

import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Consulta;
import java.util.Date;
import org.bson.types.ObjectId;
import java.util.Optional;
import java.util.List;

/**
 *
 * @author gatog
 */
public interface IConsultaRepository {

    ObjectId create(Consulta entity) throws RepositoryException;

    Optional<Consulta> findById(ObjectId _id) throws RepositoryException;

    List<Consulta> findAll() throws RepositoryException;

    boolean update(Consulta entity) throws RepositoryException;

    boolean deleteById(ObjectId _id) throws RepositoryException;

    long deleteAll() throws RepositoryException;

    Optional<Consulta> findByIdCita(ObjectId id_cita) throws RepositoryException;

    List<Consulta> findByIdPaciente(ObjectId id_paciente) throws RepositoryException;

    List<Consulta> findByIdMedico(ObjectId id_medico) throws RepositoryException;

    List<Consulta> findConsultasEntrefechas(Date fecha_inicio, Date fecha_fin) throws RepositoryException;
}
