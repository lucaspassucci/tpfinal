package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {
    private static final String DB_URL = "jdbc:h2:~/sistemamedico";
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
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            // Tabla de médicos
            statement.execute("CREATE TABLE medico (id BIGINT PRIMARY KEY, nombre VARCHAR(100), tarifa_consulta DECIMAL(10, 2))");

            // Tabla de pacientes
            statement.execute("CREATE TABLE paciente (id BIGINT PRIMARY KEY, nombre VARCHAR(100))");

            // Tabla de turnos
            statement.execute("CREATE TABLE turno (id BIGINT PRIMARY KEY, id_medico BIGINT, id_paciente BIGINT, " +
                    "fecha_hora TIMESTAMP, FOREIGN KEY (id_medico) REFERENCES medico(id), FOREIGN KEY (id_paciente) REFERENCES paciente(id))");
        }
    }
}
