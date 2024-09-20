package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountRegistrationPage extends BasePage {
    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-firstname']")
    WebElement txt_Firstname;
    @FindBy(xpath = "//input[@id='input-lastname']")
    WebElement txt_lastname;
    @FindBy(xpath = "//input[@id='input-email']")
    WebElement txt_Email;
    @FindBy(xpath = "//input[@id='input-password']")
    WebElement txt_Password;
    @FindBy(xpath = "//input[@type='checkbox'][@id='input-newsletter']")
    WebElement chk_Subscribe;
    @FindBy(xpath = "//input[@type='checkbox'][@name='agree']")
    WebElement chk_policy;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement btn_Continue;

    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement msg_Confirmation;

    public void setFirstname(String fname) {
        txt_Firstname.sendKeys(fname);
    }

    public void setlastname(String Lname) {
        txt_lastname.sendKeys(Lname);
    }

    public void setEmail(String Email) {
        txt_Email.sendKeys(Email);
    }

    public void setPassword(String Pswd) {
        txt_Password.sendKeys(Pswd);
    }

    public void setSubscribe() {
        Actions act = new Actions(driver);
        act.moveToElement(chk_Subscribe).click().perform();
    }

    public void setPrivacyPolicy() {
        Actions act = new Actions(driver);
        act.moveToElement(chk_policy).click().perform();
    }

    public void clickContinue() {
        btn_Continue.submit();

        //2.)
        //btn_Continue.submit();

        //or if .click method doesn't work then apply
        //2.
        //Actions act=new Actions(driver);
        //act.moveToElement(btn_Continue).click().perform();
        //3.)
//    JavascriptExecutor js=(JavascriptExecutor) driver;
//    js.executeScript("argument[0].click();",btn_Continue);
        //4.)
        //   btn_Continue.sendKeys(Keys.RETURN);
//        5.)
//        WebDriverWait mywait=new WebDriverWait(driver, Duration.ofSeconds(10));
//        mywait.until(ExpectedConditions.elementToBeClickable(btn_Continue)).click();
    }

    public String getConfirmationMsg() {
        try {
            return (msg_Confirmation.getText());
        } catch (Exception e) {
            return (e.getMessage());
        }
    }
}
