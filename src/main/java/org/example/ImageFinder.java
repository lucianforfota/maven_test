package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageFinder {

    private static final String SITEMAP_URL = "https://www.bancatransilvania.ro/sitemap.xml";
    private static final String TARGET_IMAGE_URL = "<div class=\"text\">Deschide cont online</div>";

    public static void main(String[] args) {
        try {
            // Descarcă și analizează fișierul XML al sitemap-ului
            Document sitemapDoc = Jsoup.connect(SITEMAP_URL).get();
            List<String> pageUrls = extractPageUrls(sitemapDoc);

            // Verifică fiecare URL pentru imaginea țintă
            for (String url : pageUrls) {
                System.out.println( url);
                if (containsTargetImage(url)) {
                    System.out.println( url);
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la descărcarea sau procesarea fișierului XML: " + e.getMessage());
        }
    }

    private static List<String> extractPageUrls(Document sitemapDoc) {
        List<String> pageUrls = new ArrayList<>();

        // Selectează toate elementele <loc> din fișierul XML
        Elements locElements = sitemapDoc.select("loc");

        for (Element loc : locElements) {
            String url = loc.text();
            pageUrls.add(url);
        }

        return pageUrls;
    }

    private static boolean containsTargetImage(String url) {
        try {
            Document pageDoc = Jsoup.connect(url).get();
            Elements images = pageDoc.select("img[src]");

            for (Element img : images) {
                String imgSrc = img.absUrl("src");
                if (TARGET_IMAGE_URL.equals(imgSrc)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la accesarea paginii " + url + ": " + e.getMessage());
        }
        return false;
    }
}
