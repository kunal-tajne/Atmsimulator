package Atm_Code;
import java.util.*;

//Used HashMap as temporary database
public class Bank {
    private HashMap<String, Account> accounts;

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public boolean login(String accountNumber, String pin) {
        Account account = getAccount(accountNumber);
        if (account == null || account.isBlocked()) {
            return false;
        }
        return account.getPin().equals(pin); // Successful login
    }


    public String getPin(String accountNumber, String pin){
        Account account = getAccount(accountNumber);
        return account.getPin();
    }

    public void addAccount(Account account) {

        accounts.put(account.getAccountNumber(), account);
       
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public boolean isAccountExists(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    public String getAccountName(String accountNumber) {
        Account currAccount = accounts.get(accountNumber);
        String accountName = currAccount.getAccountName();
        return accountName;
    }

    public boolean transferFunds(String sourceAccountNumber, String destinationAccountNumber, double amount, double balance) {

        Account sourceAccount = getAccount(sourceAccountNumber);
        Account destinationAccount = getAccount(destinationAccountNumber);

        if (sourceAccount == null || destinationAccount == null) {
            return false; // Account doesn't exist
        }

        if (!sourceAccount.withdraw(amount)) {
            return false; // Insufficient funds
        }
        sourceAccount.addTransaction(new Transaction(new Date(), "Transfer to: " + destinationAccount.getAccountNumber(), -amount, balance));
        destinationAccount.addTransaction(new Transaction(new Date(), "Transfer from: " + sourceAccount.getAccountNumber(), amount, balance));

        destinationAccount.deposit(amount);


        return true; // Transfer successful
        
}
public StringBuilder printAllAccountDetails() {

    StringBuilder allBankDetails = new StringBuilder("All account details\n");
    for (Account account : accounts.values()) {
        
        if(account.getAccountNumber() == "000000")
            continue;

        allBankDetails.append("\n"+ "Account Number: " + account.getAccountNumber()  + "\n" + "Account Name: " + account.getAccountName() + "\n" +"Balance: $" + account.getBalance() + "\n"+ "Date Created: " + account.getDateCreated() + "\n");
    }

    return allBankDetails;
}
}