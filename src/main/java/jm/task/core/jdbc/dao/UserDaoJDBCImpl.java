package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util db = new Util();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE users (" +
                "  id INT PRIMARY KEY AUTO_INCREMENT," +
                "  name VARCHAR(45) NULL," +
                "  lastName VARCHAR(45) NULL," +
                "  age TINYINT NULL)";
        db.queryUpd(query);
    }

    public void dropUsersTable() {
        String query = "DROP TABLE users";
        db.queryUpd(query);
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users (name, lastName, age) VALUES ('"+ name +"', '" + lastName + "', '" + age + "')";
        if (db.queryUpd(query) > 0) {
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE (id = '"+ id +"')";
        db.queryUpd(query);
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        return  db.querySelect(query);
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE users";
        db.queryUpd(query);
    }
}
