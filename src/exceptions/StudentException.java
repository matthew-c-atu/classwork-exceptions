package exceptions;

public class StudentException extends Exception {
   private String msg;

   StudentException(String msg) {
       this.msg = msg;
   }
}
