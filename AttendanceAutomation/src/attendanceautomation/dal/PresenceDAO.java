package attendanceautomation.dal;

import attendanceautomation.be.Presence;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class PresenceDAO{
    private DBConnector connector;
    
    public PresenceDAO(){
        connector = new DBConnector();
    }
    
    public Presence createPresence(int id, Presence p) throws SQLException{
        try(Connection conn = connector.ds.getConnection()){
            String sql = "INSERT INTO Presence(studentId, "
                    +"currentDate, isPresent) VALUES(?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, p.getStudentId());
            pstmt.setDate(2, Date.valueOf(p.getCurrentDate()));
            pstmt.setString(3, p.getIsPresent());
            
            int createdRows = pstmt.executeUpdate();
            
            if(createdRows == 0){
                throw new SQLException("Creating presence failed, no rows created.");
            }
            
            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    p.setId((int)generatedKeys.getLong(1));
                }else {
                    throw new SQLException("Creating presence failed, no ID obtained.");
                }
            }
        }
        return p;
    }
    
    public Presence getPresence(int id){
        try(Connection conn = connector.ds.getConnection()){
            PreparedStatement pstmt =
                   conn.prepareStatement("SELECT * FROM Presence WHERE id=?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int ids = rs.getInt("id");
                int studentId = rs.getInt("studentId");
                LocalDate currentDate = LocalDate.class.cast(rs.getDate("currentDate"));
                String isPresent = rs.getString("isPresent");
                Presence p = new Presence(ids, studentId,
                    currentDate, isPresent);
                return p;
            }
        }catch (SQLServerException ex){
            Logger.getLogger(PresenceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }catch(SQLException ex){
            Logger.getLogger(PresenceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void updatePresence(Presence p) throws SQLException{
        try(Connection conn = connector.ds.getConnection()){
            String sql = "UPDATE Presence SET studentId=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, p.getStudentId());
            pstmt.setInt(2, p.getId());
            
            int updatedRows = pstmt.executeUpdate();
            
            if(updatedRows == 0){
                throw new SQLException("Updating presence failed, no rows updated.");
            }
            
            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    p.setId((int)generatedKeys.getLong(1));
                }else {
                    throw new SQLException("Updating Presence failed, presence not updated.");
                }
            }
        }
    }
    
    public void deletePresence(Presence p) throws SQLException{
        try(Connection conn = connector.ds.getConnection()){
            String sql = "DELETE FROM Presence WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, p.getId());
            
            int deletedRows = pstmt.executeUpdate();
            
            if(deletedRows == 0){
                throw new SQLException("Deleting presence failed, no rows deleted.");
            }
            
            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    p.setId((int)generatedKeys.getLong(1));
                }else {
                    throw new SQLException("Deleting presence failed, no ID obtained.");
                }
            }
        }
    }
    
    public List<Presence> getAllPresence(){
        List<Presence> presence = new ArrayList();
        try(Connection conn = connector.ds.getConnection()){
            String sql = "SELECT * FROM Presence";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int ids = rs.getInt("id");
                int studentId = rs.getInt("studentId");
                LocalDate currentDate = LocalDate.class.cast(rs.getDate("currentDate"));
                String isPresent = rs.getString("isPresent");
                Presence p = new Presence(ids, studentId,
                        currentDate, isPresent);
                presence.add(p);
            }
        }catch(SQLServerException ex){
            Logger.getLogger(PresenceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }catch(SQLException ex){
            Logger.getLogger(PresenceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return presence;
    }
}