package database;

import java.sql.*;

public class DatabaseManager {
    private Connection connection;
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String USER = "sa";
    private static final String PASS = "";

    public Connection connect() throws SQLException {
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
        String sql = "INSERT INTO MEDICO(NOMBRE, TARIFA_CONSULTA) VALUES(?, ?)";

        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, medico.getNombre());
            pstmt.setDouble(2, medico.getTarifaConsulta());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Medico readMedico(long id) {
        String sql = "SELECT * FROM MEDICO WHERE ID = ?";
        Medico medico = null;

        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {

            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
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
        String sql = "UPDATE MEDICO SET NOMBRE = ?, TARIFA_CONSULTA = ? WHERE ID = ?";

        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {

            pstmt.setString(1, medico.getNombre());
            pstmt.setDouble(2, medico.getTarifaConsulta());
            pstmt.setLong(3, medico.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteMedico(long id) {
        String sql = "DELETE FROM MEDICO WHERE ID = ?";

        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {

            pstmt.setLong(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // CRUD Operations for Paciente

    public void createPaciente(Paciente paciente) {
        String sql = "INSERT INTO PACIENTE(NOMBRE) VALUES(?)";

        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, paciente.getNombre());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Paciente readPaciente(long id) {
        String sql = "SELECT * FROM PACIENTE WHERE ID = ?";
        Paciente paciente = null;

        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {

            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                paciente = new Paciente();
                paciente.setId(rs.getLong("ID"));
                paciente.setNombre(rs.getString("NOMBRE"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return paciente;
    }

    public void updatePaciente(Paciente paciente) {
        String sql = "UPDATE PACIENTE SET NOMBRE = ? WHERE ID = ?";

        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {

            pstmt.setString(1, paciente.getNombre());
            pstmt.setLong(2, paciente.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePaciente(long id) {
        String sql = "DELETE FROM PACIENTE WHERE ID = ?";

        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {

            pstmt.setLong(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // CRUD Operations for Turno

    public void createTurno(Turno turno) {
        String sql = "INSERT INTO TURNO(MEDICO_ID, PACIENTE_ID, FECHA_HORA) VALUES(?, ?, ?)";

        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setLong(1, turno.getMedico().getId());
            pstmt.setLong(2, turno.getPaciente().getId());
            pstmt.setTimestamp(3, Timestamp.valueOf(turno.getFechaHora()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Turno readTurno(long id) {
        String sql = "SELECT * FROM TURNO WHERE ID = ?";
        Turno turno = null;

        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {

            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                turno = new Turno();
                turno.setId(rs.getLong("ID"));
                turno.setMedico(readMedico(rs.getLong("MEDICO_ID")));
                turno.setPaciente(readPaciente(rs.getLong("PACIENTE_ID")));
                turno.setFechaHora(rs.getTimestamp("FECHA_HORA").toLocalDateTime());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return turno;
    }

    public void updateTurno(Turno turno) {
        String sql = "UPDATE TURNO SET MEDICO_ID = ?, PACIENTE_ID = ?, FECHA_HORA = ? WHERE ID = ?";

        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {

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
        String sql = "DELETE FROM TURNO WHERE ID = ?";

        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {

            pstmt.setLong(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
