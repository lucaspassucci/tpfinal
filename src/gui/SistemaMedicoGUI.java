import javax.swing.*;
import java.awt.*;

public class SistemaMedicoGUI {
    private JFrame frame;
    private JTextField nombreMedicoField;
    private JTextField tarifaConsultaField;
    private JButton registrarMedicoButton;

    private JTextField nombrePacienteField;
    private JButton registrarPacienteButton;

    private JTextField idMedicoField;
    private JTextField idPacienteField;
    private JTextField fechaHoraField;
    private JButton registrarTurnoButton;

    private JTextField idMedicoReporteField;
    private JTextField fechaInicioField;
    private JTextField fechaFinField;
    private JButton generarReporteButton;

    public SistemaMedicoGUI() {
        frame = new JFrame("Sistema Médico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new GridLayout(12, 2));

        // Campos para registrar un nuevo médico
        panel.add(new JLabel("Nombre Médico:"));
        nombreMedicoField = new JTextField();
        panel.add(nombreMedicoField);

        panel.add(new JLabel("Tarifa Consulta:"));
        tarifaConsultaField = new JTextField();
        panel.add(tarifaConsultaField);

        registrarMedicoButton = new JButton("Registrar Médico");
        panel.add(registrarMedicoButton);

        // Campos para registrar un nuevo paciente
        panel.add(new JLabel("Nombre Paciente:"));
        nombrePacienteField = new JTextField();
        panel.add(nombrePacienteField);

        registrarPacienteButton = new JButton("Registrar Paciente");
        panel.add(registrarPacienteButton);

        // Campos para registrar un nuevo turno
        panel.add(new JLabel("ID Médico:"));
        idMedicoField = new JTextField();
        panel.add(idMedicoField);

        panel.add(new JLabel("ID Paciente:"));
        idPacienteField = new JTextField();
        panel.add(idPacienteField);

        panel.add(new JLabel("Fecha y Hora (formato YYYY-MM-DD HH:MM):"));
        fechaHoraField = new JTextField();
        panel.add(fechaHoraField);

        registrarTurnoButton = new JButton("Registrar Turno");
        panel.add(registrarTurnoButton);

        // Campos para generar reporte de ingresos de un médico
        panel.add(new JLabel("ID Médico para Reporte:"));
        idMedicoReporteField = new JTextField();
        panel.add(idMedicoReporteField);

        panel.add(new JLabel("Fecha Inicio (formato YYYY-MM-DD):"));
        fechaInicioField = new JTextField();
        panel.add(fechaInicioField);

        panel.add(new JLabel("Fecha Fin (formato YYYY-MM-DD):"));
        fechaFinField = new JTextField();
        panel.add(fechaFinField);

        generarReporteButton = new JButton("Generar Reporte");
        panel.add(generarReporteButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SistemaMedicoGUI();
            }
        });
    }
}
