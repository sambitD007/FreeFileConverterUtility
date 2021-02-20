
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;




public class licence {


    public void backButton(MouseEvent mouseEvent) throws Exception{
        Parent root = FXMLLoader.load(App.class.getResource("FXML_Files/HomeScreen.fxml"));
        App.primaryStage.setScene(new Scene(root));

    }

}


