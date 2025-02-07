package au.nodelogic.coucal.workspaces;

import net.fortuna.ical4j.filter.FilterExpression;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.vcard.Entity;
import net.fortuna.ical4j.vcard.EntityList;
import net.fortuna.ical4j.vcard.VCard;
import net.fortuna.ical4j.vcard.property.Email;
import net.fortuna.ical4j.vcard.property.Fn;
import net.fortuna.ical4j.vcard.property.Uid;
import org.ical4j.connector.FailedOperationException;
import org.ical4j.connector.ObjectStoreException;
import org.ical4j.connector.event.ObjectCollectionEvent;
import org.ical4j.connector.event.ObjectCollectionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class EntityUpdater {

    @Autowired
    private EntityManager entityManager;

    private InboxManager inboxManager;

    private final UidGenerator uidGenerator;

    public EntityUpdater(@Autowired InboxManager inboxManager) throws IOException {
        this.inboxManager = inboxManager;
        this.uidGenerator = new RandomUidGenerator();
        inboxManager.getInboxCollection().addObjectCollectionListener(new ObjectCollectionListener<Calendar>() {
            @Override
            public void onAdd(ObjectCollectionEvent<Calendar> event) {

            }

            @Override
            public void onRemove(ObjectCollectionEvent<Calendar> event) {

            }

            @Override
            public void onMerge(ObjectCollectionEvent<Calendar> event) {
                List<Property> entities = event.getObject().getComponents().stream()
                        .map(c -> c.getProperties("ORGANIZER", "ATTENDEE"))
                        .flatMap(Collection::stream).toList();

                entities.forEach(p -> {
                    try {
                        if (entityManager.getEntityCollection().query(
                                FilterExpression.equalTo("email", p.getValue())).isEmpty()) {
                            Entity entity = new Entity();
                            entity.add(new Email.Factory().createProperty(p.getValue()));
                            Optional<Cn> cn = p.getParameter("CN");
                            cn.ifPresent(name -> entity.add(new Fn.Factory().createProperty(name.getValue())));
                            entity.add(new Uid.Factory().createProperty(uidGenerator.generateUid().getValue()));
                            try {
                                entityManager.getEntityCollection().merge(new VCard(new EntityList().add(entity)));
                            } catch (ObjectStoreException | FailedOperationException | IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            public void onReplace(ObjectCollectionEvent<Calendar> event) {

            }
        });
    }
}
