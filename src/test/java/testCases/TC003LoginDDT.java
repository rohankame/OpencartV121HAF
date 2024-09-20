package testCases;


import TestBase.BaseClass;
//import org.testng.annotations.DataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccontPage;
import utilities.DataProviders;

/*Data is valid --login success --test pass --logout
Data is valid --login failed --test fail

Data is invalid -- login success --test fail --logout
Data is invalid -- login failed -- test pass--
 */
public class TC003LoginDDT extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "Datadriven")
    //getting data provider from another class
    public void verify_loginDDT(String email, String pwd, String exp) throws InterruptedException {
        logger.info("****starting TC_OO3_LoginDDT*****");

        try {
            //Homepage
            HomePage hp = new HomePage(driver);   //1. now we can access the method in homepage. 2. we have to import this class by passing the driver as a parameter and this invoke the constructor b\c this class in another package
            hp.clickMyAccount();
            hp.clicklogin();

            //LoginPage
            LoginPage lp = new LoginPage(driver);
            lp.setEmail(email);
            lp.setPassword(pwd);
            lp.clickLogin2();

            //MyAccount
            MyAccontPage macc = new MyAccontPage(driver);
            boolean targetPage = macc.isMyAccountPageExists();

        /*
        Data is valid --login success --test pass --logout
                        --login failed --test fail

        Data is invalid -- login success --test fail --logout
                        -- login failed -- test pass--
        */

            if (exp.equalsIgnoreCase("Valid")) {
                if (targetPage == true) {
                    macc.clickLogout();
                    Assert.assertTrue(true);
                } else {
                    Assert.assertTrue(false);
                }
            }

            if (exp.equalsIgnoreCase("Invalid")) {
                if (targetPage == true) {
                    macc.clickLogout();
                    Assert.assertTrue(false);
                } else {
                    Assert.assertTrue(true);
                }
            }
        } catch (Exception e) {
            Assert.fail();
        }
        Thread.sleep(3000);
        logger.info("****Finished TC_OO3_LoginDDT*****");
    }


}
