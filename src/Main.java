import forms.MainForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MainForm f = new MainForm();
        f.setTitle("mcs-log-viewer");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setSize(800, 600);
    }
}