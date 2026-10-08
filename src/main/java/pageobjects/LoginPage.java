package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * LoginPage: Page Object Model cho trang dang nhap
 * URL: https://vanphongdientu.utc.edu.vn/Login
 */
public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By usernameField   = By.name("username");
    private final By passwordField   = By.name("userpwd");
    private final By loginButton     = By.cssSelector("input.submit_login");
    private final By rememberMeLabel = By.cssSelector("label[for='persistent']");
    private final By forgotPassLink  = By.cssSelector("a[href='/Login/GetPass']");
    private final By bannerHeading   = By.cssSelector(".caption h1");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterUsername(String username) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        el.clear();
        el.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        el.clear();
        el.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getCurrentUrl() { return driver.getCurrentUrl(); }
    public String getPageTitle()  { return driver.getTitle(); }

    public boolean isOnLoginPage() {
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return driver.getCurrentUrl().contains("/Login");
    }

    public boolean isLoginSuccessful() {
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/Login")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isForgotPasswordLinkVisible() {
        try {
            return driver.findElement(forgotPassLink).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickForgotPassword() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPassLink)).click();
    }

    public boolean isPasswordMasked() {
        try {
            WebElement pw = driver.findElement(passwordField);
            return "password".equalsIgnoreCase(pw.getAttribute("type"));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRememberMeVisible() {
        try {
            return driver.findElement(rememberMeLabel).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void toggleRememberMe() {
        wait.until(ExpectedConditions.elementToBeClickable(rememberMeLabel)).click();
    }

    public boolean isBannerHeadingDisplayed() {
        try {
            return driver.findElement(bannerHeading).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getBannerHeadingText() {
        try {
            return driver.findElement(bannerHeading).getText();
        } catch (Exception e) {
            return "";
        }
    }
}