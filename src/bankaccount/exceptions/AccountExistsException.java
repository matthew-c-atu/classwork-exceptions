package bankaccount.exceptions;

public class AccountExistsException extends Exception{
    public AccountExistsException(String msg) {
        super(msg);
    }
}
