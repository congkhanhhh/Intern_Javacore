package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/abc";
    private static final String user = "root";
    private static final String password = "";

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
