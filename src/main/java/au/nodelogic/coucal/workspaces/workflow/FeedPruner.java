package au.nodelogic.coucal.workspaces.workflow;

import au.nodelogic.coucal.workspaces.data.FeedItem;
import au.nodelogic.coucal.workspaces.data.FeedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Prune older feed items.
 */
@Component
public class FeedPruner {

    private final FeedItemRepository feedItemRepository;

    public FeedPruner(@Autowired FeedItemRepository feedItemRepository) {
        this.feedItemRepository = feedItemRepository;
    }

    /**
     * Prune feeds evey 12 hours.
     */
    @Scheduled(fixedRate = 60 * 60 * 1000 * 12)
    public void pruneFeeds() {
        List<FeedItem> prunedFeeds = new ArrayList<>();
        List<FeedItem> feedItems = feedItemRepository.findAll();
        feedItems.forEach(feedItem -> {
            if (feedItem.getPublishedDate() == null || feedItem.getPublishedDate().before(
                    Date.from(ZonedDateTime.now().minusDays(7).toInstant()))) {
                prunedFeeds.add(feedItem);
            }
        });
        feedItemRepository.deleteAll(prunedFeeds);
    }
}
