package au.nodelogic.coucal.workspaces.data;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
public class EmailMessage {

    @Id
    private String messageId;

    private String subject;

    private Date receivedDate;

    private Date sentDate;

    @ManyToOne
    @JoinColumn(name = "sender_address")
    private EmailAddress sender;

    @ManyToMany
    @JoinTable(name = "message_to")
    private Set<EmailAddress> to;

    @ManyToMany
    @JoinTable(name = "message_cc")
    private Set<EmailAddress> cc;

    @ManyToMany
    @JoinTable(name = "message_bcc")
    private Set<EmailAddress> bcc;

    @ManyToOne
    @JoinColumn(name = "in_reply_to_message_id")
    private EmailMessage inReplyTo;

    @ManyToOne
    @JoinColumn(name = "mailing_list_id")
    private MailingList mailingList;

    public EmailMessage getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(EmailMessage inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public EmailAddress getSender() {
        return sender;
    }

    public void setSender(EmailAddress from) {
        this.sender = from;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public MailingList getMailingList() {
        return mailingList;
    }

    public void setMailingList(MailingList list) {
        this.mailingList = list;
    }

    public Set<EmailAddress> getTo() {
        return to;
    }

    public void setTo(Set<EmailAddress> recipients) {
        this.to = recipients;
    }

    public Set<EmailAddress> getCc() {
        return cc;
    }

    public void setCc(Set<EmailAddress> cc) {
        this.cc = cc;
    }

    public Set<EmailAddress> getBcc() {
        return bcc;
    }

    public void setBcc(Set<EmailAddress> bcc) {
        this.bcc = bcc;
    }
}
