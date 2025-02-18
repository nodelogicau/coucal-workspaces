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

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FeedService {

    public SyndFeed getFeed(URL url) throws IOException, FeedException {
        return new SyndFeedInput().build(new XmlReader(url.openStream()));
    }

    public List<String> resolveFeeds(String url) throws IOException {
        List<String> feedUrls = new ArrayList<>();
        if (URI.create(url).toURL().getHost().endsWith("reddit.com")) {
            feedUrls.add(url.replaceAll("/$", "") + ".rss");
        } else {
            Document doc = Jsoup.connect(url).followRedirects(true).get();
            doc.select("link[type=application/rss+xml], link[type=application/atom+xml]").forEach(element -> {
                feedUrls.add(URI.create(url).resolve(
                        Objects.requireNonNull(element.attribute("href")).getValue()).toString());
            });
        }
        return feedUrls;
    }

}
