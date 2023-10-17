package exceptions;

import java.util.IllegalFormatConversionException;

public class TestException {
    public static void main(String[] args) {
        Student ss = new Student("John");
        String name = "Foo";
        try {
            //        System.out.println(name.charAt(20));
            //
            ss.setMark(100);
            System.out.println(ss.toString());
//            System.out.printf("Name is: %d", name);
        }
        catch (IllegalFormatConversionException e) {
            System.out.println("ERROR: Formatting incorrect");
        }
        catch (StringIndexOutOfBoundsException e) {
            System.out.println("ERROR: String index out of bounds");
        }
        catch (Exception e) {
                System.out.println("ERROR: Unknown Error");

        }
    }
}
