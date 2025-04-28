package com.pluralsight;
import java.io.*;
import java.util.Scanner;

public class MainMenu {

    private static Ledger ledger = new Ledger();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the BudgetManager!");
            System.out.println("Enter D to diopsit");
            System.out.println("Enter P to make payment");
            System.out.println("Enter L to view ledger");
            System.out.println("Enter X to exit");
            String choice = scanner.nextLine();





        }


    }
}
