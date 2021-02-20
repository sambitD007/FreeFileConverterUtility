import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    @FXML
    public BorderPane bp;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }


    private void pageLoader(String page) throws Exception{
        Parent root = null;
        System.gc();
        root = FXMLLoader.load(App.class.getResource(page+".fxml"));
        bp.setCenter(root);

    }

    @FXML
    private void loadImageResizer(MouseEvent event) throws Exception{

        pageLoader("FXML_Files/ImageResizer");
    }

    @FXML
    private void loadImageCompressor(MouseEvent event) throws Exception{

        pageLoader("FXML_Files/ImageCompressor");
    }

    @FXML
    private void loadFileConverter(MouseEvent event) throws Exception{

        pageLoader("FXML_Files/FileConverter");
    }
    @FXML
    private void loadLicence(MouseEvent event) throws Exception{

        pageLoader("FXML_Files/licence");
    }
    @FXML
    private void loadDonate(MouseEvent event) throws Exception{

        pageLoader("FXML_Files/donate");
    }
}
