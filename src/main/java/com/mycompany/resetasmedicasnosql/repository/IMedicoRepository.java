package com.mycompany.resetasmedicasnosql.repository;

import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Medico;
import org.bson.types.ObjectId;
import java.util.Optional;
import java.util.List;

/**
 *
 * @author gatog
 */
public interface IMedicoRepository {

    ObjectId create(Medico entity) throws RepositoryException;

    Optional<Medico> findById(ObjectId _id) throws RepositoryException;

    List<Medico> findAll() throws RepositoryException;

    boolean update(Medico entity) throws RepositoryException;

    boolean deleteById(ObjectId _id) throws RepositoryException;

    long deleteAll() throws RepositoryException;

    Optional<Medico> findByEmail(String email) throws RepositoryException;

    Optional<Medico> findByCedulaProfesional(String cedula_profesional) throws RepositoryException;

    List<Medico> findByEspecialidad(String especialidad) throws RepositoryException;

    List<Medico> findByNombre(String nombre) throws RepositoryException;

    List<Medico> findMedicosActivos() throws RepositoryException;
}
