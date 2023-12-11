import java.util.Date;
import java.util.Scanner;

public class ATM {

    private Bank bank;
    private final Scanner scanner = new Scanner(System.in);
    int loginAttemptsRemaining = 3;

    public ATM(Bank bank) {
        this.bank = bank;
    }

    public void start() {
        while (true) {
            int choice = showMenu();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    System.out.println();
                    System.out.println("Thank you for using our ATM!");
                    System.exit(0);
                default:
                    System.out.println();  
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int showMenu() {
        System.out.println();
        System.out.println("1. Login");
        System.out.println("2. Create Account");
        System.out.println("3. Exit");
        System.out.println();
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private void login() {
         try {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        if (!bank.isAccountExists(accountNumber))
            {
                throw new IllegalArgumentException("Account account number does not exist.");
            }

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

         // Check account block status
         if (isAccountBlocked(bank.getAccount(accountNumber))) {
            return;
        }

        // Track login attempts
        while (!bank.login(accountNumber, pin) && loginAttemptsRemaining > 0) {
            loginAttemptsRemaining--;
            System.out.println("Incorrect PIN. Please try again. You have " + loginAttemptsRemaining + " attempts remaining.");
            pin = scanner.nextLine();
        }

        if (loginAttemptsRemaining == 0) {
            System.out.println("Account blocked for 24 hours due to multiple incorrect login attempts.");
            bank.getAccount(accountNumber).setBlockedUntil(new Date(System.currentTimeMillis() + 86400000));
            return;
        }

        showAccountMenu(bank.getAccount(accountNumber));
    }
    catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println(e.getMessage());
            showMenu(); // Restart account creation process
        }
    }

    private boolean isAccountBlocked(Account account) {
        Date blockedUntil = account.getBlockedUntil();
        if (blockedUntil != null && blockedUntil.after(new Date())) {
            System.out.println("Account is currently blocked. Please try again later.");
            return true;
        }
        return false;
    }

    private void createAccount() {
         
        System.out.println();
        //String accountNumber = scanner.nextLine();
        try {
            System.out.print("Enter account name: ");
            String accountName = scanner.nextLine();

            if (bank.isAccountExists(accountName))
            {
                throw new IllegalArgumentException("Account Name already exist.");
            }

            System.out.print("Enter account number: ");
            String accountNumber = scanner.nextLine();
            int checkAccountNumber = Integer.parseInt(accountNumber);

            //int accountNumber = Integer.parseInt(scanner.nextLine());
            if (checkAccountNumber < 0) {
                throw new IllegalArgumentException("Account number cannot be negative.");
            }

            if (bank.isAccountExists(accountNumber))
            {
                throw new IllegalArgumentException("Account Number already exist.");
            }

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            int checkPin = Integer.parseInt(pin);

             if (checkPin < 0) {
                throw new IllegalArgumentException("Pin cannot be negative.");
            }


        //negative account number

        // if (bank.isAccountExists(accountNumber)) {
        //     System.out.println("Account number already exists.");
        //     System.out.println("Existing account details:");
        //     Account existingAccount = bank.getAccount(accountNumber);
        //     printAccountDetails(existingAccount);
        //     return;
        // }
   

        System.out.print("Enter initial deposit amount: ");
        double balance = Double.parseDouble(scanner.nextLine());

        Account account = new Account(accountName, accountNumber, pin, balance, new Date());
        bank.addAccount(account);
        System.out.println();
        System.out.println("Account created successfully!");
        }  
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            showMenu(); // Restart account creation process
        }
        
    }

    private void showAccountMenu(Account account) {
        while (true) {
            int choice = showAccountMenuOptions();
            switch (choice) {
                case 1:
                    checkBalance(account);
                    break;
                case 2:
                    deposit(account);
                    break;
                case 3:
                    withdraw(account);
                    break;
                case 4:
                    changePin(account);
                    break;
                case 5:
                    printAccountDetails(account);
                    break;
                case 6:
                    transferFunds(account);
                    break;
                case 7:
                    System.out.println("Thank you for using our ATM!");
                    return;
                case 8:
                    printAccountDetails(account);
                    printTransactionHistory(account);
                    break;
                case 9:
                    if (isAdmin(account)) {
                        bank.printAllAccountDetails();
                    } else {
                        System.out.println("Access denied: This feature is only available for the admin account.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int showAccountMenuOptions() {
        System.out.println();
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Change PIN");
        System.out.println("5. Print Mini Statement");
        System.out.println("6. Transfer Funds");
        System.out.println("7. Logout");
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private void checkBalance(Account account) {
        System.out.println("Your current balance is: $" + account.getBalance());
    }

    private void deposit(Account account) {
        System.out.print("Enter deposit amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
       
        if(account.deposit(amount))
        {
            double currentBalance = account.getBalance();
            account.addTransaction(new Transaction(new Date(), "  Deposit  ", 0, account.getBalance() ));
            System.out.println("Deposit successful. Your new balance is: $" + account.getBalance());
        }

        else
            System.out.println("Try Again");
        
       
    }

    private void withdraw(Account account) {
        System.out.print("Enter withdrawal amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        // 
        System.out.print("Enter PIN: ");
        String currentPin = scanner.nextLine();

        if (account.getPin().equals(currentPin)) {

            if (account.withdraw(amount)) {
            double currentBalance = account.getBalance();
            System.out.println();
            System.out.println("Withdrawal successful!");
            account.addTransaction(new Transaction(new Date(), " Withdrawal", -amount, currentBalance - amount));
            System.out.println("Withdrawal successful. Your new balance is: $" + account.getBalance());
            System.out.println();
            return;
            }
            else {
            System.out.println();
            System.out.println("Insufficient funds or Greater than your withdrawal limit i.e $1000");
            System.out.println(); 
        }
         return;
        }
        else
        {
            try {
                loginAttemptsRemaining--;
                
                System.out.println();
                System.out.println("Wrong Pin! " +loginAttemptsRemaining + " Chances Remaining");

                if (loginAttemptsRemaining == 0) {
                    
                throw new IllegalArgumentException("Invalid Pin. Account blocked for 24 hours due to multiple incorrect login attempts.");
            }
            }
            catch(IllegalArgumentException e)
            {
                 System.out.println(e.getMessage());
                 showMenu();
            }
                
        }
            
        } 

    private void changePin(Account account) {
        System.out.print("Enter old PIN: ");
        String oldPin = scanner.nextLine();
        if (!account.getPin().equals(oldPin)) {
            System.out.println("Incorrect PIN.");
            return;
        }
        System.out.print("Enter new PIN: ");
        String newPin = scanner.nextLine();
        account.setPin(newPin);
        System.out.println("PIN changed successfully!");
    }


     private void printAccountDetails(Account account) {
        account.printAccountDetails(account);
    }
    


    private void transferFunds(Account account) {
        System.out.print("Enter recipient account number: ");
        String recipientAccountNumber = scanner.nextLine();
        System.out.print("Enter transfer amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (!bank.transferFunds(account.getAccountNumber(), recipientAccountNumber, amount, account.getBalance())) {
            System.out.println("Transfer failed. Please try again.");
        } else {
            System.out.println("Transfer successful!");
        }
    }

    private boolean isAdmin(Account account) {
        return account.getAccountName().equalsIgnoreCase("admin");
    }


    public static void main(String[] args) {
        Bank bank = new Bank();
        ATM atm = new ATM(bank);
        atm.start();
    }

    private void printTransactionHistory(Account account)
    {
        account.printTransactionHistory(account);
    }

}
