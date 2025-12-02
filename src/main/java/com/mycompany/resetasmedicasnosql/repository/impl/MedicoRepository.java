package com.mycompany.resetasmedicasnosql.repository.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mycompany.resetasmedicasnosql.config.MongoClientProvider;
import com.mycompany.resetasmedicasnosql.exception.EntityNotFoundException;
import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Medico;
import com.mycompany.resetasmedicasnosql.repository.IMedicoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Implementación del repositorio para la entidad Medico
 * @author gatog
 */
public class MedicoRepository implements IMedicoRepository {
    
    private final MongoCollection<Medico> col;
    
    public MedicoRepository() {
        this.col = MongoClientProvider.INSTANCE.getCollection("medicos", Medico.class);
    }
    
    @Override
    public ObjectId create(Medico entity) throws RepositoryException {
        try {
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            col.insertOne(entity);
            return entity.getId();
        } catch (MongoException e) {
            throw new RepositoryException("Error insertando médico", e);
        }
    }
    
    @Override
    public Optional<Medico> findById(ObjectId _id) throws RepositoryException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("_id", _id)).first());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando médico por ID", e);
        }
    }
    
    @Override
    public List<Medico> findAll() throws RepositoryException {
        try {
            return col.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando todos los médicos", e);
        }
    }
    
    @Override
    public boolean update(Medico entity) throws RepositoryException {
        try {
            List<Bson> updates = new ArrayList<>();
            updates.add(Updates.set("email", entity.getEmail()));
            updates.add(Updates.set("contraseña", entity.getContraseña()));
            updates.add(Updates.set("nombre", entity.getNombre()));
            updates.add(Updates.set("apellido", entity.getApellido()));
            updates.add(Updates.set("telefono", entity.getTelefono()));
            updates.add(Updates.set("activo", entity.isActivo()));
            updates.add(Updates.set("cedula_profesional", entity.getCedula_profesional()));
            updates.add(Updates.set("especialidad", entity.getEspecialidad()));
            updates.add(Updates.set("licencia_medica", entity.getLicencia_medica()));
            updates.add(Updates.set("duracion_consulta_minutos", entity.getDuracion_consulta_minutos()));
            updates.add(Updates.set("disponibilidades", entity.getDisponibilidades()));
            
            var result = col.updateOne(
                Filters.eq("_id", entity.getId()),
                Updates.combine(updates)
            );
            
            if (result.getMatchedCount() == 0) {
                throw new EntityNotFoundException("Médico no encontrado: " + entity.getId());
            }
            return result.getModifiedCount() > 0;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error actualizando médico", e);
        }
    }
    
    @Override
    public boolean deleteById(ObjectId _id) throws RepositoryException {
        try {
            var result = col.deleteOne(Filters.eq("_id", _id));
            if (result.getDeletedCount() == 0) {
                throw new EntityNotFoundException("Médico no encontrado: " + _id);
            }
            return true;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando médico", e);
        }
    }
    
    @Override
    public long deleteAll() throws RepositoryException {
        try {
            return col.deleteMany(Filters.exists("_id")).getDeletedCount();
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando todos los médicos", e);
        }
    }
    
    @Override
    public Optional<Medico> findByEmail(String email) throws RepositoryException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("email", email)).first());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando médico por email", e);
        }
    }
    
    @Override
    public Optional<Medico> findByCedulaProfesional(String cedula_profesional) throws RepositoryException {
        try {
            return Optional.ofNullable(
                col.find(Filters.eq("cedula_profesional", cedula_profesional)).first()
            );
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando médico por cédula profesional", e);
        }
    }
    
    @Override
    public List<Medico> findByEspecialidad(String especialidad) throws RepositoryException {
        try {
            return col.find(Filters.eq("especialidad", especialidad)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando médicos por especialidad", e);
        }
    }
    
    @Override
    public List<Medico> findByNombre(String nombre) throws RepositoryException {
        try {
            return col.find(Filters.regex("nombre", nombre, "i")).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando médicos por nombre", e);
        }
    }
    
    @Override
    public List<Medico> findMedicosActivos() throws RepositoryException {
        try {
            return col.find(Filters.eq("activo", true)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando médicos activos", e);
        }
    }
}