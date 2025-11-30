package com.mycompany.resetasmedicasnosql.repository;

import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Paciente;
import org.bson.types.ObjectId;
import java.util.Optional;
import java.util.List;

/**
 * 
 * @author gatog
 */
public interface IPacienteRepository {

    ObjectId create(Paciente entity) throws RepositoryException;

    Optional<Paciente> findById(ObjectId _id) throws RepositoryException;

    List<Paciente> findAll() throws RepositoryException;

    boolean update(Paciente entity) throws RepositoryException;

    boolean deleteById(ObjectId _id) throws RepositoryException;

    long deleteAll() throws RepositoryException;

    Optional<Paciente> findByEmail(String email) throws RepositoryException;

    Optional<Paciente> findByCedula(String cedula) throws RepositoryException;

    List<Paciente> findByNombre(String nombre) throws RepositoryException;
}
