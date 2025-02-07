package au.nodelogic.coucal.workspaces;

import jakarta.mail.MessagingException;
import net.fortuna.ical4j.data.ContentHandler;
import net.fortuna.ical4j.data.DefaultContentHandler;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import org.ical4j.connector.FailedOperationException;
import org.ical4j.connector.ObjectStoreException;
import org.ical4j.integration.mail.data.MimeMessageParserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class InboxUpdater {

    private static final Logger LOG = LoggerFactory.getLogger(InboxUpdater.class);

    @Autowired
    private IMAPService imapService;

    @Autowired
    private InboxManager inboxManager;

//    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void refreshInbox() throws MessagingException, IOException, ObjectStoreException, FailedOperationException {
        imapService.fetchMessages(message -> {
            AtomicReference<Calendar> calendar = new AtomicReference<>();
            ContentHandler handler = new DefaultContentHandler(calendar::set,
                    TimeZoneRegistryFactory.getInstance().createRegistry());
            try {
                new MimeMessageParserImpl().parse(message, handler);
                inboxManager.getInboxCollection().merge(calendar.get());
            } catch (IOException | MessagingException | ObjectStoreException | FailedOperationException e) {
//                throw new RuntimeException(e);
                LOG.warn("Unexpected error", e);
            }
        });
    }
}
