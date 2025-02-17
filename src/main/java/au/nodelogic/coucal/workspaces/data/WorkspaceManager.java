package au.nodelogic.coucal.workspaces.data;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WorkspaceManager {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    public List<Workspace> getWorkspaces() {
        return workspaceRepository.findAll();
    }

    public Workspace getWorkspace(String name) {
        return workspaceRepository.getReferenceById(name);
    }
}
