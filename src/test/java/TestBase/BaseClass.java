package TestBase;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;   //Log4j
import org.apache.logging.log4j.Logger;       //log4j
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;


public class BaseClass {

    public static WebDriver driver;

    public Logger logger;

    public Properties p;
    // private Capabilities capabilities;


    @BeforeClass(groups = {"sanity", "Regression", "Master"})
    @Parameters({"os", "browser"})
    public void setup(String os, String br) throws IOException {
        //Loading config.properties file
        FileReader file = new FileReader("./src//test//resources//config.properties");
        p = new Properties();
        p.load(file);

         logger = LogManager.getLogger(this.getClass());   //log4j2

        //if execution on remote machine level  /Standalone system
        if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            //how to decide os
            if (os.equalsIgnoreCase("window")) {
                capabilities.setPlatform(Platform.WIN10);
            } else if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            } else if (os.equalsIgnoreCase("Linux")) {
                capabilities.setPlatform(Platform.LINUX);
            } else {
                System.out.println("No Matching os");
                return;
            }
            //how to decide Browser
            switch (br.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    break;
                case "edge":
                    capabilities.setBrowserName("edge");
                    break;
                case "firefox":
                    capabilities.setBrowserName("firefox");
                    break;
                default:
                    System.out.println("No matching browser ");
                    return;
            }
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        }

        //if execution environment is local
        if (p.getProperty("execution_env").equalsIgnoreCase("local")) {

            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    System.out.println("invalid browser");
                    return;
            }
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(p.getProperty("appURL1"));     //Reading url from properties file.
        //driver.get("http://tutorialsninja.com/demo/");
        driver.manage().window().maximize();


    }

    @AfterClass(groups = {"sanity", "Regression", "Master"})
    public void teardown() {
        driver.close();
    }


    //for generating Alphabetic  data automatically
    public String randomString() {
        String generatedstring = RandomStringUtils.randomAlphabetic(5);
        return generatedstring;
    }

    //for generating Numeric data automatically
    public String randomNumber() {
        String generatednumber = RandomStringUtils.randomNumeric(5);
        return generatednumber;
    }

    //for generating Alphabetic+Numeric data automatically
    public String randomAlphaNumeric() {
        String generatedstring = RandomStringUtils.randomAlphabetic(5);
        String generatednumber = RandomStringUtils.randomNumeric(5);   //it generate alphabet first then number
        return (generatedstring + "@" + generatednumber);                      //ncIoc@74402
    }


    //or mymethod
    //for generating Alphabetic+Numeric data automatically
   /* public String randomAlphaNumeric()
    {
        String generatedalphanumber= RandomStringUtils.randomAlphanumeric(8);   //it generate mix string
        return generatedalphanumber;                                        //Q8I637zm
    }*/

    public String captureScreen(String tname) throws IOException {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourcefile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + " " + timestamp + ".png";
        File targetFile = new File(targetFilePath);

        sourcefile.renameTo(targetFile);

        return targetFilePath;
    }

}
