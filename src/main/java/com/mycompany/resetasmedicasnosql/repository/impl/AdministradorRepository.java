package com.mycompany.resetasmedicasnosql.repository.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mycompany.resetasmedicasnosql.config.MongoClientProvider;
import com.mycompany.resetasmedicasnosql.exception.EntityNotFoundException;
import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Administrador;
import com.mycompany.resetasmedicasnosql.repository.IAdministradorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Implementación del repositorio para la entidad Administrador
 *
 * @author gatog
 */
public class AdministradorRepository implements IAdministradorRepository {

    private final MongoCollection<Administrador> col;

    public AdministradorRepository() {
        this.col = MongoClientProvider.INSTANCE.getCollection("administradores", Administrador.class);
    }

    @Override
    public ObjectId create(Administrador entity) throws RepositoryException {
        try {
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            col.insertOne(entity);
            return entity.getId();
        } catch (MongoException e) {
            throw new RepositoryException("Error insertando administrador", e);
        }
    }

    @Override
    public Optional<Administrador> findById(ObjectId _id) throws RepositoryException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("_id", _id)).first());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando administrador por ID", e);
        }
    }

    @Override
    public List<Administrador> findAll() throws RepositoryException {
        try {
            return col.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando todos los administradores", e);
        }
    }

    @Override
    public boolean update(Administrador entity) throws RepositoryException {
        try {
            List<Bson> updates = new ArrayList<>();
            updates.add(Updates.set("email", entity.getEmail()));
            updates.add(Updates.set("contraseña", entity.getContraseña()));
            updates.add(Updates.set("nombre", entity.getNombre()));
            updates.add(Updates.set("apellido", entity.getApellido()));
            updates.add(Updates.set("telefono", entity.getTelefono()));
            updates.add(Updates.set("activo", entity.isActivo()));
            updates.add(Updates.set("permisos", entity.getPermisos()));

            var result = col.updateOne(
                    Filters.eq("_id", entity.getId()),
                    Updates.combine(updates)
            );

            if (result.getMatchedCount() == 0) {
                throw new EntityNotFoundException("Administrador no encontrado: " + entity.getId());
            }
            return result.getModifiedCount() > 0;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error actualizando administrador", e);
        }
    }

    @Override
    public boolean deleteById(ObjectId _id) throws RepositoryException {
        try {
            var result = col.deleteOne(Filters.eq("_id", _id));
            if (result.getDeletedCount() == 0) {
                throw new EntityNotFoundException("Administrador no encontrado: " + _id);
            }
            return true;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando administrador", e);
        }
    }

    @Override
    public long deleteAll() throws RepositoryException {
        try {
            return col.deleteMany(Filters.exists("_id")).getDeletedCount();
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando todos los administradores", e);
        }
    }

    @Override
    public Optional<Administrador> findByEmail(String email) throws RepositoryException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("email", email)).first());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando administrador por email", e);
        }
    }
}
