package au.nodelogic.coucal.workspaces.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MailingList {

    @Id
    private String id;

    private String owner;

    private String archive;

    public String getId() {
        return id;
    }

    public void setId(String listId) {
        this.id = listId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getArchive() {
        return archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }
}
