import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

public class ImageCompressor {
    public  void CompressionAlgorithm(String inputFilePath,String outputFilePath,String extension,Float value) throws Exception {

        File input = new File(inputFilePath);
        BufferedImage image = ImageIO.read(input);

        File compressedImageFile = new File(outputFilePath);
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extension);
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(value);  // Change the quality value you prefer
        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
    }

    public void executeCompression(String[] inputFiles,String outputPath,Float value) throws Exception{
        //ExecutorService executorService = Executors.newFixedThreadPool(10);


        for(String file:inputFiles){

            FileNameExtractor extension = new FileNameExtractor();
            String s = extension.getExtension(file);
            System.out.println(file);
            this.CompressionAlgorithm(file,outputPath,s,value);

        }
       // executorService.shutdown();





    }
}
