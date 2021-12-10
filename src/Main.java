import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args){

        // MyFrame frame2= new MyFrame();
/*
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyFrame();
            }
        }); */

        JFrame jpgFrame = new JFrame();
        jpgFrame.setTitle("After filtration");
        MyImage pic= new MyImage("image-00000.dcm");
        Filter filter= new Filter();
        filter.standardHighPassFilter();
        filter.setFilter(-1, -1, -1, -1, 14, -1, -1, -1,-1);
        pic.addFilter(filter);
        ImageIcon image = new ImageIcon(pic.getJpgImage());
        JLabel imageLabel = new JLabel(image);
        jpgFrame.add(imageLabel);
        jpgFrame.setVisible(true);
        jpgFrame.setSize(pic.getWidth(), pic.getHeight());
        jpgFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JFrame frame = new JFrame();
        frame.setTitle("Before filtration");
        MyImage pic1= new MyImage("image-00000.dcm");

        ImageIcon image1 = new ImageIcon(pic.getJpgImage());
        JLabel imageLabel1 = new JLabel(image1);
        frame.add(imageLabel1);
        frame.setVisible(true);
        frame.setSize(pic.getWidth(), pic.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}