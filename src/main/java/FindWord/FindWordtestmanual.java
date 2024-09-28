package DivEmpty;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindWordtestmanual {

    private static final String SITEMAP_URL = "https://www.bancatransilvania.ro/sitemap.xml";

    public static void main(String[] args) {
        // Definim lista de URL-uri ale paginilor de verificat
        List<String> pageUrls = new ArrayList<>();
        pageUrls.add("https://www.bancatransilvania.ro/ideabank");
        pageUrls.add("https://www.bancatransilvania.ro/economii-si-investitii/economii/comparator-de-castig");

        // Definim lista de fraze care trebuie căutate în pagini
        List<String> phrases = new ArrayList<>();
        phrases.add("reteaua de unitati");
        phrases.add("retea unitati");
        phrases.add("reteaua unitatilor");
        phrases.add("rețeaua de unități");
        phrases.add("rețea unități");
        phrases.add("rețeaua unităților");

        // Iterăm prin toate URL-urile paginilor și căutăm fiecare frază din lista specificată
        for (String url : pageUrls) {
            findPhrasesInPage(url, phrases);
        }
    }

    // Verifică dacă o pagină conține oricare din frazele specificate și afișează URL-ul paginii și fraza găsită
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

            if (!found) {
                System.out.println("Niciuna dintre fraze nu a fost găsită pe pagina: " + url);
            }
        } catch (IOException e) {
            System.err.println("Eroare la preluarea paginii: " + url + " - " + e.getMessage());
        }
    }
}
