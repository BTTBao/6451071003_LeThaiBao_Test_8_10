package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * LoginPage: Page Object Model cho trang dang nhap
 * URL: https://vanphongdientu.utc.edu.vn/Login
 * Integrated with Allure @Step annotations for detailed reporting
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

    @Step("Nhập tên đăng nhập: '{username}'")
    public void enterUsername(String username) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        el.clear();
        el.sendKeys(username);
    }

    @Step("Nhập mật khẩu vào trường password")
    public void enterPassword(String password) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        el.clear();
        el.sendKeys(password);
    }

    @Step("Nhấp vào nút Đăng nhập")
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    @Step("Thực hiện đăng nhập với tài khoản: '{username}'")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    @Step("Lấy URL hiện tại của trang")
    public String getCurrentUrl() { return driver.getCurrentUrl(); }

    @Step("Lấy tiêu đề trang (title)")
    public String getPageTitle()  { return driver.getTitle(); }

    @Step("Kiểm tra trình duyệt có đang ở trang /Login không")
    public boolean isOnLoginPage() {
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return driver.getCurrentUrl().contains("/Login");
    }

    @Step("Kiểm tra đăng nhập thành công (chuyển hướng khỏi trang /Login)")
    public boolean isLoginSuccessful() {
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/Login")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Kiểm tra liên kết 'Quên mật khẩu' có hiển thị không")
    public boolean isForgotPasswordLinkVisible() {
        try {
            return driver.findElement(forgotPassLink).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Nhấp vào liên kết 'Quên mật khẩu'")
    public void clickForgotPassword() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPassLink)).click();
    }

    @Step("Kiểm tra trường mật khẩu có ẩn ký tự không (type='password')")
    public boolean isPasswordMasked() {
        try {
            WebElement pw = driver.findElement(passwordField);
            return "password".equalsIgnoreCase(pw.getAttribute("type"));
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Kiểm tra ô 'Giữ tôi luôn đăng nhập' có hiển thị không")
    public boolean isRememberMeVisible() {
        try {
            return driver.findElement(rememberMeLabel).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Nhấp toggle checkbox 'Giữ tôi luôn đăng nhập'")
    public void toggleRememberMe() {
        wait.until(ExpectedConditions.elementToBeClickable(rememberMeLabel)).click();
    }

    @Step("Kiểm tra banner tiêu đề thương hiệu UTC có hiển thị không")
    public boolean isBannerHeadingDisplayed() {
        try {
            return driver.findElement(bannerHeading).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Lấy nội dung tiêu đề banner thương hiệu UTC")
    public String getBannerHeadingText() {
        try {
            return driver.findElement(bannerHeading).getText();
        } catch (Exception e) {
            return "";
        }
    }
}