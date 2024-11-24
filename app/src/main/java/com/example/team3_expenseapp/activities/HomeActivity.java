package com.example.team3_expenseapp.activities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.team3_expenseapp.R;
import com.example.team3_expenseapp.daos.TransactionDaoImpl;
import com.example.team3_expenseapp.daos.UserDaoImpl;
import com.example.team3_expenseapp.databinding.ActivityHomeBinding;
import com.example.team3_expenseapp.databinding.FragmentHomeBinding;
import com.example.team3_expenseapp.fragments.AboutFragment;
import com.example.team3_expenseapp.fragments.DeleteFragment;
import com.example.team3_expenseapp.fragments.HomeFragment;
import com.example.team3_expenseapp.fragments.RateUsFragment;
import com.example.team3_expenseapp.fragments.SearchTransactionFragment;
import com.example.team3_expenseapp.fragments.TransactionFragment;
import com.example.team3_expenseapp.models.User;
import com.example.team3_expenseapp.services.TransactionServices;
import com.example.team3_expenseapp.services.UserServices;
import com.example.team3_expenseapp.utilities.DBHelper;
import com.google.android.material.appbar.MaterialToolbar;


public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding activityHomeBinding;
    private ActionBarDrawerToggle mToggle;
    private SharedPreferences shp;
    private UserServices userServices;
    private DBHelper dbHelper;
    private User currentUser;
    private TransactionServices transactionServices;
    private FragmentHomeBinding binding;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize view binding
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        // Set custom toolbar as action bar
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar);
        setContentView(activityHomeBinding.getRoot());
        // Initialize DBHelper, UserServices, TransactionServices
        dbHelper = new DBHelper(this);
        userServices = new UserServices(new UserDaoImpl());
        transactionServices = new TransactionServices(new TransactionDaoImpl());
        // Get current user details
        getUserDetails();
        setDefaultFragment();
        // Initialize UI elements
        initializeUI();
    }

    // Get current user details from SharedPreferences
    private void getUserDetails() {
        shp = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        String emailString = shp.getString("USER_EMAIL", null);
        currentUser = userServices.findUserByEmail(emailString, dbHelper);
        Log.d("UserDetails", "User: " + currentUser.toString());
    }

    // Initialize UI elements and set up navigation drawer
    @SuppressLint("SetTextI18n")
    private void initializeUI() {
        // Set custom toolbar as action bar
        setSupportActionBar(activityHomeBinding.materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up toggle for navigation drawer
        mToggle = new ActionBarDrawerToggle(this, activityHomeBinding.drawerLayout, activityHomeBinding.materialToolbar, R.string.navDrawerTextOpen, R.string.navDrawerTextClose);
        activityHomeBinding.drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        // Set up navigation view item selection listener
        activityHomeBinding.navView.setNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.nav_transaction) {
                fragment = new TransactionFragment(transactionServices, currentUser);
            } else if (itemId == R.id.nav_about) {
                fragment = new AboutFragment();
            } else if (itemId == R.id.nav_home) {
                fragment = new HomeFragment(transactionServices, currentUser);
            } else if (itemId == R.id.nav_rateus) {
                fragment = new RateUsFragment();
            } else if (itemId == R.id.nav_logout) {
                // Handle logout by starting MainActivity
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_delete) {
                fragment = new DeleteFragment(transactionServices, currentUser);
            }
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, fragment)
                        .commit();
                activityHomeBinding.drawerLayout.closeDrawers();
                return true;
            }
            return false;
        });
    }


    // Handle options menu item selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame, new SearchTransactionFragment(transactionServices, currentUser))
                    .addToBackStack(null) // Add to back stack to handle back navigation
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item) || mToggle.onOptionsItemSelected(item);
    }

    private void setDefaultFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new HomeFragment(transactionServices, currentUser))
                .commit();
}
}