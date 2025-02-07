package au.nodelogic.coucal.workspaces;

import org.ical4j.connector.CalendarCollection;
import org.ical4j.connector.local.LocalCalendarCollection;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class InboxManager extends AbstractWorkspaceManager {

    private final CalendarCollection inboxCollection;

    public InboxManager() throws IOException {
        this(new File(System.getProperty("user.dir"), "build/collections"));
    }

    public InboxManager(File workspaceRoot) throws IOException {
        super(workspaceRoot);
        this.inboxCollection = new LocalCalendarCollection(new File(getWorkspaceRoot(), "inbox"));
    }

    public CalendarCollection getInboxCollection() throws IOException {
        return inboxCollection;
    }
}
