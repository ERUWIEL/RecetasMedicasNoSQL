package com.mycompany.resetasmedicasnosql.repository.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mycompany.resetasmedicasnosql.config.MongoClientProvider;
import com.mycompany.resetasmedicasnosql.exception.EntityNotFoundException;
import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Paciente;
import com.mycompany.resetasmedicasnosql.repository.IPacienteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Implementación del repositorio para la entidad Paciente
 *
 * @author gatog
 */
public class PacienteRepository implements IPacienteRepository {

    private final MongoCollection<Paciente> col;

    public PacienteRepository() {
        this.col = MongoClientProvider.INSTANCE.getCollection("pacientes", Paciente.class);
    }

    @Override
    public ObjectId create(Paciente entity) throws RepositoryException {
        try {
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            col.insertOne(entity);
            return entity.getId();
        } catch (MongoException e) {
            throw new RepositoryException("Error insertando paciente", e);
        }
    }

    @Override
    public Optional<Paciente> findById(ObjectId _id) throws RepositoryException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("_id", _id)).first());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando paciente por ID", e);
        }
    }

    @Override
    public List<Paciente> findAll() throws RepositoryException {
        try {
            return col.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando todos los pacientes", e);
        }
    }

    @Override
    public boolean update(Paciente entity) throws RepositoryException {
        try {
            List<Bson> updates = new ArrayList<>();
            updates.add(Updates.set("email", entity.getEmail()));
            updates.add(Updates.set("contraseña", entity.getContraseña()));
            updates.add(Updates.set("nombre", entity.getNombre()));
            updates.add(Updates.set("apellido", entity.getApellido()));
            updates.add(Updates.set("telefono", entity.getTelefono()));
            updates.add(Updates.set("activo", entity.isActivo()));
            updates.add(Updates.set("cedula", entity.getCedula()));
            updates.add(Updates.set("fecha_nacimiento", entity.getFecha_nacimiento()));
            updates.add(Updates.set("genero", entity.getGenero()));
            updates.add(Updates.set("direccion", entity.getDireccion()));
            updates.add(Updates.set("alergias", entity.getAlergias()));
            updates.add(Updates.set("condiciones_previas", entity.getCondiciones_previas()));

            var result = col.updateOne(
                    Filters.eq("_id", entity.getId()),
                    Updates.combine(updates)
            );

            if (result.getMatchedCount() == 0) {
                throw new EntityNotFoundException("Paciente no encontrado: " + entity.getId());
            }
            return result.getModifiedCount() > 0;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error actualizando paciente", e);
        }
    }

    @Override
    public boolean deleteById(ObjectId _id) throws RepositoryException {
        try {
            var result = col.deleteOne(Filters.eq("_id", _id));
            if (result.getDeletedCount() == 0) {
                throw new EntityNotFoundException("Paciente no encontrado: " + _id);
            }
            return true;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando paciente", e);
        }
    }

    @Override
    public long deleteAll() throws RepositoryException {
        try {
            return col.deleteMany(Filters.exists("_id")).getDeletedCount();
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando todos los pacientes", e);
        }
    }

    @Override
    public Optional<Paciente> findByEmail(String email) throws RepositoryException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("email", email)).first());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando paciente por email", e);
        }
    }

    @Override
    public Optional<Paciente> findByCedula(String cedula) throws RepositoryException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("cedula", cedula)).first());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando paciente por cédula", e);
        }
    }

    @Override
    public List<Paciente> findByNombre(String nombre) throws RepositoryException {
        try {
            return col.find(Filters.regex("nombre", nombre, "i")).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando pacientes por nombre", e);
        }
    }
}
