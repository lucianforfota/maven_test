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

public class Error {

    private static final String SITEMAP_URL = "https://www.bancatransilvania.ro/nou/sitemap.xml";

    public static void main(String[] args) {
        try {
            // Jsoup descarcă conținutul răspunsului, care în acest caz este un fișier XML (conform sitemap-ului)
            //Metoda .get() transformă conținutul răspunsului într-un obiect de tip Document, care reprezintă un model al documentului în memorie (DOM - Document Object Model).
            //Obiectul Document este o structură de date care permite manipularea și extragerea ușoară a elementelor din documentul HTML sau XML.
            Document sitemapDoc = Jsoup.connect(SITEMAP_URL).get();
            List<String> pageUrls = extractPageUrls(sitemapDoc);


            for (String url : pageUrls) {
                checkForError(url);
            }
        } catch (IOException e) {
            System.err.println("Eroare la preluarea sau procesarea sitemap XML:  " + e.getMessage());
        }
    }

    private static List<String> extractPageUrls(Document sitemapDoc) {
        List<String> pageUrls = new ArrayList<>();
        Elements locElements = sitemapDoc.select("loc");
        for (Element loc : locElements) {
            //bagam in lista fiecare url. "loc" nu e introdus in url, asa functioneaza  .text().Practic se selecteaza urlul dintre noduri
            pageUrls.add(loc.text());
        }
        return pageUrls;
    }

    private static void checkForError(String url) {
        try {
            // se trimite o cerere HTTP GET la adresa URL
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();
            if (responseCode >=500) {
                {
                    System.out.println("Eroare >=500 pe pagina: " + url);
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la accesarea paginii: " + url + ", " + e.getMessage());
        }
    }
}
