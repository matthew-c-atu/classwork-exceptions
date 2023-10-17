package bankaccount;

import java.util.Comparator;

public class CompareBankAccount implements Comparator<BankAccount> {
    @Override
    public int compare(BankAccount ba1, BankAccount ba2) {
        // Overriding of compare method to allow sort to sort Bank Accounts by ID.
        if (ba1.getAccountNumber() == ba2.getAccountNumber())
            return 0;
        /*
         Returns -1 if ba1's id is less than ba2's id, and 1 if it is greater than ba2.
         The if statement above catches cases where they are equal.
         */
        return ba1.getAccountNumber() < ba2.getAccountNumber() ? -1 : 1;
    }
}
