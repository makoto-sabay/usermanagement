package usermanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Database Access
 *
 * @author Makoto
 *
 */
public class UserDataAccess {
	private static String POSTGRE_DRIVER = "org.postgresql.Driver";
	private static String DBURL = "jdbc:postgresql://127.0.0.1:5432/postgres";
	private static String DBUSER = "postgres";
	private static String DBPASSWORD = "password";
	private static String SELECT_USER_SQL = "SELECT * FROM m_user";
	private static String UPDATE_USER_SQL = "UPDATE m_user SET password = ?, name = ?, phoneNumber = ?, memo = ? WHERE id = ?";

	public UserDataAccess() {

	}

	/**
	 * Get Login User Information
	 *
	 * @param id
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public UserInfo getUserInfo(String id, String password) throws Exception {
		UserInfo user = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
           // DB Connection
        	Class.forName(POSTGRE_DRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
            statement = connection.createStatement();

            // SQL Statement
            resultSet = statement.executeQuery(SELECT_USER_SQL);

            // Get All Fields
            List<String> fields = new ArrayList<String>();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                fields.add(rsmd.getColumnName(i));
            }

            // Get UserInfo
            while (resultSet.next()) {
            	user = getLoginUserInfo(id, password, resultSet);
            	if(user != null) {
            		// User exists.
            		break;
            	}
            }
        }
        finally {
           // Close Connection
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return user;
	}


	/**
	 * Get Login User Information
	 *
	 * @param id
	 * @param password
	 * @param resultSet
	 * @return login user information
	 * @throws SQLException
	 */
	private UserInfo getLoginUserInfo(String id, String password, ResultSet resultSet) throws SQLException{
		UserInfo user = null;
		String db_id = resultSet.getString("id");
		String db_password = resultSet.getString("password");

		if(id == null) {
			System.out.println("id: null");
		}
		else if(password == null) {
			System.out.println("password: null");
		}
		else if(id.equals(db_id) && password.equals(db_password)) {
			user = new UserInfo();
			user.setId(resultSet.getString("id"));
			user.setPassword(resultSet.getString("password"));
			user.setName(resultSet.getString("name"));
			user.setPhoneNumber(resultSet.getString("phoneNumber"));
			user.setMemo(resultSet.getString("memo"));
		}
		else {
			System.out.println("The ID does not exist.");
		}
		return (user);
	}


	/**
	 * Update New User Information
	 *
	 * @param new user information
	 * @return
	 * @throws Exception
	 */
	public boolean updateUserInfo(UserInfo newInfo) throws Exception {
		boolean updateflag = false;

        Connection connection = null;
        PreparedStatement ps = null;

        try {
           // DB Connection
        	Class.forName(POSTGRE_DRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);

            // Turn Off Auto-Comitt
            connection.setAutoCommit(false);

            // Update UserInfo
            ps = connection.prepareStatement(UPDATE_USER_SQL);
            ps.setString(1, newInfo.getPassword());
            ps.setString(2, newInfo.getName());
            ps.setString(3, newInfo.getPhoneNumber());
            ps.setString(4, newInfo.getMemo());
            ps.setString(5, newInfo.getId());
            int i = ps.executeUpdate();

            // Check Data
            updateflag = checkUpdatedData(i);

            // Commit
            connection.commit();
        }
        catch (Exception e) {
        	updateflag = false;
        	connection.rollback();
        	throw e;
        }
        finally {
           // Close Connection
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
		return updateflag;
	}

	/**
	 * Check Updated Data
	 *
	 * @param the number of updated lines
	 * @return
	 */
	private boolean checkUpdatedData(int i) {
		if (i == 1) {
			return true;
		}
		else {
			System.out.println("Updated Data: "+i);
			return false;
		}
	}
}
