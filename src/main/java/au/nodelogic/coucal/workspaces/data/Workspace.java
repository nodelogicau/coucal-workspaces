package au.nodelogic.coucal.workspaces.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Workspace {

    @Id
    private String name;

    private String description;

    private String icon;

    private List<Filter> filters;
}
