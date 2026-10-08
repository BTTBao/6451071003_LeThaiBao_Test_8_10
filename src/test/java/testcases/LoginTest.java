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
}