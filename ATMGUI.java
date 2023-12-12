import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ATMGUI extends JFrame {
    
    private JTextField accountNumberField;
    private JPasswordField pinField;
    private JTextArea resultArea;
    private Bank currBank;
    private JTextField depositAmountField;


     public ATMGUI(Bank bank) {
        this.currBank = bank;
        initialize();
    }

    private void initialize() {
        setTitle("Welcome to the ATM. Please Input ID and Password");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        placeComponents(panel);
        add(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setBounds(110, 80, 200, 25);
        panel.add(accountLabel);

        accountNumberField = new JTextField(20);
        accountNumberField.setBounds(230, 80, 300, 25);
        panel.add(accountNumberField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(110, 120, 120, 25);
        panel.add(pinLabel);

        pinField = new JPasswordField(20);
        pinField.setBounds(230, 120, 300, 25);
        panel.add(pinField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(250, 180, 100, 25);
        panel.add(loginButton);
        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // resultArea = new JTextArea();
        // resultArea.setBounds(20, 120, 650, 100);
        // panel.add(resultArea);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(350, 180, 150, 25);
        panel.add(createAccountButton);

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCreateAccountDialog();
            }
        });

    }

//CREATE ACCOUNT

    private void showCreateAccountDialog() {

        String accountName = JOptionPane.showInputDialog("Enter account name:");
        String accountNumber = JOptionPane.showInputDialog("Enter account number:");
        String pin = JOptionPane.showInputDialog("Enter PIN:");

            int result = createAccount(accountName,accountNumber,pin);

            // if(result == 1)
            // resultArea.setText("Account Name already exist.");

            if(result == 2)
            resultArea.setText("Account number cannot be negative.");
            
            else if(result== 3)
            resultArea.setText("Account Number already exist.");

            else if(result== 4)
            resultArea.setText( "Pin cannot be negative.");

            else
            resultArea.setText( "Account Created Successfully");

        } 
        
    private void login() {

        String accountNumber = accountNumberField.getText();
        String pin = new String(pinField.getPassword());
        String accountName = currBank.getAccountName(accountNumber);

        if(currBank.login(accountNumber, pin))
        {
            Account currAccount = currBank.getAccount(accountNumber);
            new AccountMenuGUI(currBank, accountNumber,accountName, pin, currAccount);
        }
        else{
        String message = "Invalid Credentials try Again";
        JOptionPane.showMessageDialog(null, message, "Try Again", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public int createAccount(String accountName, String accountNumber, String pin) {

        // if (currBank.isAccountExists(accountName))
        //     return 1;

        int checkAccountNumber = Integer.parseInt(accountNumber);
        if (checkAccountNumber < 0) 
            return 2;

        if (currBank.isAccountExists(accountNumber))
            return 3;

        int checkPin = Integer.parseInt(pin);
        if (checkPin < 0)
            return 4;

        Account account = new Account(accountName, accountNumber, pin, 100, new Date());
        currBank.addAccount(account);
        return 0;  
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
        public void run() {

            Bank bank = new Bank();

            Account account1 = new Account("Kunal", "54321", "1111", 100, new Date());
            bank.addAccount(account1);

            Account account2 = new Account("Vrushali", "2", "2", 100, new Date());
            bank.addAccount(account2);

            Account account3 = new Account("Rishita", "765432", "3333", 100, new Date());
            bank.addAccount(account3);

            Account account4 = new Account("Yuvraja", "876543", "4444", 100, new Date());
            bank.addAccount(account4);

            Account account5 = new Account("Hani", "987654", "5555", 100, new Date());
            bank.addAccount(account5);

            Account account6 = new Account("Rahul", "109876", "6666", 100, new Date());
            bank.addAccount(account6);

            Account account7 = new Account("Roshni", "111098", "7777", 100, new Date());
            bank.addAccount(account7);

            Account account8 = new Account("Gaurav", "121110", "8888", 100, new Date());
            bank.addAccount(account8);

            Account account9 = new Account("Vaishnavi", "131211", "9999", 100, new Date());
            bank.addAccount(account9);

            Account account10 = new Account("Kalyani", "141312", "1000", 100, new Date());
            bank.addAccount(account10);

            Account account11 = new Account("Mrunal", "151413", "1011", 100, new Date());
            bank.addAccount(account11);

            Account account0 = new Account("Admin", "000000", "****", 0, new Date());
            bank.addAccount(account0);
            
            new ATMGUI(bank);
        
    }
        });
    }
}
