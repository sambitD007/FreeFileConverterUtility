import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class FileConverter implements Initializable{

    public String[] inputFiles;
    public String outputFilePath;

    ObservableList<String> convert = FXCollections.observableArrayList("PDF to JPG");

    @FXML
    ComboBox conversionType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        conversionType.setItems(convert);

    }

    public void backButton(MouseEvent mouseEvent) throws Exception{
        Parent root = FXMLLoader.load(App.class.getResource("FXML_Files/HomeScreen.fxml"));
        App.primaryStage.setScene(new Scene(root));

    }

    public void setSelectFile(MouseEvent mouseEvent){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileFilter() {

            public String getDescription() {
                return "JPG Images (*.jpg)";
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".pdf") ;
                }
            }
        });
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
        try{
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

    public void convertFiles(MouseEvent mouseEvent) {

        this.outputFilePath = this.outputFilePath + "\\FreeFileConverter";
        Path p1 = Paths.get(this.outputFilePath);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resizing images");
        alert.setHeaderText("Resizing images");
        alert.show();

        try {
            Files.createDirectories(p1);

//            File val = new File(this.outputFilePath);
//            if (conversionType.getValue().toString() == "PDF to JPG") {
//                for (String filename1 : inputFiles) {
//                    PDDocument document = PDDocument.load(new File(filename1));
//                    PDFRenderer pdfRenderer = new PDFRenderer(document);
//                    FileNameExtractor x = new FileNameExtractor();
//
//                    String fileName = x.getName(filename1, ".pdf");
//                    String fileExtension = "jpg";
//                    for (int page = 0; page < document.getNumberOfPages(); ++page) {
//                        File outPutFile = new File(this.outputFilePath + "\\" + fileName + "_" + (page + 1) + "." + fileExtension);
//                        BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
//                        ImageIO.write(bim, fileExtension, outPutFile);
//                    }
//                    document.close();
//                }
//            } else
            if (conversionType.getValue().toString() == "JPG to PDF") {
                for (String filename1 : inputFiles) {
                    FileNameExtractor x = new FileNameExtractor();
                    String fileName = x.getName(filename1, ".jpg");

                    PDDocument document = new PDDocument();
                    document.addPage(new PDPage());
                    document.save(this.outputFilePath + "\\" + fileName + ".pdf");
                    document.close();

                    String inputFile = this.outputFilePath + "\\" + fileName + ".pdf";
                    AddImageToPDF app = new AddImageToPDF();
                    app.createPDFFromImage(inputFile, filename1, inputFile);


                }
            }
            alert.close();

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Success");
            success.setHeaderText("File conversion successful");
            success.show();
        }catch (Exception e){
            alert.close();
            Alert failure = new Alert(Alert.AlertType.ERROR);
            failure.setTitle("Failed");
            failure.setHeaderText("Some error occurred. Image resizing failed");
            failure.show();

        }



    }
}
