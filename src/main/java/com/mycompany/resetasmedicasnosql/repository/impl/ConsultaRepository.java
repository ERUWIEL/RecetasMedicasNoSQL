package com.mycompany.resetasmedicasnosql.repository.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mycompany.resetasmedicasnosql.config.MongoClientProvider;
import com.mycompany.resetasmedicasnosql.exception.EntityNotFoundException;
import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.Consulta;
import com.mycompany.resetasmedicasnosql.repository.IConsultaRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Implementación del repositorio para la entidad Consulta
 * @author gatog
 */
public class ConsultaRepository implements IConsultaRepository {
    
    private final MongoCollection<Consulta> col;
    
    public ConsultaRepository() {
        this.col = MongoClientProvider.INSTANCE.getCollection("consultas", Consulta.class);
    }
    
    @Override
    public ObjectId create(Consulta entity) throws RepositoryException {
        try {
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
            col.insertOne(entity);
            return entity.getId();
        } catch (MongoException e) {
            throw new RepositoryException("Error insertando consulta", e);
        }
    }
    
    @Override
    public Optional<Consulta> findById(ObjectId _id) throws RepositoryException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("_id", _id)).first());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando consulta por ID", e);
        }
    }
    
    @Override
    public List<Consulta> findAll() throws RepositoryException {
        try {
            return col.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando todas las consultas", e);
        }
    }
    
    @Override
    public boolean update(Consulta entity) throws RepositoryException {
        try {
            List<Bson> updates = new ArrayList<>();
            updates.add(Updates.set("id_cita", entity.getId_cita()));
            updates.add(Updates.set("id_paciente", entity.getId_paciente()));
            updates.add(Updates.set("id_medico", entity.getId_medico()));
            updates.add(Updates.set("fecha_consulta", entity.getFecha_consulta()));
            updates.add(Updates.set("diagnostico", entity.getDiagnostico()));
            updates.add(Updates.set("observaciones", entity.getObservaciones()));
            updates.add(Updates.set("tratamiento_recomendado", entity.getTratamiento_recomendado()));
            
            var result = col.updateOne(
                Filters.eq("_id", entity.getId()),
                Updates.combine(updates)
            );
            
            if (result.getMatchedCount() == 0) {
                throw new EntityNotFoundException("Consulta no encontrada: " + entity.getId());
            }
            return result.getModifiedCount() > 0;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error actualizando consulta", e);
        }
    }
    
    @Override
    public boolean deleteById(ObjectId _id) throws RepositoryException {
        try {
            var result = col.deleteOne(Filters.eq("_id", _id));
            if (result.getDeletedCount() == 0) {
                throw new EntityNotFoundException("Consulta no encontrada: " + _id);
            }
            return true;
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando consulta", e);
        }
    }
    
    @Override
    public long deleteAll() throws RepositoryException {
        try {
            return col.deleteMany(Filters.exists("_id")).getDeletedCount();
        } catch (MongoException e) {
            throw new RepositoryException("Error eliminando todas las consultas", e);
        }
    }
    
    @Override
    public Optional<Consulta> findByIdCita(ObjectId id_cita) throws RepositoryException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("id_cita", id_cita)).first());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando consulta por ID de cita", e);
        }
    }
    
    @Override
    public List<Consulta> findByIdPaciente(ObjectId id_paciente) throws RepositoryException {
        try {
            return col.find(Filters.eq("id_paciente", id_paciente)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando consultas por ID de paciente", e);
        }
    }
    
    @Override
    public List<Consulta> findByIdMedico(ObjectId id_medico) throws RepositoryException {
        try {
            return col.find(Filters.eq("id_medico", id_medico)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando consultas por ID de médico", e);
        }
    }
    
    @Override
    public List<Consulta> findConsultasEntrefechas(Date fecha_inicio, Date fecha_fin) throws RepositoryException {
        try {
            return col.find(
                Filters.and(
                    Filters.gte("fecha_consulta", fecha_inicio),
                    Filters.lte("fecha_consulta", fecha_fin)
                )
            ).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new RepositoryException("Error consultando consultas entre fechas", e);
        }
    }
}