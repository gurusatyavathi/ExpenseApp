package com.example.team3_expenseapp.services;

import com.example.team3_expenseapp.daos.TransactionDao;
import com.example.team3_expenseapp.models.Transaction;
import com.example.team3_expenseapp.utilities.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class TransactionServices {
    private TransactionDao transactionDao;

    // Constructor to initialize TransactionServices with a TransactionDao implementation
    public TransactionServices(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    // Add a new transaction to the database
    public Boolean addTransaction(Transaction transaction, DBHelper dbHelper, long userID) {
        return this.transactionDao.addTransaction(transaction, dbHelper, userID);
    }

    // Retrieve a list of transactions for a specific user from the database
    public List<Transaction> getListOfTransactions(DBHelper dbHelper, long userID){
        return this.transactionDao.getListOfTransactions(dbHelper, userID);
    }

    // Retrieve a list of deleted transactions for a specific user from the database
    public List<Transaction> getListOfDeletedTransactions(DBHelper dbHelper, long userID){
        return this.transactionDao.getListOfDeletedTransactions(dbHelper, userID);
    }

    // Delete a transaction from the database
    public Boolean deleteTransaction(long transactionId, DBHelper dbHelper) {
        return this.transactionDao.deleteTransaction(transactionId, dbHelper);
    }

    // Permanently delete a transaction from the database
    public Boolean permanentDeleteTransaction(long transactionId, DBHelper dbHelper){
        return this.transactionDao.permanentDeleteTransaction(transactionId, dbHelper);
    }

    // Restore a deleted transaction in the database
    public Boolean restoreTransaction(long transactionId, DBHelper dbHelper){
        return this.transactionDao.restoreTransaction(transactionId, dbHelper);
    }

    // Update an existing transaction in the database
    public Transaction updateTransaction(Transaction transaction, DBHelper dbHelper) {
        return this.transactionDao.updateTransaction(transaction, dbHelper);
}

}