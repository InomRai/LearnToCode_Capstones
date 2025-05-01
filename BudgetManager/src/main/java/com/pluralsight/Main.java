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

// Main loop: keeps showing the menu until the user exits
        while (true) {
            System.out.println("\n<Home Menu>");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) View Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine().toUpperCase(); // Read input and convert to uppercase


            // Use switch to decide what to do based on user input
            switch (choice) {
                case "D": // if user choose D
                    addTransaction(scanner, ledger, true); // call the method to deposit
                    break;
                case "P":
                    addTransaction(scanner, ledger, false);
                    break;
                case "L":
                    showLedger(scanner, ledger);
                    break;
                case "X":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option.");  // Show error
            }
        }
    }


    // Method to add a deposit or payment
    public static void addTransaction(Scanner scanner, Ledger ledger, boolean isDeposit) {
        try {
            LocalDate date = LocalDate.now(); // for date
            LocalTime time = LocalTime.now(); // for time

            System.out.print("Description: "); // prompt and read transaction
            String description = scanner.nextLine();

            System.out.print("Vendor: ");
            String vendor = scanner.nextLine();

            System.out.print("Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());


            if (!isDeposit) {
                amount = Math.abs(amount); // If it's a payment, make the amount negative
            }


                //check if amount is negative
            if (amount < 0) {
                System.out.println("Amount cannot be negative. Try again");
                return;
            }


            // Create the transaction and save
            Transaction t = new Transaction(date, time, description, vendor, amount);
            ledger.addTransaction(t);
            System.out.println("Transaction successful.");
        } catch (DateTimeParseException | NumberFormatException e) {
            System.out.println("Invalid input. Try again.");
        }
    }

    // Method to show ledger entries
    public static void showLedger(Scanner scanner, Ledger ledger) {
        System.out.println("\n<Ledger View>");
        System.out.println("A) All Transactions");
        System.out.println("D) Deposits Only");
        System.out.println("P) Payments Only");
        System.out.print("Choose option: ");
        String choice = scanner.nextLine().toUpperCase();

        List<Transaction> transactions;

        switch (choice) {
            case "A":
                transactions = ledger.getAll(); // Get all entries
                break;
            case "D":
                transactions = ledger.getDeposits(); // Get only deposits
                break;
            case "P":
                transactions = ledger.getPayments(); // Get only payments
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }
}