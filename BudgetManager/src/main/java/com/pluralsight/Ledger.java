package com.pluralsight;
import java.util.ArrayList;
import java.util.List;

public class Ledger {
    private List<Trenaction> transaction = new ArrayList<>();

    public void addTransaction(Trenaction trenaction) {
        trenaction.add(trenaction);
    }
}
