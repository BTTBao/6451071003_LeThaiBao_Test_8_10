package utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ScreenshotUtils: Chup anh man hinh khi test that bai
 */
public class ScreenshotUtils {
    public static String takeScreenshot(WebDriver driver, String testName) {
        String ts  = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String dir = System.getProperty("user.dir") + "/screenshots/";
        new File(dir).mkdirs();
        String filePath = dir + testName + "_" + ts + ".png";
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(src.toPath(), new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Loi chup anh: " + e.getMessage());
        }
        return filePath;
    }
}