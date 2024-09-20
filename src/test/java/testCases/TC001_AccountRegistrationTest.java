package testCases;


import TestBase.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"Regression", "Master"})
    public void verify_acc_registration() {
        logger.info("**** starting TC001_AccountRegistrationTest****");
        logger.debug("This is a debug log message");
        try {
            HomePage hp = new HomePage(driver);   //1. now we can access the method in homepage. 2. we have to import this class by passing the driver as a parameter and this invoke the constructor b\c this class in another package
            hp.clickMyAccount();
            logger.info("Clicked on MyAccount Link");
            hp.clickRegister();
            logger.info("Clicked on Register Link");
            AccountRegistrationPage ac = new AccountRegistrationPage(driver);

            logger.info("Providing customer details..");
            ac.setFirstname(randomString().toUpperCase());
            ac.setlastname(randomString().toUpperCase());
            ac.setEmail(randomString() + "@gmail.com");
            ac.setPassword(randomAlphaNumeric());  //if there is confirm password also, first store the value in string variable  ex. String password=randomAlphaNumeric(method)
            ac.setSubscribe();
            ac.setPrivacyPolicy();
            ac.clickContinue();

            logger.info("Validating expected message..");
            String confmsg = ac.getConfirmationMsg();  //here .getconfirmationMsg(method) return value which will we store in String variable
            Assert.assertEquals(confmsg, "Your Account Has Been Created!");
            logger.info("Test passed");
//           if (confmsg.equals("Your Account Has Been Created!"))
//           {
//               Assert.assertTrue(true);
//           }
//           else
//           {
//               logger.error("Test failed..");
//               logger.debug("Debug logs..");
//               Assert.assertFalse(false);
//           }
        } catch (Exception e) {
            Assert.fail();
        }
        logger.info("*****Finished TC001_AccountRegistrationTest***** ");
    }


}
