package Service;

import DAO.DataImplement;
import Model.Student;

import java.util.List;

public class StudentService {
    private DataImplement dataImplement;

    public StudentService() {
        this.dataImplement = new DataImplement();
    }

    public List<Student> getAllStudents() {
        return dataImplement.getAllStudents();
    }
    public void addStudent(Student student){
        dataImplement.addStudent(student);
    }
    public void deleteStudent(int idStudent){
        dataImplement.deleteStudent(idStudent);
    }
    public void updateStudent(Student student){
        dataImplement.updateStudent(student);
    }
    public void importExcel(String filepath){
        dataImplement.importExcel(filepath);
    }
    public void exportExcel(String filepath){
        dataImplement.exportToCsv(filepath);
    }
    public Student getStudentById(int idstudent){
        dataImplement.getStudentById(idstudent);
        return null;
    }
}
