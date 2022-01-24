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
                    new MyFrame3();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}