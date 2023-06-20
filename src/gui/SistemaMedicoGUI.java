package gui;

import model.Medico;
import model.Paciente;
import model.Turno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaMedicoGUI {
    private JFrame frame;
    private JTabbedPane tabbedPane;

    // Componentes para la pestaña de Médicos
    private JTextField nombreMedicoField;
    private JTextField tarifaConsultaField;
    private JButton registrarMedicoButton;
    private JTable medicosTable;

    // Componentes para la pestaña de Pacientes
    private JTextField nombrePacienteField;
    private JButton registrarPacienteButton;
    private JTable pacientesTable;

    // Componentes para la pestaña de Turnos
    private JTextField idMedicoField;
    private JTextField idPacienteField;
    private JTextField fechaHoraField;
    private JButton registrarTurnoButton;
    private JTable turnosTable;

    public SistemaMedicoGUI() {
        frame = new JFrame("Sistema Médico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        tabbedPane = new JTabbedPane();

        // Pestaña de Médicos
        JPanel medicoPanel = new JPanel(new BorderLayout());

        // Panel de registro de médicos
        JPanel registroMedicoPanel = new JPanel(new GridLayout(3, 2));

        registroMedicoPanel.add(new JLabel("Nombre Médico:"));
        nombreMedicoField = new JTextField();
        registroMedicoPanel.add(nombreMedicoField);

        registroMedicoPanel.add(new JLabel("Tarifa Consulta:"));
        tarifaConsultaField = new JTextField();
        registroMedicoPanel.add(tarifaConsultaField);

        registrarMedicoButton = new JButton("Registrar Médico");
        registroMedicoPanel.add(registrarMedicoButton);

        medicoPanel.add(registroMedicoPanel, BorderLayout.NORTH);

        // Tabla de médicos
        medicosTable = new JTable();
        JScrollPane medicosScrollPane = new JScrollPane(medicosTable);
        medicoPanel.add(medicosScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Médicos", medicoPanel);

        // Pestaña de Pacientes
        JPanel pacientePanel = new JPanel(new BorderLayout());

        // Panel de registro de pacientes
        JPanel registroPacientePanel = new JPanel(new GridLayout(2, 2));

        registroPacientePanel.add(new JLabel("Nombre Paciente:"));
        nombrePacienteField = new JTextField();
        registroPacientePanel.add(nombrePacienteField);

        registrarPacienteButton = new JButton("Registrar Paciente");
        registroPacientePanel.add(registrarPacienteButton);

        pacientePanel.add(registroPacientePanel, BorderLayout.NORTH);

        // Tabla de pacientes
        pacientesTable = new JTable();
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
        turnosTable = new JTable();
        JScrollPane turnosScrollPane = new JScrollPane(turnosTable);
        turnoPanel.add(turnosScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Turnos", turnoPanel);

        frame.add(tabbedPane);

        frame.setVisible(true);
    }

    public void actualizarTablaMedicos(List<Medico> medicos) {
        DefaultTableModel model = (DefaultTableModel) medicosTable.getModel();
        model.setRowCount(0);

        for (Medico medico : medicos) {
            Object[] row = { medico.getId(), medico.getNombre(), medico.getTarifaConsulta() };
            model.addRow(row);
        }
    }

    public void actualizarTablaPacientes(List<Paciente> pacientes) {
        DefaultTableModel model = (DefaultTableModel) pacientesTable.getModel();
        model.setRowCount(0);

        for (Paciente paciente : pacientes) {
            Object[] row = { paciente.getId(), paciente.getNombre() };
            model.addRow(row);
        }
    }

    public void actualizarTablaTurnos(List<Turno> turnos) {
        DefaultTableModel model = (DefaultTableModel) turnosTable.getModel();
        model.setRowCount(0);

        for (Turno turno : turnos) {
            Object[] row = { turno.getId(), turno.getMedico().getNombre(), turno.getPaciente().getNombre(), turno.getFechaHora() };
            model.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SistemaMedicoGUI gui = new SistemaMedicoGUI();

                // Ejemplo: Actualizar tablas con datos ficticios
                List<Medico> medicos = new ArrayList<>();
                medicos.add(new Medico(1, "Dr. Juan Pérez", 100.0));
                medicos.add(new Medico(2, "Dra. María Gómez", 150.0));
                gui.actualizarTablaMedicos(medicos);

                List<Paciente> pacientes = new ArrayList<>();
                pacientes.add(new Paciente(1, "Ana Sánchez"));
                pacientes.add(new Paciente(2, "Pedro Rodríguez"));
                gui.actualizarTablaPacientes(pacientes);

                List<Turno> turnos = new ArrayList<>();
                turnos.add(new Turno(1, medicos.get(0), pacientes.get(0), "2023-06-19 10:00"));
                turnos.add(new Turno(2, medicos.get(1), pacientes.get(1), "2023-06-20 15:30"));
                gui.actualizarTablaTurnos(turnos);
            }
        });
    }
}
