package datasource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionDB {
    private static final Log log = LogFactory.getLog(ConnectionDB.class);


    //lisatu contructor private
    private ConnectionDB() {
        throw new UnsupportedOperationException("Utility class");
    }
    public static Connection obtenerConexion() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            String host = System.getenv("DB_HOST");
            if (host == null || host.isEmpty()) {
                host = "localhost"; // fallback
            }

            String url = "jdbc:mariadb://" + host + ":3306/Studyplanner";
            String user = "root";
            String password = "admin";

            Logger log = Logger.getLogger("ConnectionDB");

            if (log.isLoggable(Level.INFO)) {
                log.info("Connecting to " + url);
            }

            Connection conn = DriverManager.getConnection(url, user, password);

            if (log.isLoggable(Level.INFO)) {
                log.info("Connection established: " + (conn != null));
            }

            return conn;
        } catch (ClassNotFoundException e) {
            log.error("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            log.error("Connection error: " + e.getMessage());
        }
        return null;
    }
}
