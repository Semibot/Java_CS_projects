package attendanceautomation.gui.controller;

import attendanceautomation.be.Presence;
import attendanceautomation.gui.model.AAModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * A class that creates the attendance view.
 *
 * @author Daniel
 */
public class AttendanceController implements Initializable{
    @FXML private Label attendanceLbl;
    @FXML private Label attendanceSubmitLbl;
    @FXML private JFXButton attendanceBtn;
    @FXML private JFXDatePicker attendanceDatePicker;
    @FXML private ComboBox attendanceComboBox;
    private LoginPageController parent;
    private String myText;
    private AAModel aam;
    
    public AttendanceController(){
        aam = new AAModel();
    }
    
    @FXML
    private void handleSubmitButtonAction(ActionEvent e) throws IOException, SQLException{
        LocalDate date = attendanceDatePicker.getValue();
        String present = String.valueOf(attendanceComboBox.getValue());
        
        if(date.equals(LocalDate.now()) && !present.isEmpty()){
            Presence p = new Presence(0, parent.getStudentId(),
                    date, present);
            aam.createPresence(p);
            
            Stage attendanceListWindow = new Stage();
            attendanceListWindow.initModality(Modality.NONE);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/attendanceautomation/gui/view/AttendanceList.fxml"));
            Parent root = loader.load();
            AttendanceListController alc = loader.getController();
            alc.setParentWindowController(this);
            Stage al = (Stage)attendanceBtn.getScene().getWindow();
            al.close();
            
            Scene scene = new Scene(root);
            attendanceListWindow.setTitle("Student Attendance List");
            attendanceListWindow.setScene(scene);
            attendanceListWindow.showAndWait();
        }else {
            aam.createPresence(null);
            attendanceSubmitLbl.setText("Something went wrong!");
        }
    }
    
    public void setText(String s){
        myText = s;
        fillLabel();
    }
    
    @FXML
    private void fillLabel(){
        attendanceLbl.setText("Welcome " +myText);
    }
    
    private void setCurrentDateDatePicker(){
        final Callback<DatePicker, DateCell>
                dayCellFactory = new Callback<DatePicker, DateCell>(){
            @Override
            public DateCell call(final DatePicker datePicker){
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty){
                        super.updateItem(item, empty);
                        if(item.isBefore(attendanceDatePicker.getValue().minusDays(0))){
                            setDisable(true);
                            setStyle("-fx-background-color: #EEEEEE;");
                        }
                        if(item.isAfter(attendanceDatePicker.getValue().plusDays(0))){
                            setDisable(true);
                            setStyle("-fx-background-color: #EEEEEE;");
                        }
                    }
                };
            }
        };
        attendanceDatePicker.setDayCellFactory(dayCellFactory);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        setCurrentDateDatePicker();
        attendanceDatePicker.setValue(LocalDate.now());
        attendanceDatePicker.setShowWeekNumbers(true);
        createComboBoxItems();
    }
    
    private void createComboBoxItems(){
        ObservableList<String> aList =
                FXCollections.observableArrayList(
                "Present", "Absent");
        attendanceComboBox.getItems().addAll(aList);
    }
    
    public void setParentWindowController(LoginPageController parent){
        this.parent = parent;
    }
}