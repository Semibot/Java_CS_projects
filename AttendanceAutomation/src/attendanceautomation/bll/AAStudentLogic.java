package attendanceautomation.bll;

import attendanceautomation.be.Student;
import attendanceautomation.dal.StudentDAO;
import java.sql.SQLException;
import java.util.List;

/**
 * A simple pass through logic layer class.
 * 
 * @author Daniel
 */
public class AAStudentLogic{
    private StudentDAO sdao;
    
    public AAStudentLogic(){
        sdao = new StudentDAO();
    }
    
    public Student createStudent(Student s) throws SQLException{
        return sdao.createStudent(0, s);
    }
    
    public Student getStudent(int id){
        return sdao.getStudent(id);
    }
    
    public void updateStudent(Student s) throws SQLException{
        sdao.updateStudent(s);
    }
    
    public void deleteStudent(Student s) throws SQLException{
        sdao.deleteStudent(s);
    }
    
    public List<Student> getAllStudents(){
        return sdao.getAllStudents();
    }
}