import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ImageResizerController implements Initializable {

    String[] inputFiles;
    String outputFilePath;


    ObservableList<String> rotate = FXCollections.observableArrayList("0","90","180","270");
    ObservableList<String> format = FXCollections.observableArrayList("jpg", "png", "gif","bmp","wbmp");
    ObservableList<String> quality = FXCollections.observableArrayList("1","0.9","0.8","0.7","0.6","0.5");

    @FXML
    ComboBox rotateList;

    @FXML
    ComboBox formatList;

    @FXML
    ComboBox qualityList;

    @FXML
    Button  selectFile;

    @FXML
    Button outputDirectory;

    @FXML
    Button createPictures;

    @FXML
    TextField height;

    @FXML
    TextField width;

    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        rotateList.setItems(rotate);
        formatList.setItems(format);
        qualityList.setItems(quality);
        rotateList.getSelectionModel().selectFirst();
        formatList.getSelectionModel().selectFirst();
        qualityList.getSelectionModel().selectFirst();
    }

    public void backButton(MouseEvent mouseEvent) throws Exception{
        Parent root = FXMLLoader.load(App.class.getResource("FXML_Files/HomeScreen.fxml"));
        App.primaryStage.setScene(new Scene(root));

    }

    public void setSelectFile(MouseEvent mouseEvent){
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.showOpenDialog(null);
        File[] files = chooser.getSelectedFiles();
        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getAbsolutePath();
        }
        this.inputFiles = names;

    }

    public void setOutputDirectory(MouseEvent mouseEvent){
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Output File Directory");
            File selectedFolder = directoryChooser.showDialog(null);
            this.outputFilePath = selectedFolder.getAbsolutePath();
        }catch (NullPointerException e){
            Alert failure = new Alert(Alert.AlertType.ERROR);
            failure.setHeaderText("No output path selected");
            failure.show();
        }

    }

    public void generatePictures(MouseEvent mouseEvent) {

        this.outputFilePath = this.outputFilePath + "\\FreeFileConverter";
        Path p1 = Paths.get(this.outputFilePath);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Resizing images");
        alert.show();

        try {
            Files.createDirectories(p1);
            ImageResizer val = new ImageResizer();
            val.convert(this.inputFiles, this.outputFilePath, Integer.parseInt(height.getText())
                    , Integer.parseInt(width.getText()), Double.parseDouble(rotateList.getValue().toString())
                    , formatList.getValue().toString(), Double.parseDouble(qualityList.getValue().toString()));
            alert.close();

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setHeaderText("Image resizing successful");
            success.show();
        }catch (Exception e){
            alert.close();
            Alert failure = new Alert(Alert.AlertType.ERROR);
            failure.setHeaderText("Some error occurred. Image resizing failed");
            failure.show();

        }



    }


}
