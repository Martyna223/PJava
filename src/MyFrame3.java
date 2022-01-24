import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class MyFrame3 extends JFrame implements ActionListener {
    JButton selectFileButton;
    JButton button1;
    JButton button2;
    Filter filter;
    MyImage picAfter;
    MyImage picBefore;
    JPanel panel2;
    JButton button3;
    JButton button4;
    JButton buttonL;
    JButton buttonR;
    JButton saveButton;
    int i;
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
    ImageIcon image1;
    ImageIcon image2;
    JComboBox selectBox;
    String[] filters;
    File file;
    Image3D imagesBefore;
    Image3D imagesAfter;


    MyFrame3() throws IOException {
        i=0;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1300,700);
        this.setVisible(true);

        filter= new Filter();

        selectFileButton= new JButton();
        selectFileButton.addActionListener(this);
        selectFileButton.setBounds(20,575,125,50);
        selectFileButton.setText("Select file");
        selectFileButton.setFocusable(false);

        selectBox= new JComboBox();
        filters= new String[]{"No Effect Filter", "Standard High Pass Filter" ,"Standard Low Pass Filter", "Gauss Filter", "Horizontal High Pass Filter", "Vertical High Pass Filter", "Diagonal High Pass Filter", "Laplace Filter", "Sobel Horizontal Filter", "Sobel Vertical Filter", "Prewitte Horizontal Filter",  "Prewitte Vertical Filter"};
        for(int i=0; i<filters.length; i++ ){
            selectBox.addItem(filters[i]);
        }
        selectBox.setBounds(150,575,170,50);
        selectBox.addActionListener(this);

        button2= new JButton();
        button2.addActionListener(this);
        button2.setBounds(325,575,155,50);
        button2.setText("Add Matrix Filter");
        button2.setFocusable(false);

        button3= new JButton("Choose your own filter parameters ->");
        button3.addActionListener(this);
        button3.setBounds(485, 575,255,50);
        button3.setFocusable(false);

        buttonL= new JButton("<-");
        buttonL.addActionListener(this);
        buttonL.setBounds(1150, 400,50,50);
        buttonL.setFocusable(false);

        buttonR= new JButton("->");
        buttonR.addActionListener(this);
        buttonR.setBounds(1150, 460,50,50);
        buttonR.setFocusable(false);

        saveButton= new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setBounds(1180, 575,80,50);
        saveButton.setFocusable(false);

        label1= new JLabel();
        label1.setVerticalTextPosition(JLabel.TOP);
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setBounds(0,0,600, 550);

        label2 = new JLabel();
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

        textField0= new JTextField();
        textField0.setText("-1");
        textField0.setBounds(745,550,30,30);

        textField1= new JTextField();
        textField1.setText("-1");
        textField1.setBounds(780,550,30,30);

        textField2= new JTextField();
        textField2.setText("0");
        textField2.setBounds(815,550,30,30);

        textField3= new JTextField();
        textField3.setText("-1");
        textField3.setBounds(745,583,30,30);

        textField4= new JTextField();
        textField4.setText("0");
        textField4.setBounds(780,583,30,30);

        textField5= new JTextField();
        textField5.setText("1");
        textField5.setBounds(815,583,30,30);

        textField6= new JTextField();
        textField6.setText("0");
        textField6.setBounds(745,615,30,30);

        textField7= new JTextField();
        textField7.setText("1");
        textField7.setBounds(780,615,30,30);

        textField8= new JTextField();
        textField8.setText("1");
        textField8.setBounds(815,615,30,30);

        button4= new JButton();
        button4.addActionListener(this);
        button4.setBounds(850,575,155,50);
        button4.setText("Set the Gabor filter");
        button4.setFocusable(false);

        button1= new JButton();
        button1.addActionListener(this);
        button1.setBounds(1010,575,165,50);
        button1.setText("Restore image");
        button1.setFocusable(false);

        filter.noEffectFilter();

        this.add(panel1);
        this.add(panel2);
        this.add(button1);
        this.add(button2);
        this.add(selectFileButton);
        this.add(selectBox);
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
        this.add(buttonL);
        this.add(buttonR);
        this.add(saveButton);

    }
    public void upDate(){
        panel2.setVisible(false);
        picBefore=imagesBefore.myImages.get(i);
        picAfter= imagesAfter.myImages.get(i);

        image1 = new ImageIcon(picBefore.getPngImage());
        image2 = new ImageIcon(picAfter.getPngImage());
        label1.setIcon(image1);
        label2.setIcon(image2);
        panel2.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectBox) {
            int a = ((JComboBox) e.getSource()).getSelectedIndex();
            switch (a) {
                case 0:
                    filter.noEffectFilter();
                    break;
                case 1:
                    filter.standardHighPassFilter();
                    break;
                case 2:
                    filter.standardLowPassFilter();
                    break;
                case 3:
                    filter.gaussFilter();
                    break;
                case 4:
                    filter.horizontalFilter();
                    break;
                case 5:
                    filter.verticalFilter();
                    break;
                case 6:
                    filter.diagonalFilter();
                    break;
                case 7:
                    filter.laplaceFilter();
                    break;
                case 8:
                    filter.sobelHorizontalFilter();
                    break;
                case 9:
                    filter.sobelVerticalFilter();
                    break;
                case 10:
                    filter.prewitteHorizontalFilter();
                    break;
                case 11:
                    filter.prewitteVerticalFilter();
                    break;
            }
        } else if (e.getSource() == button1) {
            panel2.setVisible(false);
            picAfter= picBefore;
            imagesAfter.myImages.set(i, picAfter);
            upDate();
            panel2.setVisible(true);

        } else if (e.getSource() == button2) {
            panel2.setVisible(false);
            picAfter.addFilter(filter);
            imagesAfter.myImages.set(i, picAfter);
            upDate();
            panel2.setVisible(true);

        } else if (e.getSource() == button3) {
            int mask[][] = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
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
            imagesAfter.myImages.set(i, picAfter);
            panel2.setVisible(true);

        } else if (e.getSource() == button4) {
            panel2.setVisible(false);
            BufferedImage bufferedImage = new BufferedImage(picAfter.getPngImage().getWidth(null), picAfter.getPngImage().getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            g.drawImage(picAfter.getPngImage(), 0, 0, null);

            GaborFilter gaborImage = new GaborFilter(16, new double[]{0, Math.PI / 4, Math.PI}, 0, 0.5, 1, 3, 3);
            bufferedImage = (BufferedImage) gaborImage.filter(bufferedImage, null);
            picAfter.setPngImage(bufferedImage);

            image2 = new ImageIcon(picAfter.getPngImage());
            label2.setIcon(image2);
            panel2.setVisible(true);

        } else if (e.getSource() == selectFileButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                panel2.setVisible(false);
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                imagesBefore = new Image3D(file.getParent());
                imagesAfter = new Image3D(file.getParent());
                upDate();
                label1.setText("Before Filtration");
                label1.setIcon(image1);
                label2.setText("After Filtration");
                label2.setIcon(image2);
            }
        } else if (e.getSource() == saveButton) {
            JFileChooser fileToSave = new JFileChooser();
            fileToSave.setCurrentDirectory(new File("."));
            int response = fileToSave.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                file = new File(fileToSave.getSelectedFile().getAbsolutePath());
                try {
                    picAfter.savePngImage(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } else if (e.getSource() == buttonL) {
            if (i <= 0) {
                i = 0;
            } else {
                i = i - 1;
            }
            upDate();

        } else if (e.getSource() == buttonR) {
            if (i >= 150) {
                i = 150;
            } else {
                i = i + 1;
            }
            upDate();

        }
    }
}


