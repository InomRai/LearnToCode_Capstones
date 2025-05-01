package com.pluralsight;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Ledger {

    private final String FILE_NAME = "src/main/resources/transactions.csv"; // The file path to store your transaction data
    private List<Transaction> transactions;  // to holds all transactions in memory


//    runs when Ledger object is created
   public Ledger() {
        transactions = new ArrayList<>(); // Initializes the list
        loadTransactions(); // Loads existing transactions from the file
    }


    // Loads transactions from the file into the 'transactions' list
    private void loadTransactions() {
        transactions.clear(); // Clear the current list to avoid duplicates
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean isFirstLine = true;

            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Skip the header line
                if (isFirstLine && line.toLowerCase().contains("date|time|description|vendor|amount")) {
                    isFirstLine = false;
                    continue;
                }
                isFirstLine = false; // After first line, reset the flag

                String[] parts = line.split("\\|");  // Split the line by
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);
                    transactions.add(new Transaction(date, time, description, vendor, amount)); // Create a new Transaction and add it to the list
                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }


    // Adds a transaction to the list and saves it to file
    public void addTransaction(Transaction t) {
        transactions.add(0, t); // Add to the top of the list (newest first)
        saveTransactionToFile(t);
    }

    // Appends a transaction to the CSV file
    private void saveTransactionToFile(Transaction t) {
        File file = new File(FILE_NAME); // Reference to the file
        boolean isNewFile = !file.exists(); // Check if it's a new fil

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (isNewFile) {
                writer.write("date|time|description|vendor|amount");
                writer.newLine();
            }
            writer.write(t.toCSV());  // Write the transaction's data in CSV format
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }


    // Returns all transactions
    public List<Transaction> getAll() {
        return transactions;
    }


    // Returns only the deposits
    public List<Transaction> getDeposits() {
        List<Transaction> deposits = new ArrayList<>(); //New list to transaction
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) deposits.add(t); //add only if amount is posistive
        }
        return deposits;
    }


    //return all the payment
    public List<Transaction> getPayments() {
        List<Transaction> payments = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) payments.add(t);
        }
        return payments;
    }
}