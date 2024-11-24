package com.example.team3_expenseapp.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.team3_expenseapp.models.User;
import com.example.team3_expenseapp.utilities.DBHelper;

import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public Boolean addUser(User user, DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.KEY_NAME, user.getName());
        values.put(DBHelper.KEY_EMAIL, user.getEmail());
        values.put(DBHelper.KEY_PASSWORD, user.getPassword());

        // Insert the new user into the users table
        long id = db.insert(DBHelper.TABLE_USERS, null, values);

        // Check if the insertion was successful
        return (id != -1);
    }

    @Override
    public User deleteUser(long userId) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User findUserByEmail(String userEmail, DBHelper dbHelper) {
        User user = null; // Default value if user is not found

        // Get a readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the raw SQL query
        String query = "SELECT * FROM " + DBHelper.TABLE_USERS +
                " WHERE " + DBHelper.KEY_EMAIL + " = '" + userEmail + "'";

        // Execute the raw query
        Cursor cursor = db.rawQuery(query, null);

        // Check if the cursor contains data
        if (cursor != null && cursor.moveToFirst()) {
            // Extract user data from the cursor
            long userId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.KEY_ID));
            String userName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_NAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_EMAIL));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_PASSWORD));

            // Create a new User object with retrieved data
            user = new User(userName, email, password);
            user.setId(userId);
            cursor.close(); // Close the cursor
        }

        // Close the database connection
        db.close();

        // Return the retrieved User object
        return user;
}
}