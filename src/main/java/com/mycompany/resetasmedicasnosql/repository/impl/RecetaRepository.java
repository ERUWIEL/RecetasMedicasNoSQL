package com.mycompany.resetasmedicasnosql.repository.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mycompany.resetasmedicasnosql.config.MongoClientProvider;
import com.mycompany.resetasmedicasnosql.exception.EntityNotFoundException;
import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Receta;
import com.mycompany.resetasmedicasnosql.repository.IRecetaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Implementación del repositorio para la entidad Receta
 * @author gatog
 */
public class RecetaRepository implements IRecetaRepository {

    private final MongoCollection<Receta> col;

    public RecetaRepository() {
        this.col = MongoClientProvider.INSTANCE.getCollection("recetas", Receta.class);
    }

    @Override
    public ObjectId create(Receta entity) throws RepositoryException {
        try {
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            col.insertOne(entity);
            return entity.getId();
        } catch (MongoException e) {
            throw new RepositoryException("Error insertando receta", e);
        }
    }

    @Override
    public Optional<Receta> findById(ObjectId _id) throws RepositoryException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("_id", _id)).first());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando receta por ID", e);
        }
    }

    @Override
    public List<Receta> findAll() throws RepositoryException {
        try {
            return col.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando todas las recetas", e);
        }
    }

    @Override
    public boolean update(Receta entity) throws RepositoryException {
        try {
            List<Bson> updates = new ArrayList<>();

            updates.add(Updates.set("id_consulta", entity.getId_consulta()));
            updates.add(Updates.set("id_paciente", entity.getId_paciente()));
            updates.add(Updates.set("id_medico", entity.getId_medico()));
            updates.add(Updates.set("medicamentos", entity.getMedicamentos()));
            updates.add(Updates.set("fecha_emision", entity.getFecha_emision()));
            updates.add(Updates.set("activa", entity.isActiva()));

            var result = col.updateOne(
                Filters.eq("_id", entity.getId()),
                Updates.combine(updates)
            );

            if (result.getMatchedCount() == 0) {
                throw new EntityNotFoundException("Receta no encontrada: " + entity.getId());
            }

            return result.getModifiedCount() > 0;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error actualizando receta", e);
        }
    }

    @Override
    public boolean deleteById(ObjectId _id) throws RepositoryException {
        try {
            var result = col.deleteOne(Filters.eq("_id", _id));

            if (result.getDeletedCount() == 0) {
                throw new EntityNotFoundException("Receta no encontrada: " + _id);
            }
            return true;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando receta", e);
        }
    }

    @Override
    public long deleteAll() throws RepositoryException {
        try {
            return col.deleteMany(Filters.exists("_id")).getDeletedCount();
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando todas las recetas", e);
        }
    }

    @Override
    public List<Receta> findByIdPaciente(ObjectId id_paciente) throws RepositoryException {
        try {
            return col.find(Filters.eq("id_paciente", id_paciente)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando recetas por paciente", e);
        }
    }

    @Override
    public List<Receta> findByIdMedico(ObjectId id_medico) throws RepositoryException {
        try {
            return col.find(Filters.eq("id_medico", id_medico)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando recetas por medico", e);
        }
    }

    @Override
    public List<Receta> findByIdConsulta(ObjectId id_consulta) throws RepositoryException {
        try {
            return col.find(Filters.eq("id_consulta", id_consulta)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando recetas por consulta", e);
        }
    }

    @Override
    public List<Receta> findRecetasActivas() throws RepositoryException {
        try {
            return col.find(Filters.eq("activa", true)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando recetas activas", e);
        }
    }

    @Override
    public List<Receta> findRecetasPorPacienteActivas(ObjectId id_paciente) throws RepositoryException {
        try {
            Bson filter = Filters.and(
                Filters.eq("id_paciente", id_paciente),
                Filters.eq("activa", true)
            );
            return col.find(filter).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando recetas activas por paciente", e);
        }
    }
}

