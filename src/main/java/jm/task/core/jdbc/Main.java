package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        final UserService userService = new UserServiceImpl();


        userService.createUsersTable();


        userService.saveUser("Alex", "Alexoff", (byte) 18);
        userService.saveUser("Ivan", "Umnov", (byte) 30);
        userService.saveUser("Ann", "Petrova", (byte) 40);
        userService.saveUser("Vova", "Ivanov", (byte) 50);

        List<User> userList = userService.getAllUsers();
        userList.forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}
