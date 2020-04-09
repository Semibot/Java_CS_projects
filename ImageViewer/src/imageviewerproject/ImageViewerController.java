package imageviewerproject;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * 
 * @author Daniel
 */
public class ImageViewerController implements Initializable{
    private final List<Image> images = new ArrayList<>();
    private final List<String> filenames = new ArrayList<>();
    private int currentImageIndex = 0;
    private ExecutorService executor;
    @FXML Parent root;
    @FXML private Button btnLoad;
    @FXML private Button btnPrevious;
    @FXML private Button btnNext;
    @FXML private Button btnStartSlideshow;
    @FXML private Button btnStopSlideshow;
    @FXML private ImageView imageView;
    @FXML private Label lblFilename;
    
    private void handleBtnLoadAction(ActionEvent e){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images",
            "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
        
        if(!files.isEmpty()){
            files.forEach((File f) ->{
                filenames.add(f.getName());
                images.add(new Image(f.toURI().toString()));
            });
            displayImage();
        }
    }
    
    private void handleBtnPreviousAction(ActionEvent e){
        if(!images.isEmpty()){
            currentImageIndex = 
                    (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }
    }
    
    private void handleBtnNextAction(ActionEvent e){
        if(!images.isEmpty()){
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();
        }
    }
    
    private void handleBtnStartSlideshowAction(ActionEvent e){
        Runnable slideshow = new Slideshow(imageView,
                lblFilename, images, filenames);
        executor = Executors.newSingleThreadExecutor();
        executor.submit(slideshow);
    }
    
    private void handleBtnStopSlideshowAction(ActionEvent e){
        executor.shutdownNow();
    }
    
    private void displayImage(){
        if(!images.isEmpty()){
            imageView.setImage(images.get(currentImageIndex));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        btnLoad.setOnAction((ActionEvent e) ->{
            handleBtnLoadAction(e);
        });
        
        btnPrevious.setOnAction((ActionEvent e) ->{
            handleBtnPreviousAction(e);
        });
        
        btnNext.setOnAction((ActionEvent e) ->{
            handleBtnNextAction(e);
        });
        
        btnStartSlideshow.setOnAction((ActionEvent e) ->{
            handleBtnStartSlideshowAction(e);
        });
        
        btnStopSlideshow.setOnAction((ActionEvent e) ->{
            handleBtnStopSlideshowAction(e);
        });
    }
}