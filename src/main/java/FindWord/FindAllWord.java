package DivEmpty;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindAllWord {

    private static final String SITEMAP_URL = "https://www.bancatransilvania.ro/sitemap.xml";

    public static void main(String[] args) {
        try {
            // Descarcă și parsează fișierul sitemap.xml
            Document sitemapDoc = Jsoup.connect(SITEMAP_URL).get();
            List<String> pageUrls = extractPageUrls(sitemapDoc);

            List<String> phrases = new ArrayList<>();
            phrases.add("reteaua de unitati");
            phrases.add("retea unitati");
            phrases.add("reteaua unitatilor");
            phrases.add("rețeaua de unități");
            phrases.add("rețea unități");
            phrases.add("rețeaua unităților");


            for (String url : pageUrls) {
                findPhrasesInPage(url,phrases);
            }
        } catch (IOException e) {
            System.err.println("Eroare la preluarea sau procesarea sitemap XML:  " + e.getMessage());
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

    // Verifică dacă o pagină conține un <div> cu clasa "bt-default-grid-aside" și dacă acel div este gol
    private static void findPhrasesInPage(String url, List<String> phrases) {
        try {
            // Conectează-te la pagină și descarcă HTML-ul
            Document doc = Jsoup.connect(url).get();

            // Extragem textul complet al documentului (paginii)
            String pageText = doc.text().toLowerCase(); // Convertim la lowercase pentru căutare insensibilă la majuscule/minuscule

            // Verificăm fiecare frază în textul paginii
            boolean found = false;
            for (String phrase : phrases) {
                if (pageText.contains(phrase.toLowerCase())) { // Căutăm fraza, convertind și fraza la lowercase
                    System.out.println("Fraza \"" + phrase + "\" a fost găsită pe pagina: " + url);

                    found = true;
                }
            }

//            if (!found) {
//                System.out.println("Niciuna dintre fraze nu a fost găsită pe pagina: " + url);
//            }
        } catch (IOException e) {
            System.err.println("Eroare la preluarea paginii: " + url + " - " + e.getMessage());
        }
    }
}