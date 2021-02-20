import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class ImageCompressor {
    public  void CompressionAlgorithm(String inputFilePath,String outputFilePath,String extension,Double value) throws Exception {


        File imageFile = new File(inputFilePath);
        File compressedImageFile = new File(outputFilePath);

        InputStream is = new FileInputStream(imageFile);
        OutputStream os = new FileOutputStream(compressedImageFile);

        double quality = value;

        // create a BufferedImage as the result of decoding the supplied InputStream
        BufferedImage image = ImageIO.read(is);

        // get all image writers for JPG format
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extension);

        if (!writers.hasNext())
            throw new IllegalStateException("No writers found");

        ImageWriter writer = (ImageWriter) writers.next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        // compress to a given quality
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality((float) quality);

        // appends a complete image stream containing a single image and
        //associated stream and image metadata and thumbnails to the output
        writer.write(null, new IIOImage(image, null, null), param);

        // close all streams
        is.close();
        os.close();
        ios.close();
        writer.dispose();
    }

    public void executeCompression(String[] inputFiles,String outputPath,Double value) throws Exception{
        //ExecutorService executorService = Executors.newFixedThreadPool(10);


        for(String file:inputFiles){

            FileNameExtractor extension = new FileNameExtractor();
            String s = extension.getExtension(file);
            String p = outputPath+"\\"+extension.getName(file,"."+s)+"."+s;
            this.CompressionAlgorithm(file,p,s,value);
        }

    }
}
