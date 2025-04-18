package au.nodelogic.coucal.workspaces.desktop;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class StatusIcon extends TrayIcon {

    public StatusIcon() {
        super(getDefaultImage());
        setToolTip("Coucal Workspaces is running");
        setPopupMenu(new PopupMenu());
    }

    private static Image getDefaultImage() {
        return Toolkit.getDefaultToolkit().getImage(StatusIcon.class.getResource("/public/images/favicon-16x16.png"));
    }

    static class PopupMenu extends java.awt.PopupMenu {

        public PopupMenu() {
            MenuItem open = new MenuItem("Open");
            open.addActionListener(event -> {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI("http://localhost:8080"));
                    } catch (IOException | URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            add(open);
            addSeparator();
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(event -> System.exit(0));
            add(exitItem);
        }
    }
}
