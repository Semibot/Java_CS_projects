package attendanceautomation.dal;

import attendanceautomation.be.Student;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to create, read, update and delete students
 * in the database.
 * 
 * @author Daniel
 */
public class StudentDAO{
    private DBConnector connector;
    
    public StudentDAO(){
        connector = new DBConnector();
    }
    
    //Crud Create
    public Student createStudent(int id, Student s) throws SQLException{
        try(Connection conn = connector.ds.getConnection()){
            String sql = "INSERT INTO Student(name) VALUES(?)";
            PreparedStatement stmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, s.getName());
            
            int createdRows = stmt.executeUpdate();
            
            if(createdRows == 0){
                throw new SQLException("Creating student failed, no rows created.");
            }
            
            try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    s.setId((int)generatedKeys.getLong(1));
                }else {
                    throw new SQLException("Creating student failed, no ID obtained");
                }
            }
        }
        return s;
    }
    
    //cRud Read
    public Student getStudent(int id){
        try(Connection conn = connector.ds.getConnection()){
            PreparedStatement pstmt =
                    conn.prepareStatement("SELECT * FROM Student WHERE id=?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int ids = rs.getInt("id");
                String name = rs.getString("name");
                Student s = new Student(ids, name);
                return s;
            }
        }catch(SQLServerException ex){
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }catch(SQLException ex){
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    //crUd Update
    public void updateStudent(Student s) throws SQLException{
        try(Connection conn = connector.ds.getConnection()){
            String sql = "UPDATE Student SET name=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, s.getName());
            stmt.setInt(2, s.getId());
            
            int updatedRows = stmt.executeUpdate();
            
            if(updatedRows == 0){
                throw new SQLException("Updating student failed, no rows updated.");
            }
            
            try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    s.setId((int) generatedKeys.getLong(1));
                }else {
                    throw new SQLException("Updating a student failed, no student updated.");
                }
            }
        }
    }
    
    //cruD Delete
    public void deleteStudent(Student s) throws SQLException{
        try(Connection conn = connector.ds.getConnection()){
            String sql = "DELETE FROM Student WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, s.getId());
            
            int deletedRows = stmt.executeUpdate();
            
            if(deletedRows == 0){
                throw new SQLException("Deleting a student failed, no rows deleted.");
            }
            
            try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    s.setId((int) generatedKeys.getLong(1));
                }else {
                    throw new SQLException("Deleting a student failed, no ID obtained.");
                }
            }
        }
    }
    
    public List<Student> getAllStudents(){
        List<Student> studentList = new ArrayList();
        try(Connection conn = connector.ds.getConnection()){
            String sqlStatement = "SELECT * FROM Student";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next()){
                int ids = rs.getInt("id");
                String name = rs.getString("name");
                Student s = new Student(ids, name);
                studentList.add(s);
            }
        }catch(SQLServerException ex){
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }catch(SQLException ex){
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studentList;
    }
}