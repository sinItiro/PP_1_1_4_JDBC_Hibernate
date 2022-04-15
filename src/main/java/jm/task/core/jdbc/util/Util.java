package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Util {
    private final String user = "1234";
    private final String password = "1234";
    private final String url = "jdbc:mysql://localhost:3306/test";
    private Connection connection;
    private ResultSet resultSet;
    private Statement statement;

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }
    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        connection.close();
    }

    public int queryUpd(String sql) {

        try {


            this.connect();
            statement = connection.createStatement();
            int res = statement.executeUpdate(sql);
            return res;
        } catch (SQLException ex) {
            return 0;
        } finally {
            try {
                statement.close();
                this.close();
            } catch (SQLException e) {
            }
        }
    }

    public List<User> querySelect(String sql) {

        ArrayList<User> usersList = new ArrayList<>();

        try {
            this.connect();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");

                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                long id = resultSet.getByte("id");

                User user = new User(name, lastName, age);
                user.setId(id);
                usersList.add(user);
            }
            return usersList;

        } catch (SQLException ex) {
            return usersList;
        } finally {
            try {
                statement.close();
                this.close();
            } catch (SQLException e) {
            }
        }
    }

}
