package application;
	
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class Main extends Application 
{
	private static HashMap<String , BufferedImage> cacheImages;
	@Override
	public void start(Stage primaryStage) 
	{
		ListView<String> listView = new ListView<String>();
        ObservableList<String> items =FXCollections.observableArrayList (cacheImages.keySet());
        listView.setItems(items);

        listView.setCellFactory(param -> new ListCell<String>() 
        {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) 
            {
                super.updateItem(name, empty);
                if (empty) 
                {
                    setText(null);
                    setGraphic(null);
                } 
                else 
                {  
            	    Image image = SwingFXUtils.toFXImage((BufferedImage) cacheImages.get(name), null);
            	    imageView.setImage(image);
            	    imageView.setFitWidth(100);
            	    imageView.setPreserveRatio(true);
            	    imageView.setSmooth(true);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
        VBox box = new VBox(listView);
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	public static void main(String[] args) 
	{
		JsonReader rdr = new JsonReader();
		ImageLoader loader = new ImageLoader();
		if(!new File("C:\\Images").exists())
		{
			ArrayList<String> imagesList = rdr.GetJsonData();
			if(!imagesList.isEmpty())
			{
				loader.DownloadImages(imagesList);
			}
		}
		cacheImages = loader.CacheImages();
		launch(args);
	}
}
