package DivEmpty;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindWordSitemapV2 {

    private static final String SITEMAP_URL = "https://www.bancatransilvania.ro/sitemap.xml";

    public static void main(String[] args) {
        try {
            // Descarcă și parsează fișierul sitemap.xml
            Document sitemapDoc = Jsoup.connect(SITEMAP_URL).get();
            List<String> pageUrls = extractPageUrls(sitemapDoc);

            // Definim lista de fraze care trebuie căutate
            List<String> phrases = new ArrayList<>();
            phrases.add("reteaua de unitati");
            phrases.add("retea unitati");
            phrases.add("reteaua unitatilor");
            phrases.add("rețeaua de unități");
            phrases.add("rețea unități");
            phrases.add("rețeaua unităților");

            // Iterăm prin toate URL-urile și căutăm fiecare frază în paginile respective
            for (String url : pageUrls) {
                findPhrasesInPage(url, phrases);
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

    // Verifică dacă o pagină conține frazele specificate doar în conținutul vizibil (fără elemente structurale)
    private static void findPhrasesInPage(String url, List<String> phrases) {
        try {
            // Conectează-te la pagină și descarcă HTML-ul
            Document doc = Jsoup.connect(url).get();

            // Selectăm doar elementele vizibile de pe pagina principală (excludem meta, script, style, etc.)
            Elements mainContentElements = doc.select("body").select("div, p, h1, h2, h3, li, span, a");

            // Căutăm fiecare frază în elementele vizibile
            boolean found = false;
            for (String phrase : phrases) {
                String lowerCasePhrase = phrase.toLowerCase(); // Convertim fraza la lowercase pentru comparație

                // Verificăm fiecare element din conținutul vizibil
                for (Element element : mainContentElements) {
                    // Extragem doar textul elementului curent
                    String elementText = element.ownText().toLowerCase();

                    if (elementText.contains(lowerCasePhrase)) {
                        // Dacă fraza este găsită în textul elementului, afișăm URL-ul și ne oprim din căutare
                        System.out.println("Fraza \"" + phrase + "\" a fost găsită pe pagina: " + url);
                        found = true;
                        break;
                    }
                }

                if (found) {
                    break; // Dacă o frază a fost găsită, nu mai căutăm alte fraze pe această pagină
                }
            }

        } catch (IOException e) {
            System.err.println("Eroare la preluarea paginii: " + url + " - " + e.getMessage());
        }
    }
}
