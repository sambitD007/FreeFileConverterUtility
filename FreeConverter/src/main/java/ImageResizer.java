import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.File;

public class ImageResizer {
    public void convert(String[] source, String destination,int height,int width,
                        double rotate,String outputFormat,double outputQuality ) throws Exception{
        //JPEG, PNG, GIF, BMP and WBMP -> for output format
        File outDestination = new File(destination);



            Thumbnails.of(source)
                    .size(height, width)
                    .rotate(rotate)
                    .outputFormat(outputFormat)
                    .outputQuality(outputQuality)
                    .toFiles(outDestination, Rename.NO_CHANGE);

    }
}
