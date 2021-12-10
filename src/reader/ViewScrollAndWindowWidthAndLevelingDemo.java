/* package reader;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.*;

import com.pixelmed.display.SourceImage;


public class ViewScrollAndWindowWidthAndLevelingDemo {

    public static BufferedImage jpgImage;

    public static void main(String[] args) {
        String dicomInputFile = "image-00000.dcm";
        try {
            SourceImage sImg = new SourceImage(dicomInputFile);
            OverriddenSingleImagePanelForDemo singleImagePanel = new OverriddenSingleImagePanelForDemo(sImg);

            jpgImage = sImg.getBufferedImage();


            JFrame jpgFrame = new JFrame();
            ImageIcon image = new ImageIcon(jpgImage);
            JLabel imageLabel = new JLabel(image);
            jpgFrame.add(imageLabel);
            jpgFrame.setVisible(true);
            jpgFrame.setSize(jpgImage.getWidth(), jpgImage.getHeight());
            jpgFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } catch (Exception e) {
            e.printStackTrace(); //in real life, do something about this exception
        }
    }
}
*/