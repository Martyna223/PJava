import com.pixelmed.display.SourceImage;
import reader.OverriddenSingleImagePanelForDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Image3D  {

    ArrayList<MyImage> myImages= new ArrayList<>();

    public Image3D(String dir) {
        String dirPathname = dir;
        File directory = new File(dirPathname);
        if (!directory.isDirectory()) {
            System.out.println(dirPathname + " is not directory");
            return;
        }
        File[] files = directory.listFiles();

        for (int i = 0; i < directory.listFiles().length; i++) {
            try {
                myImages.add(new MyImage(files[i].toString().replace("\\", "\\\\")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Arrays.stream(files).close();
    }


}