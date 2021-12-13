import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyFrame1();
            }
        });

        /*
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(1200, 700);
        frame.setVisible(true);

        MyImage picBefore = new MyImage("image-00000.dcm");
        ImageIcon image1 = new ImageIcon(picBefore.getJpgImage());

        MyImage picAfter = new MyImage("image-00000.dcm");
        ImageIcon image2 = new ImageIcon(picAfter.getJpgImage());
        Filter filter = new Filter();

        JButton button1 = new JButton();
        button1.addActionListener(this);
        button1.setBounds(200, 550, 100, 50);
        button1.setText("I am a button");
        button1.setFocusable(false);

        filter.HPFilter();
        // picAfter.addFilter(filter);

        JLabel label1 = new JLabel();
        label1.setText("Before Filtration");
        label1.setIcon(image1);
        label1.setVerticalTextPosition(JLabel.TOP);
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setBounds(0, 0, 600, 550);

        JLabel label2 = new JLabel();
        label2.setText("After Filtration");
        label2.setIcon(image2);
        label2.setVerticalTextPosition(JLabel.TOP);
        label2.setHorizontalTextPosition(JLabel.CENTER);
        label2.setBounds(0, 0, 600, 550);

        JPanel panel1 = new JPanel();
        panel1.setBounds(0, 0, 600, 550);

        JPanel panel2 = new JPanel();
        panel2.setBounds(600, 0, 512, 550);

        panel1.add(label1);
        panel2.add(label2);

        frame.add(panel1);
        frame.add(panel2);
        frame.add(button1);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == button1) {
            setBackground(Color.GREEN);
        }
    }

         */
    }
}