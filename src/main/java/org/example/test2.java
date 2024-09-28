package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.List;

public class test2{
    public static void main(String[] args) {
        // Actualizează calea către driver-ul ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\LucianForfota\\IdeaProjects\\Chromedriver\\28\\chromedriver.exe");

        // Opțiuni pentru ChromeDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Rulează browserul în modul headless (fără interfață grafică)

        // Inițializează WebDriver
        WebDriver driver = new ChromeDriver(options);

        try {
            // Accesează URL-ul
            driver.get("https://www.bancatransilvania.ro/sitemap.xml");

            // Găsește butonul "inapoi la bancatransilvania.ro" și face click pe el
            WebElement backButton = driver.findElement(By.linkText("inapoi la bancatransilvania.ro"));
            backButton.click();

            // Așteaptă ca pagina să se încarce și să fie gata de interacțiune
            Thread.sleep(2000); // Poți ajusta timpul de așteptare după necesitate

            // Găsește toate elementele <loc> care conțin URL-uri
            List<WebElement> urlElements = driver.findElements(By.tagName("loc"));

            // Afișează fiecare URL găsit
            for (WebElement urlElement : urlElements) {
                System.out.println(urlElement.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Închide browserul
            driver.quit();
        }
    }
}
