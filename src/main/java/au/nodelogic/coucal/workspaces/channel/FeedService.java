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

package au.nodelogic.coucal.workspaces.channel;

import au.nodelogic.coucal.workspaces.util.HtmlParser;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndImage;
import com.rometools.rome.feed.synd.SyndImageImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class FeedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedService.class);

    public List<String> resolveFeeds(String url) throws IOException {
        List<String> feedUrls = new ArrayList<>();
        URL source = URI.create(url).toURL();
        if (source.getHost().endsWith("reddit.com")) {
            feedUrls.add(url.replaceAll("/$", "") + ".rss");
        } else if (source.getHost().endsWith("theguardian.com")) {
            feedUrls.add(url.replaceAll("/$", "") + "/rss");
        } else {
            try {
                SyndFeed feed = getFeed(source);
                feedUrls.add(url);
            } catch (IOException | FeedException e) {
                feedUrls.addAll(HtmlParser.getFeeds(url));
            }
        }
        return feedUrls;
    }

    public void refreshFeed(URL source, Consumer<SyndFeed> consumer) throws IOException, FeedException {
        SyndFeed syndFeed = getFeed(source);
        consumer.accept(syndFeed);
    }

    private SyndFeed getFeed(URL url) throws IOException, FeedException {
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(url.openStream()));
        if (feed.getIcon() == null) {
            SyndImage icon = new SyndImageImpl();
            try {
                icon.setUrl(HtmlParser.getIcon(url.toURI().resolve(feed.getLink()).toString()));
                feed.setIcon(icon);
            } catch (Exception e) {
                // some feeds provide a "self" link which doesn't include an icon.. try again without URL path..
                try {
                    URL root = new URI(url.getProtocol(), null, url.getHost(), url.getPort(), null, null, null).toURL();
                    icon.setUrl(HtmlParser.getIcon(root.toString()));
                    feed.setIcon(icon);
                } catch (Exception e2) {
                    LOGGER.warn("Unable to load icon: {}", feed.getLink());
                }
            }
        } else if (feed.getIcon().getUrl().equals("https://www.redditstatic.com/icon.png/")) {
            //HACK: fix invalid URL..
            feed.getIcon().setUrl("https://www.redditstatic.com/icon.png");
        }
        return feed;
    }
}
