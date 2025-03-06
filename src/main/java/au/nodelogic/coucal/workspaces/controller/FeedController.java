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

package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.channel.FeedService;
import au.nodelogic.coucal.workspaces.data.Feed;
import au.nodelogic.coucal.workspaces.data.FeedItem;
import au.nodelogic.coucal.workspaces.data.FeedItemRepository;
import au.nodelogic.coucal.workspaces.data.FeedRepository;
import au.nodelogic.coucal.workspaces.workflow.FeedConsumer;
import com.rometools.rome.io.FeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/feeds")
public class FeedController extends AbstractLayoutController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedController.class);

    private final FeedService feedService;

    private final FeedRepository feedRepository;

    private final FeedItemRepository feedItemRepository;

    public FeedController(@Autowired FeedService feedService, @Autowired FeedRepository feedRepository,
                          FeedItemRepository feedItemRepository) {
        this.feedService = feedService;
        this.feedRepository = feedRepository;
        this.feedItemRepository = feedItemRepository;
    }

    @GetMapping("/")
    public String listFeeds(Model model) throws IOException {
        populateModelForLayout("Coucal Feeds", model);
        model.addAttribute("feeds", feedRepository.findAll());
        List<FeedItem> feedItems = feedItemRepository.findAll(Example.of(new FeedItem()));
        Collections.reverse(feedItems);
        model.addAttribute("feedItems", feedItems);
        return "feeds/list";
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String addFeed(@ModelAttribute("feedUrl") String url, Model model) throws IOException {
        List<String> feedUrls = feedService.resolveFeeds(url);
        List<Feed> feeds = new ArrayList<>();
        feedUrls.forEach(feedUrl -> {
            try {
                URL source = URI.create(feedUrl).toURL();
                Feed feed = new Feed();
                feed.setUri(feedUrl);
                feed.setSource(source);
                feedService.refreshFeed(source,
                        new FeedConsumer(feed, feedRepository, feedItemRepository));
            } catch (FeedException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        feedRepository.saveAll(feeds);
        return listFeeds(model);
    }
}
