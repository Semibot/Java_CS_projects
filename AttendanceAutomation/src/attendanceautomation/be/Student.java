package attendanceautomation.be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Daniel
 */
public class Student{
    private final IntegerProperty id;
    private final StringProperty name;
    
    public Student(final int id, final String name){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
    }
    
    public final String getName(){
        return name.get();
    }
    
    public void setName(final String name){
        this.name.set(name);
    }
    
    public final StringProperty nameProperty(){
        return name;
    }
    
    public final int getId(){
        return id.get();
    }
    
    public void setId(final int id){
        this.id.set(id);
    }
    
    public final IntegerProperty idProperty(){
        return id;
    }
    
    @Override
    public String toString(){
        return name.get();
    }
}