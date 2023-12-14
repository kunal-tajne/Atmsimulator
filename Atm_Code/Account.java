package Atm_Code;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;

public class Account {

    private double dailyWithdrawalLimit;
    private double dailyDepositLimit;
    private double lastDeposit;
    private double lastWithdrawal;
    private double remainingWithdrawalLimit;
    private double remainingDepositLimit;
    private Date dateCreated;
    private Date balanceUpdated;
    private Date withdrawUpdated;
    private String accountName;
    public int loginAttemptsRemaining = 3;
    public double currAmount = 0;

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

        this.dailyWithdrawalLimit = 1000; // Set default withdrawal limit
        this.dailyDepositLimit = 10000; // Set default deposit limit
        this.remainingWithdrawalLimit = dailyWithdrawalLimit;
        this.remainingDepositLimit = dailyDepositLimit;
        this.transactions = new ArrayList<>();
    }


    public void invalitAttempt()
    {
        loginAttemptsRemaining--;
        return;
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
        return;
    }

    public boolean deposit(double amount) {
        if (amount > remainingDepositLimit) {
            {
                System.out.println("Daily Deposit Limit for your account is $10,000");
            }
            return false; // Exceeding deposit limit
        }

        if (amount <= 0) {
            return false; // Invalid deposit amount
        }

        balance += amount;
        remainingDepositLimit -= amount;
        balanceUpdated = new Date();
        lastDeposit = amount;
        currAmount = amount;

        return true;
    }

    public boolean withdraw(double amount) {

        // if (amount > remainingWithdrawalLimit) {
        //     System.out.println("Daily withdrawal Limit for your account is $1000");
        //     return false; // Exceeding withdrawal limit
        // }
        if (amount <= 0 || amount > balance) {
            return false; // Invalid withdrawal amount or insufficient funds
        }
        remainingWithdrawalLimit -= amount;
        balance -= amount;

        withdrawUpdated = new Date();
        lastWithdrawal = amount;
        currAmount = amount;

        return true;
    }

    public double getAmount()
    {
        return currAmount;
    }

    public void printAccountDetails(Account account) {

        if(account.getAccountNumber()== "000000")
        return;
        System.out.println();
        System.out.println("Bank Statement");
        System.out.println("Account Name : " +  account.getAccountName());
        System.out.println("Account Number : " +  account.getAccountNumber());
        System.out.println("Account Balance : " +  account.getBalance() + " || Last Deposit amount: " +lastDeposit+ " : Received at : " +balanceUpdated);
        System.out.println("Last Deposit Value : " +  account.getBalance() + " || Last Withdrawal amount: " + lastWithdrawal + " : withdrawn at : " + withdrawUpdated);
        System.out.println("Account Created Date : " + account.getDateCreated());
        System.out.println();
        // System.out.println("Balance: $" + account.getBalance());
        // System.out.println("Date Created: " + account.getDateCreated());
    }
    


    public boolean isBlocked() {
        return blockedUntil != null && blockedUntil.after(new Date());
    }

    public Date getBlockedUntil()
 
{
        return blockedUntil;
    }

    public void setBlockedUntil(Date blockedUntil)
 
    {
        this.blockedUntil = blockedUntil;
    }

public void addTransaction(Transaction transaction)
 
{
    this.transactions.add(transaction);
}

public List<Transaction> getTransactions()
 
{
    return Collections.unmodifiableList(transactions); // Return unmodifiable list for safety
}

public void printTransactionHistory(Account account) {
    System.out.println("Transaction History:");
    System.out.println();
    System.out.println("|            Date              |    Description    |      Amount      |      Balance      |");
    System.out.println("|------------------------------|-------------------|------------------|-------------------|");

    for (Transaction transaction : account.getTransactions()) {
        //System.out.println(transaction.toString());
        System.out.println(transaction.getDate() + "       " + transaction.getDescription()  + "             "  + transaction.getAmount()  + "            "  + transaction.getBalance());
    }
}
}