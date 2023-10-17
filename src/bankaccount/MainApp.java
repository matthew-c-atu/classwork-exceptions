package bankaccount;
import bankaccount.exceptions.InvalidAccountNumberException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        String filename = "bankaccounts.csv";
        String option = "_";
        BankAccountApp ba = new BankAccountApp();
        // Read bankaccounts csv file into memory
        ba.loadFromDatabase(filename);

        while (!(option.equals("quit") || option.equals("q") || option.equals("q!") || option.equals("quit!"))) {
            try {

                switch(option) {
                    // Fall through the "_" case to prompt on program startup
                    case "_":
                        ba.welcome();
                    case "back":
                        option = ba.promptForInput();
                        break;
                }
//                System.out.println("option is " + option);

                // State Machine
                    switch(option) {
                        case "a":
                        case "add":
                            // Enter bank account menu
                            do {
                                option = ba.promptForNewAccount();
                            } while (option.equals("continue"));

                           break;
                        case "g":
                        case "get":
                            do {
                                option = ba.findAccount();
                            } while (option.equals("continue"));
                            break;
                        case "ls":
                        case "list":
                            option = ba.listAccounts();
                            break;
                        case "h":
                        case "help":
                            // Print help
                            option = ba.help();
                            break;
                        case "w":
                        case "write":
                            System.out.println("Data written to file " + filename);
                            ba.writeToDatabase(filename);
                            option = "back";
                            break;
                        case "q":
                        case "quit":
                        case "q!":
                        case "quit!":
                            break;
                        default:
                            option = "back";
                            break;
                    }
            }

            catch(InvalidAccountNumberException e) {
                System.err.println(e.getMessage());
            }
            catch(NoSuchElementException e) {
                option = "q";
            }
            catch(Exception e) {
                System.err.println("Unknown Error");
            }
        }

        if(option.equals("q") || option.equals("quit")) {
            System.out.println("Data written to file " + filename);
            ba.writeToDatabase(filename);
        }
    }
}
