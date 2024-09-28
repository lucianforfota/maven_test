package DivEmpty;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DivEmptytest2 {

    private static final String SITEMAP_URL = "https://www.bancatransilvania.ro/sitemap.xml";

    public static void main(String[] args) {
        try {
            // Descarcă și parsează fișierul sitemap.xml
            Document sitemapDoc = Jsoup.connect(SITEMAP_URL).get();
            List<String> pageUrls = extractPageUrls(sitemapDoc);

            // Iterăm prin toate URL-urile paginilor și căutăm div-urile goale cu clasa specificată
            for (String url : pageUrls) {
                divempty(url);
            }
        } catch (IOException e) {
            System.err.println("Eroare la preluarea sau procesarea sitemap XML: " + e.getMessage());
        }
    }

    // Extrage toate URL-urile din sitemap
    private static List<String> extractPageUrls(Document sitemapDoc) {
        List<String> pageUrls = new ArrayList<>();
        Elements locElements = sitemapDoc.select("loc");
        for (Element loc : locElements) {
            // Adăugăm în listă fiecare URL din elementele "loc"
            pageUrls.add(loc.text());
        }
        return pageUrls;
    }

    // Verifică dacă o pagină conține un <div> cu clasa "bt-default-grid-aside" și dacă acel div este complet gol
    private static void divempty(String url) {
        try {
            // Conectează-te la pagină și descarcă HTML-ul
            Document doc = Jsoup.connect(url).get();

            // Selectează toate div-urile cu clasa "bt-default-grid-aside"
            Elements divs = doc.select("div.bt-default-grid-aside");

            // Verifică fiecare div dacă este complet gol
            boolean isDivEmpty = false;
            for (Element div : divs) {
                // Verificăm dacă div-ul este complet gol (fără niciun conținut între tag-uri și fără text)
                if (div.children().isEmpty() && div.ownText().trim().isEmpty()) {
                    isDivEmpty = true;
                    break; // Nu e nevoie să verifici alte div-uri dacă ai găsit unul gol
                }
            }

            // Afișează URL-ul doar dacă conține un div complet gol
            if (isDivEmpty) {
                System.out.println(url);
            }
        } catch (IOException e) {
            System.err.println("Eroare la preluarea paginii: " + url + " - " + e.getMessage());
        }
    }
}
