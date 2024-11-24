package com.example.team3_expenseapp.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.team3_expenseapp.R;
import com.example.team3_expenseapp.adapters.ListAdapter;
import com.example.team3_expenseapp.adapters.TransactionAdapter;
import com.example.team3_expenseapp.databinding.FragmentDeleteBinding;
import com.example.team3_expenseapp.models.Transaction;
import com.example.team3_expenseapp.models.User;
import com.example.team3_expenseapp.services.TransactionServices;
import com.example.team3_expenseapp.utilities.DBHelper;

import java.util.ArrayList;
import java.util.List;


public class DeleteFragment extends Fragment implements AdapterView.OnItemClickListener {

    FragmentDeleteBinding fragmentDeleteBinding;
    DBHelper dbHelper;
    TransactionServices transactionServices;
    User currentUser;
    List<Transaction> listOfDeletedTransactions;

    public DeleteFragment() {
        // Required empty public constructor
    }

    public DeleteFragment(TransactionServices transactionServices, User currentUser) {
        this.transactionServices = transactionServices;
        this.currentUser = currentUser;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentDeleteBinding = FragmentDeleteBinding.inflate(inflater, container, false);
        dbHelper = new DBHelper(getContext());
        showList(); // displaying the list on the UI
        init(); // setting click listeners
        return  fragmentDeleteBinding.getRoot();
    }

    private void init() {
        fragmentDeleteBinding.recentDelListView.setOnItemClickListener(this);
    }

    // func to display list on UI
    private void showList() {
        // getting list of deleted transactions
        listOfDeletedTransactions = this.transactionServices.getListOfDeletedTransactions(dbHelper, currentUser.getId());


        boolean flag = true;

        // Populate the list view with transaction data
        if(listOfDeletedTransactions == null){
            // Show a message if the list is empty
            Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show();
            flag = false;

        }
        // Set up the adapter
        ListAdapter adapter = new ListAdapter(getContext(), listOfDeletedTransactions);
        fragmentDeleteBinding.recentDelListView.setAdapter(adapter);
        if(!flag){
            Toast.makeText(getContext(), "List is Empty", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Display a dialog before deleting an transaction's data
        builder.setTitle("Transaction");

        // Positive button for deleting the data
        builder.setPositiveButton("Restore Transaction", (dialog, which) -> {
            transactionServices.restoreTransaction(listOfDeletedTransactions.get((int) id).getId(), dbHelper);
            Toast.makeText(getActivity(), "Restored", Toast.LENGTH_SHORT).show();
            showList(); // Refresh the list view
        });

        // Negative button for cancelling the delete
        builder.setNegativeButton("Delete Permanently",(dialog, which) ->{
            transactionServices.permanentDeleteTransaction(listOfDeletedTransactions.get((int) id).getId(), dbHelper);
            Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_SHORT).show();
            showList(); // Refresh the list view
        });

        builder.setNeutralButton("Cancel", (dialog, which) -> {
            // Cancel button clicked
            dialog.dismiss();
        });

        builder.create().show();

}

}