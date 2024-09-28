package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ButonChecker {

    public static void main(String[] args) {
        String sitemapUrl = "https://www.bancatransilvania.ro/sitemap.xml";


        try {
            // Parse sitemap.xml
            Document sitemapDoc = Jsoup.connect(sitemapUrl).get();
            Elements locElements = sitemapDoc.select("url > loc");

            List<String> urls = new ArrayList<>();
            for (Element locElement : locElements) {

                urls.add(locElement.text());


            }

            // Check each URL for the button
            List<String> urlsWithButton = new ArrayList<>();
            for (String url : urls) {

                if (urlContainsButton(url)) {
                    urlsWithButton.add(url);
                }
            }

            // Print URLs containing the button
            for (String urlWithButton : urlsWithButton) {
                System.out.println(urlWithButton);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean urlContainsButton(String url) {
        try {
            // Fetch the page content
            Document doc = Jsoup.connect(url).get();
            // Check for the button
            Elements buttons = doc.select("div.text:contains(Află ce credit e potrivit pentru afacerea ta)");
            return !buttons.isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
