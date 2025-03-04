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

import au.nodelogic.coucal.workspaces.channel.FeedService;
import au.nodelogic.coucal.workspaces.data.Feed;
import au.nodelogic.coucal.workspaces.data.FeedItem;
import au.nodelogic.coucal.workspaces.data.FeedItemRepository;
import au.nodelogic.coucal.workspaces.data.FeedRepository;
import com.rometools.rome.io.FeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FeedUpdater {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedUpdater.class);

    private final FeedService feedService;

    private final FeedRepository feedRepository;

    private final FeedItemRepository feedItemRepository;

    public FeedUpdater(@Autowired FeedService feedService, @Autowired FeedRepository feedRepository,
                       @Autowired FeedItemRepository feedItemRepository) {
        this.feedService = feedService;
        this.feedRepository = feedRepository;
        this.feedItemRepository = feedItemRepository;
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void refreshFeeds() {
        List<Feed> feeds = feedRepository.findAll();
        List<FeedItem> feedItems = new ArrayList<>();
        feeds.forEach(feed -> {
            try {
                feedService.refreshFeed(feed.getSource(),
                        new FeedConsumer(feed, feedRepository, feedItemRepository));
            } catch (FeedException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        feedRepository.saveAll(feeds);
        feedItemRepository.saveAll(feedItems);
    }
}
