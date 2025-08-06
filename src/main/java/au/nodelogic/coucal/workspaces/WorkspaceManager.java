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

import au.nodelogic.coucal.workspaces.data.Workspace;
import au.nodelogic.coucal.workspaces.data.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * WorkspaceManager is responsible for managing workspaces in the application.
 * It provides methods to retrieve all workspaces and a specific workspace by its ID.
 * The class uses a WorkspaceRepository to interact with the underlying data store.
 */
@Component
public class WorkspaceManager {

    private final WorkspaceRepository workspaceRepository;

    public WorkspaceManager(@Autowired WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public List<Workspace> getWorkspaces() {
        return workspaceRepository.findAll();
    }

    public Workspace getWorkspace(int id) {
        return workspaceRepository.getReferenceById(id);
    }
}
