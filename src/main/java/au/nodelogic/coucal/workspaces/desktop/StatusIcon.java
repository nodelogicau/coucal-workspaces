package au.nodelogic.coucal.workspaces.desktop;

import au.nodelogic.coucal.workspaces.util.Filesystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class StatusIcon extends TrayIcon {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusIcon.class);

    public StatusIcon() {
        super(getDefaultImage());
        setToolTip("Coucal Workspaces is running");
        setPopupMenu(new PopupMenu());
    }

    private static Image getDefaultImage() {
        return Toolkit.getDefaultToolkit().getImage(StatusIcon.class.getResource("/public/images/coucal-icon.png"));
    }

    static class PopupMenu extends java.awt.PopupMenu {

        public PopupMenu() {
            MenuItem open = new MenuItem("Open",
                    new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('o')));
            open.addActionListener(event -> {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI("http://localhost:8080"));
                    } catch (IOException | URISyntaxException e) {
                        LOGGER.error("Failed to open browser", e);
                    }
                }
            });
            add(open);
            MenuItem logs = new MenuItem("Show Logs", new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('l')));
            logs.addActionListener(event -> {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().open(new File(Filesystem.getDataDirectory() + "/Coucal/logs"));
                    } catch (IOException e) {
                        LOGGER.error("Failed to open location", e);
                    }
                }
            });
            add(logs);
            addSeparator();
            MenuItem aboutItem = new MenuItem("About Coucal", new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('a')));
            aboutItem.addActionListener(event -> {
//                new AboutDialog().setVisible(true);
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(dialog, "Coucal Workspaces is running",
                        "Coucal Workspaces",  JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            });
            add(aboutItem);
            MenuItem exitItem = new MenuItem("Quit",
                    new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('q')));
            exitItem.addActionListener(event -> System.exit(0));
            add(exitItem);
        }
    }
}
