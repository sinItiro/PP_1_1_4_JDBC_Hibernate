package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE users (" +
                    "  id INT PRIMARY KEY AUTO_INCREMENT," +
                    "  name VARCHAR(45) NULL," +
                    "  lastName VARCHAR(45) NULL," +
                    "  age TINYINT NULL)");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                Util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE users");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                Util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Connection connection = Util.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3,age);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                Util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void removeUserById(long id) {
        try {
            Connection connection = Util.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                Util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {

        ArrayList<User> usersList = new ArrayList<>();

        try {
            Connection connection = Util.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");

                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                long id = resultSet.getByte("id");

                User user = new User(name, lastName, age);
                user.setId(id);
                usersList.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                Util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usersList;
    }

    public void cleanUsersTable() {

        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                Util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
