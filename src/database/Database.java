public class Database {

    private static final String DB_NAME = "sistemamedico";
    private static String dbUrl;

    static {
        try {
            URL dbPathUrl = Database.class.getClassLoader().getResource(DB_NAME);
            if (dbPathUrl != null) {
                String dbPath = dbPathUrl.toURI().getPath();
                dbUrl = "jdbc:h2:" + dbPath;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static String getDbUrl() {
        return dbUrl;
    }
}
