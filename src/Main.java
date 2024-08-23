import Model.Student;
import Service.StudentService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        Scanner input = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        while (true) {
            printMenu();
            int choice = getValidChoice(input);

            switch (choice) {
                case 1:
                    listStudents(studentService);
                    break;
                case 2:
                    addStudent(studentService, input, dateFormat);
                    break;
                case 3:
                    deleteStudent(studentService, input);
                    break;
                case 4:
                    updateStudent(studentService, input, dateFormat);
                    break;
                case 5:
                    importStudents(studentService, input);
                    break;
                case 6:
                    exportStudents(studentService, input);
                    break;
                case 7:
                    System.out.println("Exiting program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("1. List students");
        System.out.println("2. Add student");
        System.out.println("3. Delete student");
        System.out.println("4. Update student");
        System.out.println("5. Import Excel");
        System.out.println("6. Export Excel");
        System.out.println("7. Exit");
        System.out.print("Enter the number to execute: ");
    }

    private static int getValidChoice(Scanner input) {
        while (true) {
            try {
                return input.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();  // Clear the invalid input
            }
        }
    }

    private static void listStudents(StudentService studentService) {
        List<Student> studentList = studentService.getAllStudents();
        if (studentList.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student stu : studentList) {
                displayStudentDetails(stu);
            }
        }
    }

    private static void addStudent(StudentService studentService, Scanner input, SimpleDateFormat dateFormat) {
        Student newStudent = new Student();
        input.nextLine(); // Clear buffer

        System.out.print("Enter Name: ");
        newStudent.setStudentname(input.nextLine());

        System.out.print("Enter Gmail: ");
        newStudent.setGmail(input.nextLine());

        System.out.print("Enter Dob (yyyy-MM-dd): ");
        String dobStr = input.nextLine();
        try {
            Date dob = dateFormat.parse(dobStr);
            newStudent.setDob(new java.sql.Date(dob.getTime()));
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
            return;
        }

        System.out.print("Enter Address: ");
        newStudent.setAddress(input.nextLine());

        System.out.print("Enter Gender: ");
        newStudent.setGender(input.nextLine());

        studentService.addStudent(newStudent);
        System.out.println("Student added successfully.");
    }

    private static void deleteStudent(StudentService studentService, Scanner input) {
        System.out.print("Enter the ID of the student you want to delete: ");
        int idToDelete = input.nextInt();
        if (studentService.getStudentById(idToDelete) == null) {
            System.out.println("Student not found with ID: " + idToDelete);
        } else {
            studentService.deleteStudent(idToDelete);
            System.out.println("Student deleted successfully.");
        }
    }

    private static void updateStudent(StudentService studentService, Scanner input, SimpleDateFormat dateFormat) {
        System.out.print("Enter the ID of the student you want to update: ");
        int idToUpdate = input.nextInt();
        input.nextLine();  // Consume newline character
        Student existingStudent = studentService.getStudentById(idToUpdate);
        if (existingStudent == null) {
            System.out.println("Student not found with ID: " + idToUpdate);
            return;
        }

        Student updatedStudent = new Student();
        updatedStudent.setIdstudent(idToUpdate);

        System.out.print("Enter new Name: ");
        updatedStudent.setStudentname(input.nextLine());

        System.out.print("Enter new Gmail: ");
        updatedStudent.setGmail(input.nextLine());

        System.out.print("Enter new Dob (yyyy-MM-dd): ");
        String newDobStr = input.nextLine();
        try {
            Date newDob = dateFormat.parse(newDobStr);
            updatedStudent.setDob(new java.sql.Date(newDob.getTime()));
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
            return;
        }

        System.out.print("Enter new Address: ");
        updatedStudent.setAddress(input.nextLine());

        System.out.print("Enter new Gender: ");
        updatedStudent.setGender(input.nextLine());

        studentService.updateStudent(updatedStudent);
        System.out.println("Student updated successfully.");
    }

    private static void importStudents(StudentService studentService, Scanner input) {
        System.out.print("Enter the path to the Excel file (CSV format): ");
        String filePath = input.nextLine();
        studentService.importExcel(filePath);
    }

    private static void exportStudents(StudentService studentService, Scanner input) {
        System.out.print("Enter the path to save the Excel file (CSV format): ");
        String exportFilePath = input.nextLine();
        studentService.exportExcel(exportFilePath);
        System.out.println("Data exported successfully to " + exportFilePath);
    }

    private static void displayStudentDetails(Student student) {
        System.out.println("ID: " + student.getIdstudent());
        System.out.println("Full Name: " + student.getStudentname());
        System.out.println("Gmail: " + student.getGmail());
        System.out.println("Date Of Birth: " + student.getDob());
        System.out.println("Address: " + student.getAddress());
        System.out.println("Gender: " + student.getGender());
        System.out.println("------------------------------");
    }
}
