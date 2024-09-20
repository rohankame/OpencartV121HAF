package testCases;

import TestBase.BaseClass;
import org.mortbay.jetty.security.Password;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccontPage;

public class TC002LoginTest extends BaseClass {


    @Test(groups = {"sanity", "Master"})
    public void verify_Login() {
        logger.info("**** starting TC_002_LoginTest*****");
        try {
            //Homepage
            HomePage hp = new HomePage(driver);   //1. now we can access the method in homepage. 2. we have to import this class by passing the driver as a parameter and this invoke the constructor b\c this class in another package
            hp.clickMyAccount();
            hp.clicklogin();

            //LoginPage
            LoginPage lp = new LoginPage(driver);
            lp.setEmail(p.getProperty("email"));
            lp.setPassword(p.getProperty("password"));
            lp.clickLogin2();

            //MyAccount
            MyAccontPage macc = new MyAccontPage(driver);
            boolean targetPage = macc.isMyAccountPageExists();

            Assert.assertTrue(targetPage); // Assert.assertEquals(targetPage,true,"Login Failed");
        } catch (Exception e) {
            Assert.fail();
        }

        logger.info("*** Finished TC_002_LoginTest ****");
    }
}
