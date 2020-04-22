package attendanceautomation.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

/**
 * A class to connect to the DB.
 * 
 * @author Daniel
 */
public class DBConnector{
    SQLServerDataSource ds = new SQLServerDataSource();
    
    public DBConnector(){
        ds.setDatabaseName("Attendance Automation");
        ds.setUser("CS2018B_3");
        ds.setPassword("CS2018B_3");
        ds.setPortNumber(1433);
        ds.setServerName("10.176.111.31");
    }
}