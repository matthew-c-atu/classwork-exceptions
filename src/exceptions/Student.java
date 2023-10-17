package exceptions;

public class Student {

    private String name;
    private int mark;

    public Student(String name) {
        this.name = name;
        this.mark = mark;
    }

    public Student(String name, int mark) throws StudentException {
        if(!(2 < name.length() && name.length() < 20) )
            throw new StudentException("Name must be between 2 and 20 characters long");
        this.name = name;

        if (!(0 <= mark && mark <= 100))
            throw new StudentException("Mark must be between 0 and 100");
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws StudentException {
        if(!(2 < name.length() && name.length() < 20) )
            throw new StudentException("Name must be between 2 and 20 characters long");
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) throws StudentException {
        if (!(0 <= mark && mark <= 100))
            throw new StudentException("Mark must be between 0 and 100");
        this.mark = mark;
    }

    public String toString() {
        return String.format("Name: %s\nMark: %d", name, mark);
    }
}
