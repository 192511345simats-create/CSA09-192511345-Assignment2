import java.util.*;

class Student {
    private final String regNo;
    private final String name;
    Student(String regNo, String name) { this.regNo = regNo; this.name = name; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student other = (Student) o;
        return regNo.equals(other.regNo);
    }
    @Override public int hashCode() { return Objects.hash(regNo); }
    @Override public String toString() { return regNo + " " + name; }
}

public class Attendance {
    private static int scanCount(Student... scans) { Set<Student> set = new HashSet<>(Arrays.asList(scans)); return set.size(); }
    public static void main(String[] args) {
        System.out.println("Test 1 (same student scanned twice): " + scanCount(
            new Student("192311001", "Arun"), new Student("192311001", "Arun")) + " unique");
        System.out.println("Test 2 (three scans, one repeated): " + scanCount(
            new Student("192311001", "Arun"), new Student("192311002", "Divya"), new Student("192311001", "Arun")) + " unique");
        System.out.println("Test 3 (same ID, different display name): " + scanCount(
            new Student("192311003", "Kavi"), new Student("192311003", "Kavitha"), new Student("192311004", "Ravi")) + " unique");
        Set<Student> present = new HashSet<>();
        present.add(new Student("192311001", "Arun"));
        present.add(new Student("192311002", "Divya"));
        present.add(new Student("192311001", "Arun"));
        System.out.println("Unique students: " + present.size());
    }
}