/*
 *  Copyright 2025 Node Logic
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package au.nodelogic.coucal.workspaces;

import java.io.File;

/**
 * AbstractWorkspaceManager is an abstract class that provides a base implementation for managing workspaces.
 * It initializes the workspace root directory and provides access to it.
 * Subclasses can extend this class to implement specific workspace management functionalities.
 */
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
