package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //to read user input
        Ledger ledger = new Ledger(); //to manage transaction

        // Main menu loop: runs until user chooses to exit
        while (true) {
            // Display main menu options
            System.out.println("\n<Home Menu>");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) View Ledger");
            System.out.println("R) View Reports");
            System.out.println("X) Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine().toUpperCase(); // Read and normalize user input

            // Determine what to do based on user's menu choice
            switch (choice) {
                case "D":
                    addTransaction(scanner, ledger, true); // call the method to deposit
                    break;
                case "P":
                    addTransaction(scanner, ledger, false);
                    break;
                case "L":
                    showLedger(scanner, ledger);
                    break;
                case "R":
                    showReports(scanner, ledger);
                    break;
                case "X":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option."); // show error
            }
        }
    }

    // Method to add a deposit or a payment
    public static void addTransaction(Scanner scanner, Ledger ledger, boolean isDeposit) {
        try {
            // Automatically set the date and time of transaction to now
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            // Ask user for transaction description
            System.out.print("Description: ");
            String description = scanner.nextLine();

            // Ask user for vendor (who you paid or received money from)
            System.out.print("Vendor: ");
            String vendor = scanner.nextLine();

            // Ask user for amount and convert to double
            System.out.print("Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            // If it's a payment, make sure the amount is negative
            if (!isDeposit) {
                amount = -Math.abs(amount);
            }

            // Prevent zero amount transactions
            if (amount == 0) {
                System.out.println("Amount cannot be zero. Try again.");
                return;
            }

            // Create the transaction and add it to the ledger
            Transaction t = new Transaction(date, time, description, vendor, amount);
            ledger.addTransaction(t);
            System.out.println("Transaction successful.");
        } catch (DateTimeParseException | NumberFormatException e) {
            System.out.println("Invalid input. Try again."); //show error
        }
    }


    // Method to display transactions (ledger view)
    public static void showLedger(Scanner scanner, Ledger ledger) {
        // Show ledger menu
        System.out.println("\n<Ledger View>");
        System.out.println("A) All Transactions");
        System.out.println("D) Deposits Only");
        System.out.println("P) Payments Only");
        System.out.print("Choose option: ");
        String choice = scanner.nextLine().toUpperCase();

        List<Transaction> transactions; // List to store results

        // Determine which transactions to show
        switch (choice) {
            case "A":
                transactions = ledger.getAll(); // All transactions
                break;
            case "D":
                transactions = ledger.getDeposits(); // Only positive amounts
                break;
            case "P":
                transactions = ledger.getPayments(); // Only negative amounts
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

        // Print out results
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }

    // Method to display reports like month-to-date, year-to-date, or search
    public static void showReports(Scanner scanner, Ledger ledger) {
        // Show report options
        System.out.println("\n<Reports>");
        System.out.println("1) Month To Date");
        System.out.println("2) Year To Date");
        System.out.println("3) Search by Vendor");
        System.out.print("Choose option: ");
        String choice = scanner.nextLine().trim(); // Clean up input

        List<Transaction> results; // To store report output


        switch (choice) {
            case "1":
                results = ledger.getMonthToDate(); // This month only
                break;
            case "2":
                results = ledger.getYearToDate(); // This year only
                break;
            case "3":
                System.out.print("Enter keyword to search: ");
                String keyword = scanner.nextLine().trim(); // Read search keyword
                results = ledger.searchByVendor(keyword); // Search in descriptions
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }


        if (results.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction t : results) {
                System.out.println(t); // Formatted output from Transaction
            }
        }
    }
}



