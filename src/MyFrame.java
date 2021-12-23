import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MyFrame extends JFrame implements ActionListener{
    JButton button1;
    JButton button2;
    Filter filter;
    MyImage picAfter;
    MyImage picBefore;
    JPanel panel2;
    MyFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1200,700);
        this.setVisible(true);

        picBefore= new MyImage("image-00000.dcm");
        ImageIcon image1 = new ImageIcon(picBefore.getPngImage());

        picAfter= new MyImage("image-00000.dcm");
        ImageIcon image2 = new ImageIcon(picAfter.getPngImage());
        filter= new Filter();

        button1= new JButton();
        button1.addActionListener(this);
        button1.setBounds(50,550,200,50);
        button1.setText("Set the low pass filter");
        button1.setFocusable(false);

        button2= new JButton();
        button2.addActionListener(this);
        button2.setBounds(300,550,200,50);
        button2.setText("Set the high pass filter");
        button2.setFocusable(false);

        JLabel label1= new JLabel();
        label1.setText("Before Filtration");
        label1.setIcon(image1);
        label1.setVerticalTextPosition(JLabel.TOP);
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setBounds(0,0,600, 550);

        JLabel label2 = new JLabel();
        label2.setText("After Filtration");
        label2.setIcon(image2);
        label2.setVerticalTextPosition(JLabel.TOP);
        label2.setHorizontalTextPosition(JLabel.CENTER);
        label2.setBounds(0,0,600, 550);

        JPanel panel1= new JPanel();
        panel1.setBounds(0,0,600,550);

        panel2= new JPanel();
        panel2.setBounds(600,0,512,550);

        panel1.add(label1);
        panel2.add(label2);
        panel2.setVisible(false);

        this.add(panel1);
        this.add(panel2);
        this.add(button1);
        this.add(button2);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==button1){
            filter.standardLowPassFilter();
            panel2.setVisible(false);
            picAfter.addFilter(filter);
            //button1.setEnabled(false);
            panel2.setVisible(true);
        } else if(e.getSource()==button2) {
            filter.standardHighPassFilter();
            panel2.setVisible(false);
            picAfter.addFilter(filter);
           // button2.setEnabled(false);
            panel2.setVisible(true);
        }
    }
}
