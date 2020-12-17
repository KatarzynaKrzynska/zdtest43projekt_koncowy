import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FirstTests {

    WebDriver driver; // utworzenie pustego pola driver, aby było dostępne we wszystkich metodach
    WebDriverWait wait;

    public void highlightElement(WebElement element){
        JavascriptExecutor js=(JavascriptExecutor)driver;
        js. executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    @Before //warunki początkowe testów, wykona sie przed kazdą metodą test
    public void setup(){
        System.setProperty("webdriver.chrome.driver","C:\\chromedriver\\chromedriver.exe");
        //ustawiamy property ze wskazaniem na chromedriver ktorego uzyjemy w testach
        driver = new ChromeDriver(); // otworzy nam przegladarke
        System.out.println("wykonuję się tutaj! przed metodą testową");
        wait = new WebDriverWait(driver,10);
    }


    @Test // kroki testowe
    public void firstTest(){
        driver.get("https://dev.to"); //przejdz na strone dev.to
        WebElement weekBtn = driver.findElement(By.cssSelector("#articles-list > header > nav > a:nth-child(2)")); //znajdz element week
        weekBtn.click();
    }
    @Test
    public void secondTest(){
        driver.get("https://dev.to");
        WebElement sideBarPodcasts = driver.findElement(By.xpath("/html/body/div[9]/div/div/div[1]/aside/nav[1]/div/a[2]")); //znajdz Podcasts
        highlightElement(sideBarPodcasts);
        // sideBarPodcasts.click();

        //driver.findElement(By.xpath("//a[@href='https://dev.to/pod']"))
        // driver - uzywamy przegladarki do znalezienia elementu
        // findElement() - tej metody uzywamy aby zlokalizować dany element
        // By.xpath() - uzyjemy lokatora xpath
        // //a[@href='https://dev.to/pod'] - lokator
        // znajdz mi element a  - //a
        // który posiada atrybut href - @href
        // o wartosci równej:  https://dev.to/pod
    }
    @Test
    public void openFirstVideoPage(){
        driver.get("https://dev.to");
        WebElement sideBarVideo = driver.findElement(By.partialLinkText("Videos"));
        highlightElement(sideBarVideo);
        sideBarVideo.click();
        //przechodzimy na strone z video
        //powinniśmy poczekać na załadowanie nowej strony
        wait.until(ExpectedConditions.urlToBe("https://dev.to/videos"));
        WebElement firstVideo = driver.findElement(By.className("video-image"));
        highlightElement(firstVideo);
        firstVideo.click();
    }

    @Test
    public void highlightFirstVideo(){
        driver.get("https://dev.to/videos");
        WebElement firstVideo = driver.findElement(By.className("video-image"));
        highlightElement(firstVideo);
        firstVideo.click();
    }
    @Test
    public void openFirstWeekPage(){
        driver.get("https://dev.to/top/week");
        WebElement WeekBtn = driver.findElement(By.cssSelector("#articles-list > header > nav > a:nth-child(2)"));
        highlightElement(WeekBtn);
        WeekBtn.click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.urlToBe("https://dev.to/top/week"));
        WebElement firstWeek = driver.findElement(By.className("crayons-story__cover"));
        highlightElement(firstWeek);
        firstWeek.click();

    }
    //wejdz na strone dev.to
    //kliknac w podcasts
    //wybrac pierwszy podcast - pobiore nazwe pierwszego podcastu z listy
    //sprawdzic czy jestem na odpowiedniej stronie - czy tytul podcastu sie zgadza
    //sprawdzic czy moge nacisnac play

    @Test
    public void selectFirstPodcast(){
        driver.get("https://dev.to");
        WebElement podcasts = driver.findElement(By.partialLinkText("Podcasts"));
        podcasts.click();
        wait.until(ExpectedConditions.urlToBe("https://dev.to/pod"));
        WebElement firstPodcast = driver.findElement(By.cssSelector(".content > h3:first-child")); // kropka dlatego ze to div classs - class=.
        String podcastTitleFromList = firstPodcast.getText();

        String firstPodcastFromListLink = driver.findElement(By.cssSelector("#substories > a:first-child")).getAttribute("href");
        firstPodcast.click();
        wait.until(ExpectedConditions.urlToBe(firstPodcastFromListLink));
        WebElement podcastTitle = driver.findElement(By.cssSelector(".title > h1:nth-child(2)"));
        String podcastTitleText = podcastTitle.getText();
        assertTrue(podcastTitleFromList.contains(podcastTitleText));

        WebElement record = driver.findElement(By.className("record"));
        record.click();
        WebElement initializing = driver.findElement(By.className("status-message"));
        wait.until(ExpectedConditions.invisibilityOf(initializing));

        WebElement recordWrapper = driver.findElement(By.className("record-wrapper"));
        String classAttribute = recordWrapper.getAttribute("class");
        Boolean isPodcastPlayed = classAttribute.contains("playing"); // albo zamiast tych 2 linijek tylko jedna:    Boolean isPodcastPlayed = recordWrapper.getAttribute("class").contains("playing");

        assertTrue(isPodcastPlayed);
    }

    //sprawdzanie czy wyszukiwarka wyszukuje poprawnie - 3 pierwsze wyniki czy zawierają szukane slowo:
    @Test
    public void searching(){
        driver.get("https://dev.to");
        WebElement searchBar = driver.findElement(By.name("q"));
        highlightElement(searchBar);
        searchBar.sendKeys("testing");
        searchBar.sendKeys(Keys.ENTER);//wciska enter
        wait.until(ExpectedConditions.urlContains("search?q=testing"));
        WebElement topResult = driver.findElement(By.className("crayons-story__title"));
        String topResultTitle = topResult.getText();
        topResultTitle = topResultTitle.toLowerCase();
        assertTrue(topResultTitle.contains("testing"));
//        WebElement doesSearchingWork1 = driver.findElement(By.cssSelector(".content > h3:first-child"));
//        highlightElement(doesSearchingWork1);
        //String doesSearchingWork1 = driver.findElement(By.cssSelector("#substories > a:first-child")).getAttribute("href");
        //Boolean doesSearchingWork = doesSearchingWork1.contains("testing");
    //    assertTrue(doesSearchingWork1.contains("testing"));


//        Na stronie dev.to:
//        1. Wyszukaj w search barze text : testing
//        2. Naciśnij enter
//        3. Poczekaj na stronę - wait tym razem będzie troche inny niż urlToBe
//        4. Sprawdź czy pierwszy element na stronie zawiera słowo testing w nazwie ;)
    }

//    @After // czynnosci zamykające testy
//    public void tearDown(){
//        driver.quit(); //zamyka cala przegladarke  a close zamyka karte
//        System.out.println("po kazdej metodzie testowej");
//    }
}
