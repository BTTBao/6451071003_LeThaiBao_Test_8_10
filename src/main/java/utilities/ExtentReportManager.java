package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * ExtentReportManager: Quan ly ExtentReports HTML
 */
public class ExtentReportManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/reports/TestReport.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setTheme(Theme.DARK);
            reporter.config().setDocumentTitle("Bao Cao Kiem Thu Dang Nhap - UTC");
            reporter.config().setReportName("Login Module - Automation Test Report");
            reporter.config().setEncoding("utf-8");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Ung dung", "Van Phong Dien Tu UTC");
            extent.setSystemInfo("URL", "https://vanphongdientu.utc.edu.vn/Login");
            extent.setSystemInfo("Framework", "Selenium WebDriver + TestNG + ExtentReports");
            extent.setSystemInfo("Browser", "Google Chrome");
        }
        return extent;
    }

    public static void flushReports() {
        if (extent != null) extent.flush();
    }
}