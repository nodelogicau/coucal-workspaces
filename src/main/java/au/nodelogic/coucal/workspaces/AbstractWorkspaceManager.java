package au.nodelogic.coucal.workspaces;

import java.io.File;

public abstract class AbstractWorkspaceManager {

    private final File workspaceRoot;

    public AbstractWorkspaceManager(File workspaceRoot) {
        this.workspaceRoot = workspaceRoot;
    }

    public File getWorkspaceRoot() {
        return workspaceRoot;
    }
}
