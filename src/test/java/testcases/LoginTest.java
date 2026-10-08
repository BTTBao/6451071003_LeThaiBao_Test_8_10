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
 * LoginTest: Bo kiem thu module dang nhap
 * Web: https://vanphongdientu.utc.edu.vn/Login
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
        System.out.println("==> Report: reports/TestReport.html");
    }

    // TC_Login_01: Dang nhap thanh cong voi tai khoan hop le
    @Test(priority = 1, description = "TC_Login_01: Dang nhap thanh cong voi tai khoan hop le")
    public void TC_Login_01_ValidLogin() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(1, COL_TC_ID);
        String desc = ExcelUtils.getCellData(1, COL_DESC);
        String user = ExcelUtils.getCellData(1, COL_USERNAME);
        String pass = ExcelUtils.getCellData(1, COL_PASSWORD);
        String exp  = ExcelUtils.getCellData(1, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "URL: " + driver.getCurrentUrl());
        test.log(Status.INFO, "Nhap username=" + user);

        loginPage.login(user, pass);
        boolean ok = loginPage.isLoginSuccessful();
        test.log(Status.INFO, "URL sau login: " + loginPage.getCurrentUrl());

        Assert.assertTrue(ok, "Dang nhap voi tai khoan hop le phai chuyen trang");
        test.log(Status.PASS, "Expected: " + exp);
    }

    // TC_Login_02: Dang nhap that bai - sai mat khau
    @Test(priority = 2, description = "TC_Login_02: Dang nhap that bai - sai mat khau")
    public void TC_Login_02_WrongPassword() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(2, COL_TC_ID);
        String desc = ExcelUtils.getCellData(2, COL_DESC);
        String user = ExcelUtils.getCellData(2, COL_USERNAME);
        String pass = ExcelUtils.getCellData(2, COL_PASSWORD);
        String exp  = ExcelUtils.getCellData(2, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "username=" + user + " | password=" + pass + " (sai)");

        loginPage.login(user, pass);
        boolean stay = loginPage.isOnLoginPage();

        Assert.assertTrue(stay, "Phai o lai trang /Login khi mat khau sai");
        test.log(Status.PASS, "Expected: " + exp);
    }

    // TC_Login_03: Dang nhap that bai - bo trong ten dang nhap
    @Test(priority = 3, description = "TC_Login_03: Dang nhap that bai - bo trong ten dang nhap")
    public void TC_Login_03_EmptyUsername() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(3, COL_TC_ID);
        String desc = ExcelUtils.getCellData(3, COL_DESC);
        String user = ExcelUtils.getCellData(3, COL_USERNAME);
        String pass = ExcelUtils.getCellData(3, COL_PASSWORD);
        String exp  = ExcelUtils.getCellData(3, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "username=[trong] | password=" + pass);

        loginPage.enterUsername(user);
        loginPage.enterPassword(pass);
        loginPage.clickLogin();
        boolean stay = loginPage.isOnLoginPage();

        Assert.assertTrue(stay, "Phai o lai trang /Login khi username trong");
        test.log(Status.PASS, "Expected: " + exp);
    }

    // TC_Login_04: Dang nhap that bai - bo trong mat khau
    @Test(priority = 4, description = "TC_Login_04: Dang nhap that bai - bo trong mat khau")
    public void TC_Login_04_EmptyPassword() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(4, COL_TC_ID);
        String desc = ExcelUtils.getCellData(4, COL_DESC);
        String user = ExcelUtils.getCellData(4, COL_USERNAME);
        String pass = ExcelUtils.getCellData(4, COL_PASSWORD);
        String exp  = ExcelUtils.getCellData(4, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "username=" + user + " | password=[trong]");

        loginPage.enterUsername(user);
        loginPage.enterPassword(pass);
        loginPage.clickLogin();
        boolean stay = loginPage.isOnLoginPage();

        Assert.assertTrue(stay, "Phai o lai trang /Login khi password trong");
        test.log(Status.PASS, "Expected: " + exp);
    }

    // TC_Login_05: Dang nhap that bai - bo trong ca hai truong
    @Test(priority = 5, description = "TC_Login_05: Dang nhap that bai - bo trong ca hai truong")
    public void TC_Login_05_BothFieldsEmpty() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(5, COL_TC_ID);
        String desc = ExcelUtils.getCellData(5, COL_DESC);
        String exp  = ExcelUtils.getCellData(5, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "username=[trong] | password=[trong] - click Login ngay");

        loginPage.clickLogin();
        boolean stay = loginPage.isOnLoginPage();

        Assert.assertTrue(stay, "Phai o lai trang /Login khi ca hai truong de trong");
        test.log(Status.PASS, "Expected: " + exp);
    }

    // TC_Login_06: Kiem tra chuc nang Quen mat khau
    @Test(priority = 6, description = "TC_Login_06: Kiem tra lien ket Quen mat khau")
    public void TC_Login_06_ForgotPasswordLink() throws IOException {
        ExcelUtils.setExcelFile(EXCEL_PATH, SHEET_NAME);
        String tcId = ExcelUtils.getCellData(6, COL_TC_ID);
        String desc = ExcelUtils.getCellData(6, COL_DESC);
        String exp  = ExcelUtils.getCellData(6, COL_EXPECTED);

        test = extent.createTest(tcId, desc);
        test.log(Status.INFO, "Kiem tra lien ket Quen mat khau co hien thi tren trang");

        boolean visible = loginPage.isForgotPasswordLinkVisible();
        Assert.assertTrue(visible, "Lien ket Quen mat khau phai hien thi");

        loginPage.clickForgotPassword();
        String url = loginPage.getCurrentUrl();
        test.log(Status.INFO, "URL sau click: " + url);

        Assert.assertTrue(url.contains("/Login/GetPass"), "Phai chuyen den /Login/GetPass");
        test.log(Status.PASS, "Expected: " + exp);
    }
}