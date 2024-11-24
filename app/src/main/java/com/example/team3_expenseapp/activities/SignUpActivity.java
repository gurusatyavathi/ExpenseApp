package com.example.team3_expenseapp.activities;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;
import com.example.team3_expenseapp.daos.UserDaoImpl;
import com.example.team3_expenseapp.databinding.ActivitySignUpBinding;
import com.example.team3_expenseapp.models.User;
import com.example.team3_expenseapp.services.UserServices;
import com.example.team3_expenseapp.utilities.DBHelper;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding activitySignUpBinding;
    UserServices userServices = new UserServices(new UserDaoImpl());

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up view binding
        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(activitySignUpBinding.getRoot());
        // Set input type for email EditText
        activitySignUpBinding.enterEmailText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        dbHelper = new DBHelper(getApplicationContext());
        myinit();
    }

    // Initialize UI elements and click listeners
    private void myinit() {
        activitySignUpBinding.SignUpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    // Handle user sign up
    private void signUp() {
        // Retrieve user input from the EditText fields
        String userName = activitySignUpBinding.enterNameText.getText().toString().trim();
        String email = activitySignUpBinding.enterEmailText.getText().toString().trim();
        String password = activitySignUpBinding.enterPasswordText.getText().toString().trim();

        // Validate user input
        if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // validate email
        if(!isValidEmail(email)){
            Toast.makeText(this, "Enter Email in correct format", Toast.LENGTH_SHORT).show();
            activitySignUpBinding.enterEmailText.setError("Enter correct format");
            return;
        }
        // validate password
        if(password.length()<3){
            activitySignUpBinding.enterPasswordText.setError("Length should be more than 2");
            return;

        }

        // Create a new User object
        User user = new User(userName, email, password);

        // Add the user to the database
        Boolean success = userServices.addUser(user, dbHelper);

        // Check if user insertion was successful
        if (success) {
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
            finish(); // Finish the SignUpActivity and return to the MainActivity
        } else {
            Toast.makeText(this, "Failed to register user", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmail(String email) {
        return email.contains("@");
}
}