package attendanceautomation.gui.controller;

import attendanceautomation.be.Student;
import attendanceautomation.gui.model.AAModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class to create a login page.
 * 
 * @author Daniel
 */
public class LoginPageController implements Initializable{
    @FXML private JFXPasswordField passwordField;
    @FXML private JFXComboBox loginPageComboBox;
    @FXML private ImageView imageView;
    @FXML private JFXButton loginBtn;
    @FXML private Label loginPageLbl;
    private AAModel aam;
    
    public LoginPageController(){
        aam = new AAModel();
    }
    
    public int getStudentId(){
        List<Student> list = aam.getAllStudents();
        Map<Integer, String> map = new HashMap<>();
        String studentName = String.valueOf(loginPageComboBox.getValue());
        
        for(Student s : list){
            map.put(s.getId(), s.getName());
            Integer key = s.getId();
            String value = s.getName();
            if(value.equals(studentName)){
                return key;
            }
        }
        return 0;
    }
    
    @FXML
    private void handleLoginBtnAction(ActionEvent e) throws IOException{
        String password = passwordField.getText();
        String nameField = String.valueOf(loginPageComboBox.getValue());
        
        if(!nameField.isEmpty() && password.equals("student")){
            Stage attendanceWindow = new Stage();
            attendanceWindow.initModality(Modality.NONE);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/attendanceautomation/gui/view/Attendance.fxml"));
            Parent root = loader.load();
            AttendanceController ac = loader.getController();
            ac.setParentWindowController(this);
            ac.setText(String.valueOf(loginPageComboBox.getValue()));
            Stage lp = (Stage)loginBtn.getScene().getWindow();
            lp.close();
            
            Scene scene = new Scene(root);
            attendanceWindow.setTitle("Attendance");
            attendanceWindow.setScene(scene);
            attendanceWindow.showAndWait();
        }else {
            loginPageLbl.setText("Wrong username and/or password");
        }
    }
    
    private void createComboBoxItems(){
        ObservableList<Student> sList =
                FXCollections.observableArrayList(
                aam.getAllStudents());
        loginPageComboBox.getItems().addAll(sList);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try{
            Path dir = FileSystems.getDefault().getPath("./src/images/Attendance.png");
            Image image = new Image(dir.toUri().toURL().toExternalForm());
            imageView.setImage(image);
            createComboBoxItems();
        }catch (MalformedURLException ex){
            ex.printStackTrace();
        }
    }
}