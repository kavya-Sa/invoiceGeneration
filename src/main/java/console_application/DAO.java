package console_application;

import java.sql.*;
public class DAO  {

        private static Connection connection = null;

        // Method to establish a database connection
        public static Connection getConnection() throws ClassNotFoundException {
            Class.forName("org.postgresql.Driver");
                try {
                    // Establish the connection
                    if(connection==null || connection.isClosed())
                        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/untitled",
                            "root", "");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            return connection;
        }


   }

