package Validate;

import Model.Student;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Validate {
    // Validate the listing of students
    public static boolean validateListStudents(List<Student> students) {
        if (students == null || students.isEmpty()) {
            System.out.println("No students found.");
            return false;
        }
        return true;
    }

    // Validate the addition of a new student
    public static boolean validateAddStudent(Student student) {
        if (student.getStudentname() == null || student.getStudentname().isEmpty()) {
            System.out.println("Student name cannot be empty.");
            return false;
        }
        if (!isValidEmail(student.getGmail())) {
            System.out.println("Invalid email format.");
            return false;
        }
        if (!isValidDate(student.getDob())) {
            System.out.println("Invalid date of birth. The format should be yyyy-MM-dd.");
            return false;
        }
        if (student.getAddress() == null || student.getAddress().isEmpty()) {
            System.out.println("Address cannot be empty.");
            return false;
        }
        if (student.getGender() == null || !(student.getGender().equalsIgnoreCase("Male") || student.getGender().equalsIgnoreCase("Female"))) {
            System.out.println("Invalid gender. Please enter Male or Female.");
            return false;
        }
        return true;
    }

    // Validate updating a student
    public static boolean validateUpdateStudent(int id, Student student) {
        if (id <= 0) {
            System.out.println("Invalid student ID.");
            return false;
        }
        return validateAddStudent(student); // Reuse validation from addStudent
    }

    // Validate deleting a student
    public static boolean validateDeleteStudent(int id) {
        if (id <= 0) {
            System.out.println("Invalid student ID.");
            return false;
        }
        return true;
    }

    // Validate the file path for import/export operations
    public static boolean validateFilePath(String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            System.out.println("The directory does not exist: " + file.getParentFile());
            return false;
        }
        if (!file.getParentFile().canWrite()) {
            System.out.println("Cannot write to the directory: " + file.getParentFile());
            return false;
        }
        return true;
    }

    // Validate importing students from Excel (CSV)
    public static boolean validateImportExcel(String filePath) {
        if (filePath == null || !filePath.endsWith(".csv")) {
            System.out.println("Invalid file format. Please provide a CSV file.");
            return false;
        }
        return validateFilePath(filePath);
    }

    // Validate exporting students to Excel (CSV)
    public static boolean validateExportExcel(String filePath) {
        if (filePath == null || !filePath.endsWith(".csv")) {
            System.out.println("Invalid file format. The export should be a CSV file.");
            return false;
        }
        return validateFilePath(filePath);
    }

    // Helper method to validate email format
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex);
    }

    // Helper method to validate date format
    private static boolean isValidDate(java.sql.Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.toString());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
