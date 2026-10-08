package testcases;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageobjects.LoginPage;
import utilities.ExcelUtils;
import utilities.ExtentReportManager;
import utilities.ScreenshotUtils;
import java.io.IOException;

/**
 * LoginTest - Automation test suite for Login module
 * App: https://vanphongdientu.utc.edu.vn/Login
 */
public class LoginTest extends BaseTest {
    private LoginPage loginPage;
    private ExtentReports extent;
    private ExtentTest test;

    private static final String EXCEL_PATH = System.getProperty("user.dir") + "/testdata/LoginTestData.xlsx";
    private static final String SHEET_NAME  = "LoginTest";
    private static final int COL_TC_ID     = 0;
    private static final int COL_DESC      = 1;
    private static final int COL_USERNAME  = 2;
    private static final int COL_PASSWORD  = 3;
    private static final int COL_EXPECTED  = 4;

    @BeforeSuite(alwaysRun = true)
    public void initReport() { extent = ExtentReportManager.getInstance(); }

    @BeforeMethod(alwaysRun = true)
    @Override
    public void setUp() { super.setUp(); loginPage = new LoginPage(driver); }

    @AfterMethod(alwaysRun = true)
    public void captureResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "FAILED: " + result.getThrowable().getMessage());
            test.addScreenCaptureFromPath(
                ScreenshotUtils.takeScreenshot(driver, result.getName()), "Screenshot");
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "PASSED");
        } else {
            test.log(Status.SKIP, "SKIPPED");
        }
        super.tearDown();
    }

    @AfterSuite(alwaysRun = true)
    public void generateReport() {
        ExtentReportManager.flushReports();
        System.out.println("==> Report saved: reports/TestReport.html");
    }

    /**
     * TC_Login_01: Valid login with correct credentials
     * Type: Positive Test
     */
    @Test(priority = 1, description = "TC_Login_01: Login succeeds with valid credentials")
    public void TC_Login_01_ValidLogin() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(1, COL_TC_ID);
        String desc = ExcelUtils.getCellData(1, COL_DESC);
        String user = ExcelUtils.getCellData(1, COL_USERNAME);
        String pass = ExcelUtils.getCellData(1, COL_PASSWORD);
        String exp  = ExcelUtils.getCellData(1, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "URL: " + driver.getCurrentUrl());
        test.log(Status.INFO, "Enter username: " + user);

        loginPage.login(user, pass);
        boolean redirected = loginPage.isLoginSuccessful();
        test.log(Status.INFO, "URL after login: " + loginPage.getCurrentUrl());

        Assert.assertTrue(redirected, "Login with valid credentials must redirect away from /Login");
        test.log(Status.PASS, "Expected: " + exp);
    }

    /**
     * TC_Login_02: Login fails with wrong password
     * Type: Negative Test
     */
    @Test(priority = 2, description = "TC_Login_02: Login fails with wrong password")
    public void TC_Login_02_WrongPassword() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(2, COL_TC_ID);
        String desc = ExcelUtils.getCellData(2, COL_DESC);
        String user = ExcelUtils.getCellData(2, COL_USERNAME);
        String pass = ExcelUtils.getCellData(2, COL_PASSWORD);
        String exp  = ExcelUtils.getCellData(2, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "username=" + user + " | password=" + pass + " (incorrect)");

        loginPage.login(user, pass);
        boolean stayOnLoginPage = loginPage.isOnLoginPage();

        Assert.assertTrue(stayOnLoginPage, "Must remain on /Login page when password is wrong");
        test.log(Status.PASS, "Expected: " + exp);
    }

    /**
     * TC_Login_03: Login fails when username is left empty
     * Type: Boundary Test
     */
    @Test(priority = 3, description = "TC_Login_03: Login fails when username is empty")
    public void TC_Login_03_EmptyUsername() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(3, COL_TC_ID);
        String desc = ExcelUtils.getCellData(3, COL_DESC);
        String user = ExcelUtils.getCellData(3, COL_USERNAME); // empty string
        String pass = ExcelUtils.getCellData(3, COL_PASSWORD);
        String exp  = ExcelUtils.getCellData(3, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "username=[empty] | password=" + pass);

        loginPage.enterUsername(user);
        loginPage.enterPassword(pass);
        loginPage.clickLogin();
        boolean stayOnLoginPage = loginPage.isOnLoginPage();

        Assert.assertTrue(stayOnLoginPage, "Must remain on /Login page when username is empty");
        test.log(Status.PASS, "Expected: " + exp);
    }

    /**
     * TC_Login_04: Login fails when password is left empty
     * Type: Boundary Test
     */
    @Test(priority = 4, description = "TC_Login_04: Login fails when password is empty")
    public void TC_Login_04_EmptyPassword() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(4, COL_TC_ID);
        String desc = ExcelUtils.getCellData(4, COL_DESC);
        String user = ExcelUtils.getCellData(4, COL_USERNAME);
        String pass = ExcelUtils.getCellData(4, COL_PASSWORD); // empty string
        String exp  = ExcelUtils.getCellData(4, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "username=" + user + " | password=[empty]");

        loginPage.enterUsername(user);
        loginPage.enterPassword(pass);
        loginPage.clickLogin();
        boolean stayOnLoginPage = loginPage.isOnLoginPage();

        Assert.assertTrue(stayOnLoginPage, "Must remain on /Login page when password is empty");
        test.log(Status.PASS, "Expected: " + exp);
    }

    /**
     * TC_Login_05: Login fails when both username and password are empty
     * Type: Boundary Test
     */
    @Test(priority = 5, description = "TC_Login_05: Login fails when both fields are empty")
    public void TC_Login_05_BothFieldsEmpty() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(5, COL_TC_ID);
        String desc = ExcelUtils.getCellData(5, COL_DESC);
        String exp  = ExcelUtils.getCellData(5, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "username=[empty] | password=[empty] - clicking Login directly");

        loginPage.clickLogin();
        boolean stayOnLoginPage = loginPage.isOnLoginPage();

        Assert.assertTrue(stayOnLoginPage, "Must remain on /Login page when both fields are empty");
        test.log(Status.PASS, "Expected: " + exp);
    }

    /**
     * TC_Login_06: Forgot password link navigates to GetPass page
     * Type: Navigation Test
     */
    @Test(priority = 6, description = "TC_Login_06: Forgot password link navigates to /Login/GetPass")
    public void TC_Login_06_ForgotPasswordLink() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(6, COL_TC_ID);
        String desc = ExcelUtils.getCellData(6, COL_DESC);
        String exp  = ExcelUtils.getCellData(6, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "Verifying forgot password link is visible on login page");

        boolean isVisible = loginPage.isForgotPasswordLinkVisible();
        Assert.assertTrue(isVisible, "Forgot password link must be visible on the login page");

        test.log(Status.INFO, "Clicking forgot password link...");
        loginPage.clickForgotPassword();

        String currentUrl = loginPage.getCurrentUrl();
        test.log(Status.INFO, "URL after click: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("/Login/GetPass"),
            "Must navigate to /Login/GetPass after clicking forgot password link");
        test.log(Status.PASS, "Expected: " + exp);
    }

    /**
     * TC_Login_07: Login fails with non-existent account
     * Type: Negative Test
     */
    @Test(priority = 7, description = "TC_Login_07: Login fails with non-existent account")
    public void TC_Login_07_NonExistentAccount() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(7, COL_TC_ID);
        String desc = ExcelUtils.getCellData(7, COL_DESC);
        String user = ExcelUtils.getCellData(7, COL_USERNAME);
        String pass = ExcelUtils.getCellData(7, COL_PASSWORD);
        String exp  = ExcelUtils.getCellData(7, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "Testing non-existent user: " + user);

        loginPage.login(user, pass);
        boolean stayOnLoginPage = loginPage.isOnLoginPage();

        Assert.assertTrue(stayOnLoginPage, "Must remain on /Login page for non-existent account");
        test.log(Status.PASS, "Expected: " + exp);
    }

    /**
     * TC_Login_08: Security test - SQL Injection in username field
     * Type: Security Test
     */
    @Test(priority = 8, description = "TC_Login_08: Security test - SQL Injection in username")
    public void TC_Login_08_SQLInjection() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(8, COL_TC_ID);
        String desc = ExcelUtils.getCellData(8, COL_DESC);
        String user = ExcelUtils.getCellData(8, COL_USERNAME);
        String pass = ExcelUtils.getCellData(8, COL_PASSWORD);
        String exp  = ExcelUtils.getCellData(8, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "Injecting SQL payload into username: " + user);

        loginPage.login(user, pass);
        boolean stayOnLoginPage = loginPage.isOnLoginPage();

        Assert.assertTrue(stayOnLoginPage, "Application must safely block SQL injection attempt");
        test.log(Status.PASS, "Expected: " + exp);
    }

    /**
     * TC_Login_09: Security test - XSS payload in username field
     * Type: Security Test
     */
    @Test(priority = 9, description = "TC_Login_09: Security test - XSS payload in username")
    public void TC_Login_09_XSSInjection() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(9, COL_TC_ID);
        String desc = ExcelUtils.getCellData(9, COL_DESC);
        String user = ExcelUtils.getCellData(9, COL_USERNAME);
        String pass = ExcelUtils.getCellData(9, COL_PASSWORD);
        String exp  = ExcelUtils.getCellData(9, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "Injecting XSS script into username: " + user);

        loginPage.login(user, pass);
        boolean stayOnLoginPage = loginPage.isOnLoginPage();

        Assert.assertTrue(stayOnLoginPage, "Application must sanitize XSS input and remain on /Login");
        test.log(Status.PASS, "Expected: " + exp);
    }

    /**
     * TC_Login_10: Edge case - Username with leading/trailing spaces
     * Type: Edge Case
     */
    @Test(priority = 10, description = "TC_Login_10: Username with leading/trailing spaces")
    public void TC_Login_10_WhitespaceUsername() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(10, COL_TC_ID);
        String desc = ExcelUtils.getCellData(10, COL_DESC);
        String user = ExcelUtils.getCellData(10, COL_USERNAME);
        String pass = ExcelUtils.getCellData(10, COL_PASSWORD);
        String exp  = ExcelUtils.getCellData(10, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "Testing username with whitespace padding: '" + user + "'");

        loginPage.login(user, pass);
        boolean stayOnLoginPage = loginPage.isOnLoginPage();

        Assert.assertTrue(stayOnLoginPage, "Must remain on /Login page for invalid padded username");
        test.log(Status.PASS, "Expected: " + exp);
    }
}
