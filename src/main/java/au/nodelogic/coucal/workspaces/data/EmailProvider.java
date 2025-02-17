package au.nodelogic.coucal.workspaces.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class EmailProvider {

    @Id
    private String name;

    @OneToOne
    private IMAPConfiguration imapConfiguration;

    @OneToOne
    private SMTPConfiguration smtpConfiguration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IMAPConfiguration getImapConfiguration() {
        return imapConfiguration;
    }

    public void setImapConfiguration(IMAPConfiguration imapConfiguration) {
        this.imapConfiguration = imapConfiguration;
    }

    public SMTPConfiguration getSmtpConfiguration() {
        return smtpConfiguration;
    }

    public void setSmtpConfiguration(SMTPConfiguration smtpConfiguration) {
        this.smtpConfiguration = smtpConfiguration;
    }
}
