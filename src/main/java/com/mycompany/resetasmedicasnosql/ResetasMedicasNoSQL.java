package com.mycompany.resetasmedicasnosql;

import com.mycompany.resetasmedicasnosql.config.MongoClientProvider;
import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.model.*;

import com.mycompany.resetasmedicasnosql.repository.impl.AdministradorRepository;
import com.mycompany.resetasmedicasnosql.repository.impl.CitaRepository;
import com.mycompany.resetasmedicasnosql.repository.impl.ConsultaRepository;
import com.mycompany.resetasmedicasnosql.repository.impl.MedicoRepository;
import com.mycompany.resetasmedicasnosql.repository.impl.PacienteRepository;
import com.mycompany.resetasmedicasnosql.repository.impl.NotificacionRepository;
import com.mycompany.resetasmedicasnosql.repository.impl.RecetaRepository;

import java.util.Arrays;
import java.util.Date;
import org.bson.types.ObjectId;

/**
 * Clase principal con pruebas de repositorios
 *
 * @author gatog
 */
public class ResetasMedicasNoSQL {

    public static void main(String[] args) {
        MongoClientProvider.INSTANCE.init();

        System.out.println("=== SISTEMA DE GESTION DE CITAS MEDICAS ===");
        System.out.println("=== Iniciando pruebas de repositorios ===\n");

        try {
            correrPruebasRepository();
        } catch (Exception e) {
            System.err.println("Error en las pruebas: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== Pruebas finalizadas ===");
    }

    public static void correrPruebasRepository() throws RepositoryException {
        System.out.println(">>> PRUEBAS DE ADMINISTRADOR <<<");
        probarAdministradorRepository();

        System.out.println("\n>>> PRUEBAS DE PACIENTE <<<");
        probarPacienteRepository();

        System.out.println("\n>>> PRUEBAS DE MEDICO <<<");
        probarMedicoRepository();

        System.out.println("\n>>> PRUEBAS DE CITA <<<");
        probarCitaRepository();

        System.out.println("\n>>> PRUEBAS DE CONSULTA <<<");
        probarConsultaRepository();

        System.out.println("\n>>> PRUEBAS DE NOTIFICACION<<<");
        probarNotificacionRepository();
        
        System.out.println("\n>>> PRUEBAS DE RECETAS<<<");
        probarRecetaRepository();
    }

    private static void probarAdministradorRepository() throws RepositoryException {
        AdministradorRepository repo = new AdministradorRepository();

        long deleted = repo.deleteAll();
        System.out.println("Registros eliminados: " + deleted);

        Administrador admin = new Administrador(
                "admin@clinica.com",
                "admin123",
                "Carlos",
                "Ramirez",
                "6442222222"
        );
        admin.setPermisos(Arrays.asList("CREAR_USUARIOS", "GESTIONAR_CITAS", "VER_REPORTES"));

        ObjectId id = repo.create(admin);
        System.out.println("Administrador creado con ID: " + id);

        var found = repo.findById(id);
        if (found.isPresent()) {
            System.out.println("Administrador encontrado: " + found.get().getNombre() + " " + found.get().getApellido());
        }

        var byEmail = repo.findByEmail("admin@clinica.com");
        System.out.println("Busqueda por email exitosa: " + byEmail.isPresent());

        admin.setTelefono("6443333333");
        boolean updated = repo.update(admin);
        System.out.println("Administrador actualizado: " + updated);

        var todos = repo.findAll();
        System.out.println("Total de administradores: " + todos.size());
    }

    private static void probarPacienteRepository() throws RepositoryException {
        PacienteRepository repo = new PacienteRepository();

        long deleted = repo.deleteAll();
        System.out.println("Registros eliminados: " + deleted);

        Paciente paciente1 = new Paciente(
                "juan.perez@email.com",
                "password123",
                "Juan",
                "Perez",
                "6441234567",
                "PERJ900101ABC",
                new Date(90, 0, 1),
                "Masculino",
                "Calle Principal 123"
        );
        paciente1.setAlergias(Arrays.asList("Penicilina", "Polen"));
        paciente1.setCondiciones_previas(Arrays.asList("Hipertension"));

        ObjectId id1 = repo.create(paciente1);
        System.out.println("Paciente 1 creado con ID: " + id1);

        Paciente paciente2 = new Paciente(
                "maria.lopez@email.com",
                "pass456",
                "Maria",
                "Lopez",
                "6449876543",
                "LOPM850615XYZ",
                new Date(85, 5, 15),
                "Femenino",
                "Avenida Central 456"
        );
        paciente2.setAlergias(Arrays.asList("Mariscos"));

        ObjectId id2 = repo.create(paciente2);
        System.out.println("Paciente 2 creado con ID: " + id2);

        var found = repo.findById(id1);
        if (found.isPresent()) {
            System.out.println("Paciente encontrado: " + found.get().getNombre() + " " + found.get().getApellido());
        }

        var byEmail = repo.findByEmail("juan.perez@email.com");
        System.out.println("Busqueda por email exitosa: " + byEmail.isPresent());

        var byCedula = repo.findByCedula("PERJ900101ABC");
        System.out.println("Busqueda por cedula exitosa: " + byCedula.isPresent());

        var byNombre = repo.findByNombre("Juan");
        System.out.println("Pacientes con nombre Juan: " + byNombre.size());

        paciente1.setTelefono("6449999999");
        boolean updated = repo.update(paciente1);
        System.out.println("Paciente actualizado: " + updated);

        var todos = repo.findAll();
        System.out.println("Total de pacientes: " + todos.size());
    }

    private static void probarMedicoRepository() throws RepositoryException {
        MedicoRepository repo = new MedicoRepository();

        long deleted = repo.deleteAll();
        System.out.println("Registros eliminados: " + deleted);

        Medico medico1 = new Medico(
                "dra.maria@clinica.com",
                "securepass",
                "Maria",
                "Gonzalez",
                "6441111111",
                "MED12345",
                "Cardiologia",
                "LIC-98765",
                30
        );

        Disponibilidad disp1 = new Disponibilidad("Lunes", "09:00", "13:00");
        Disponibilidad disp2 = new Disponibilidad("Miercoles", "14:00", "18:00");
        medico1.setDisponibilidades(Arrays.asList(disp1, disp2));

        ObjectId id1 = repo.create(medico1);
        System.out.println("Medico 1 creado con ID: " + id1);

        Medico medico2 = new Medico(
                "dr.roberto@clinica.com",
                "pass789",
                "Roberto",
                "Martinez",
                "6445555555",
                "MED67890",
                "Pediatria",
                "LIC-54321",
                20
        );

        Disponibilidad disp3 = new Disponibilidad("Martes", "08:00", "14:00");
        Disponibilidad disp4 = new Disponibilidad("Jueves", "08:00", "14:00");
        medico2.setDisponibilidades(Arrays.asList(disp3, disp4));

        ObjectId id2 = repo.create(medico2);
        System.out.println("Medico 2 creado con ID: " + id2);

        var found = repo.findById(id1);
        if (found.isPresent()) {
            System.out.println("Medico encontrado: Dr(a). " + found.get().getNombre() + " " + found.get().getApellido());
        }

        var porEspecialidad = repo.findByEspecialidad("Cardiologia");
        System.out.println("Medicos de Cardiologia: " + porEspecialidad.size());

        var porCedula = repo.findByCedulaProfesional("MED12345");
        System.out.println("Busqueda por cedula profesional exitosa: " + porCedula.isPresent());

        var byEmail = repo.findByEmail("dra.maria@clinica.com");
        System.out.println("Busqueda por email exitosa: " + byEmail.isPresent());

        var activos = repo.findMedicosActivos();
        System.out.println("Medicos activos: " + activos.size());

        var todos = repo.findAll();
        System.out.println("Total de medicos: " + todos.size());
    }

    private static void probarCitaRepository() throws RepositoryException {
        CitaRepository citaRepo = new CitaRepository();
        PacienteRepository pacienteRepo = new PacienteRepository();
        MedicoRepository medicoRepo = new MedicoRepository();

        long deleted = citaRepo.deleteAll();
        System.out.println("Registros eliminados: " + deleted);

        var pacientes = pacienteRepo.findAll();
        var medicos = medicoRepo.findAll();

        if (pacientes.isEmpty() || medicos.isEmpty()) {
            System.out.println("Se requieren pacientes y medicos para probar citas");
            return;
        }

        ObjectId idPaciente1 = pacientes.get(0).getId();
        ObjectId idMedico1 = medicos.get(0).getId();

        Date fechaCita1 = new Date(System.currentTimeMillis() + 86400000);
        Cita cita1 = new Cita(idPaciente1, idMedico1, fechaCita1, "Revision general");

        ObjectId id1 = citaRepo.create(cita1);
        System.out.println("Cita 1 creada con ID: " + id1);

        Date fechaCita2 = new Date(System.currentTimeMillis() + 172800000);
        Cita cita2 = new Cita(idPaciente1, idMedico1, fechaCita2, "Seguimiento");
        cita2.setEstado("confirmada");

        ObjectId id2 = citaRepo.create(cita2);
        System.out.println("Cita 2 creada con ID: " + id2);

        var found = citaRepo.findById(id1);
        if (found.isPresent()) {
            System.out.println("Cita encontrada, estado: " + found.get().getEstado());
        }

        var citasPaciente = citaRepo.findByIdPaciente(idPaciente1);
        System.out.println("Citas del paciente: " + citasPaciente.size());

        var citasMedico = citaRepo.findByIdMedico(idMedico1);
        System.out.println("Citas del medico: " + citasMedico.size());

        var citasPendientes = citaRepo.findByEstado("pendiente");
        System.out.println("Citas pendientes: " + citasPendientes.size());

        var conflicto = citaRepo.findConflictoCita(idMedico1, fechaCita1);
        System.out.println("Conflicto detectado: " + conflicto.isPresent());

        cita1.setEstado("confirmada");
        cita1.setNotas("Paciente confirmo por telefono");
        boolean updated = citaRepo.update(cita1);
        System.out.println("Cita actualizada: " + updated);

        var confirmadas = citaRepo.findCitasConfirmadas();
        System.out.println("Citas confirmadas: " + confirmadas.size());

        var citasPorFecha = citaRepo.findCitasPorMedicoYFecha(idMedico1, fechaCita1);
        System.out.println("Citas del medico para la fecha especifica: " + citasPorFecha.size());

        var todas = citaRepo.findAll();
        System.out.println("Total de citas: " + todas.size());
    }

    private static void probarConsultaRepository() throws RepositoryException {
        ConsultaRepository repo = new ConsultaRepository();
        CitaRepository citaRepo = new CitaRepository();
        PacienteRepository pacienteRepo = new PacienteRepository();
        MedicoRepository medicoRepo = new MedicoRepository();

        long deleted = repo.deleteAll();
        System.out.println("Registros eliminados: " + deleted);

        var pacientes = pacienteRepo.findAll();
        var medicos = medicoRepo.findAll();
        var citas = citaRepo.findAll();

        if (pacientes.isEmpty() || medicos.isEmpty() || citas.isEmpty()) {
            System.out.println("Se requieren pacientes, medicos y citas para probar consultas");
            return;
        }

        ObjectId idPaciente = pacientes.get(0).getId();
        ObjectId idMedico = medicos.get(0).getId();
        ObjectId idCita = citas.get(0).getId();

        // Crear consulta 1 usando el constructor disponible
        Consulta consulta1 = new Consulta(
                idCita,
                idPaciente,
                idMedico,
                "Dolor de cabeza persistente"
        );
        consulta1.setTratamiento_recomendado("Ibuprofeno cada 8 horas");
        consulta1.setObservaciones("Paciente con antecedentes de migraña");

        ObjectId id1 = repo.create(consulta1);
        System.out.println("Consulta 1 creada con ID: " + id1);

        // Crear consulta 2
        Consulta consulta2 = new Consulta(
                idCita,
                idPaciente,
                idMedico,
                "Revision general posterior"
        );
        consulta2.setTratamiento_recomendado("Continuar reposo");

        ObjectId id2 = repo.create(consulta2);
        System.out.println("Consulta 2 creada con ID: " + id2);

        var found = repo.findById(id1);
        if (found.isPresent()) {
            System.out.println("Consulta encontrada: Diagnostico -> " + found.get().getDiagnostico());
        }

        var porCita = repo.findByIdCita(idCita);
        System.out.println("Consulta por cita encontrada: " + porCita.isPresent());

        var porPaciente = repo.findByIdPaciente(idPaciente);
        System.out.println("Consultas del paciente: " + porPaciente.size());

        var porMedico = repo.findByIdMedico(idMedico);
        System.out.println("Consultas del medico: " + porMedico.size());

        Date inicio = new Date(System.currentTimeMillis() - 86400000L * 5);
        Date fin = new Date(System.currentTimeMillis() + 86400000L);
        var entreFechas = repo.findConsultasEntrefechas(inicio, fin);
        System.out.println("Consultas entre fechas: " + entreFechas.size());

        // Actualizar consulta
        consulta1.setTratamiento_recomendado("Paracetamol cada 12 horas");
        consulta1.setObservaciones("Paciente reporta mejoria significativa");
        boolean updated = repo.update(consulta1);
        System.out.println("Consulta actualizada: " + updated);

        // Eliminar una consulta
        boolean deletedOne = repo.deleteById(id2);
        System.out.println("Consulta 2 eliminada: " + deletedOne);

        var todas = repo.findAll();
        System.out.println("Total de consultas: " + todas.size());
    }

    private static void probarNotificacionRepository() throws RepositoryException {
        NotificacionRepository repo = new NotificacionRepository();
        PacienteRepository pacienteRepo = new PacienteRepository();

        long deleted = repo.deleteAll();
        System.out.println("Registros eliminados: " + deleted);

        var pacientes = pacienteRepo.findAll();
        if (pacientes.isEmpty()) {
            System.out.println("Se requieren pacientes para probar notificaciones");
            return;
        }

        ObjectId idPaciente = pacientes.get(0).getId();

        // Crear notificacion 1
        Notificacion n1 = new Notificacion(
                idPaciente,
                "recordatorio_cita",
                "Recordatorio de cita",
                "Tiene una cita programada"
        );
        ObjectId id1 = repo.create(n1);
        System.out.println("Notificacion 1 creada con ID: " + id1);

        // Crear notificacion 2
        Notificacion n2 = new Notificacion(
                idPaciente,
                "nueva_receta",
                "Nueva receta disponible",
                "Su medico ha registrado una nueva receta"
        );
        ObjectId id2 = repo.create(n2);
        System.out.println("Notificacion 2 creada con ID: " + id2);

        // Buscar por ID
        var found = repo.findById(id1);
        if (found.isPresent()) {
            System.out.println("Notificacion encontrada: " + found.get().getTitulo());
        }

        // Buscar por paciente
        var porPaciente = repo.findByIdPaciente(idPaciente);
        System.out.println("Notificaciones del paciente: " + porPaciente.size());

        // Buscar no leidas
        var noLeidas = repo.findNotificacionesNoLeidas(idPaciente);
        System.out.println("No leidas: " + noLeidas.size());

        // Buscar por tipo
        var porTipo = repo.findByTipo("recordatorio_cita");
        System.out.println("Por tipo recordatorio: " + porTipo.size());

    }

    private static void probarRecetaRepository() throws RepositoryException {
        RecetaRepository repo = new RecetaRepository();
        PacienteRepository pacienteRepo = new PacienteRepository();
        MedicoRepository medicoRepo = new MedicoRepository();
        ConsultaRepository consultaRepo = new ConsultaRepository();

        long deleted = repo.deleteAll();
        System.out.println("Registros eliminados: " + deleted);

        var pacientes = pacienteRepo.findAll();
        var medicos = medicoRepo.findAll();
        var consultas = consultaRepo.findAll();

        if (pacientes.isEmpty() || medicos.isEmpty() || consultas.isEmpty()) {
            System.out.println("Se requieren pacientes, medicos y consultas para probar recetas");
            return;
        }

        ObjectId idPaciente = pacientes.get(0).getId();
        ObjectId idMedico = medicos.get(0).getId();
        ObjectId idConsulta = consultas.get(0).getId();

        // --- Receta 1 ---
        Receta r1 = new Receta(idConsulta, idPaciente, idMedico);
        Medicamento m1 = new Medicamento("Ibuprofeno", "200mg", "Cada 8 horas", 5, "Tomar con alimentos");
        Medicamento m2 = new Medicamento("Omeprazol", "20mg", "1 al dia", 7, "Antes del desayuno");
        r1.setMedicamentos(Arrays.asList(m1, m2));

        ObjectId id1 = repo.create(r1);
        System.out.println("Receta 1 creada con ID: " + id1);

        // --- Receta 2 ---
        Receta r2 = new Receta(idConsulta, idPaciente, idMedico);

        // ❗ Corregido: aquí faltaban paréntesis y ;
        Medicamento m3 = new Medicamento("Paracetamol", "500mg", "Cada 6 horas", 3, "En caso de dolor o fiebre");

        r2.setMedicamentos(Arrays.asList(m3));

        ObjectId id2 = repo.create(r2);
        System.out.println("Receta 2 creada con ID: " + id2);

        // --- Búsquedas ---
        var found = repo.findById(id1);
        if (found.isPresent()) {
            System.out.println("Receta encontrada: medicamentos " + found.get().getMedicamentos().size());
        }

        var porPaciente = repo.findByIdPaciente(idPaciente);
        System.out.println("Recetas del paciente: " + porPaciente.size());

        var porMedico = repo.findByIdMedico(idMedico);
        System.out.println("Recetas del medico: " + porMedico.size());
    }

}
