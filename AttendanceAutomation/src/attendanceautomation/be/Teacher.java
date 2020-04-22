package attendanceautomation.be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Daniel
 */
public class Teacher{
    private final IntegerProperty id;
    private final StringProperty name;
    
    public Teacher(final int id, final String name){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
    }
    
    public String getName(){
        return name.get();
    }
    
    public void setName(String value){
        name.set(value);
    }
    
    public int getId(){
        return id.get();
    }
    
    public void setId(int value){
        id.set(value);
    }
    
    public StringProperty nameProperty(){
        return name;
    }
    
    public IntegerProperty idProperty(){
        return id;
    }
    
    @Override
    public String toString(){
        return name.get();
    }
}