package accounts;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountService {
    Connection connection;

    public AccountService(Connection connection) {
        this.connection = connection;
    } // end constructor

    public void addNewUser(UserProfile userProfile) throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = connection.createStatement();
            stmt.execute("SELECT COUNT(*) FROM users WHERE login ='" + userProfile.getLogin() + "'");
            resultSet = stmt.getResultSet();
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                System.out.println("Запись в базе отсутствует.");
                stmt.execute(
                        "INSERT INTO users VALUES("
                        + (getMaxId() + 1) + ", '"
                        + userProfile.getLogin() + "', '"
                        + userProfile.getPass() + "')"
                );
            } else {
                System.out.println("Запись с данным login уже имеется в базе.");
                System.out.println(resultSet.getRow());
            }

        } catch (SQLException ex) {

        } finally {
            resultSet.close();
            stmt.close();
        }
    } // end void addNewUser()


    public UserProfile getUserByLogin(String login) throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = connection.createStatement();
            stmt.execute("SELECT * FROM users WHERE login = '" + login + "'");
            resultSet = stmt.getResultSet();
            resultSet.last();
            int resultRowCount = resultSet.getRow();
            if (resultRowCount > 0) {
                return new UserProfile(
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("login") + "@example.org" );
            }
            else return null;
        } catch (SQLException ex) {

        } finally {
            resultSet.close();
            stmt.close();
        }
        return null;
    }

    public Integer getMaxId() throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = connection.createStatement();
            stmt.execute("SELECT id FROM users ORDER BY id");
            resultSet = stmt.getResultSet();
            resultSet.last();
            int resultRowCount = resultSet.getRow();
            if (resultRowCount > 0) return resultSet.getInt("id");
            else return 0;
        } catch (SQLException ex) {

        } finally {
            resultSet.close();
            stmt.close();
        }

        return 0;
    } // end getMaxId()

} // end class AccountService()

    /*



    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }

}
     */

