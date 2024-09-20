package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccontPage extends BasePage {
    public MyAccontPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h2[normalize-space()=\"My Account\"]")    //My Account page heading  
    WebElement msgHeading;

    @FindBy(xpath = "//a[@class=\"list-group-item\"][normalize-space()=\"Logout\"]")  //added in step 6 // for logout
    WebElement lnkLogout;


    public boolean isMyAccountPageExists() {
        try {
            return (msgHeading.isDisplayed());
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogout() {
        lnkLogout.click();
    }
}
