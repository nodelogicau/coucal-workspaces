package au.nodelogic.coucal.workspaces.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class ChatProvider {

    @Id
    private String name;

    @OneToOne
    private XMPPConfiguration xmppConfiguration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XMPPConfiguration getXmppConfiguration() {
        return xmppConfiguration;
    }

    public void setXmppConfiguration(XMPPConfiguration xmppConfiguration) {
        this.xmppConfiguration = xmppConfiguration;
    }
}
