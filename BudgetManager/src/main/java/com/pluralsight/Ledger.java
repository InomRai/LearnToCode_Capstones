package com.pluralsight;
import java.io.*;
import java.util.*;

public class Ledger {
    private static final String File_Name = "transactions.cvs";
    private List<Ledger> transactions = new ArrayList<>();


    public void loadTransaction() {
        try (BufferedReader br = new BufferedReader(new FileReader(File_Name))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String date = parts[0];
                    String time =parts[1];
                    String description = parts[2];
                    String vendor = parts[3];
                    String amount = parts[4];

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}