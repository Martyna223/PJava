import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FlatDarculaLaf.setup();
                    MyFrame3 frame = new MyFrame3();
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}