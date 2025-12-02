package com.mycompany.resetasmedicasnosql.repository.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mycompany.resetasmedicasnosql.config.MongoClientProvider;
import com.mycompany.resetasmedicasnosql.exception.EntityNotFoundException;
import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Notificacion;
import com.mycompany.resetasmedicasnosql.repository.INotificacionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Implementación del repositorio para la entidad Notificacion
 * @author gatog
 */
public class NotificacionRepository implements INotificacionRepository {
    
    private final MongoCollection<Notificacion> col;
    
    public NotificacionRepository() {
        this.col = MongoClientProvider.INSTANCE.getCollection("notificaciones", Notificacion.class);
    }
    
    @Override
    public ObjectId create(Notificacion entity) throws RepositoryException {
        try {
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            col.insertOne(entity);
            return entity.getId();
        } catch (MongoException e) {
            throw new RepositoryException("Error insertando notificación", e);
        }
    }
    
    @Override
    public Optional<Notificacion> findById(ObjectId _id) throws RepositoryException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("_id", _id)).first());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando notificación por ID", e);
        }
    }
    
    @Override
    public List<Notificacion> findAll() throws RepositoryException {
        try {
            return col.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando todas las notificaciones", e);
        }
    }
    
    @Override
    public boolean update(Notificacion entity) throws RepositoryException {
        try {
            List<Bson> updates = new ArrayList<>();
            updates.add(Updates.set("id_paciente", entity.getId_paciente()));
            updates.add(Updates.set("tipo", entity.getTipo()));
            updates.add(Updates.set("titulo", entity.getTitulo()));
            updates.add(Updates.set("mensaje", entity.getMensaje()));
            updates.add(Updates.set("fecha_envio", entity.getFecha_envio()));
            updates.add(Updates.set("leida", entity.isLeida()));
            
            var result = col.updateOne(
                Filters.eq("_id", entity.getId()),
                Updates.combine(updates)
            );
            
            if (result.getMatchedCount() == 0) {
                throw new EntityNotFoundException("Notificación no encontrada: " + entity.getId());
            }
            return result.getModifiedCount() > 0;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error actualizando notificación", e);
        }
    }
    
    @Override
    public boolean deleteById(ObjectId _id) throws RepositoryException {
        try {
            var result = col.deleteOne(Filters.eq("_id", _id));
            if (result.getDeletedCount() == 0) {
                throw new EntityNotFoundException("Notificación no encontrada: " + _id);
            }
            return true;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando notificación", e);
        }
    }
    
    @Override
    public long deleteAll() throws RepositoryException {
        try {
            return col.deleteMany(Filters.exists("_id")).getDeletedCount();
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando todas las notificaciones", e);
        }
    }
    
    @Override
    public List<Notificacion> findByIdPaciente(ObjectId id_paciente) throws RepositoryException {
        try {
            return col.find(Filters.eq("id_paciente", id_paciente)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando notificaciones por ID de paciente", e);
        }
    }
    
    @Override
    public List<Notificacion> findNotificacionesNoLeidas(ObjectId id_paciente) throws RepositoryException {
        try {
            return col.find(
                Filters.and(
                    Filters.eq("id_paciente", id_paciente),
                    Filters.eq("leida", false)
                )
            ).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando notificaciones no leídas", e);
        }
    }
    
    @Override
    public List<Notificacion> findByTipo(String tipo) throws RepositoryException {
        try {
            return col.find(Filters.eq("tipo", tipo)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando notificaciones por tipo", e);
        }
    }
    
    @Override
    public long marcarComoLeida(ObjectId id_notificacion) throws RepositoryException {
        try {
            var result = col.updateOne(
                Filters.eq("_id", id_notificacion),
                Updates.set("leida", true)
            );
            
            if (result.getMatchedCount() == 0) {
                throw new EntityNotFoundException("Notificación no encontrada: " + id_notificacion);
            }
            return result.getModifiedCount();
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error marcando notificación como leída", e);
        }
    }
}
