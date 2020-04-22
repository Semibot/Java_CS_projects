package attendanceautomation.be;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Daniel
 */
public class Presence{
    private IntegerProperty id;
    private IntegerProperty studentId;
    private final ObjectProperty<LocalDate> currentDate;
    private final StringProperty isPresent;
    
    public Presence(int id, int studentId,
            LocalDate currentDate, String isPresent){
        this.id = new SimpleIntegerProperty(id);
        this.studentId = new SimpleIntegerProperty(studentId);
        this.currentDate = new SimpleObjectProperty<>(currentDate);
        this.isPresent = new SimpleStringProperty(isPresent);
    }
    
    public Presence(LocalDate currentDate, String isPresent){
        this.currentDate = new SimpleObjectProperty<>(currentDate);
        this.isPresent = new SimpleStringProperty(isPresent);
    }
    
    public int getId(){
        return id.get();
    }
    
    public void setId(int value){
        id.set(value);
    }
    
    public IntegerProperty idProperty(){
        return id;
    }
    
    public int getStudentId(){
        return studentId.get();
    }
    
    public void setStudentId(int value){
        studentId.set(value);
    }
    
    public IntegerProperty studentIdProperty(){
        return studentId;
    }
    
    public LocalDate getCurrentDate(){
        return currentDateProperty().get();
    }
    
    public void setCurrentDate(LocalDate value){
        currentDateProperty().set(value);
    }
    
    public ObjectProperty<LocalDate> currentDateProperty(){
        return currentDate;
    }
    
    public String getIsPresent(){
        return isPresentProperty().get();
    }
    
    public void setIsPresent(String value){
        isPresentProperty().set(value);
    }
    
    public StringProperty isPresentProperty(){
        return isPresent;
    }
    
    @Override
    public String toString(){
        return currentDate+ ", " +isPresent;
    }
}