package Atm_Code;
import java.util.Date;

public class Transaction {
    private Date date;
    private String description;
    private double amount;
    private double balance;

    public Transaction(Date date, String description, double amount, double balance) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.balance = balance;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

     public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("| %10s | %20s | %8.2f | %8.2f |",
                date, description, amount, getBalanceAfterTransaction());
    }

    public double getBalanceAfterTransaction() {
        // Check the transaction type (deposit or withdrawal) from the description
        if (description.contains("Deposit")) {
            return amount; // Increase balance for deposits
        } else if (description.contains("Withdrawal")) {
            return -amount; // Decrease balance for withdrawals
        } else {
            // Handle other transaction types if applicable
            return 0;
        }
    }    
}
