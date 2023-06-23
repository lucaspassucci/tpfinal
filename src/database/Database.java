package database;

import java.io.File;

public class Database {
    private static final String DB_NAME = "sistemamedico";
    private static final String DB_EXTENSION = ".mv.db";

    public static String getDbUrl() {
        String dbPath = System.getProperty("user.dir") + File.separator + DB_NAME + DB_EXTENSION;
        return "jdbc:h2:" + dbPath;
    }
}