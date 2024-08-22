package DAO;

import Config.DataSource;
import Model.Student;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DataImplement extends DataClose {

    // List all students
    public List<Student> getAllStudents() {
        List<Student> studentInfos = new ArrayList<>();
        String query = "SELECT * FROM student";

        try (Connection connection = DataSource.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Student stu = new Student();
                stu.setIdstudent(resultSet.getInt("idstudent"));
                stu.setStudentname(resultSet.getString("studentname"));
                stu.setGmail(resultSet.getString("gmail"));

                // Handle the zero-date issue
                Date dob = resultSet.getDate("dob");
                if (dob != null && dob.toString().equals("0000-00-00")) {
                    dob = null;  // Set to null or handle as needed
                }
                stu.setDob(dob);

                stu.setAddress(resultSet.getString("address"));
                stu.setGender(resultSet.getString("gender"));

                studentInfos.add(stu);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving student data: " + e.getMessage());
        }

        return studentInfos;
    }

    // Add a student
    public static void addStudent(Student student) {
        String addStudent = "INSERT INTO student (studentname, gmail, dob, address, gender) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DataSource.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addStudent)) {

            preparedStatement.setString(1, student.getStudentname());
            preparedStatement.setString(2, student.getGmail());
            preparedStatement.setDate(3, student.getDob());
            preparedStatement.setString(4, student.getAddress());
            preparedStatement.setString(5, student.getGender());

            preparedStatement.executeUpdate();
            System.out.println("A new student was added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a student
    public void deleteStudent(int idstudent) {
        String deleteStudent = "DELETE FROM student WHERE idstudent = ?";

        try (Connection connection = DataSource.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteStudent)) {

            preparedStatement.setInt(1, idstudent);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A student was deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update student information
    public void updateStudent(Student student) {
        String updateStudent = "UPDATE student SET studentname = ?, gmail = ?, dob = ?, address = ?, gender = ? WHERE idstudent = ?";

        try (Connection connection = DataSource.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateStudent)) {

            preparedStatement.setString(1, student.getStudentname());
            preparedStatement.setString(2, student.getGmail());
            preparedStatement.setDate(3, student.getDob());
            preparedStatement.setString(4, student.getAddress());
            preparedStatement.setString(5, student.getGender());
            preparedStatement.setInt(6, student.getIdstudent());  // Bind the idstudent parameter

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student information was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Import data from Excel (CSV file)
    public void importExcel(String filePath) {
        String insertQuery = "INSERT INTO student (studentname, gmail, dob, address, gender) VALUES (?, ?, ?, ?, ?)";

        // Define the input date format (from CSV) and output date format (for database)
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        outputDateFormat.setLenient(false); // Ensures strict date parsing

        try (Connection connection = DataSource.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
             BufferedReader lineReader = new BufferedReader(new FileReader(filePath))) {

            String lineText;
            lineReader.readLine(); // Skip the header line if present

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(","); // Split by comma

                if (data.length != 5) {
                    System.out.println("Skipping invalid line: " + lineText);
                    continue;
                }

                String studentName = data[0];
                String gmail = data[1];
                Date dob = null;
                try {
                    // Parse the date from the CSV format
                    java.util.Date parsedDate = inputDateFormat.parse(data[2]);
                    // Convert to the desired format
                    String formattedDate = outputDateFormat.format(parsedDate);
                    // Convert to SQL Date
                    dob = Date.valueOf(formattedDate.replace("/", "-")); // Replace slashes with hyphens for SQL
                } catch (ParseException e) {
                    System.out.println("Invalid date format for line: " + lineText);
                    continue; // Skip this record if the date is invalid
                }
                String address = data[3];
                String gender = data[4];

                preparedStatement.setString(1, studentName);
                preparedStatement.setString(2, gmail);
                preparedStatement.setDate(3, dob); // Set the SQL Date
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, gender);

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch(); // Execute batch insert
            System.out.println("Data imported successfully!");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    //Export file excel
    public void exportToCsv(String filePath) {
        String EXPORT_QUERY = "SELECT studentname, gmail, dob, address, gender FROM student";
        String CSV_HEADER = "Student Name,Gmail,Date of Birth,Address,Gender";
        try (Connection connection = DataSource.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EXPORT_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery();
             FileWriter fileWriter = new FileWriter(filePath)) {
            // Write header
            fileWriter.write(CSV_HEADER);
            fileWriter.write("\n");

            // Write data
            while (resultSet.next()) {
                String studentName = resultSet.getString("studentname");
                String gmail = resultSet.getString("gmail");
                String dob = resultSet.getString("dob"); // Ensure date is in the correct format
                String address = resultSet.getString("address");
                String gender = resultSet.getString("gender");

                String line = String.format("%s,%s,%s,%s,%s",
                        studentName, gmail, dob, address, gender);

                fileWriter.write(line);
                fileWriter.write("\n");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}