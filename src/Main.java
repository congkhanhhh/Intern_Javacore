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
            System.out.println("1. List students");
            System.out.println("2. Add student");
            System.out.println("3. Delete student");
            System.out.println("4. Update student");
            System.out.println("5. Import Excel");
            System.out.println("6. Export Excel");
            System.out.println("7. Exit");
            System.out.print("Enter the number to execute: ");
            int choice = input.nextInt();
            input.nextLine();  // Consume newline character

            switch (choice) {
                case 1:
                    List<Student> studentList = studentService.getAllStudents();
                    for (Student stu : studentList) {
                        System.out.println("ID: " + stu.getIdstudent());
                        System.out.println("Full Name: " + stu.getStudentname());
                        System.out.println("Gmail: " + stu.getGmail());
                        System.out.println("Date Of Birth: " + stu.getDob());
                        System.out.println("Address: " + stu.getAddress());
                        System.out.println("Gender: " + stu.getGender());
                        System.out.println("------------------------------");
                    }
                    break;

                case 2:
                    Student newStudent = new Student();
                    System.out.println("Enter Name:");
                    newStudent.setStudentname(input.nextLine());
                    System.out.println("Enter Gmail:");
                    newStudent.setGmail(input.nextLine());
                    System.out.println("Enter Dob (yyyy-MM-dd):");
                    String dobStr = input.nextLine();
                    try {
                        Date dob = dateFormat.parse(dobStr);
                        newStudent.setDob(new java.sql.Date(dob.getTime()));
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
                        break;
                    }
                    System.out.println("Enter Address:");
                    newStudent.setAddress(input.nextLine());
                    System.out.println("Enter Gender:");
                    newStudent.setGender(input.nextLine());

                    studentService.addStudent(newStudent);
                    System.out.println("Student added successfully.");
                    break;

                case 3:
                    System.out.println("Enter the ID of the student you want to delete:");
                    int idToDelete = input.nextInt();
                    studentService.deleteStudent(idToDelete);
                    System.out.println("Student deleted successfully.");
                    break;

                case 4:
                    System.out.println("Enter the ID of the student you want to update:");
                    int idToUpdate = input.nextInt();
                    input.nextLine();  // Consume newline character

                    Student updatedStudent = new Student();
                    updatedStudent.setIdstudent(idToUpdate);

                    System.out.println("Enter new Name:");
                    updatedStudent.setStudentname(input.nextLine());
                    System.out.println("Enter new Gmail:");
                    updatedStudent.setGmail(input.nextLine());
                    System.out.println("Enter new Dob (yyyy-MM-dd):");
                    String newDobStr = input.nextLine();
                    try {
                        Date newDob = dateFormat.parse(newDobStr);
                        updatedStudent.setDob(new java.sql.Date(newDob.getTime()));
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
                        break;
                    }
                    System.out.println("Enter new Address:");
                    updatedStudent.setAddress(input.nextLine());
                    System.out.println("Enter new Gender:");
                    updatedStudent.setGender(input.nextLine());

                    studentService.updateStudent(updatedStudent);
                    System.out.println("Student updated successfully.");
                    break;

                case 5:
                    // Import students from Excel (CSV)
                    System.out.print("Enter the path to the Excel file (CSV format): ");
                    String filePath = input.nextLine();
                    studentService.importExcel(filePath);
                    break;
                case 6:
                    //Export students data to Excel (CSV)
                    System.out.print("Enter the path to save the Excel file (CSV format): ");
                    String exportFilePath = input.nextLine();
                    studentService.exportExcel(exportFilePath);
                    System.out.println("Data exported successfully to " + exportFilePath);
                    break;
                case 7:
                    System.out.println("Exiting program.");
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}