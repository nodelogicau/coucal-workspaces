package au.nodelogic.coucal.workspaces;

import java.io.File;

public abstract class AbstractWorkspaceManager {

    private final File workspaceRoot;

    public AbstractWorkspaceManager(File workspaceRoot) {
        if (!workspaceRoot.exists() && !workspaceRoot.mkdirs()) {
            throw new IllegalArgumentException(String.format("Unable to initialise workspace: %s",
                    workspaceRoot.getPath()));
        }
        this.workspaceRoot = workspaceRoot;
    }

    public File getWorkspaceRoot() {
        return workspaceRoot;
    }
}
