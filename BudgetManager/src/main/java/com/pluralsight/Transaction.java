package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

        private LocalDate date;
        private LocalTime time;
        private String description;
        private String vendor;
        private double amount;

        public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
            this.date = date;
            this.time = time;
            this.description = description;
            this.vendor = vendor;
            this.amount = amount;
        }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() { return amount; }


    public String getDescription() {
        return description;
    }

    public String toCSV() {
            return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
        }



    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return date.format(dateFormatter) + " | " + time.format(timeFormatter)
                + " | " + description + " | " + vendor + " | " + amount;
    }
}
