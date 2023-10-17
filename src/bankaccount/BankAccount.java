package bankaccount;

import bankaccount.exceptions.BankAccountException;
import bankaccount.exceptions.InsufficientFundsException;

public class BankAccount {
    private int accountNumber;
    private double balance;

    BankAccount() {
        this.accountNumber = 0;
        this.balance = 0;
    }
    BankAccount(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance= balance;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(int accountNumber) throws BankAccountException {
        // TODO: Add exception code
        if (accountNumber <= 0)
            throw new BankAccountException("setAccountNumber Error: Account number must be 0 or greater");
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) throws InsufficientFundsException {
        if (balance < 0)
            throw new InsufficientFundsException("SetBalance Error: Balance must be 0 or greater");
        this.balance = balance;
    }

    public void deposit(double amount) throws InsufficientFundsException {
        // Don't allow negative numbers
        if (amount <= 0)
            throw new InsufficientFundsException("Deposit Error: Amount must be greater than 0");
        this.balance += amount;
    }

    public void withdraw(double amount) throws InsufficientFundsException{
        if (this.balance < amount)
            throw new InsufficientFundsException("Withdraw Error: Not enough funds");
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return "Account ID: " + this.accountNumber + " Balance: " + this.balance;
    }


}
