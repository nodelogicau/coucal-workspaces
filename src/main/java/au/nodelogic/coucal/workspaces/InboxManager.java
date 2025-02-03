package au.nodelogic.coucal.workspaces;

import org.ical4j.connector.CalendarCollection;
import org.ical4j.connector.local.LocalCalendarCollection;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class InboxManager extends AbstractWorkspaceManager {

    public InboxManager() {
        this(new File(System.getProperty("user.dir"), "build/collections"));
    }

    public InboxManager(File workspaceRoot) {
        super(workspaceRoot);
    }

    public CalendarCollection getInboxCollection() throws IOException {
        return new LocalCalendarCollection(new File(getWorkspaceRoot(), "inbox"));
    }
}
