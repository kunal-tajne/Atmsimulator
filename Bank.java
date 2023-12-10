import java.util.*;


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

    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public boolean isAccountExists(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    public boolean transferFunds(String sourceAccountNumber, String destinationAccountNumber, double amount) {
        Account sourceAccount = getAccount(sourceAccountNumber);
        Account destinationAccount = getAccount(destinationAccountNumber);

        if (sourceAccount == null || destinationAccount == null) {
            return false; // Account doesn't exist
        }

        if (!sourceAccount.withdraw(amount)) {
            return false; // Insufficient funds
        }

        destinationAccount.deposit(amount);
        return true; // Transfer successful
}
}