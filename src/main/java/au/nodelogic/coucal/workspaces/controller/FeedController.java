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
import au.nodelogic.coucal.workspaces.data.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/feeds")
public class FeedController extends AbstractLayoutController {

    private final FeedService feedService;

    private final FeedRepository feedRepository;

    public FeedController(@Autowired FeedService feedService, @Autowired FeedRepository feedRepository) {
        this.feedService = feedService;
        this.feedRepository = feedRepository;
    }

    @GetMapping("/")
    public String listFeeds(Model model) throws IOException {
        populateModelForLayout("Coucal Feeds", model);
        model.addAttribute("feeds", feedRepository.findAll());
        return "list/feeds";
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String addFeed(@ModelAttribute("url") String url, Model model) throws IOException {
        List<String> feedUrls = feedService.resolveFeeds(url);
        List<Feed> feeds = new ArrayList<>();
        feedUrls.forEach(feedUrl -> {
            try {
                Feed feed = new Feed();
                feed.setSource(URI.create(feedUrl).toURL());
                feeds.add(feed);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });
        feedRepository.saveAll(feeds);
        return listFeeds(model);
    }
}
