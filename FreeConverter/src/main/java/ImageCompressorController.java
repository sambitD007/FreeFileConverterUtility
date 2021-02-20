import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ImageCompressorController implements Initializable{

    public String[] inputFiles;
    public String outputFilePath;

    @FXML
    TextField compress;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

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
        System.out.println(files);
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

    public void compressFiles(MouseEvent mouseEvent) {

        this.outputFilePath = this.outputFilePath + "\\FreeFileConverter_Compressor";
        Path p1 = Paths.get(this.outputFilePath);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Compressing Images");
        alert.show();

        try {

            Files.createDirectories(p1);
            File val = new File(this.outputFilePath);
            Thumbnails.of(this.inputFiles)
                    .scale(1)
                    .outputQuality(Double.parseDouble(compress.getText()) * 0.01)
                    .toFiles(val, Rename.NO_CHANGE);

            alert.close();

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setHeaderText("Image compression successful");
            success.show();
        }catch (Exception e){
            alert.close();
            Alert failure = new Alert(Alert.AlertType.ERROR);
            failure.setHeaderText("Some error occurred. Image resizing failed");
            failure.show();

        }

    }
}
