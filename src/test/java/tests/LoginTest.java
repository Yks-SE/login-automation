package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @DataProvider(name = "negativeCreds")
    public Object[][] negativeCreds() {
        return new Object[][] {
                {"standard_user", "wrong_password", "Username and password do not match"},
                {"locked_out_user", "secret_sauce", "Sorry, this user has been locked out."},
                {"", "secret_sauce", "Username is required"},
                {"standard_user", "", "Password is required"}
        };
    }

    @Test(description = "Positive: valid username/password should land on Products page")
    public void positiveLogin() {
        LoginPage page = new LoginPage(driver);
        page.open();
        page.login("standard_user", "secret_sauce");
        Assert.assertTrue(page.isLoggedIn(), "Expected successful login");
    }

    @Test(dataProvider = "negativeCreds", description = "Negative: invalid/empty credentials should show error")
    public void negativeLogin(String user, String pass, String expectedMessagePart) {
        LoginPage page = new LoginPage(driver);
        page.open();
        page.login(user, pass);
        String err = page.getErrorText();
        Assert.assertTrue(err.contains(expectedMessagePart),
                "Expected error to contain: " + expectedMessagePart + " but got: " + err);
    }
}
