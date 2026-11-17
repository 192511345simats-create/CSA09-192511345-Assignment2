import java.util.HashSet;
import java.util.Set;

// Demonstrates the equals/hashCode contract violation before the correction.
class StudentWithoutHashCode {
    final String regNo;
    StudentWithoutHashCode(String regNo) { this.regNo = regNo; }
    @Override public boolean equals(Object o) {
        return o instanceof StudentWithoutHashCode
            && regNo.equals(((StudentWithoutHashCode) o).regNo);
    }
    // Deliberately does not override hashCode().
}
public class AttendanceOriginal {
    public static void main(String[] args) {
        Set<StudentWithoutHashCode> present = new HashSet<>();
        present.add(new StudentWithoutHashCode("192311001"));
        present.add(new StudentWithoutHashCode("192311002"));
        present.add(new StudentWithoutHashCode("192311001"));
        System.out.println("Set size without hashCode override: " + present.size());
        System.out.println("This intentionally broken version violates the equals/hashCode contract.");
    }
}