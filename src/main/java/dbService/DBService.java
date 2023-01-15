package dbService;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBService {
    private static final String url = "jdbc:h2:./h2db";
    private static final String name = "h2";
    private static final String pass = "h2";

    private final Connection connection;

    public DBService() throws SQLException {
        this.connection = createH2Connection();
        Statement stmt = connection.createStatement();
        stmt.execute("create table if not exists users " +
                "(id bigint auto_increment, login varchar(256), " +
                "password varchar(256), primary key (id));");
        stmt.execute("CREATE TABLE IF NOT EXISTS sessions " +
                "(id BIGINT AUTO_INCREMENT, session VARCHAR(256))");
        stmt.close();

    }

    public Connection getCurrentConnection() {
        return this.connection;
    }

    public static Connection createH2Connection() {
        try {
            return DriverManager.getConnection(url, name, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
