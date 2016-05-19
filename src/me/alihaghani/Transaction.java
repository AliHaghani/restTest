package me.alihaghani;

/**
 * Created by Ali on 2016-05-18.
 */
public class Transaction {
    private int number;
    private String date; // change to util.Date later
    private String ledger;
    private long amount;
    private String company;

    public Transaction(int number, String date, String ledger, long amount, String company) {
        this.number = number;
        this.date = date;
        this.ledger = ledger;
        this.amount = amount;
        this.company = company;
    }
}
