package au.nodelogic.coucal.workspaces.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * Utility methods for parsing information in a HTML stream.
 */
public interface HtmlParser {

    String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36";

    static List<String> getFeeds(String url) throws IOException {
        List<String> feeds = new ArrayList<>();
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).followRedirects(true).get();
        doc.select("link[type=application/rss+xml], link[type=application/atom+xml]").forEach(element ->
                feeds.add(URI.create(url).resolve(Objects.requireNonNull(element.attribute("href")).getValue()).toString()));
        return feeds;
    }

    static String getIcon(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).followRedirects(true).get();
        return URI.create(url).resolve(Objects.requireNonNull(doc.select("link[rel=icon], link[rel=shortcut icon], link[rel=icon shortcut]").stream()
                .findFirst().orElseThrow().attribute("href")).getValue()).toString();
    }

    static String getTitle(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).followRedirects(true).get();
        return Objects.requireNonNull(doc.select("title").stream().findFirst().orElseThrow().text());
    }

    static String getLinkedData(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).followRedirects(true).get();
        return Objects.requireNonNull(doc.select("script[type=application/ld+json]").stream().findFirst().orElseThrow().data());
    }

    static Map<String, String> getProperties(String url) throws IOException {
        Map<String, String> properties = new HashMap<>();
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).followRedirects(true).get();
        doc.select("meta[property^=og:]").forEach(element ->
                properties.put(element.attr("property"), element.attr("content")));
        return properties;
    }
}
