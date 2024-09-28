package october;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OctoberErrorChecker {

    private static final String SITEMAP_URL = "https://beta.test.bancatransilvania.ro/new/sitemap.xml";
    private static final String OCTOBER_ERROR_MESSAGE = "syntax error, unexpected";

    public static void main(String[] args) {
        try {
            // Fetch and parse the sitemap XML
            Document sitemapDoc = Jsoup.connect(SITEMAP_URL).get();
            List<String> pageUrls = extractPageUrls(sitemapDoc);

            // Check each URL for October CMS error
            for (String url : pageUrls) {
                checkForOctoberError(url);


            }
        } catch (IOException e) {
            System.err.println("Error fetching or processing the sitemap XML: " + e.getMessage());
        }
    }

    private static List<String> extractPageUrls(Document sitemapDoc) {
        List<String> pageUrls = new ArrayList<>();
        Elements locElements = sitemapDoc.select("loc");
        for (Element loc : locElements) {
            pageUrls.add(loc.text());
        }
        return pageUrls;
    }

    private static void checkForOctoberError(String url) {
        try {
            // Send HTTP GET request to the URL
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Document doc = Jsoup.connect(url).get();
                if (doc.body().text().contains(OCTOBER_ERROR_MESSAGE)) {
                    System.out.println("October CMS error found at: " + url);
                }
            } else {
                System.out.println("Failed to fetch URL (" + responseCode + "): " + url);
            }
        } catch (IOException e) {
            System.err.println("Error checking URL: " + url + ", " + e.getMessage());
        }
    }
}
