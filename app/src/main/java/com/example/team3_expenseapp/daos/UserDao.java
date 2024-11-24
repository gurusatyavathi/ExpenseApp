package com.example.team3_expenseapp.daos;

import com.example.team3_expenseapp.models.User;
import com.example.team3_expenseapp.utilities.DBHelper;

import java.util.List;

public interface UserDao {


    // Add a new user to the database
    Boolean addUser(User user, DBHelper dbHelper);

    // Delete a user from the database by their user ID
    User deleteUser(long userId);

    // Update an existing user in the database
    User updateUser(User user);

    // Find and retrieve a user by their email from the database
    User findUserByEmail(String email, DBHelper dbHelper);

}