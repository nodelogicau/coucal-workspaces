package au.nodelogic.coucal.workspaces;

import java.io.File;

public abstract class AbstractManager {

    private final File workspaceRoot;

    public AbstractManager(File workspaceRoot) {
        this.workspaceRoot = workspaceRoot;
    }

    public File getWorkspaceRoot() {
        return workspaceRoot;
    }
}
