package imageviewerproject;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Daniel
 */
public class Slideshow implements Runnable{
    private final long DELAY = 2;
    private int index = 0;
    private final ImageView imageView;
    private final Label lblFilename;
    private final List<Image> images;
    private final List<String> filenames;
    
    public Slideshow(ImageView imageView, Label label,
            List<Image> images, List<String> filenames){
        this.imageView = imageView;
        this.images = images;
        this.lblFilename = label;
        this.filenames = filenames;
    }
    
    @Override
    public void run(){
        if(!images.isEmpty()){
            try{
                while(true){
                    Platform.runLater(()->{
                            imageView.setImage(images.get(index));
                            lblFilename.setText(filenames.get(index));
                    });
                    index = (index + 1) % images.size();
                    TimeUnit.SECONDS.sleep(DELAY);
                }
            }catch (InterruptedException ex){
                System.out.println("Slideshow was stopped.");
            }
        }
    }
}