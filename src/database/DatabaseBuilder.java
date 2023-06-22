package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {
    private static final String DB_URL = "jdbc:h2://Users/lucaspassucci/Desktop/TPfinal/src/sistemamedico;DB_CLOSE_ON_EXIT=FALSE";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        try {
            createTables();
        } catch (SQLException e) {
            System.out.println("Error al crear las tablas: " + e.getMessage());
        }
    }

    private static void createTables() throws SQLException {
        Conection c = DatabaseManager.connection();
        String sqlMedico = "CREATE TABLE IF NOT EXISTS medico (id BIGINT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(100), tarifa_consulta DECIMAL(10, 2))";
        String sqlPaciente = "CREATE TABLE IF NOT EXISTS paciente (id BIGINT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(100))";
        String sqlTurnos = "CREATE TABLE IF NOT EXISTS turno (id BIGINT AUTO_INCREMENT PRIMARY KEY, id_medico BIGINT, id_paciente BIGINT, " +
                "fecha_hora TIMESTAMP, FOREIGN KEY (id_medico) REFERENCES medico(id), FOREIGN KEY (id_paciente) REFERENCES paciente(id))";

        try{
            Statement s = c.createStatement();
            s.execute(sqlMedico);
            s.execute(sqlPaciente);
            s.execute(sqlTurnos);
        }catch (SQLException e){
            try{
                c.rollback();
                e.printStackTrace();
                System.exit(0);
            } catch(SQLException e1){
                e1.printStackTrace();
            }
        }finally{
            try{
                c.close();
            }catch (SQLException e){
                e.printStackTrace;
            }
        }
    }
}
