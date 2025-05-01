package com.pluralsight;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Ledger {

    private final String FILE_NAME = "transactions.csv";
    private List<Transaction> transactions;

    public Ledger() {
        transactions = new ArrayList<>();
        loadTransactions();
    }

    private void loadTransactions() {
        transactions.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);
                    transactions.add(new Transaction(date, time, description, vendor, amount));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("transactions.csv not found. It will be created.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void addTransaction(Transaction t) {
        transactions.add(0, t); // Add to top of the list (latest first)
        saveTransactionToFile(t);
    }

    private void saveTransactionToFile(Transaction t) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(t.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    public List<Transaction> getAll() {
        return transactions;
    }

    public List<Transaction> getDeposits() {
        List<Transaction> deposits = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) deposits.add(t);
        }
        return deposits;
    }

    public List<Transaction> getPayments() {
        List<Transaction> payments = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) payments.add(t);
        }
        return payments;
    }

}
