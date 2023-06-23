package gui;

import model.Medico;
import model.Paciente;
import model.Turno;
import database.DatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SistemaMedicoGUI {
    private JFrame frame;
    private JTabbedPane tabbedPane;

    // Components for Medico tab
    private JTextField nombreMedicoField;
    private JTextField tarifaConsultaField;
    private JButton registrarMedicoButton;
    private JTable medicosTable;
    private JButton deleteMedicoButton;
    private JTextField idMedicoDeleteField;
    private JButton updateMedicoButton; // Added
    private JTextField idMedicoUpdateField; // Added
    private JTextField nombreMedicoUpdateField; // Added
    private JTextField tarifaConsultaUpdateField; // Added
    private JTextField obraSocialMedicoField;
    private JTextField obraSocialMedicoUpdateField;

    // Components for Paciente tab
    private JTextField nombrePacienteField;
    private JButton registrarPacienteButton;
    private JTable pacientesTable;
    private JButton deletePacienteButton;
    private JTextField idPacienteDeleteField;
    private JButton updatePacienteButton; // Added
    private JTextField idPacienteUpdateField; // Added
    private JTextField nombrePacienteUpdateField; // Added
    private JTextField obraSocialPacienteField;
    private JTextField obraSocialPacienteUpdateField;

    // Components for Turno tab
    private JTextField idMedicoField;
    private JTextField idPacienteField;
    private JTextField fechaHoraField;
    private JButton registrarTurnoButton;
    private JTable turnosTable;
    private JButton updateTurnoButton; // Added
    private JTextField idTurnoUpdateField; // Added
    private JTextField idMedicoUpdateFieldTurno; // Added
    private JTextField idPacienteUpdateFieldTurno; // Added
    private JTextField fechaHoraUpdateField; // Added
    // Components for Report tab
    private JTextField idMedicoReportField;
    private JTextField fechaDesdeReportField;
    private JTextField fechaHastaReportField;
    private JButton consultarReportButton;
    private JTable reportesTable;

    private DatabaseManager databaseManager;

    public SistemaMedicoGUI() {
        frame = new JFrame("Sistema Médico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        tabbedPane = new JTabbedPane();

        // Medico tab
        JPanel medicoPanel = new JPanel(new BorderLayout());
        JPanel registroMedicoPanel = new JPanel(new GridLayout(8, 2)); // Rows increased for update functionality

        registroMedicoPanel.add(new JLabel("Nombre Médico:"));
        nombreMedicoField = new JTextField();
        registroMedicoPanel.add(nombreMedicoField);
        registroMedicoPanel.add(new JLabel("Tarifa Consulta:"));
        tarifaConsultaField = new JTextField();
        registroMedicoPanel.add(tarifaConsultaField);
        registrarMedicoButton = new JButton("Registrar Médico");
        registroMedicoPanel.add(registrarMedicoButton);
        registroMedicoPanel.add(new JLabel("ID Médico para borrar:"));
        idMedicoDeleteField = new JTextField();
        registroMedicoPanel.add(idMedicoDeleteField);
        deleteMedicoButton = new JButton("Borrar Médico");
        registroMedicoPanel.add(deleteMedicoButton);
        registroMedicoPanel.add(new JLabel("ID Médico para actualizar:"));
        idMedicoUpdateField = new JTextField();
        registroMedicoPanel.add(idMedicoUpdateField);

        registroMedicoPanel.add(new JLabel("Nuevo Nombre Médico:"));
        nombreMedicoUpdateField = new JTextField();
        registroMedicoPanel.add(nombreMedicoUpdateField);

        registroMedicoPanel.add(new JLabel("Nueva Tarifa Consulta:"));
        tarifaConsultaUpdateField = new JTextField();
        registroMedicoPanel.add(tarifaConsultaUpdateField);

        updateMedicoButton = new JButton("Actualizar Médico");
        registroMedicoPanel.add(updateMedicoButton);
        registroMedicoPanel.add(new JLabel("Obra Social Médico:"));
        obraSocialMedicoField = new JTextField();
        registroMedicoPanel.add(obraSocialMedicoField);

        registroMedicoPanel.add(new JLabel("Nueva Obra Social Médico:"));
        obraSocialMedicoUpdateField = new JTextField();
        registroMedicoPanel.add(obraSocialMedicoUpdateField);


        medicoPanel.add(registroMedicoPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Nombre", "Tarifa de Consulta"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        medicosTable = new JTable(model);
        JScrollPane medicosScrollPane = new JScrollPane(medicosTable);
        medicoPanel.add(medicosScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Médicos", medicoPanel);

        // Paciente tab
        JPanel pacientePanel = new JPanel(new BorderLayout());
        JPanel registroPacientePanel = new JPanel(new GridLayout(6, 2)); // Rows increased for update functionality

        registroPacientePanel.add(new JLabel("Nombre Paciente:"));
        nombrePacienteField = new JTextField();
        registroPacientePanel.add(nombrePacienteField);

        registrarPacienteButton = new JButton("Registrar Paciente");
        registroPacientePanel.add(registrarPacienteButton);

        registroPacientePanel.add(new JLabel("ID Paciente para borrar:"));
        idPacienteDeleteField = new JTextField();
        registroPacientePanel.add(idPacienteDeleteField);

        deletePacienteButton = new JButton("Borrar Paciente");
        registroPacientePanel.add(deletePacienteButton);

        registroPacientePanel.add(new JLabel("ID Paciente para actualizar:"));
        idPacienteUpdateField = new JTextField();
        registroPacientePanel.add(idPacienteUpdateField);

        registroPacientePanel.add(new JLabel("Nuevo Nombre Paciente:"));
        nombrePacienteUpdateField = new JTextField();
        registroPacientePanel.add(nombrePacienteUpdateField);

        updatePacienteButton = new JButton("Actualizar Paciente");
        registroPacientePanel.add(updatePacienteButton);
        registroPacientePanel.add(new JLabel("Obra Social Paciente:"));
        obraSocialPacienteField = new JTextField();
        registroPacientePanel.add(obraSocialPacienteField);

        registroPacientePanel.add(new JLabel("Nueva Obra Social Paciente:"));
        obraSocialPacienteUpdateField = new JTextField();
        registroPacientePanel.add(obraSocialPacienteUpdateField);


        pacientePanel.add(registroPacientePanel, BorderLayout.NORTH);

        String[] columnNamesPacientes = {"ID", "Nombre"};
        DefaultTableModel modelPacientes = new DefaultTableModel(columnNamesPacientes, 0);
        pacientesTable = new JTable(modelPacientes);
        JScrollPane pacientesScrollPane = new JScrollPane(pacientesTable);
        pacientePanel.add(pacientesScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Pacientes", pacientePanel);

        // Turno tab
        JPanel turnoPanel = new JPanel(new BorderLayout());
        JPanel registroTurnoPanel = new JPanel(new GridLayout(8, 2)); // Rows increased for update functionality

        registroTurnoPanel.add(new JLabel("ID Médico:"));
        idMedicoField = new JTextField();
        registroTurnoPanel.add(idMedicoField);

        registroTurnoPanel.add(new JLabel("ID Paciente:"));
        idPacienteField = new JTextField();
        registroTurnoPanel.add(idPacienteField);

        registroTurnoPanel.add(new JLabel("Fecha y Hora (formato YYYY-MM-DD HH:MM):"));
        fechaHoraField = new JTextField();
        registroTurnoPanel.add(fechaHoraField);

        registrarTurnoButton = new JButton("Registrar Turno");
        registroTurnoPanel.add(registrarTurnoButton);

        registroTurnoPanel.add(new JLabel("ID Turno para actualizar:"));
        idTurnoUpdateField = new JTextField();
        registroTurnoPanel.add(idTurnoUpdateField);

        registroTurnoPanel.add(new JLabel("Nuevo ID Médico:"));
        idMedicoUpdateFieldTurno = new JTextField();
        registroTurnoPanel.add(idMedicoUpdateFieldTurno);

        registroTurnoPanel.add(new JLabel("Nuevo ID Paciente:"));
        idPacienteUpdateFieldTurno = new JTextField();
        registroTurnoPanel.add(idPacienteUpdateFieldTurno);

        registroTurnoPanel.add(new JLabel("Nueva Fecha y Hora (formato YYYY-MM-DD HH:MM):"));
        fechaHoraUpdateField = new JTextField();
        registroTurnoPanel.add(fechaHoraUpdateField);

        updateTurnoButton = new JButton("Actualizar Turno");
        registroTurnoPanel.add(updateTurnoButton);

        turnoPanel.add(registroTurnoPanel, BorderLayout.NORTH);

        String[] columnNamesTurnos = {"ID", "ID Médico", "ID Paciente", "Fecha y Hora"};
        DefaultTableModel modelTurnos = new DefaultTableModel(columnNamesTurnos, 0);
        turnosTable = new JTable(modelTurnos);
        JScrollPane turnosScrollPane = new JScrollPane(turnosTable);
        turnoPanel.add(turnosScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Turnos", turnoPanel);

        frame.add(tabbedPane);
        frame.setVisible(true);

        // Assuming your DatabaseManager is initialized somewhere
        databaseManager = new DatabaseManager();
        // Report tab
        JPanel reportPanel = new JPanel(new BorderLayout());
        JPanel consultaReportPanel = new JPanel(new GridLayout(4, 2));

        consultaReportPanel.add(new JLabel("ID Médico:"));
        idMedicoReportField = new JTextField();
        consultaReportPanel.add(idMedicoReportField);

        consultaReportPanel.add(new JLabel("Fecha Desde (formato YYYY-MM-DD):"));
        fechaDesdeReportField = new JTextField();
        consultaReportPanel.add(fechaDesdeReportField);

        consultaReportPanel.add(new JLabel("Fecha Hasta (formato YYYY-MM-DD):"));
        fechaHastaReportField = new JTextField();
        consultaReportPanel.add(fechaHastaReportField);

        consultarReportButton = new JButton("Consultar Reporte");
        consultaReportPanel.add(consultarReportButton);

        reportPanel.add(consultaReportPanel, BorderLayout.NORTH);

        String[] columnNamesReportes = {"IDTurno", "Nombre Medico Asignado", "Nombre Paciente asignado","Tarifa consulta", "Fecha"};
        DefaultTableModel modelReportes = new DefaultTableModel(columnNamesReportes, 0);
        reportesTable = new JTable(modelReportes);
        JScrollPane reportesScrollPane = new JScrollPane(reportesTable);
        reportPanel.add(reportesScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Reportes", reportPanel);
   // Action listeners for buttons
        registrarMedicoButton.addActionListener(e -> {
            String nombre = nombreMedicoField.getText();
            double tarifaConsulta = Double.parseDouble(tarifaConsultaField.getText());
            String obraSocial = obraSocialMedicoField.getText();
            Medico medico = new Medico(nombre, tarifaConsulta, obraSocial);
            try {
                databaseManager.createMedico(medico);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            updateMedicosTable();
        });

        deleteMedicoButton.addActionListener(e -> {
            int idMedico = Integer.parseInt(idMedicoDeleteField.getText());
            databaseManager.deleteMedico(idMedico);
            updateMedicosTable();
        });

        updateMedicoButton.addActionListener(e -> {
            int idMedico = Integer.parseInt(idMedicoUpdateField.getText());
            String nombre = nombreMedicoUpdateField.getText();
            double tarifaConsulta = Double.parseDouble(tarifaConsultaUpdateField.getText());
            String obraSocial = obraSocialMedicoUpdateField.getText();
            Medico medico = new Medico(idMedico, nombre, tarifaConsulta, obraSocial);
            databaseManager.updateMedico(medico);
            updateMedicosTable();
        });

        registrarPacienteButton.addActionListener(e -> {
            String nombre = nombrePacienteField.getText();String obraSocial = obraSocialPacienteField.getText();
            Paciente paciente = new Paciente(nombre, obraSocial);
            try {
                databaseManager.createPaciente(paciente);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            updatePacientesTable();
        });

        deletePacienteButton.addActionListener(e -> {
            int idPaciente = Integer.parseInt(idPacienteDeleteField.getText());
            databaseManager.deletePaciente(idPaciente);
            updatePacientesTable();
        });

        updatePacienteButton.addActionListener(e -> {
            int idPaciente = Integer.parseInt(idPacienteUpdateField.getText());
            String nombre = nombrePacienteUpdateField.getText();
            String obraSocial = obraSocialPacienteUpdateField.getText();
            Paciente paciente = new Paciente(nombre, obraSocial);
            databaseManager.updatePaciente(paciente);
            updatePacientesTable();
        });
        registrarTurnoButton.addActionListener(e -> {
            int idMedico = Integer.parseInt(idMedicoField.getText());
            int idPaciente = Integer.parseInt(idPacienteField.getText());
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            Medico medico = databaseManager.getMedicoById(idMedico);
            Paciente paciente = databaseManager.getPacienteById(idPaciente);

            if (medico != null && paciente != null) {
                Turno turno = new Turno(medico, paciente, fechaHora);  // Fixed here
                try {
                    databaseManager.createTurno(turno);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                // Handle situation where either the medico or paciente could not be found
            }
            updateTurnosTable();
            idMedicoField.setText("");
            idPacienteField.setText("");
            fechaHoraField.setText("");
        });

        updateTurnoButton.addActionListener(e -> {
            int idTurno = Integer.parseInt(idTurnoUpdateField.getText());
            int idMedico = Integer.parseInt(idMedicoUpdateFieldTurno.getText());
            int idPaciente = Integer.parseInt(idPacienteUpdateFieldTurno.getText());
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraUpdateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            Medico medico = databaseManager.getMedicoById(idMedico);
            Paciente paciente = databaseManager.getPacienteById(idPaciente);

            if (medico != null && paciente != null) {
                Turno turno = new Turno(medico, paciente, fechaHora);  // Fixed here
                //databaseManager.updateTurno(turno);
            } else {
                // Handle situation where either the medico or paciente could not be found
            }
            updateTurnosTable();

            idTurnoUpdateField.setText("");
            idMedicoUpdateFieldTurno.setText("");
            idPacienteUpdateFieldTurno.setText("");
            fechaHoraUpdateField.setText("");
        });
    }

    private void updateMedicosTable() {
        DefaultTableModel model = (DefaultTableModel) medicosTable.getModel();
        model.setRowCount(0); // Clear current table contents

        List<Medico> medicos = databaseManager.getAllMedicos();

        for (Medico medico : medicos) {
            Object[] row = {medico.getId(), medico.getNombre(), medico.getTarifaConsulta()};
            model.addRow(row);
        }
    }

    private void updatePacientesTable() {
        DefaultTableModel model = (DefaultTableModel) pacientesTable.getModel();
        model.setRowCount(0); // Clear current table contents

        List<Paciente> pacientes = databaseManager.getAllPacientes();

        for (Paciente paciente : pacientes) {
            Object[] row = {paciente.getId(), paciente.getNombre()};
            model.addRow(row);
        }
    }

    private void updateTurnosTable() {
        DefaultTableModel model = (DefaultTableModel) turnosTable.getModel();
        model.setRowCount(0); // Clear current table contents

        List<Turno> turnos = databaseManager.getAllTurnos();

        for (Turno turno : turnos) {
            Object[] row = {turno.getId(), turno.getIdMedico(), turno.getIdPaciente(), turno.getFechaHora()};
            model.addRow(row);
        }
    }

    public static void main(String[] args) {
        new SistemaMedicoGUI();
    }
}
