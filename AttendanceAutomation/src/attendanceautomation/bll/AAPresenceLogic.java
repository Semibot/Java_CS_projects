package attendanceautomation.bll;

import attendanceautomation.be.Presence;
import attendanceautomation.dal.PresenceDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class AAPresenceLogic{
    private PresenceDAO pdao;
    
    public AAPresenceLogic(){
        pdao = new PresenceDAO();
    }
    
    public Presence createPresence(Presence p) throws SQLException{
        return pdao.createPresence(0, p);
    }
    
    public Presence getPresence(int id){
        return pdao.getPresence(id);
    }
    
    public void updatePresence(Presence p) throws SQLException{
        pdao.updatePresence(p);
    }
    
    public void deletePresence(Presence p) throws SQLException{
        pdao.deletePresence(p);
    }
    
    public List<Presence> getAllPresence(){
        return pdao.getAllPresence();
    }
}