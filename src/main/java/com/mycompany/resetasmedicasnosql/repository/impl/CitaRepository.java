package com.mycompany.resetasmedicasnosql.repository.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mycompany.resetasmedicasnosql.config.MongoClientProvider;
import com.mycompany.resetasmedicasnosql.exception.EntityNotFoundException;
import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Cita;
import com.mycompany.resetasmedicasnosql.repository.ICitaRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Implementación del repositorio para la entidad Cita
 * @author gatog
 */
public class CitaRepository implements ICitaRepository {
    
    private final MongoCollection<Cita> col;
    
    public CitaRepository() {
        this.col = MongoClientProvider.INSTANCE.getCollection("citas", Cita.class);
    }
    
    @Override
    public ObjectId create(Cita entity) throws RepositoryException {
        try {
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            col.insertOne(entity);
            return entity.getId();
        } catch (MongoException e) {
            throw new RepositoryException("Error insertando cita", e);
        }
    }
    
    @Override
    public Optional<Cita> findById(ObjectId _id) throws RepositoryException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("_id", _id)).first());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando cita por ID", e);
        }
    }
    
    @Override
    public List<Cita> findAll() throws RepositoryException {
        try {
            return col.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando todas las citas", e);
        }
    }
    
    @Override
    public boolean update(Cita entity) throws RepositoryException {
        try {
            List<Bson> updates = new ArrayList<>();
            updates.add(Updates.set("id_paciente", entity.getId_paciente()));
            updates.add(Updates.set("id_medico", entity.getId_medico()));
            updates.add(Updates.set("fecha_hora", entity.getFecha_hora()));
            updates.add(Updates.set("estado", entity.getEstado()));
            updates.add(Updates.set("motivo_consulta", entity.getMotivo_consulta()));
            updates.add(Updates.set("notas", entity.getNotas()));
            updates.add(Updates.set("fecha_cancelacion", entity.getFecha_cancelacion()));
            
            var result = col.updateOne(
                Filters.eq("_id", entity.getId()),
                Updates.combine(updates)
            );
            
            if (result.getMatchedCount() == 0) {
                throw new EntityNotFoundException("Cita no encontrada: " + entity.getId());
            }
            return result.getModifiedCount() > 0;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error actualizando cita", e);
        }
    }
    
    @Override
    public boolean deleteById(ObjectId _id) throws RepositoryException {
        try {
            var result = col.deleteOne(Filters.eq("_id", _id));
            if (result.getDeletedCount() == 0) {
                throw new EntityNotFoundException("Cita no encontrada: " + _id);
            }
            return true;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando cita", e);
        }
    }
    
    @Override
    public long deleteAll() throws RepositoryException {
        try {
            return col.deleteMany(Filters.exists("_id")).getDeletedCount();
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando todas las citas", e);
        }
    }
    
    @Override
    public List<Cita> findByIdPaciente(ObjectId id_paciente) throws RepositoryException {
        try {
            return col.find(Filters.eq("id_paciente", id_paciente)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando citas por ID de paciente", e);
        }
    }
    
    @Override
    public List<Cita> findByIdMedico(ObjectId id_medico) throws RepositoryException {
        try {
            return col.find(Filters.eq("id_medico", id_medico)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando citas por ID de médico", e);
        }
    }
    
    @Override
    public List<Cita> findByEstado(String estado) throws RepositoryException {
        try {
            return col.find(Filters.eq("estado", estado)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando citas por estado", e);
        }
    }
    
    @Override
    public List<Cita> findByFechaHora(Date fecha_hora) throws RepositoryException {
        try {
            return col.find(Filters.eq("fecha_hora", fecha_hora)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando citas por fecha y hora", e);
        }
    }
    
    @Override
    public List<Cita> findCitasPorMedicoYFecha(ObjectId id_medico, Date fecha) throws RepositoryException {
        try {
            // Configurar inicio del día
            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date startDate = cal.getTime();
            
            // Configurar fin del día
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            Date endDate = cal.getTime();
            
            return col.find(
                Filters.and(
                    Filters.eq("id_medico", id_medico),
                    Filters.gte("fecha_hora", startDate),
                    Filters.lte("fecha_hora", endDate)
                )
            ).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando citas por médico y fecha", e);
        }
    }
    
    @Override
    public List<Cita> findCitasConfirmadas() throws RepositoryException {
        try {
            return col.find(Filters.eq("estado", "confirmada")).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando citas confirmadas", e);
        }
    }
    
    @Override
    public Optional<Cita> findConflictoCita(ObjectId id_medico, Date fecha_hora) throws RepositoryException {
        try {
            return Optional.ofNullable(
                col.find(
                    Filters.and(
                        Filters.eq("id_medico", id_medico),
                        Filters.eq("fecha_hora", fecha_hora),
                        Filters.in("estado", "pendiente", "confirmada")
                    )
                ).first()
            );
        } catch (MongoException e) {
            throw new RepositoryException("Error verificando conflicto de cita", e);
        }
    }
}
