package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {

  public static void main(String[] args) {
    DatabaseConnectionManager manager = new DatabaseConnectionManager("localhost",
        "hplussport", "postgres", "password");

    try {
      Connection connection = manager.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      customerDAO.delete(10000);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
