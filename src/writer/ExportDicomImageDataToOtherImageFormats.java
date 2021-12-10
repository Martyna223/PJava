package writer;
import com.pixelmed.display.*;
import com.pixelmed.display.ConsumerFormatImageMaker;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ExportDicomImageDataToOtherImageFormats {

    public static void main(String[] args) {

        String dicomFile = "src/resources/MRBRAIN.dcm";
//        String outputJpgFile = "src/resources/skull.jpg";
        String outputPngFile = "src/resources/skull.png";
//        String outputTiffFile = "D:\\JavaProjects\\Sample Images\\Outputs\\MR-MONO2-16-head.tiff";

        try {
//            ConsumerFormatImageMaker.convertFileToEightBitImage(dicomFile, outputJpgFile, "jpeg", 0);
            ConsumerFormatImageMaker.convertFileToEightBitImage(dicomFile, outputPngFile, "png");
//            ConsumerFormatImageMaker.convertFileToEightBitImage(dicomFile, outputTiffFile, "tiff", 0);
        } catch (Exception e) {
            e.printStackTrace(); //in real life, do something about this exception
        }
    }

}