package database;
import java.time.LocalDate;

import model.Medico;
import model.Paciente;
import model.Turno;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
    /*
    private static Connection connection;
    //private static final String DB_URL = "jdbc:h2:/Users/lucaspassucci/Desktop/sistemamedico.mv.db";
    //url para la mac
    //private static final String DB_URL = "jdbc:h2://Users/lucaspassucci/Desktop/TPfinal/src/sistemamedico";
    //url para windows
    //private static final String DB_URL = "jdbc:h2:/C:\\Users\\lucas\\Desktop\\tpfinal\\src\\sistemamedico.mv.db";
    private static final String DB_URL = "jdbc:h2:../database/sistemamedico";
    private static final String USER = "sa";
    private static final String PASS = "";
    private static final String DB_DRIVER = "org.h2.Driver";

    /*public Connection getConnection() throws SQLException {
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
        public static Connection getConnection() { //me conecto y desconecto de la clase
        Connection c = null;
        try{
            Class.forName(DB_DRIVER); //recibe el FQN de una clase
        }catch (ClassNotFoundExcepcion e){ //por si pongo mail el "org.h2.Driver"
            e.printStackTrace(); //aca y la otra línea mato el programa. Podría tirar un mensaje de error.
            System.exit(0);
        }
        //Me Conecto
        try{
            c = DriverManager.getConnection(DB_URL, DB_USERNAME_ DB_PASSWORD); //Uso esta clase para conectarme. Driver manager usa la clase que llamé en DB_DRIVER
            c.setAutoCommit(false);
        }catch (SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
    }
 */
    public class DatabaseManager {
        private static final String DB_DRIVER = "org.h2.Driver";
        private static final String DB_URL = Database.getDbUrl();
        private static final String USER = "sa";
        private static final String PASS = "";

        public static Connection getConnection() {
            Connection c = null;
            try {
                Class.forName(DB_DRIVER);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(0);
            }
            try {
                c = DriverManager.getConnection(DB_URL, USER, PASS);
                c.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(0);
            }
            return c;
        }

        public void close(Connection c, PreparedStatement ps, ResultSet rs) {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) { /* ignored */}
            }
        }


        //create methods
        public void createMedico(Medico medico) throws SQLException {
            String sqlCheck = "SELECT COUNT(*) FROM medico WHERE nombre = ? AND TARIFA_CONSULTA = ? AND obra_social = ?";
            String sql = "INSERT INTO medico(nombre, tarifa_consulta, obra_social) VALUES(?, ?, ?)";
            Connection c = getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                ps = c.prepareStatement(sqlCheck);
                ps.setString(1, medico.getNombre());
                ps.setDouble(2, medico.getTarifaConsulta());
                ps.setString(3, medico.getObraSocial());
                rs = ps.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Duplicate Medico entry.");
                }

                ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, medico.getNombre());
                ps.setDouble(2, medico.getTarifaConsulta());
                ps.setString(3, medico.getObraSocial());

                int affectedRows = ps.executeUpdate();
                c.commit();
                if (affectedRows == 0) {
                    JOptionPane.showMessageDialog(null, "No se pudo crear el médico");
                    throw new SQLException("Creating medico failed, no rows affected.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Se creo el médico");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        medico.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Creating medico failed, no ID obtained.");
                    }
                }
            } finally {
                close(c, ps, rs);
            }
            //JOptionPane.showMessageDialog(null, "Se creo el médico");
        }

        public void createPaciente(Paciente paciente) throws SQLException {
            String sqlCheck = "SELECT COUNT(*) FROM paciente WHERE nombre = ? AND obra_social = ?";
            String sql = "INSERT INTO paciente(nombre, obra_social) VALUES(?, ?)";
            Connection c = getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                ps = c.prepareStatement(sqlCheck);
                ps.setString(1, paciente.getNombre());
                ps.setString(2, paciente.getObraSocial());
                rs = ps.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Duplicate Paciente entry.");
                }

                ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, paciente.getNombre());
                ps.setString(2, paciente.getObraSocial());

                int affectedRows = ps.executeUpdate();
                c.commit();

                if (affectedRows == 0) {
                    JOptionPane.showMessageDialog(null, "No se pudo crear el paciente");
                    throw new SQLException("Creating paciente failed, no rows affected.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Se creo el paciente");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        paciente.setId((int) generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Creating paciente failed, no ID obtained.");
                    }
                }
            } finally {
                close(c, ps, rs);
            }
        }

        public void createTurno(Turno turno) throws SQLException {
            String sqlCheck = "SELECT COUNT(*) FROM turno WHERE id_medico = ? AND id_paciente = ? AND fecha_hora = ?";
            String sql = "INSERT INTO turno(id_medico, id_paciente, fecha_hora, tarifa) VALUES(?, ?, ?, ?)";
            Connection c = getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                ps = c.prepareStatement(sqlCheck);
                ps.setLong(1, turno.getMedico().getId());
                ps.setLong(2, turno.getPaciente().getId());
                ps.setObject(3, turno.getFechaHora());
                rs = ps.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Duplicate Turno entry.");
                }

                ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, turno.getMedico().getId());
                ps.setLong(2, turno.getPaciente().getId());
                ps.setObject(3, turno.getFechaHora());
                ps.setDouble(4,turno.getTarifa());
                int affectedRows = ps.executeUpdate();
                c.commit();

                if (affectedRows == 0) {
                    JOptionPane.showMessageDialog(null, "No se pudo crear el turno");
                    throw new SQLException("Creating turno failed, no rows affected.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Se creó el turno");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        turno.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Creating turno failed, no ID obtained.");
                    }
                }
            } finally {
                close(c, ps, rs);
            }
        }

        //update methods
        public void updatePaciente(Paciente paciente) {
            String sql = "UPDATE paciente SET nombre = ?, obra_social = ? dni = ? WHERE id = ?";
            Connection c = getConnection();
            try {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, paciente.getNombre());
                ps.setString(2, paciente.getObraSocial());
                ps.setLong(4, paciente.getId());
                int affectedRows = ps.executeUpdate();
                c.commit();
                if (affectedRows == 0) {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el paciente");
                    throw new SQLException("updating paciente failed, no rows affected.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Se actualizo el paciente");
                }
            } catch (SQLException e) {
                try {
                    e.printStackTrace();
                    c.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    c.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void updateMedico(Medico medico) {
            String sql = "UPDATE medico SET nombre = ?, obra_social = ? , tarifa_consulta = ? WHERE id = ?";
            Connection c = getConnection();
            try {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, medico.getNombre());
                ps.setString(2, medico.getObraSocial());
                ps.setDouble(3, medico.getTarifaConsulta());
                ps.setLong(4, medico.getId());
                int affectedRows = ps.executeUpdate();
                c.commit();
                if (affectedRows == 0) {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el medico");
                    throw new SQLException("updating medico failed, no rows affected.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Se actualizo el medico");
                }
            } catch (SQLException e) {
                try {
                    e.printStackTrace();
                    c.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    c.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }

        /*
        public void updateTurno(Turno turno) {
            String sql = "UPDATE turno SET medico_id = ?, paciente_id = ?, fecha_hora = ? WHERE id = ?";
            Connection c = getConnection();
            try {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setLong(1, turno.getMedicoId());
                ps.setLong(2, turno.getPacienteId());
                ps.setDate(3, java.sql.Date.valueOf(turno.getFechaHora()));
                ps.setLong(5, turno.getId());
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e){
                try{
                    e.printStackTrace();
                    c.rollback();
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            } finally{
                try{
                    c.close();
                }catch(SQLException e1){
                    e1.printStackTrace();
                }
            }
        }
         */
        //delete methods
        public void deletePaciente(long id) {
            String sql = "DELETE FROM paciente WHERE id = ?";
            Connection c = getConnection();
            try {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setLong(1, id);
                int affectedRows = ps.executeUpdate();
                c.commit();
                if (affectedRows == 0) {
                    JOptionPane.showMessageDialog(null, "No se pudo borrar el paciente");
                    throw new SQLException("delete paciente failed, no rows affected.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Se bobrro el paciente");
                }
            } catch (SQLException e) {
                try {
                    e.printStackTrace();
                    c.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    c.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void deleteMedico(long id) {
            String sql = "DELETE FROM medico WHERE id = ?";
            Connection c = getConnection();
            try {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setLong(1, id);
                int affectedRows = ps.executeUpdate();
                c.commit();
                if (affectedRows == 0) {
                    JOptionPane.showMessageDialog(null, "No se pudo borrar el medico");
                    throw new SQLException("deleting medico  failed, no rows affected.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Se borro el medico");
                }
            } catch (SQLException e) {
                try {
                    e.printStackTrace();
                    c.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    c.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void deleteTurno(long id) {
            String sql = "DELETE FROM turno WHERE id = ?";
            Connection c = getConnection();
            try {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setLong(1, id);
                int affectedRows = ps.executeUpdate();
                c.commit();
                if (affectedRows == 0) {
                    JOptionPane.showMessageDialog(null, "No se pudo borrar el turno");
                    throw new SQLException("deleting turno failed, no rows affected.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Se borro el turno");
                }
            } catch (SQLException e) {
                try {
                    e.printStackTrace();
                    c.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    c.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }

        //getall methods
        public List<Medico> getAllMedicos() {
            List<Medico> medicos = new ArrayList<>();
            Connection connection = getConnection();
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM medico")) {

                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String nombre = resultSet.getString("nombre");
                    double tarifaConsulta = resultSet.getDouble("tarifa_consulta");
                    String obraSocial = resultSet.getString("obra_social");
                    Medico medico = new Medico(id, nombre, tarifaConsulta,obraSocial);
                    medicos.add(medico);
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener los médicos: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return medicos;
        }

        public List<Paciente> getAllPacientes() {
            List<Paciente> pacientes = new ArrayList<>();
            Connection connection = getConnection();
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente")) {

                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String nombre = resultSet.getString("nombre");
                    String obraSocial = resultSet.getString("obra_social");

                    Paciente paciente = new Paciente();
                    paciente.setId(id);
                    paciente.setNombre(nombre);
                    paciente.setObraSocial(obraSocial);
                    pacientes.add(paciente);
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener los pacientes: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return pacientes;
        }

        public List<Turno> getAllTurnos() {
            return this.getAllTurnos(null, null, null) ;
        };
        //Se utiliza para traer turnos y reportes
        public List<Turno> getAllTurnos(Medico medicoParam, LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
            List<Turno> turnos = new ArrayList<>();
            Connection connection = getConnection();
            String sql = "SELECT * FROM turno";
            PreparedStatement ps = null;

            if(medicoParam != null){
                sql = "SELECT * FROM turno WHERE id_medico = ? AND fecha_hora BETWEEN ? AND ?";
            }
            try {
                //Statement statement = connection.createStatement()
                //ResultSet resultSet = statement.executeQuery(sql);
                ps = connection.prepareStatement(sql);
                if(medicoParam != null) {
                    ps.setLong(1, medicoParam.getId());
                    ps.setObject(2, fechaDesde);
                    ps.setObject(3, fechaHasta);
                }
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    long medicoId = resultSet.getLong("id_medico");
                    long pacienteId = resultSet.getLong("id_paciente");
                    LocalDate fecha_hora = resultSet.getDate("fecha_hora").toLocalDate();
                    double tarifa = resultSet.getDouble("tarifa");
                    Medico medico = getMedicoById(medicoId);
                    Paciente paciente = getPacienteById(pacienteId);
                    LocalDateTime fechaHora = LocalDateTime.of(fecha_hora, LocalTime.MIDNIGHT); // example of setting time to midnight

                    Turno turno = new Turno(medicoParam==null? medico : medicoParam, paciente, fechaHora,tarifa);
                    turnos.add(turno);
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener los turnos: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return turnos;
        }

        public Medico getMedicoById(long id) {
            Medico medico = null;
            Connection connection = getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                String sql = "SELECT * FROM medico WHERE id = ?";
                statement = connection.prepareStatement(sql);
                statement.setLong(1, id);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    double tarifaConsulta = resultSet.getDouble("tarifa_consulta");
                    String obraSocial = resultSet.getString("obra_social");
                    medico = new Medico(id, nombre, tarifaConsulta, obraSocial);
                    medico.setObraSocial(obraSocial);
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener el medico: " + e.getMessage());
            } finally {
                close(connection, statement, resultSet);
            }
            return medico;
        }

        public Paciente getPacienteById(long id) {
            Paciente paciente = null;
            Connection connection = getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                String sql = "SELECT * FROM paciente WHERE id = ?";
                statement = connection.prepareStatement(sql);
                statement.setLong(1, id);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    String obraSocial = resultSet.getString("obra_social");
                    paciente = new Paciente(nombre, obraSocial);
                    paciente.setId(id);
                    paciente.setObraSocial(obraSocial);
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener el paciente: " + e.getMessage());
            } finally {
                close(connection, statement, resultSet);
            }
            return paciente;
        }

    }
