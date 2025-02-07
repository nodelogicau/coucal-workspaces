package au.nodelogic.coucal.workspaces;

import jakarta.mail.*;
import jakarta.mail.search.ComparisonTerm;
import jakarta.mail.search.SearchTerm;
import jakarta.mail.search.SentDateTerm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

@Service
public class IMAPService {

    private static Logger logger = LoggerFactory.getLogger(IMAPService.class);
    private final Properties properties;

    private static final String INBOX_FOLDER_NAME = "INBOX";

    private final String username;
    private final String password;

    public IMAPService() {
        this("", "");
    }

    public IMAPService(String username, String password) {
        this.properties = new Properties();

        // SMTP
        this.properties.put("mail.transport.protocol", "smtps");
        this.properties.put("mail.smtp.auth", "true");
        this.properties.put("mail.smtp.starttls.enable", "true");
        this.properties.put("mail.smtp.host", "smtp.gmail.com");
        this.properties.put("mail.smtp.port", "587");

        // IMAP
        this.properties.put("mail.store.protocol", "imaps");
        this.properties.put("mail.imap.host", "imap.gmail.com");
//        this.properties.put("mail.imap.host", "imap-mail.outlook.com");
        this.properties.put("mail.imap.port", "993");

        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
    }

    public Session getSession(boolean debug) {
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        session.setDebug(debug);
        return session;
    }

    public Store getStore() throws NoSuchProviderException {
        return getSession(true).getStore(this.properties.getProperty("mail.store.protocol"));
    }

    public void connectStore(Store store) throws MessagingException {
        store.connect(this.properties.getProperty("mail.imap.host"), this.username, this.password);
    }

    public int getMessageCount() {
        try (Store store = getStore()) {
            connectStore(store);

            Folder emailFolder = store.getFolder(INBOX_FOLDER_NAME);
            return emailFolder.getMessageCount();
        } catch (MessagingException e) {
            logger.error("Error fetching emails", e);
            return -1;
        }
    }

    public void fetchMessages(Consumer<Message> messageConsumer) {
        try (Store store = getStore()) {
            connectStore(store);

            Folder emailFolder = store.getFolder(INBOX_FOLDER_NAME);
            emailFolder.open(Folder.READ_ONLY);

//            Message[] messages = fetchNewMessages(emailFolder);
            Message[] messages = emailFolder.getMessages(31, 40);
//            List<MimeMessage> convertedMessages = new ArrayList<>();
//
//            for (Message msg : messages) {
//                MimeMessage mimeMessage = new MimeMessage((MimeMessage) msg);
//                convertedMessages.add(mimeMessage);
//            }
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add("Subject");
            fp.add("List-Id");
            emailFolder.fetch(messages, fp);

            Arrays.stream(messages).forEach(messageConsumer::accept);

            emailFolder.close(false);

//            return Arrays.stream(messages).toList();
        } catch (MessagingException e) {
            logger.error("Error fetching emails", e);
//            return Collections.emptyList();
        }
    }

    private Message[] fetchNewMessages(Folder emailFolder) throws MessagingException {
        Message[] messages;

        LocalDate localDate = LocalDate.now().minusDays(1);
        SearchTerm newer = new SentDateTerm(ComparisonTerm.GE,
                Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        messages = emailFolder.search(newer);

// Fetch only unseen messages
//        SearchTerm unseenMessagesTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
//        messages = emailFolder.search(unseenMessagesTerm);

// Fetch messages containing the news word in the subject
//        SearchTerm subjectTerm = new SubjectTerm("news");
//        messages = emailFolder.search(subjectTerm);

// Fetch messages marked not seen and containing the news word in the subject
//        SearchTerm combinedTerm = new AndTerm(unseenMessagesTerm, subjectTerm);
//        messages = emailFolder.search(subjectTerm);
         return messages;
    }
}