package com.example.team3_expenseapp.services;

import com.example.team3_expenseapp.daos.UserDao;
import com.example.team3_expenseapp.models.User;
import com.example.team3_expenseapp.utilities.DBHelper;

public class UserServices {
    private final UserDao userDao;

    // Constructor to initialize UserServices with a UserDao implementation
    public UserServices(UserDao userDao) {
        this.userDao = userDao;
    }

    // Add a new user to the database
    public Boolean addUser(User user, DBHelper dbHelper) {
        return this.userDao.addUser(user, dbHelper);
    }

    // Delete a user from the database (implementation not provided)
    public User deleteUser(long userId) {
        return null; // Placeholder for deletion logic
    }

    // Update user information in the database (implementation not provided)
    public User updateUser(User user) {
        return null; // Placeholder for update logic
    }

    // Find a user by email in the database
    public User findUserByEmail(String idString, DBHelper dbHelper) {
        return this.userDao.findUserByEmail(idString, dbHelper);
}
}