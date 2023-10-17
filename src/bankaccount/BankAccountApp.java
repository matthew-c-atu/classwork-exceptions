package bankaccount;

import bankaccount.exceptions.AccountExistsException;
import bankaccount.exceptions.InsufficientFundsException;
import bankaccount.exceptions.InvalidAccountNumberException;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankAccountApp {
    private ArrayList<BankAccount> accounts;
    BankAccountApp() {
        this.accounts = new ArrayList<BankAccount>();
    }

    public boolean loadFromDatabase(String filename) {
        if (!filename.endsWith(".csv"))
            return false;

        String line;
        String pattern = "[0-9]*,[0-9]+.?[0-9]*";
        FileReader fin;
        Scanner sc;

        int temp_i = 0;
        double temp_d = 1;
        try {
            sc = new Scanner(new File(filename));
            while(sc.hasNext(pattern)){
                line = sc.next(pattern);

                String[] lines = line.split(",");
//                System.out.println(lines[0] + "\t" + lines[1]);
                temp_i = Integer.parseInt(lines[0]);
                temp_d = Double.parseDouble(lines[1]);
                accounts.add(new BankAccount(temp_i, temp_d));
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found");
            return false;
        }
        return true;
    }

    public boolean writeToDatabase(String filename) {
        if (!filename.endsWith(".csv"))
            return false;
        if (this.accounts.isEmpty())
            return false;

        try {
            FileWriter writer = new FileWriter(filename);
            this.accounts.sort(new CompareBankAccount());

            for (BankAccount ba : this.accounts) {
                writer.write(ba.getAccountNumber() + "," + String.format("%.2f", ba.getBalance()) + "\n");
            }
            writer.close();
        }

        catch (IOException e) {
                System.err.println("Could not write to file");
                return false;
        }
        return true;
    }
    void welcome() {
        System.out.println("Welcome to Super Bank Account App.");
        help();
    }

    String help() {
        System.out.println("\nOptions: " +
                "\nUse the following from the main menu:" +
                "\na , add - Add a bank account" +
                "\ng, get - Get the details of a specific bank account." +
                "\nls , list - List all bank accounts." +
//                "\n[n] - list details of bank account n"

                "\n\nUse the following from any menu: " +
                "\nh , help - Print help" +
                "\nb , back - Go back" +
                "\nw , write - Save changes to database" +
                "\nq , quit - Quit program and saves changes to database)" +
                "\nq! , quit! - Quit program, discard changes)\n"
        );

        return "back";

    }
    String promptForInput() {
        String option = "_";

        Scanner sc = new Scanner(System.in);
        System.out.println("---Main Menu---\nPlease enter an option: ");
        option = sc.next();

        return option.toLowerCase();
    }
    public String promptForNewAccount() throws InvalidAccountNumberException, InsufficientFundsException {
        boolean id_valid = false;
        boolean balance_valid = false;
        int temp_n = 0;
        double temp_d = 0;
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the account number for the new account followed by the balance" + "\ne.g. 20 4000.99");

        try {
            temp_n = sc.nextInt();
            if(temp_n < 1)
                throw new InvalidAccountNumberException("Account number must be greater than 0");
            id_valid = true;
        }
        catch (InputMismatchException e) {
            String next = sc.next();
            String input = "";
            if (! (input = handleUnexpectedInput(next)).equals("continue")) {
                return input;
            }
            System.err.println("Account number must be of type int and greater than 0");
            return "continue";
        }
        try {
            temp_d = sc.nextDouble();
            if(temp_d < 0)
                throw new InsufficientFundsException("Balance must be at least 0");
            balance_valid = true;
        }
        catch (InputMismatchException e) {
            String next = sc.next();;
            String input = "";
            if (! (input = handleUnexpectedInput(next)).equals("continue"))
                return input;
            System.err.println("Balance must be of type double and 0 or greater");
            return "continue";
        }
        try {
            if (id_valid && balance_valid)
                this.addBankAccount(temp_n, temp_d);

        }

        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return "continue";
    }

    private String handleUnexpectedInput(String s) {
           switch (s.toLowerCase()) {
               case "q":
               case "quit":
                    return "quit";
               case "q!":
               case "quit!":
                   return "quit!";
               case "b":
               case "back":
                   return "back";
               case "g":
               case "get":
                   return "get";
               case "h":
               case "help":
                   return "help";
               case "w":
               case "write":
                   return "write";
               default:
                  return "continue";
           }
    }
    private void addBankAccount(int accountNumber, double balance) throws AccountExistsException {

            for (BankAccount bb : accounts) {
                if(bb.getAccountNumber() == accountNumber)
                    throw new AccountExistsException("Error - Account number already exists");
            }
            BankAccount temp = new BankAccount(accountNumber, balance);
            this.accounts.add(temp);
            System.out.println("Added Account number " + temp.getAccountNumber() + " with Balance " + String.format("%.2f", temp.getBalance()));
    }

    public String listAccounts() {
        if (!this.accounts.isEmpty()) {
            this.accounts.sort(new CompareBankAccount());
            for (BankAccount ba : this.accounts)
                System.out.println(ba);
        }
        else {
            System.out.println("No accounts exist yet");
        }
        return "back";
    }
    public String findAccount() throws InvalidAccountNumberException {

        if (!this.accounts.isEmpty()) {
            int id = 0;
            System.out.println("Enter an account number to get its details: ");
            Scanner sc = new Scanner(System.in);
            try {
                id = sc.nextInt();
            }
            catch (InputMismatchException e) {
                String input = sc.next();
                return handleUnexpectedInput(input);
            }
            if (id < 1)
                throw new InvalidAccountNumberException("Account number must be greater than 0");
            Comparator<BankAccount> idComp = new CompareBankAccount();
            this.accounts.sort(idComp);
            // Perform binary search
            try {
                System.out.println(this.accounts.get(binarySearch(this.accounts, id)));
            }
            catch (Exception e) {
                System.err.println("Bank account " + id + " doesn't exist");
            }
        }
        else {
            System.out.println("No accounts exist yet");
        }
        return "continue";
    }

    private int binarySearch(ArrayList<BankAccount> list, int id) {
       int low = 0;
       int high = list.size();
       int mid;
       BankAccount guess;

       while (low <= high) {
            mid = (low + high) / 2;
            guess = list.get(mid);

            if (guess.getAccountNumber() == id)
                return mid;

            if (guess.getAccountNumber() > id)
                high = mid -1;
            else
                low = mid + 1;
       }

       return 0;
    }





}
