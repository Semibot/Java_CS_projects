package attendanceautomation.gui.controller;

import attendanceautomation.be.Presence;
import attendanceautomation.gui.model.AAModel;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Daniel
 */
public class AttendanceListController implements Initializable{
    @FXML private TableView<Presence> attendanceListTableView;
    @FXML private TableColumn<Presence, LocalDate> dateColumn;
    @FXML private TableColumn<Presence, String> attendanceColumn;
    private AttendanceController parent;
    private AAModel aam;
    
    public AttendanceListController(){
        aam = new AAModel();
    }
    
    public void setParentWindowController(AttendanceController parent){
        this.parent = parent;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("currentDate"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory<>("isPresent"));
        attendanceListTableView.setItems(aam.getPresenceData());
    }
}