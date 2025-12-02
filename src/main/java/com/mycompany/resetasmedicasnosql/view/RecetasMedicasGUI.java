/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resetasmedicasnosql.view;

/**
 *
 * @author Luis Valenzuela
 */
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import org.bson.types.ObjectId;
import com.mycompany.resetasmedicasnosql.config.MongoClientProvider;
import com.mycompany.resetasmedicasnosql.model.*;
import com.mycompany.resetasmedicasnosql.repository.*;
import com.mycompany.resetasmedicasnosql.service.*;
import com.mycompany.resetasmedicasnosql.exception.RepositoryException;
import com.mycompany.resetasmedicasnosql.repository.impl.AdministradorRepository;
import com.mycompany.resetasmedicasnosql.repository.impl.CitaRepository;
import com.mycompany.resetasmedicasnosql.repository.impl.MedicoRepository;
import com.mycompany.resetasmedicasnosql.repository.impl.PacienteRepository;
import com.mycompany.resetasmedicasnosql.repository.impl.RecetaRepository;

public class RecetasMedicasGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String usuarioActual;
    private String tipoUsuario;
    private ObjectId usuarioId;
    
    // Servicios
    private PacienteService pacienteService;
    private MedicoService medicoService;
    private CitaService citaService;
    private RecetaService recetaService;
    private AdministradorService administradorService;

    public RecetasMedicasGUI() {
        initializeServices();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setTitle("Sistema de Recetas Médicas NoSQL");

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        mainPanel.add(new LoginPanel(), "login");
        
        add(mainPanel);
        setVisible(true);
    }

    private void initializeServices() {
        try {
            MongoClientProvider.INSTANCE.init();
            
            // Inicializar repositorios
            PacienteRepository pacienteRepo = new PacienteRepository();
            MedicoRepository medicoRepo = new MedicoRepository();
            CitaRepository citaRepo = new CitaRepository();
            RecetaRepository recetaRepo = new RecetaRepository();
            AdministradorRepository adminRepo = new AdministradorRepository();
            
            // Inicializar servicios
            pacienteService = new PacienteService(pacienteRepo);
            medicoService = new MedicoService(medicoRepo);
            citaService = new CitaService(citaRepo, medicoRepo);
            recetaService = new RecetaService(recetaRepo, pacienteRepo);
            administradorService = new AdministradorService(adminRepo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage(),
                "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class LoginPanel extends JPanel {
        private JTextField emailField;
        private JPasswordField passwordField;

        public LoginPanel() {
            setLayout(new GridBagLayout());
            setBackground(new Color(70, 130, 180));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBackground(Color.WHITE);
            centerPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
            centerPanel.setMaximumSize(new Dimension(400, 500));

            JLabel titleLabel = new JLabel("Recetas Médicas");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(titleLabel);

            JLabel subtitleLabel = new JLabel("Sistema NoSQL");
            subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            subtitleLabel.setForeground(Color.GRAY);
            subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(subtitleLabel);

            centerPanel.add(Box.createVerticalStrut(30));

            JLabel emailLabel = new JLabel("Email:");
            emailLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            centerPanel.add(emailLabel);
            emailField = new JTextField(20);
            emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
            centerPanel.add(emailField);

            centerPanel.add(Box.createVerticalStrut(15));

            JLabel passLabel = new JLabel("Contraseña:");
            passLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            centerPanel.add(passLabel);
            passwordField = new JPasswordField(20);
            passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
            centerPanel.add(passwordField);

            centerPanel.add(Box.createVerticalStrut(25));

            JButton loginButton = new JButton("Iniciar Sesión");
            loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            loginButton.setBackground(new Color(70, 130, 180));
            loginButton.setForeground(Color.WHITE);
            loginButton.setFont(new Font("Arial", Font.BOLD, 14));
            loginButton.addActionListener(e -> handleLogin());
            centerPanel.add(loginButton);

            centerPanel.add(Box.createVerticalStrut(20));

            JPanel testPanel = new JPanel();
            testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
            testPanel.setBackground(new Color(240, 248, 255));
            testPanel.setBorder(new LineBorder(new Color(70, 130, 180), 1));

            JLabel testLabel = new JLabel("Usuarios de prueba:");
            testLabel.setFont(new Font("Arial", Font.BOLD, 11));
            testPanel.add(testLabel);
            testPanel.add(new JLabel("📧 admin@ejemplo.com - pass: admin"));
            testPanel.add(new JLabel("👨‍⚕️ medico@ejemplo.com - pass: medico"));
            testPanel.add(new JLabel("👤 paciente@ejemplo.com - pass: paciente"));

            centerPanel.add(testPanel);

            gbc.gridx = 0;
            gbc.gridy = 0;
            add(centerPanel, gbc);
        }

        private void handleLogin() {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor completa todos los campos",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                boolean autenticado = false;
                
                if (email.contains("admin")) {
                    Administrador admin = administradorService.autenticarAdmin(email, password);
                    if (admin != null) {
                        usuarioActual = email;
                        tipoUsuario = "admin";
                        usuarioId = admin.getId();
                        autenticado = true;
                    }
                } else if (email.contains("medico")) {
                    Medico medico = medicoService.autenticarMedico(email, password);
                    if (medico != null) {
                        usuarioActual = email;
                        tipoUsuario = "medico";
                        usuarioId = medico.getId();
                        autenticado = true;
                    }
                } else {
                    Paciente paciente = pacienteService.autenticarPaciente(email, password);
                    if (paciente != null) {
                        usuarioActual = email;
                        tipoUsuario = "paciente";
                        usuarioId = paciente.getId();
                        autenticado = true;
                    }
                }

                if (autenticado) {
                    mainPanel.add(new DashboardPanel(), "dashboard");
                    cardLayout.show(mainPanel, "dashboard");
                } else {
                    JOptionPane.showMessageDialog(this, "Email o contraseña incorrectos",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error de autenticación: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DashboardPanel extends JPanel {
        private CardLayout contentCardLayout;
        private JPanel contentPanel;

        public DashboardPanel() {
            setLayout(new BorderLayout());

            JPanel sidebarPanel = new JPanel();
            sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
            sidebarPanel.setBackground(new Color(25, 45, 85));
            sidebarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            JLabel logoLabel = new JLabel("RecetasMed");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 16));
            logoLabel.setForeground(Color.WHITE);
            logoLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            sidebarPanel.add(logoLabel);

            sidebarPanel.add(Box.createVerticalStrut(20));

            JButton dashboardBtn = createSidebarButton("📊 Dashboard", e -> showContent("dashboard_content"));
            JButton citasBtn = createSidebarButton("📅 Citas", e -> showContent("citas_content"));
            JButton recetasBtn = createSidebarButton("📋 Recetas", e -> showContent("recetas_content"));

            sidebarPanel.add(dashboardBtn);
            sidebarPanel.add(citasBtn);
            sidebarPanel.add(recetasBtn);

            if (tipoUsuario.equals("admin") || tipoUsuario.equals("medico")) {
                JButton usuariosBtn = createSidebarButton(
                    tipoUsuario.equals("admin") ? "👨‍⚕️ Médicos" : "👤 Pacientes",
                    e -> showContent("usuarios_content")
                );
                sidebarPanel.add(usuariosBtn);
            }

            sidebarPanel.add(Box.createVerticalGlue());

            JButton logoutBtn = new JButton("Cerrar Sesión");
            logoutBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            logoutBtn.setBackground(new Color(220, 20, 60));
            logoutBtn.setForeground(Color.WHITE);
            logoutBtn.addActionListener(e -> handleLogout());
            sidebarPanel.add(logoutBtn);

            contentCardLayout = new CardLayout();
            contentPanel = new JPanel(contentCardLayout);
            contentPanel.add(createDashboardContent(), "dashboard_content");
            contentPanel.add(createCitasContent(), "citas_content");
            contentPanel.add(createRecetasContent(), "recetas_content");
            if (tipoUsuario.equals("admin") || tipoUsuario.equals("medico")) {
                contentPanel.add(createUsuariosContent(), "usuarios_content");
            }

            add(sidebarPanel, BorderLayout.WEST);
            add(contentPanel, BorderLayout.CENTER);
        }

        private void showContent(String panelName) {
            contentCardLayout.show(contentPanel, panelName);
        }

        private JButton createSidebarButton(String text, ActionListener listener) {
            JButton btn = new JButton(text);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            btn.setBackground(new Color(45, 85, 150));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.PLAIN, 12));
            btn.addActionListener(listener);
            btn.setMargin(new Insets(10, 10, 10, 10));
            return btn;
        }

        private JPanel createDashboardContent() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.WHITE);
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));

            JLabel titleLabel = new JLabel("Dashboard");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            panel.add(titleLabel);

            panel.add(Box.createVerticalStrut(20));

            JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
            statsPanel.setOpaque(false);
            
            try {
                int citasCount = 0;
                int recetasCount = 0;
                int pacientesCount = 0;

                if (tipoUsuario.equals("paciente")) {
                    List<Cita> citas = citaService.obtenerCitasPaciente(usuarioId);
                    citasCount = citas.size();
                    List<Receta> recetas = recetaService.obtenerRecetasActivasPaciente(usuarioId);
                    recetasCount = recetas.size();
                } else if (tipoUsuario.equals("medico")) {
                    List<Cita> citas = citaService.obtenerCitasMedico(usuarioId);
                    citasCount = citas.size();
                    List<Receta> recetas = recetaService.obtenerRecetasMedico(usuarioId);
                    recetasCount = recetas.size();
                    pacientesCount = 12;
                } else {
                    pacientesCount = pacienteService.obtenerTodosPacientes().size();
                }

                statsPanel.add(createStatCard("Citas Próximas", String.valueOf(citasCount), new Color(70, 130, 180)));
                statsPanel.add(createStatCard("Recetas Activas", String.valueOf(recetasCount), new Color(34, 139, 34)));
                statsPanel.add(createStatCard("Pacientes", String.valueOf(pacientesCount), new Color(153, 102, 204)));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(panel, "Error al cargar estadísticas: " + e.getMessage());
            }

            panel.add(statsPanel);
            panel.add(Box.createVerticalGlue());

            return panel;
        }

        private JPanel createStatCard(String title, String value, Color color) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(color);
            card.setBorder(new EmptyBorder(20, 20, 20, 20));
            card.setMaximumSize(new Dimension(200, 120));

            JLabel valueLabel = new JLabel(value);
            valueLabel.setFont(new Font("Arial", Font.BOLD, 32));
            valueLabel.setForeground(Color.WHITE);
            card.add(valueLabel);

            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            titleLabel.setForeground(Color.WHITE);
            card.add(titleLabel);

            return card;
        }

        private JPanel createCitasContent() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.WHITE);
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));

            JLabel titleLabel = new JLabel("Gestión de Citas");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            panel.add(titleLabel);

            if (tipoUsuario.equals("paciente")) {
                JButton agendar = new JButton("+ Agendar Cita");
                agendar.setMaximumSize(new Dimension(200, 40));
                agendar.setBackground(new Color(70, 130, 180));
                agendar.setForeground(Color.WHITE);
                agendar.addActionListener(e -> mostrarFormularioCita());
                panel.add(Box.createVerticalStrut(15));
                panel.add(agendar);
            }

            panel.add(Box.createVerticalStrut(20));

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Médico");
            model.addColumn("Fecha");
            model.addColumn("Hora");
            model.addColumn("Estado");

            try {
                List<Cita> citas = new ArrayList<>();
                if (tipoUsuario.equals("paciente")) {
                    citas = citaService.obtenerCitasPaciente(usuarioId);
                } else if (tipoUsuario.equals("medico")) {
                    citas = citaService.obtenerCitasMedico(usuarioId);
                }

                for (Cita cita : citas) {
                    Medico medico = medicoService.obtenerMedico(cita.getId_medico());
                    model.addRow(new Object[]{
                        medico.getNombre(),
                        cita.getFecha_hora().toString().substring(0, 10),
                        cita.getFecha_hora().toString().substring(11, 16),
                        cita.getEstado()
                    });
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(panel, "Error al cargar citas: " + e.getMessage());
            }

            JTable table = new JTable(model);
            table.setRowHeight(25);
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane);

            panel.add(Box.createVerticalGlue());

            return panel;
        }

        private JPanel createRecetasContent() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.WHITE);
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));

            JLabel titleLabel = new JLabel("Gestión de Recetas");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            panel.add(titleLabel);

            panel.add(Box.createVerticalStrut(20));

            JPanel recetasPanel = new JPanel();
            recetasPanel.setLayout(new BoxLayout(recetasPanel, BoxLayout.Y_AXIS));

            try {
                List<Receta> recetas = new ArrayList<>();
                if (tipoUsuario.equals("paciente")) {
                    recetas = recetaService.obtenerRecetasPaciente(usuarioId);
                } else if (tipoUsuario.equals("medico")) {
                    recetas = recetaService.obtenerRecetasMedico(usuarioId);
                }

                for (Receta receta : recetas) {
                    JPanel recetaCard = new JPanel();
                    recetaCard.setLayout(new BorderLayout());
                    recetaCard.setBackground(new Color(240, 248, 255));
                    recetaCard.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
                    recetaCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                    Medico medico = medicoService.obtenerMedico(receta.getId_medico());
                    String recetaText = "Receta - " + medico.getNombre() + " - " + 
                        receta.getFecha_emision().toString().substring(0, 10) + " - " +
                        (receta.getMedicamentos() != null ? receta.getMedicamentos().size() : 0) + 
                        " medicamentos - " + (receta.isActiva() ? "Activa" : "Inactiva");

                    JLabel recetaLabel = new JLabel(recetaText);
                    recetaLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                    recetaCard.add(recetaLabel, BorderLayout.CENTER);

                    JButton verBtn = new JButton("Ver");
                    verBtn.setPreferredSize(new Dimension(80, 30));
                    recetaCard.add(verBtn, BorderLayout.EAST);

                    recetasPanel.add(recetaCard);
                    recetasPanel.add(Box.createVerticalStrut(10));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(panel, "Error al cargar recetas: " + e.getMessage());
            }

            JScrollPane scrollPane = new JScrollPane(recetasPanel);
            panel.add(scrollPane);

            panel.add(Box.createVerticalGlue());

            return panel;
        }

        private JPanel createUsuariosContent() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.WHITE);
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));

            JLabel titleLabel = new JLabel(tipoUsuario.equals("admin") ? "Gestión de Médicos" : "Gestión de Pacientes");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            panel.add(titleLabel);

            JButton agregarBtn = new JButton("+ Agregar");
            agregarBtn.setMaximumSize(new Dimension(200, 40));
            agregarBtn.setBackground(new Color(70, 130, 180));
            agregarBtn.setForeground(Color.WHITE);
            agregarBtn.addActionListener(e -> mostrarFormularioUsuario());
            panel.add(Box.createVerticalStrut(15));
            panel.add(agregarBtn);

            panel.add(Box.createVerticalStrut(20));

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre");
            model.addColumn("Email");
            model.addColumn("Especialidad");
            model.addColumn("Estado");

            try {
                if (tipoUsuario.equals("admin")) {
                    List<Medico> medicos = medicoService.obtenerMedicosActivos();
                    for (Medico medico : medicos) {
                        model.addRow(new Object[]{
                            medico.getNombre(),
                            medico.getEmail(),
                            medico.getEspecialidad(),
                            medico.isActivo() ? "Activo" : "Inactivo"
                        });
                    }
                } else {
                    List<Paciente> pacientes = pacienteService.obtenerTodosPacientes();
                    for (Paciente paciente : pacientes) {
                        model.addRow(new Object[]{
                            paciente.getNombre(),
                            paciente.getEmail(),
                            "N/A",
                            paciente.isActivo() ? "Activo" : "Inactivo"
                        });
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(panel, "Error al cargar usuarios: " + e.getMessage());
            }

            JTable table = new JTable(model);
            table.setRowHeight(25);
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane);

            panel.add(Box.createVerticalGlue());

            return panel;
        }

        private void mostrarFormularioCita() {
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                "Nueva Cita", true);
            dialog.setSize(400, 350);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));

            panel.add(new JLabel("Fecha (YYYY-MM-DD):"));
            JTextField fechaField = new JTextField();
            fechaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            panel.add(fechaField);

            panel.add(Box.createVerticalStrut(10));
            panel.add(new JLabel("Hora (HH:MM):"));
            JTextField horaField = new JTextField();
            horaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            panel.add(horaField);

            panel.add(Box.createVerticalStrut(10));
            panel.add(new JLabel("Médico:"));
            JComboBox<String> medicoBox = new JComboBox<>();
            medicoBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            
            try {
                List<Medico> medicos = medicoService.obtenerMedicosActivos();
                for (Medico m : medicos) {
                    medicoBox.addItem(m.getId().toString() + "|" + m.getNombre());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(dialog, "Error al cargar médicos");
            }
            
            panel.add(medicoBox);

            panel.add(Box.createVerticalStrut(10));
            panel.add(new JLabel("Motivo:"));
            JTextArea motivoArea = new JTextArea(4, 20);
            motivoArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
            panel.add(motivoArea);

            JButton guardarBtn = new JButton("Guardar");
            guardarBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            guardarBtn.addActionListener(e -> {
                try {
                    String[] medicoParts = ((String) medicoBox.getSelectedItem()).split("\\|");
                    ObjectId medicoId = new ObjectId(medicoParts[0]);
                    
                    java.util.Date fecha = new java.util.Date();
                    Cita cita = new Cita(usuarioId, medicoId, fecha, motivoArea.getText());
                    citaService.crearCita(cita);
                    
                    JOptionPane.showMessageDialog(dialog, "Cita agendada correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            panel.add(Box.createVerticalStrut(10));
            panel.add(guardarBtn);

            dialog.add(new JScrollPane(panel));
            dialog.setVisible(true);
        }

        private void mostrarFormularioUsuario() {
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                "Nuevo Usuario", true);
            dialog.setSize(400, 350);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));

            panel.add(new JLabel("Nombre:"));
            JTextField nombreField = new JTextField();
            nombreField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            panel.add(nombreField);

            panel.add(Box.createVerticalStrut(10));
            panel.add(new JLabel("Email:"));
            JTextField emailField = new JTextField();
            emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            panel.add(emailField);

            panel.add(Box.createVerticalStrut(10));
            panel.add(new JLabel("Teléfono:"));
            JTextField telefonoField = new JTextField();
            telefonoField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            panel.add(telefonoField);

            panel.add(Box.createVerticalStrut(10));
            panel.add(new JLabel("Especialidad:"));
            JTextField especialidadField = new JTextField();
            especialidadField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            panel.add(especialidadField);

            JButton guardarBtn = new JButton("Guardar");
            guardarBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            guardarBtn.addActionListener(e -> {
                try {
                    if (tipoUsuario.equals("admin")) {
                        Medico medico = new Medico(emailField.getText(), "password123",
                            nombreField.getText(), "", telefonoField.getText(),
                            "", especialidadField.getText(), "", 30);
                        medicoService.registrarMedico(medico);
                    }
                    JOptionPane.showMessageDialog(dialog, "Usuario creado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            panel.add(Box.createVerticalStrut(10));
            panel.add(guardarBtn);

            dialog.add(new JScrollPane(panel));
            dialog.setVisible(true);
        }

        private void handleLogout() {
            mainPanel.removeAll();
            mainPanel.add(new LoginPanel(), "login");
            cardLayout.show(mainPanel, "login");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecetasMedicasGUI());
    }
}
