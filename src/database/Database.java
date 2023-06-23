package database;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String DB_NAME = "sistemamedico";
    private static final String DB_EXTENSION = ".mv.db";
    private static String dbUrl;

    static {
        try {
            URL dbPathUrl = Database.class.getClassLoader().getResource(DB_NAME + DB_EXTENSION);
            if (dbPathUrl != null) {
                String dbPath = Paths.get(dbPathUrl.toURI()).toAbsolutePath().toString();
                dbUrl = "jdbc:h2:" + dbPath;
            } else {
                createDatabase();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static String getDbUrl() {
        return dbUrl;
    }

    private static void createDatabase() {
        try {
            String dbPath = System.getProperty("user.dir") + File.separator + DB_NAME + DB_EXTENSION;
            dbUrl = "jdbc:h2:" + dbPath;
            Connection connection = DriverManager.getConnection(dbUrl);
            Statement statement = connection.createStatement();

            // Create 'medico' table
            String sqlMedico = "CREATE TABLE IF NOT EXISTS medico (id BIGINT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(100), tarifa_consulta DECIMAL(10, 2), obra_social VARCHAR(100))";
            statement.executeUpdate(sqlMedico);

            // Create 'paciente' table
            String sqlPaciente = "CREATE TABLE IF NOT EXISTS paciente (id BIGINT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(100), obra_social VARCHAR(100))";
            statement.executeUpdate(sqlPaciente);

            // Create 'turno' table
            String sqlTurnos = "CREATE TABLE IF NOT EXISTS turno (id BIGINT AUTO_INCREMENT PRIMARY KEY, id_medico BIGINT, id_paciente BIGINT, " +
                    "fecha_hora TIMESTAMP, FOREIGN KEY (id_medico) REFERENCES medico(id), FOREIGN KEY (id_paciente) REFERENCES paciente(id))";
            statement.executeUpdate(sqlTurnos);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
