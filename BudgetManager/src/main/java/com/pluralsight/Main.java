package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Ledger ledger = new Ledger();

        while (true) {
            System.out.println("\nHome Menu");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) View Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "D":
                    addTransaction(scanner, ledger, true);
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
                    System.out.println("Invalid option.");
            }
        }
    }
    public static void addTransaction(Scanner scanner, Ledger ledger, boolean isDeposit) {
        try {
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            System.out.print("Vendor: ");
            String vendor = scanner.nextLine();

            System.out.print("Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());
            if (!isDeposit) {
                amount *= -1; // Make it negative for payment
            }

            Transaction t = new Transaction(date, time, description, vendor, amount);
            ledger.addTransaction(t);
            System.out.println("Transaction saved.");
        } catch (DateTimeParseException | NumberFormatException e) {
            System.out.println("Invalid input. Try again.");
        }
    }


    public static void showLedger(Scanner scanner, Ledger ledger) {
        System.out.println("\nLedger View");
        System.out.println("A) All Transactions");
        System.out.println("D) Deposits Only");
        System.out.println("P) Payments Only");
        System.out.print("Choose option: ");
        String choice = scanner.nextLine().toUpperCase();

        List<Transaction> transactions;

        switch (choice) {
            case "A":
                transactions = ledger.getAll();
                break;
            case "D":
                transactions = ledger.getDeposits();
                break;
            case "P":
                transactions = ledger.getPayments();
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