🏦 Banking System (Java 21 + Swing + MySQL + Maven)

A simple Online Banking System built using:

Java 21

Swing GUI

JDBC (MySQL Connector/J 8.3.0)

MySQL

Maven (with jar-with-dependencies)

This project demonstrates user authentication, account management, deposits, withdrawals, and transaction history storage in a MySQL database.

🚀 Features
✔ User Module

User registration

User login (email + password)

Password validation

✔ Account Module

Create new bank accounts

View account balance

Deposit money

Withdraw money

Transfer funds

Auto-generated account numbers

✔ Transaction Module

Stores every transaction

Type (deposit / withdraw / transfer)

Amount and balance after operation

Timestamp for each transaction

✔ GUI (Swing)

Login screen

Dashboard

Simple clean UI with buttons & text fields

🛠 Technologies Used
Technology	Purpose
Java 21	Backend and GUI
Swing	User Interface
MySQL	Database
JDBC	Database connectivity
Maven	Build automation & dependency management
MySQL Connector/J 8.3.0	JDBC driver
📁 Project Structure
BankingSystem/
│ pom.xml
│ README.md
│
└───src/main/java/com/shreyash/banking/
     │  AppMain.java
     │
     ├── db/
     │     DBConnection.java
     │
     ├── dao/
     │     UserDAO.java
     │     AccountDAO.java
     │
     └── ui/
           LoginPanel.java
           MainFrame.java
           DashboardPanel.java
│
└───src/main/resources/
      application.properties

🗄 Database Setup (MySQL)

Run these queries in MySQL:

CREATE DATABASE bankingdb;
USE bankingdb;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  password VARCHAR(100)
);

CREATE TABLE accounts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  account_number VARCHAR(20) UNIQUE,
  balance DOUBLE DEFAULT 0,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE transactions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  account_number VARCHAR(20),
  type VARCHAR(20),
  amount DOUBLE,
  balance_after DOUBLE,
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

➕ Insert Sample Data
Users
INSERT INTO users (name, email, password)
VALUES 
('John Doe', 'john@example.com', 'password123'),
('Emma Watson', 'emma@example.com', 'mypassword'),
('Shreyash Shrivastava', 'shreyash@example.com', 'admin123');

Accounts
INSERT INTO accounts (user_id, account_number, balance)
VALUES
(1, 'ACC10001', 5000),
(2, 'ACC20001', 3000),
(3, 'ACC30001', 8000);

Transactions
INSERT INTO transactions (account_number, type, amount, balance_after)
VALUES
('ACC10001', 'deposit', 2000, 7000),
('ACC20001', 'withdraw', 500, 2500),
('ACC30001', 'deposit', 1500, 9500);

⚙️ Configuration

Create file:

src/main/resources/application.properties


Add:

db.url=jdbc:mysql://localhost:3306/bankingdb
db.user=root
db.password=YOUR_PASSWORD

🔧 Build (Maven)

To build the fat jar:

mvn clean package


This produces:

target/banking-system-1.0-SNAPSHOT-jar-with-dependencies.jar

▶ Run the Application

Run from terminal:

java -jar target/banking-system-1.0-SNAPSHOT-jar-with-dependencies.jar


Or run from IDE:

AppMain.java

📤 Push to GitHub
git init
git add .
git commit -m "Initial commit - Banking System project"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/BANKINGSYSTEM.git
git push -u origin main

📌 Notes

Requires Java 21 installed

MySQL server must be running

Database connection uses values from application.properties

Make sure MySQL Connector is added through Maven

Includes proper folder structure for development and submission

🧑‍💻 Author

Shreyash Shrivastava
Final Year IT Student
GitHub: https://github.com/shr3yashx/
