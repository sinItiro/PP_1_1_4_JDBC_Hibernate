package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/test");
                settings.put(Environment.USER, "1234");
                settings.put(Environment.PASS, "1234");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

                settings.put(Environment.SHOW_SQL, "true");
                //settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                                                        .applySettings(configuration.getProperties())
                                                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

}

