package com.mycompany.resetasmedicasnosql.repository;

import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Receta;
import org.bson.types.ObjectId;
import java.util.Optional;
import java.util.List;

/**
 *
 * @author gatog
 */
public interface IRecetaRepository {

    ObjectId create(Receta entity) throws RepositoryException;

    Optional<Receta> findById(ObjectId _id) throws RepositoryException;

    List<Receta> findAll() throws RepositoryException;

    boolean update(Receta entity) throws RepositoryException;

    boolean deleteById(ObjectId _id) throws RepositoryException;

    long deleteAll() throws RepositoryException;

    List<Receta> findByIdPaciente(ObjectId id_paciente) throws RepositoryException;

    List<Receta> findByIdMedico(ObjectId id_medico) throws RepositoryException;

    List<Receta> findByIdConsulta(ObjectId id_consulta) throws RepositoryException;

    List<Receta> findRecetasActivas() throws RepositoryException;

    List<Receta> findRecetasPorPacienteActivas(ObjectId id_paciente) throws RepositoryException;
}
