package com.mycompany.resetasmedicasnosql.repository;

import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Administrador;
import org.bson.types.ObjectId;
import java.util.Optional;
import java.util.List;

/**
 *
 * @author gatog
 */
public interface IAdministradorRepository {

    ObjectId create(Administrador entity) throws RepositoryException;

    Optional<Administrador> findById(ObjectId _id) throws RepositoryException;

    List<Administrador> findAll() throws RepositoryException;

    boolean update(Administrador entity) throws RepositoryException;

    boolean deleteById(ObjectId _id) throws RepositoryException;

    long deleteAll() throws RepositoryException;

    Optional<Administrador> findByEmail(String email) throws RepositoryException;
}
