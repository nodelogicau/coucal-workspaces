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

package au.nodelogic.coucal.workspaces.workflow;

import au.nodelogic.coucal.workspaces.channel.IMAPService;
import au.nodelogic.coucal.workspaces.data.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.ical4j.connector.FailedOperationException;
import org.ical4j.connector.ObjectStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InboxUpdater {

    private static final Logger LOG = LoggerFactory.getLogger(InboxUpdater.class);

    private final IMAPService imapService;

    private final EmailAddressRepository emailAddressRepository;

    private final MailingListRepository mailingListRepository;

    //    private InboxManager inboxManager;
    private final EmailMessageRepository emailMessageRepository;

    public InboxUpdater(@Autowired IMAPService imapService, @Autowired EmailAddressRepository emailAddressRepository,
                        @Autowired MailingListRepository mailingListRepository,
                        @Autowired EmailMessageRepository emailMessageRepository) {
        this.imapService = imapService;
        this.emailAddressRepository = emailAddressRepository;
        this.mailingListRepository = mailingListRepository;
        this.emailMessageRepository = emailMessageRepository;
    }

    //    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void refreshInbox() throws MessagingException, IOException, ObjectStoreException, FailedOperationException {
        List<EmailMessage> emailMessages = new ArrayList<>();
        List<EmailAddress> addresses = new ArrayList<>();
        List<MailingList> mailingLists = new ArrayList<>();
        imapService.fetchMessages(message -> {
            /*
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

             */
            MimeMessage mimeMessage = (MimeMessage) message;
            try {
                EmailMessage m = new EmailMessage();
                m.setMessageId(mimeMessage.getMessageID());
                m.setSubject(mimeMessage.getSubject());
                if (message.getFrom().length > 0) {
                    InternetAddress internetAddress = (InternetAddress) mimeMessage.getFrom()[0];
                    EmailAddress sender = new EmailAddress();
                    sender.setAddress(internetAddress.getAddress());
                    sender.setPersonal(internetAddress.getPersonal());
                    addresses.add(sender);
                    m.setSender(sender);
                }
                if (message.getRecipients(MimeMessage.RecipientType.TO) != null) {
                    Set<EmailAddress> recipients = Arrays.stream(mimeMessage.getRecipients(MimeMessage.RecipientType.TO)).map(a -> {
                        InternetAddress internetAddress = (InternetAddress) a;
                        EmailAddress recipient = new EmailAddress();
                        recipient.setAddress(internetAddress.getAddress());
                        recipient.setPersonal(internetAddress.getPersonal());
                        return recipient;
                    }).collect(Collectors.toSet());
                    addresses.addAll(recipients);
                    m.setTo(recipients);
                }
                if (message.getRecipients(MimeMessage.RecipientType.CC) != null) {
                    Set<EmailAddress> recipients = Arrays.stream(mimeMessage.getRecipients(MimeMessage.RecipientType.CC)).map(a -> {
                        InternetAddress internetAddress = (InternetAddress) a;
                        EmailAddress recipient = new EmailAddress();
                        recipient.setAddress(internetAddress.getAddress());
                        recipient.setPersonal(internetAddress.getPersonal());
                        return recipient;
                    }).collect(Collectors.toSet());
                    addresses.addAll(recipients);
                    m.setCc(recipients);
                }
                if (message.getRecipients(MimeMessage.RecipientType.BCC) != null) {
                    Set<EmailAddress> recipients = Arrays.stream(mimeMessage.getRecipients(MimeMessage.RecipientType.BCC)).map(a -> {
                        InternetAddress internetAddress = (InternetAddress) a;
                        EmailAddress recipient = new EmailAddress();
                        recipient.setAddress(internetAddress.getAddress());
                        recipient.setPersonal(internetAddress.getPersonal());
                        return recipient;
                    }).collect(Collectors.toSet());
                    addresses.addAll(recipients);
                    m.setBcc(recipients);
                }
                if (mimeMessage.getHeader("In-Reply-To") != null) {
                    EmailMessage inReplyTo = new EmailMessage();
                    inReplyTo.setMessageId(mimeMessage.getHeader("In-Reply-To")[0]);
                    emailMessages.add(inReplyTo);
                    m.setInReplyTo(inReplyTo);
                }
                if (mimeMessage.getHeader("List-ID") != null) {
                    MailingList mailingList = new MailingList();
                    mailingList.setId(mimeMessage.getHeader("List-ID")[0]);
//                    mailingList.setOwner(mimeMessage.getHeader("List-Owner")[0]);
//                    mailingList.setArchive(mimeMessage.getHeader("List-Archive")[0]);
                    mailingLists.add(mailingList);
                    m.setMailingList(mailingList);
                }
                m.setReceivedDate(mimeMessage.getReceivedDate());
                m.setSentDate(mimeMessage.getSentDate());
                emailMessages.add(m);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
        emailAddressRepository.saveAll(addresses);
        mailingListRepository.saveAll(mailingLists);
        emailMessageRepository.saveAll(emailMessages);
    }
}
