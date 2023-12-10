import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Account {

    private double dailyWithdrawalLimit;
    private double dailyDepositLimit;
    private double remainingWithdrawalLimit;
    private double remainingDepositLimit;
    private Date dateCreated;
    private String accountName;

     // Transaction history data
    private List<Transaction> transactions;


    private String accountNumber;
    private String pin;
    private double balance;
    private Date blockedUntil;


    public Account( String accountName, String accountNumber, String pin, double balance, Date dateCreated) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new ArrayList<>();
        this.blockedUntil = null;
        this.accountName = accountName;

        this.dateCreated = dateCreated;

        this.dailyWithdrawalLimit = 500; // Set default withdrawal limit
        this.dailyDepositLimit = 5000; // Set default deposit limit
        this.remainingWithdrawalLimit = dailyWithdrawalLimit;
        this.remainingDepositLimit = dailyDepositLimit;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName()
    {   
        return accountName;
    }

    public void setAccountName(String accountName)
    {
            this.accountName = accountName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public boolean deposit(double amount) {
        if (amount > remainingDepositLimit) {
            return false; // Exceeding deposit limit
        }

        if (amount <= 0) {
            return false; // Invalid deposit amount
        }

        remainingDepositLimit -= amount;

        balance += amount;

        return true;
    }

    public boolean withdraw(double amount) {
        if (amount > remainingWithdrawalLimit) {
            return false; // Exceeding withdrawal limit
        }
        if (amount <= 0 || amount > balance) {
            return false; // Invalid withdrawal amount or insufficient funds
        }
        remainingWithdrawalLimit -= amount;
        balance -= amount;

        return true;
    }

    public void printAccountDetails(Account account) {
        System.out.println("Bank Statement");
        System.out.print("    Account Number     |       Amount    |      Balance      |      Date    " + account.getAccountNumber());
        System.out.println();
         System.out.print("    " + account.getAccountNumber() + "    " +account.getBalance() +  "    " +account.getDateCreated() +  "    " + "    ");
        // System.out.println("Balance: $" + account.getBalance());
        // System.out.println("Date Created: " + account.getDateCreated());
    }
    
    
    //  public String printMiniStatement(Account account) {
    //         StringBuilder miniStatement = new StringBuilder();
    //         miniStatement.append("Mini Statement").append("\n");
    //         miniStatement.append("Account Number: ").append(account.getAccountNumber()).append("\n");
    //         miniStatement.append("|---|---|---|---|");

    //         int numTransactions = Math.min(transactions.size(), 5); // Display last 5 transactions
    //         for (int i = transactions.size() - 1; i >= transactions.size() - numTransactions; i--) {
    //             miniStatement.append("\n").append(transactions.get(i).toString());
    //         }
    //         return miniStatement.toString();
    //     }


    public boolean isBlocked() {
        return blockedUntil != null && blockedUntil.after(new Date());
    }

    public Date getBlockedUntil()
 
{
        return blockedUntil;
    }

    public
 
void
 
setBlockedUntil(Date blockedUntil)
 
{
        this.blockedUntil = blockedUntil;
    }
}