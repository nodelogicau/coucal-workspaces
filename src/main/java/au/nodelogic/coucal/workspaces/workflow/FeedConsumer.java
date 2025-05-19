package au.nodelogic.coucal.workspaces.workflow;

import au.nodelogic.coucal.workspaces.data.Feed;
import au.nodelogic.coucal.workspaces.data.FeedItem;
import com.rometools.rome.feed.synd.SyndFeed;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class FeedConsumer implements Consumer<SyndFeed> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedConsumer.class);

    private final Feed feed;

    private final List<FeedItem> feedItems;

    private final PolicyFactory htmlSanitizerPolicy;

    public FeedConsumer(Feed feed, List<FeedItem> feedItems) {
        this.feed = feed;
        this.feedItems = feedItems;
        this.htmlSanitizerPolicy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
    }

    @Override
    public void accept(SyndFeed syndFeed) {
        if (syndFeed.getUri() != null) {
            feed.setUri(syndFeed.getUri());
        }
        feed.setTitle(syndFeed.getTitle());
        feed.setDescription(syndFeed.getDescription());
        try {
            feed.setLink(URI.create(syndFeed.getLink()).toURL());
        } catch (MalformedURLException e) {
            LOGGER.warn("Invalid feed link {}", syndFeed.getLink());
        }
        if (syndFeed.getIcon() != null) {
            try {
                feed.setIcon(URI.create(syndFeed.getLink()).resolve(syndFeed.getIcon().getUrl()).toURL());
            } catch (MalformedURLException e) {
                LOGGER.warn("Invalid icon link {}", syndFeed.getIcon().getUrl());
            }
        }
        syndFeed.getEntries().forEach(entry -> {
            try {
                FeedItem item = new FeedItem();
                //identity field is mandatory..
                item.setUri(Objects.requireNonNull(entry.getUri()));
                item.setTitle(entry.getTitle());
                item.setLink(entry.getLink());
                if (entry.getDescription() != null) {
                    item.setDescription(htmlSanitizerPolicy.sanitize(entry.getDescription().getValue()));
                }
                item.setPublishedDate(entry.getPublishedDate());
                item.setFeed(feed);
                feedItems.add(item);
            } catch (Exception e) {
                LOGGER.warn("Invalid feed entry {}", entry.getUri());
            }
        });
    }
}
