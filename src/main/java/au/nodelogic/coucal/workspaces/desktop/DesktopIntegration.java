package au.nodelogic.coucal.workspaces.desktop;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class DesktopIntegration implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(DesktopIntegration.class);

    private StatusIcon statusIcon;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("http://localhost:8080"));
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            try {
                statusIcon = new StatusIcon();
                tray.add(statusIcon);
            } catch (AWTException e) {
                log.error("e: ", e);
            }
        }
    }

    @PreDestroy
    public void removeTrayIcon() {
        if (SystemTray.isSupported() && statusIcon != null) {
            SystemTray.getSystemTray().remove(statusIcon);
        }
    }
}
