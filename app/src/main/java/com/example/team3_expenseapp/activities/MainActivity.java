package com.example.team3_expenseapp.activities;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.team3_expenseapp.R;
import com.example.team3_expenseapp.daos.UserDaoImpl;
import com.example.team3_expenseapp.databinding.ActivityMainBinding;
import com.example.team3_expenseapp.models.User;
import com.example.team3_expenseapp.services.UserServices;
import com.example.team3_expenseapp.utilities.DBHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // View binding
    ActivityMainBinding activityMainBinding = null;
    Intent intent = null;
    SharedPreferences shp1 = null;
    User user;
    UserServices userServices = new UserServices(new UserDaoImpl());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize view binding
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase sql= dbHelper.getReadableDatabase();

        // initialization method
        myinit();
    }

    // Initialization method
    private void myinit() {
        activityMainBinding.loginButton.setOnClickListener(this);
        activityMainBinding.signUptextButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // login button clicked
        if (v.getId()==activityMainBinding.loginButton.getId()){
            String userEmail = activityMainBinding.userEmailText.getText().toString().trim();
            String password = activityMainBinding.textPassword.getText().toString();
            intent = new Intent(this, HomeActivity.class);
            shp1 = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
            // Validate email format
            if (!isValidEmail(userEmail)) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }
            // Find user by email
            user = userServices.findUserByEmail(userEmail,new DBHelper(getApplicationContext()));

            // Check if user exists and password matches
            if (user != null && password.equals(user.getPassword())) {
                // Save user email in shared preferences
                SharedPreferences.Editor editor = shp1.edit();
                editor.putString("USER_EMAIL", userEmail);
                editor.commit();
                // Start HomeActivity
                startActivity(intent);
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Invalid credentials or not a user", Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == activityMainBinding.signUptextButton.getId()) { // sign up button clicked
            intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
    }
    // validate email method
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
}
}