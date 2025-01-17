package au.nodelogic.coucal.workspaces;

import org.ical4j.connector.CardCollection;
import org.ical4j.connector.local.LocalCardCollection;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class EntityManager extends AbstractManager {

    public EntityManager() {
        this(new File(System.getProperty("user.dir"), "build/collections"));
    }

    public EntityManager(File workspaceRoot) {
        super(workspaceRoot);
    }

    public CardCollection getEntityCollection() throws IOException {
        return (CardCollection) new LocalCardCollection(new File(getWorkspaceRoot(), "entities"));
    }
}
