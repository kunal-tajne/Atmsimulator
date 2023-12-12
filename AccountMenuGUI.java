import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AccountMenuGUI extends JFrame {
    private JTextArea resultArea;
    private JTextArea textAreaScrollable;
    private JScrollPane scrollPane;
    private String accountNumber;
    private String accountName;
    private String pin;
    private Bank currBank;
    Account currAccount;

     public AccountMenuGUI(Bank currBank , String accountNumber, String accountName, String Pin, Account currAccount) {
        this.currBank = currBank;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.currAccount = currAccount;
        resultArea = new JTextArea();
        textAreaScrollable = new JTextArea();
        textAreaScrollable.setEditable(false);
        scrollPane = new JScrollPane(textAreaScrollable);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        initialize();
    }

    private void initialize() {
        setTitle("Account Menu");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        placeComponents(panel);
        add(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome, to your banking portal " + "" + accountName);
        welcomeLabel.setBounds(200, 20, 300, 40);
        panel.add(welcomeLabel);

        resultArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(20, 150, 650, 100);
        panel.add(scrollPane);

        //DEPOSIT
        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(10, 70, 150, 25);

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });
        panel.add(depositButton);

        //CHANGE PIN

        JButton changePin = new JButton("Change Pin");
        changePin.setBounds(10, 110, 150, 25);
        changePin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changePin();
            }
        });
        panel.add(changePin);

        //WITHDRAW

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(180, 70, 150, 25);
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });
        panel.add(withdrawButton);

        //CHECK BALANCE

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setBounds(350, 70, 150, 25);
        checkBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
        panel.add(checkBalanceButton);

         //TRANSFER FUNDS

        JButton transferFunds = new JButton("Transfer Funds");
        transferFunds.setBounds(520, 70, 150, 25);
        transferFunds.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                transferFunds();
            }
        });
        panel.add(transferFunds);

        //LOGOUT

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(350, 110, 150, 25);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        panel.add(logoutButton);

        //PRINT STATEMENT

        JButton printStatement = new JButton("Print Statement");
        printStatement.setBounds(180, 110, 150, 25);
        printStatement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printStatement();
            }
        });
        panel.add(printStatement);

        JButton adminAccess = new JButton("Admin Access");
        adminAccess.setBounds(520, 110, 150, 25);
        adminAccess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printAllAccountDetails();
            }
        });
        if(currAccount.getAccountName() == "Admin") 
            panel.add(adminAccess);
        
    }

    //-------------------------------------------------------------//
    //DEPOSIT METHOD

    private void deposit() {

        String takeInputAmount = JOptionPane.showInputDialog("Enter Amount to be Deposited");
        double depositAmount = Double.parseDouble(takeInputAmount);

        Account currAccount = currBank.getAccount(accountNumber);
        currAccount.deposit(depositAmount);

        double accountBalance = currAccount.getBalance();
        currAccount.addTransaction(new Transaction(new Date(), "   Deposit     ",depositAmount,  currAccount.getBalance()));

        String message = "Deposit successful!\nYour new balance is: $" + accountBalance;
        JOptionPane.showMessageDialog(null, message, "Deposit Successful", JOptionPane.INFORMATION_MESSAGE);
            
    }

    //-------------------------------------------------------------//
    //WITHDRAW METHOD

    private void withdraw() {
        String takeInputAmount = JOptionPane.showInputDialog("Enter Amount to be Withdrawn");
        double withdrawAmount = Double.parseDouble(takeInputAmount);

        String currPin = JOptionPane.showInputDialog("Enter Account Pin");

        if(currBank.getPin(accountNumber, pin).equals(currPin))
        {
            Account currAccount = currBank.getAccount(accountNumber);
            currAccount.withdraw(withdrawAmount);

            double accountBalance = currAccount.getBalance();
            currAccount.addTransaction(new Transaction(new Date(), " Withdrawal", -withdrawAmount,  currAccount.getBalance()));

            String message = "Withdraw successful!\nYour new balance is: $" + accountBalance;
            JOptionPane.showMessageDialog(null, message, "Deposit Successful", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(currAccount.loginAttemptsRemaining == 0)
        {
            String message = "Account blocked for 24 hours due to multiple incorrect login attempts.";
            JOptionPane.showMessageDialog(null, message, "Account Blocked", JOptionPane.INFORMATION_MESSAGE);
            currBank.getAccount(accountNumber).setBlockedUntil(new Date(System.currentTimeMillis() + 86400000));
            return;
        }
        else
        {
            currAccount.loginAttemptsRemaining--;
            String message = "Wrong Pin Try Again. You have " + currAccount.loginAttemptsRemaining + " attempts remaining";
            JOptionPane.showMessageDialog(null, message, "Wrong Pin", JOptionPane.INFORMATION_MESSAGE);
        }

        
    }


     //-------------------------------------------------------------//
    //CHECK BALANCE METHOD

    private void checkBalance() {
        
        Double balance = currAccount.getBalance();
        resultArea.setText("Your Current Account Balance is : " +balance);
    }

    //-------------------------------------------------------------//
    //CHNAGE PIN METHOD

    private void changePin() {

        String oldPin = JOptionPane.showInputDialog("Enter Your Previous Pin");

        if (currAccount.getPin().equals(oldPin)) {
            String newPin = JOptionPane.showInputDialog("Enter Your New Pin");
            currAccount.setPin(newPin);
        
             String message = "PIN changed successfully! ";
            JOptionPane.showMessageDialog(null, message, "Pin Changed", JOptionPane.INFORMATION_MESSAGE);
        }
    
        else if(currAccount.loginAttemptsRemaining == 0)
        {
            String message = "Account blocked for 24 hours due to multiple incorrect login attempts.";
            JOptionPane.showMessageDialog(null, message, "Account Blocked", JOptionPane.INFORMATION_MESSAGE);
            currBank.getAccount(accountNumber).setBlockedUntil(new Date(System.currentTimeMillis() + 86400000));
            return;
        }
        else
        {
            currAccount.loginAttemptsRemaining--;
            String message = "Wrong Pin Try Again. You have " + currAccount.loginAttemptsRemaining + " attempts remaining";
            JOptionPane.showMessageDialog(null, message, "Wrong Pin", JOptionPane.INFORMATION_MESSAGE);
            throw new IllegalArgumentException("Wrong Pin");
        }
    }

    //-------------------------------------------------------------//
    //TRANSFER FUNDS

    private void transferFunds() {

        String recipientAccountNumber = JOptionPane.showInputDialog("Enter recipient account number: ");
        String confirmAccountNumber = JOptionPane.showInputDialog("Re-enter recipient account number to confirm: ");

        if(!recipientAccountNumber.equals(confirmAccountNumber))
        {
            String message = "Account Number do not match. Try Again. Be careful funds once transferred can't be undone";
            JOptionPane.showMessageDialog(null, message, "Account number don't match", JOptionPane.INFORMATION_MESSAGE);
        }
        
        String accPin = JOptionPane.showInputDialog("Enter pin to confirm Transaction : ");
        if(currAccount.getPin().equals(accPin))
        {
            String transferAmount = JOptionPane.showInputDialog("Enter transfer amount: ");
            double amount = Double.parseDouble(transferAmount);

            if (!currBank.transferFunds(currAccount.getAccountNumber(), recipientAccountNumber, amount, currAccount.getBalance())) 
            {
            resultArea.setText("Transfer failed. Check balance or account number and Please try again.");
            } 
        else {
            resultArea.setText("Transfer Successfull. Remaining Balance : " + currAccount.getBalance());
        }
        }
         else if(currAccount.loginAttemptsRemaining == 0)
        {
            String message = "Account blocked for 24 hours due to multiple incorrect login attempts.";
            JOptionPane.showMessageDialog(null, message, "Account Blocked", JOptionPane.INFORMATION_MESSAGE);
            currBank.getAccount(accountNumber).setBlockedUntil(new Date(System.currentTimeMillis() + 86400000));
            return;
        }
        else
        {
            currAccount.loginAttemptsRemaining--;
            String message = "Wrong Pin Try Again. You have " + currAccount.loginAttemptsRemaining + " attempts remaining";
            JOptionPane.showMessageDialog(null, message, "Wrong Pin", JOptionPane.INFORMATION_MESSAGE);
            throw new IllegalArgumentException("Wrong Pin");
        }

       
    }

    //PRINT STATEMENT

     private void printStatement() {

    StringBuilder statement = new StringBuilder("Account Statement:\n");

    // Iterate over transactions and append them to the statement
    for (Transaction transaction : currAccount.getTransactions()) {
        statement.append(transaction.getDate() + "       " + transaction.getDescription()  + "             "  + transaction.getAmount()  + "            "  + transaction.getBalance()).append("\n");
    }
    resultArea.setText(statement.toString());
    }

    //-------------------------------------------------------------//
    //LOGOUT METHOD
    private void logout() {
        new ATMGUI(currBank); // Close the current window
    }

    public void printAllAccountDetails() {

        StringBuilder allAccountDetails = currBank.printAllAccountDetails();

        resultArea.setText(allAccountDetails.toString());
    }



    // public static void main(String[] args) {
    //     // This part is just for testing
    //     SwingUtilities.invokeLater(new Runnable() {
    //         public void run() {
    //             Bank bank = new Bank();
    //             ATM atm = new ATM(bank); // Instantiate your ATM class
    //             //atm.createAccount(new Account("John Doe", "123456", "1111", 100, null)); // Set a dummy account
    //             new AccountMenuGUI(atm);
    //         }
    //     });
    // }
}
