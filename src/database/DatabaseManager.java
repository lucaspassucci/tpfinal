package database;

import model.Medico;
import model.Paciente;
import model.Turno;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection connection;
    //private static final String DB_URL = "jdbc:h2:/Users/lucaspassucci/Desktop/sistemamedico.mv.db";
    //url para la mac
    //private static final String DB_URL = "jdbc:h2://Users/lucaspassucci/Desktop/TPfinal/src/sistemamedico";
    //url para windows
    private static final String DB_URL = "jdbc:h2:/C:\\Users\\lucas\\Desktop\\tpfinal\\src\\sistemamedico.mv.db";
    private static final String USER = "sa";
    private static final String PASS = "";

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        return connection;
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    // CRUD Operations for Medico

    public void createMedico(Medico medico) {
        String sql = "INSERT INTO medico(nombre, tarifa_consulta) VALUES(?, ?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, medico.getNombre());
            pstmt.setDouble(2, medico.getTarifaConsulta());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Medico readMedico(long id) {
        String sql = "SELECT * FROM medico WHERE id = ?";
        Medico medico = null;

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {

            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                medico = new Medico();
                medico.setId(rs.getLong("ID"));
                medico.setNombre(rs.getString("NOMBRE"));
                medico.setTarifaConsulta(rs.getDouble("TARIFA_CONSULTA"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return medico;
    }

    public void updateMedico(Medico medico) {
        String sql = "UPDATE medico SET nombre = ?, tarifa_consulta = ? WHERE ID = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {

            pstmt.setString(1, medico.getNombre());
            pstmt.setDouble(2, medico.getTarifaConsulta());
            pstmt.setLong(3, medico.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteMedico(long id) {
        String sql = "DELETE FROM medico WHERE id = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {

            pstmt.setLong(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // CRUD Operations for Paciente

    public void createPaciente(Paciente paciente) {
        String sql = "INSERT INTO paciente(nombre) VALUES(?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, paciente.getNombre());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Paciente readPaciente(long id) {
        String sql = "SELECT * FROM paciente WHERE id = ?";
        Paciente paciente = null;

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {

            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                paciente = new Paciente();
                paciente.setId((int) rs.getLong("ID"));
                paciente.setNombre(rs.getString("NOMBRE"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return paciente;
    }

    public void updatePaciente(Paciente paciente) {
        String sql = "UPDATE paciente SET nombre = ? WHERE id = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {

            pstmt.setString(1, paciente.getNombre());
            pstmt.setLong(2, paciente.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePaciente(long id) {
        String sql = "DELETE FROM paciente WHERE id = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {

            pstmt.setLong(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // CRUD Operations for Turno

    public void createTurno(Turno turno) {
        String sql = "INSERT INTO turno(id_medico, id_paciente, fecha_hora) VALUES(?, ?, ?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, turno.getMedico().getId());
            pstmt.setLong(2, turno.getPaciente().getId());
            pstmt.setTimestamp(3, Timestamp.valueOf(turno.getFechaHora()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Turno readTurno(long id) {
        String sql = "SELECT * FROM turno WHERE id = ?";
        Turno turno = null;

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {

            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                turno = new Turno();
                turno.setId(rs.getLong("ID"));
                turno.setMedico(readMedico(rs.getLong("ID_MEDICO")));
                turno.setPaciente(readPaciente(rs.getLong("ID_PACIENTE")));
                turno.setFechaHora(rs.getTimestamp("FECHA_HORA").toLocalDateTime());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return turno;
    }

    public void updateTurno(Turno turno) {
        String sql = "UPDATE turno SET id_medico = ?, id_paciente = ?, fecha_hora = ? WHERE ID = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {

            pstmt.setLong(1, turno.getMedico().getId());
            pstmt.setLong(2, turno.getPaciente().getId());
            pstmt.setTimestamp(3, Timestamp.valueOf(turno.getFechaHora()));
            pstmt.setLong(4, turno.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTurno(long id) {
        String sql = "DELETE FROM turno WHERE id = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {

            pstmt.setLong(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Medico getMedicoById(long id) {
        Medico medico = null;

        String sql = "SELECT * FROM medico WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                double tarifaConsulta = rs.getDouble("tarifa_consulta");

                medico = new Medico(id, nombre, tarifaConsulta);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el médico por ID: " + e.getMessage());
        }

        return medico;
    }

    public Paciente getPacienteById(long id) {
        Paciente paciente = null;

        String sql = "SELECT * FROM paciente WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");

                paciente = new Paciente(nombre);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el paciente por ID: " + e.getMessage());
        }

        return paciente;
    }

    public Turno getTurnoById(long id) {
        Turno turno = null;

        String sql = "SELECT * FROM turno WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                long idMedico = rs.getLong("id_medico");
                long idPaciente = rs.getLong("id_paciente");
                LocalDateTime fechaHora = rs.getTimestamp("fecha_hora").toLocalDateTime();

                Medico medico = getMedicoById(idMedico);
                Paciente paciente = getPacienteById(idPaciente);

                turno = new Turno(medico, paciente, fechaHora);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el turno por ID: " + e.getMessage());
        }

        return turno;
    }

    public List<Medico> getAllMedicos() {
        List<Medico> medicos = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM medico")) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String nombre = resultSet.getString("nombre");
                double tarifaConsulta = resultSet.getDouble("tarifa_consulta");

                Medico medico = new Medico(id, nombre, tarifaConsulta);
                medicos.add(medico);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los médicos: " + e.getMessage());
        }

        return medicos;
    }


    public List<Paciente> getAllPacientes() {
        List<Paciente> pacientes = new ArrayList<>();

        String sql = "SELECT * FROM paciente";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String nombre = resultSet.getString("nombre");

                Paciente paciente = new Paciente(nombre);
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los pacientes: " + e.getMessage());
        }

        return pacientes;
    }

    public List<Turno> getAllTurnos() {
        List<Turno> turnos = new ArrayList<>();

        String sql = "SELECT * FROM turno";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long idMedico = resultSet.getLong("id_medico");
                long idPaciente = resultSet.getLong("id_paciente");
                LocalDateTime fechaHora = resultSet.getTimestamp("fecha_hora").toLocalDateTime();

                Medico medico = getMedicoById(idMedico);
                Paciente paciente = getPacienteById(idPaciente);

                Turno turno = new Turno(medico, paciente, fechaHora);
                turnos.add(turno);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los turnos: " + e.getMessage());
        }

        return turnos;
    }
}