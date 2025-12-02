package com.mycompany.resetasmedicasnosql.service;

import com.mycompany.resetasmedicasnosql.config.MongoClientProvider;
import com.mycompany.resetasmedicasnosql.model.Medico;
import com.mycompany.resetasmedicasnosql.model.Paciente;
import com.mycompany.resetasmedicasnosql.model.Administrador;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.Optional;
import java.util.Date;
import java.util.List;

public class LoginService {
    
    public enum TipoUsuario {
        MEDICO, PACIENTE, ADMINISTRADOR, NINGUNO
    }
    
    public static class UsuarioLogin {
        public TipoUsuario tipo;
        public Object usuario;
        public String id;
        
        public UsuarioLogin(TipoUsuario tipo, Object usuario, String id) {
            this.tipo = tipo;
            this.usuario = usuario;
            this.id = id;
        }
    }
    
    /**
     * Autentica un usuario buscando en todas las colecciones
     */
    public static UsuarioLogin autenticar(String email, String contraseña) {
        System.out.println("=== INTENTANDO AUTENTICAR ===");
        System.out.println("Email: " + email);
        System.out.println("Contraseña: " + contraseña);
        
        try {
            // Primero intenta con Médicos
            System.out.println("\n1. Buscando en médicos...");
            Optional<Medico> medico = buscarMedicoByEmail(email);
            if (medico.isPresent()) {
                Medico m = medico.get();
                System.out.println("Médico encontrado: " + m.getNombre());
                System.out.println("Contraseña en BD: " + m.getContraseña());
                System.out.println("Contraseña ingresada: " + contraseña);
                System.out.println("¿Coinciden? " + (m.getContraseña() != null && m.getContraseña().equals(contraseña)));
                
                if (m.getContraseña() != null && m.getContraseña().equals(contraseña)) {
                    System.out.println("✓ MÉDICO AUTENTICADO EXITOSAMENTE");
                    return new UsuarioLogin(TipoUsuario.MEDICO, m, m.getId().toString());
                }
            }
            
            // Luego intenta con Pacientes
            System.out.println("\n2. Buscando en pacientes...");
            Optional<Paciente> paciente = buscarPacienteByEmail(email);
            if (paciente.isPresent()) {
                Paciente p = paciente.get();
                System.out.println("Paciente encontrado: " + p.getNombre());
                System.out.println("Contraseña en BD: " + p.getContraseña());
                System.out.println("Contraseña ingresada: " + contraseña);
                System.out.println("¿Coinciden? " + (p.getContraseña() != null && p.getContraseña().equals(contraseña)));
                
                if (p.getContraseña() != null && p.getContraseña().equals(contraseña)) {
                    System.out.println("✓ PACIENTE AUTENTICADO EXITOSAMENTE");
                    return new UsuarioLogin(TipoUsuario.PACIENTE, p, p.getId().toString());
                }
            }
            
            // Finalmente intenta con Administradores
            System.out.println("\n3. Buscando en administradores...");
            Optional<Administrador> admin = buscarAdminByEmail(email);
            if (admin.isPresent()) {
                Administrador a = admin.get();
                System.out.println("Administrador encontrado: " + a.getNombre());
                System.out.println("Contraseña en BD: " + a.getContraseña());
                System.out.println("Contraseña ingresada: " + contraseña);
                System.out.println("¿Coinciden? " + (a.getContraseña() != null && a.getContraseña().equals(contraseña)));
                
                if (a.getContraseña() != null && a.getContraseña().equals(contraseña)) {
                    System.out.println("✓ ADMINISTRADOR AUTENTICADO EXITOSAMENTE");
                    return new UsuarioLogin(TipoUsuario.ADMINISTRADOR, a, a.getId().toString());
                }
            }
            
            System.out.println("\n✗ NO SE ENCONTRÓ USUARIO CON ESE EMAIL O CONTRASEÑA INCORRECTA");
            
        } catch (Exception e) {
            System.err.println("ERROR EN AUTENTICACIÓN: " + e.getMessage());
            e.printStackTrace();
        }
        
        return new UsuarioLogin(TipoUsuario.NINGUNO, null, null);
    }
    
    private static Optional<Medico> buscarMedicoByEmail(String email) {
        try {
            MongoCollection<Document> collection = MongoClientProvider.INSTANCE
                .database().getCollection("medicos");
            
            Document doc = collection.find(Filters.eq("email", email)).first();
            
            if (doc != null) {
                System.out.println("   → Médico encontrado en BD");
                Medico medico = new Medico();
                medico.setId(doc.getObjectId("_id"));
                medico.setEmail(doc.getString("email"));
                medico.setContraseña(doc.getString("contraseña"));
                medico.setNombre(doc.getString("nombre"));
                medico.setApellido(doc.getString("apellido"));
                medico.setTelefono(doc.getString("telefono"));
                medico.setEspecialidad(doc.getString("especialidad"));
                medico.setCedula_profesional(doc.getString("cedula_profesional"));
                medico.setLicencia_medica(doc.getString("licencia_medica"));
                medico.setActivo(doc.getBoolean("activo") != null ? doc.getBoolean("activo") : true);
                return Optional.of(medico);
            } else {
                System.out.println("   → Médico NO encontrado con email: " + email);
            }
        } catch (Exception e) {
            System.err.println("   ERROR buscando médico: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    private static Optional<Paciente> buscarPacienteByEmail(String email) {
        try {
            MongoCollection<Document> collection = MongoClientProvider.INSTANCE
                .database().getCollection("pacientes");
            
            Document doc = collection.find(Filters.eq("email", email)).first();
            
            if (doc != null) {
                System.out.println("   → Paciente encontrado en BD");
                Paciente paciente = new Paciente();
                paciente.setId(doc.getObjectId("_id"));
                paciente.setEmail(doc.getString("email"));
                paciente.setContraseña(doc.getString("contraseña"));
                paciente.setNombre(doc.getString("nombre"));
                paciente.setApellido(doc.getString("apellido"));
                paciente.setTelefono(doc.getString("telefono"));
                paciente.setCedula(doc.getString("cedula"));
                paciente.setActivo(doc.getBoolean("activo") != null ? doc.getBoolean("activo") : true);
                return Optional.of(paciente);
            } else {
                System.out.println("   → Paciente NO encontrado con email: " + email);
            }
        } catch (Exception e) {
            System.err.println("   ERROR buscando paciente: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    private static Optional<Administrador> buscarAdminByEmail(String email) {
        try {
            MongoCollection<Document> collection = MongoClientProvider.INSTANCE
                .database().getCollection("administradores");
            
            Document doc = collection.find(Filters.eq("email", email)).first();
            
            if (doc != null) {
                System.out.println("   → Administrador encontrado en BD");
                Administrador admin = new Administrador();
                admin.setId(doc.getObjectId("_id"));
                admin.setEmail(doc.getString("email"));
                admin.setContraseña(doc.getString("contraseña"));
                admin.setNombre(doc.getString("nombre"));
                admin.setApellido(doc.getString("apellido"));
                admin.setTelefono(doc.getString("telefono"));
                admin.setActivo(doc.getBoolean("activo") != null ? doc.getBoolean("activo") : true);
                return Optional.of(admin);
            } else {
                System.out.println("   → Administrador NO encontrado con email: " + email);
            }
        } catch (Exception e) {
            System.err.println("   ERROR buscando administrador: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    /**
     * Método útil para listar todos los emails de médicos en la BD
     */
    public static void listarMedicosBD() {
        System.out.println("\n=== MÉDICOS EN BD ===");
        try {
            MongoCollection<Document> collection = MongoClientProvider.INSTANCE
                .database().getCollection("medicos");
            
            collection.find().forEach(doc -> {
                System.out.println("- Email: " + doc.getString("email") + 
                                 ", Contraseña: " + doc.getString("contraseña") +
                                 ", Nombre: " + doc.getString("nombre"));
            });
        } catch (Exception e) {
            System.err.println("Error listando médicos: " + e.getMessage());
        }
    }
    
    /**
     * Método útil para listar todos los emails de pacientes en la BD
     */
    public static void listarPacientesBD() {
        System.out.println("\n=== PACIENTES EN BD ===");
        try {
            MongoCollection<Document> collection = MongoClientProvider.INSTANCE
                .database().getCollection("pacientes");
            
            collection.find().forEach(doc -> {
                System.out.println("- Email: " + doc.getString("email") + 
                                 ", Contraseña: " + doc.getString("contraseña") +
                                 ", Nombre: " + doc.getString("nombre"));
            });
        } catch (Exception e) {
            System.err.println("Error listando pacientes: " + e.getMessage());
        }
    }
}