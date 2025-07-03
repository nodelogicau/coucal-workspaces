package au.nodelogic.coucal.workspaces.desktop;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AboutDialog extends JDialog {

    public AboutDialog() {
        add(new JLabel("Coucal Workspaces"));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setLocationByPlatform(true);
        setTitle("About Coucal");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }
}
