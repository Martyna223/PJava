import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class MyFrame1 extends JFrame implements ActionListener{
    JButton button1;
    JButton button2;
    Filter filter;
    MyImage picAfter;
    MyImage picBefore;
    JPanel panel2;
    JButton button3;
    JButton button4;
    JTextField textField0;
    JTextField textField1;
    JTextField textField2;
    JTextField textField3;
    JTextField textField4;
    JTextField textField5;
    JTextField textField6;
    JTextField textField7;
    JTextField textField8;
    JLabel label1;
    JLabel label2;

    MyFrame1() throws IOException {
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
        button1.setBounds(20,575,165,50);
        button1.setText("Set the low pass filter");
        button1.setFocusable(false);

        button2= new JButton();
        button2.addActionListener(this);
        button2.setBounds(200,575,165,50);
        button2.setText("Set the high pass filter");
        button2.setFocusable(false);

        label1= new JLabel();
        label1.setText("Before Filtration");
        label1.setIcon(image1);
        label1.setVerticalTextPosition(JLabel.TOP);
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setBounds(0,0,600, 550);

        label2 = new JLabel();
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


        button3= new JButton("Choose your own filter parameters ->");
        button3.addActionListener(this);
        button3.setBounds(380, 575,260,50);
        button3.setFocusable(false);

        textField0= new JTextField();
        textField0.setText("-1");
        textField0.setBounds(650,550,30,30);

        textField1= new JTextField();
        textField1.setText("-1");
        textField1.setBounds(685,550,30,30);

        textField2= new JTextField();
        textField2.setText("0");
        textField2.setBounds(720,550,30,30);

        textField3= new JTextField();
        textField3.setText("-1");
        textField3.setBounds(650,583,30,30);

        textField4= new JTextField();
        textField4.setText("0");
        textField4.setBounds(685,583,30,30);

        textField5= new JTextField();
        textField5.setText("1");
        textField5.setBounds(720,583,30,30);

        textField6= new JTextField();
        textField6.setText("0");
        textField6.setBounds(650,615,30,30);

        textField7= new JTextField();
        textField7.setText("1");
        textField7.setBounds(685,615,30,30);

        textField8= new JTextField();
        textField8.setText("1");
        textField8.setBounds(720,615,30,30);


        this.add(panel1);
        this.add(panel2);
        this.add(button1);
        this.add(button2);

        button4= new JButton();
        button4.addActionListener(this);
        button4.setBounds(775,575,165,50);
        button4.setText("Set the Gabor filter");
        button4.setFocusable(false);


        this.add(textField0);
        this.add(textField1);
        this.add(textField2);
        this.add(textField3);
        this.add(textField4);
        this.add(textField5);
        this.add(textField6);
        this.add(textField7);
        this.add(textField8);
        this.add(button3);
        this.add(button4);


        //write to file
        File file = new File("beforeFiltration.png");
        ImageIO.write(picBefore.getPngImage(), "png", file);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==button1){

            filter.standardLowPassFilter();
            panel2.setVisible(false);
            picAfter.addFilter(filter);
            try {
                picAfter.savePngImage("afterFiltration.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            panel2.setVisible(true);
        } else if(e.getSource()==button2) {
            filter.standardHighPassFilter();
            panel2.setVisible(false);
            picAfter.addFilter(filter);

            try {
                picAfter.savePngImage("afterFiltration.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            panel2.setVisible(true);
        } else if(e.getSource()==button3) {
            int mask[][]={{0,0,0},{0,1,0},{0,0,0}};
            mask[0][0] = Integer.valueOf(textField0.getText());
            mask[0][1] = Integer.valueOf(textField1.getText());
            mask[0][2] = Integer.valueOf(textField2.getText());
            mask[1][0] = Integer.valueOf(textField3.getText());
            mask[1][1] = Integer.valueOf(textField4.getText());
            mask[1][2] = Integer.valueOf(textField5.getText());
            mask[2][0] = Integer.valueOf(textField6.getText());
            mask[2][1] = Integer.valueOf(textField7.getText());
            mask[2][2] = Integer.valueOf(textField8.getText());
            filter.setFilter(mask);
            panel2.setVisible(false);
            picAfter.addFilter(filter);
            try {
                picAfter.savePngImage("afterFiltration.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            panel2.setVisible(true);

        } else if(e.getSource()==button4) {
            panel2.setVisible(false);
            BufferedImage bufferedImage = new BufferedImage(picAfter.getPngImage().getWidth(null), picAfter.getPngImage().getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            g.drawImage(picAfter.getPngImage(), 0, 0, null);

            GaborFilter gaborImage= new GaborFilter(16, new double[] {0, Math.PI/4, Math.PI}, 0, 0.5, 1, 3, 3);
            bufferedImage= (BufferedImage) gaborImage.filter(bufferedImage, null);
            picAfter.setPngImage(bufferedImage);

            ImageIcon imageIcon= new ImageIcon(picAfter.getPngImage());
            label2.setIcon(imageIcon);

            try {
                picAfter.savePngImage("afterFiltration.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            panel2.setVisible(true);
        }

    }
}

