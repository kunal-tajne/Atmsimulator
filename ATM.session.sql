CREATE TABLE accounts (
  account_number VARCHAR(20) PRIMARY KEY,
  pin VARCHAR(20) NOT NULL,
  account_name VARCHAR(50) NOT NULL,
  balance DOUBLE NOT NULL,
  date_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  blocked_until DATETIME DEFAULT NULL
);

CREATE TABLE transactions (
  id INT PRIMARY KEY AUTO_INCREMENT,
  account_number VARCHAR(20) NOT NULL,
  date DATETIME NOT NULL,
  description VARCHAR(20) NOT NULL,
  amount DOUBLE NOT NULL,
  FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);
