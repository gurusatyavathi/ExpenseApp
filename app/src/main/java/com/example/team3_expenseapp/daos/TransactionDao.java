package com.example.team3_expenseapp.daos;

import com.example.team3_expenseapp.models.Transaction;
import com.example.team3_expenseapp.utilities.DBHelper;
import java.util.List;

public interface TransactionDao {

    // Add a new transaction to the database
    Boolean addTransaction(Transaction transaction, DBHelper dbHelper, long userID);

    // Temporarily delete a transaction by marking it as deleted in the database
    Boolean deleteTransaction(long transactionId, DBHelper dbHelper);

    // Permanently delete a transaction from the database
    Boolean permanentDeleteTransaction(long transactionId, DBHelper dbHelper);

    // Restore a deleted transaction by marking it as not deleted in the database
    Boolean restoreTransaction(long transactionId, DBHelper dbHelper);

    // Update an existing transaction in the database
    Transaction updateTransaction(Transaction transaction, DBHelper dbHelper);

    // Retrieve a list of transactions associated with a specific user
    List<Transaction> getListOfTransactions(DBHelper dbHelper, long userID);

    // Retrieve a list of deleted transactions associated with a specific user
    List<Transaction> getListOfDeletedTransactions(DBHelper dbHelper, long userID);


}