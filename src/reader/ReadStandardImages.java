package reader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ReadStandardImages {

    public static void main(String[] args) {

        String imagePath = "image-00000.dcm";
        try {
            BufferedImage loadedPicture = ImageIO.read(new File(imagePath));
            JLabel picLabel = new JLabel(new ImageIcon(loadedPicture));
            JPanel jPanel = new JPanel();
            jPanel.add(picLabel);

            JFrame f = new JFrame();
            f.setSize(new Dimension(loadedPicture.getWidth(), loadedPicture.getHeight()));
            f.add(jPanel);
            f.setVisible(true);
        }
        catch (IOException e){
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
    }
}
