package au.nodelogic.coucal.workspaces.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.temporal.Temporal;
import java.util.Date;

@Entity
public class Message {

    @Id
    private String messageId;

    private String subject;

    private Date receivedDate;

    private Date sentDate;

    private String listId;
}
