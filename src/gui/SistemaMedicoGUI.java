package gui;

import model.Medico;
import model.Paciente;
import model.Turno;
import database.DatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SistemaMedicoGUI {
    private JFrame frame;
    private JTabbedPane tabbedPane;

    // Componentes para la pestaña de Médicos
    private JTextField nombreMedicoField;
    private JTextField tarifaConsultaField;
    private JButton registrarMedicoButton;
    private JTable medicosTable;
    private JButton deleteMedicoButton;
    private JTextField idMedicoDeleteField;

    // Componentes para la pestaña de Pacientes
    private JTextField nombrePacienteField;
    private JButton registrarPacienteButton;
    private JTable pacientesTable;
    private JButton deletePacienteButton;
    private JTextField idPacienteDeleteField;

    // Componentes para la pestaña de Turnos
    private JTextField idMedicoField;
    private JTextField idPacienteField;
    private JTextField fechaHoraField;
    private JButton registrarTurnoButton;
    private JTable turnosTable;

    private DatabaseManager databaseManager;

    public SistemaMedicoGUI() {
        frame = new JFrame("Sistema Médico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        tabbedPane = new JTabbedPane();

        // Pestaña de Médicos
        JPanel medicoPanel = new JPanel(new BorderLayout());

        // Panel de registro de médicos
        JPanel registroMedicoPanel = new JPanel(new GridLayout(5, 2));

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

        medicoPanel.add(registroMedicoPanel, BorderLayout.NORTH);

        // Tabla de médicos
        String[] columnNames = {"ID", "Nombre", "Tarifa de Consulta"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        medicosTable = new JTable(model);
        JScrollPane medicosScrollPane = new JScrollPane(medicosTable);
        medicoPanel.add(medicosScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Médicos", medicoPanel);

        // Pestaña de Pacientes
        JPanel pacientePanel = new JPanel(new BorderLayout());

        // Panel de registro de pacientes
        JPanel registroPacientePanel = new JPanel(new GridLayout(3, 2));

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

        pacientePanel.add(registroPacientePanel, BorderLayout.NORTH);

        // Tabla de pacientes
        String[] columnNamesPacientes = {"ID", "Nombre"};
        DefaultTableModel modelPacientes = new DefaultTableModel(columnNamesPacientes, 0);
        pacientesTable = new JTable(modelPacientes);
        JScrollPane pacientesScrollPane = new JScrollPane(pacientesTable);
        pacientePanel.add(pacientesScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Pacientes", pacientePanel);

        // Pestaña de Turnos
        JPanel turnoPanel = new JPanel(new BorderLayout());

        // Panel de registro de turnos
        JPanel registroTurnoPanel = new JPanel(new GridLayout(4, 2));

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

        turnoPanel.add(registroTurnoPanel, BorderLayout.NORTH);

        // Tabla de turnos
        String[] columnNamesTurnos = {"ID", "ID Médico", "ID Paciente", "Fecha y Hora"};
        DefaultTableModel modelTurnos = new DefaultTableModel(columnNamesTurnos, 0);
        turnosTable = new JTable(modelTurnos);
        JScrollPane turnosScrollPane = new JScrollPane(turnosTable);
        turnoPanel.add(turnosScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Turnos", turnoPanel);

        frame.add(tabbedPane);

        databaseManager = new DatabaseManager();

        registrarMedicoButton.addActionListener(e -> {
            String nombre = nombreMedicoField.getText();
            double tarifaConsulta = Double.parseDouble(tarifaConsultaField.getText());

            Medico medico = new Medico(nombre, tarifaConsulta);
            databaseManager.createMedico(medico);

            actualizarTablaMedicos(databaseManager.getAllMedicos());

            nombreMedicoField.setText("");
            tarifaConsultaField.setText("");
        });

        registrarPacienteButton.addActionListener(e -> {
            String nombre = nombrePacienteField.getText();

            Paciente paciente = new Paciente(nombre);
            databaseManager.createPaciente(paciente);

            actualizarTablaPacientes(databaseManager.getAllPacientes());

            nombrePacienteField.setText("");
        });

        registrarTurnoButton.addActionListener(e -> {
            int idMedico = Integer.parseInt(idMedicoField.getText());
            int idPaciente = Integer.parseInt(idPacienteField.getText());
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            Medico medico = databaseManager.getMedicoById(idMedico);
            Paciente paciente = databaseManager.getPacienteById(idPaciente);

            if (medico != null && paciente != null) {
                Turno turno = new Turno(idMedico, medico, paciente, fechaHora);
                databaseManager.createTurno(turno);
            } else {
                // Handle situation where either the medico or paciente could not be found
            }

            actualizarTablaTurnos(databaseManager.getAllTurnos());

            idMedicoField.setText("");
            idPacienteField.setText("");
            fechaHoraField.setText("");
        });

        deleteMedicoButton.addActionListener(e -> {
            int idMedico = Integer.parseInt(idMedicoDeleteField.getText());
            databaseManager.deleteMedico(idMedico);
            actualizarTablaMedicos(databaseManager.getAllMedicos());
            idMedicoDeleteField.setText("");
        });

        deletePacienteButton.addActionListener(e -> {
            int idPaciente = Integer.parseInt(idPacienteDeleteField.getText());
            databaseManager.deletePaciente(idPaciente);
            actualizarTablaPacientes(databaseManager.getAllPacientes());
            idPacienteDeleteField.setText("");
        });

        // Update the tables at the start
        actualizarTablaMedicos(databaseManager.getAllMedicos());
        actualizarTablaPacientes(databaseManager.getAllPacientes());
        actualizarTablaTurnos(databaseManager.getAllTurnos());

        frame.setVisible(true);
    }

    private void actualizarTablaMedicos(List<Medico> medicos) {
        DefaultTableModel model = (DefaultTableModel) medicosTable.getModel();
        model.setRowCount(0);

        for (Medico medico : medicos) {
            Object[] row = {medico.getId(), medico.getNombre(), medico.getTarifaConsulta()};
            model.addRow(row);
        }
    }

    private void actualizarTablaPacientes(List<Paciente> pacientes) {
        DefaultTableModel model = (DefaultTableModel) pacientesTable.getModel();
        model.setRowCount(0);

        for (Paciente paciente : pacientes) {
            Object[] row = {paciente.getId(), paciente.getNombre()};
            model.addRow(row);
        }
    }

    private void actualizarTablaTurnos(List<Turno> turnos) {
        DefaultTableModel model = (DefaultTableModel) turnosTable.getModel();
        model.setRowCount(0);

        for (Turno turno : turnos) {
            Object[] row = {turno.getId(), turno.getMedico().getId(), turno.getPaciente().getId(), turno.getFechaHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))};
            model.addRow(row);
        }
    }

    public static void main(String[] args) {
        new SistemaMedicoGUI();
    }
}
