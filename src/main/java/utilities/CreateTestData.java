package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * CreateTestData: Script to generate LoginTestData.xlsx with 16 Test Cases
 */
public class CreateTestData {
    public static void main(String[] args) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("LoginTest");

        XSSFCellStyle headerStyle = workbook.createCellStyle();
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        String[] headers = {"TC_ID", "Mo Ta", "Username", "Password", "Ket Qua Mong Doi", "Loai Test"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        Object[][] testData = {
            {"TC_Login_01", "Dang nhap thanh cong voi tai khoan hop le", "admin", "admin123", "Chuyen den trang dashboard", "Positive"},
            {"TC_Login_02", "Dang nhap that bai voi mat khau khong dung", "admin", "wrongpass999", "O lai trang /Login", "Negative"},
            {"TC_Login_03", "Dang nhap that bai khi bo trong ten dang nhap", "", "admin123", "O lai trang /Login", "Boundary"},
            {"TC_Login_04", "Dang nhap that bai khi bo trong mat khau", "admin", "", "O lai trang /Login", "Boundary"},
            {"TC_Login_05", "Dang nhap that bai khi bo trong ca hai truong", "", "", "O lai trang /Login", "Boundary"},
            {"TC_Login_06", "Kiem tra lien ket 'Quen mat khau' chuyen huong dung", "N/A", "N/A", "Chuyen den /Login/GetPass", "Navigation"},
            {"TC_Login_07", "Dang nhap that bai voi tai khoan khong ton tai", "user_not_exist_xyz999", "Pass@123456", "O lai trang /Login", "Negative"},
            {"TC_Login_08", "Kiem tra bao mat: Dang nhap voi ma doc SQL Injection", "' OR '1'='1", "admin123", "O lai trang /Login, tu choi truy cap", "Security"},
            {"TC_Login_09", "Kiem tra bao mat: Dang nhap voi ma doc XSS Script", "<script>alert('XSS')</script>", "admin123", "O lai trang /Login, khong thuc thi script", "Security"},
            {"TC_Login_10", "Dang nhap voi khoang trang dau va cuoi ten dang nhap", "   admin   ", "admin123", "O lai trang /Login hoac cat khoang trang", "Edge Case"},
            {"TC_Login_11", "Dang nhap that bai voi ten dang nhap chua ky tu dac biet", "user!@#$%^&*()", "Pass123456", "O lai trang /Login", "Negative"},
            {"TC_Login_12", "Kiem tra gioi han do dai ten dang nhap qua dai (>100 ky tu)", "superlongusername_abcdefghijklmnopqrstuvwxyz_1234567890_abcdefghijklmnopqrstuvwxyz_1234567890_test", "Pass123456", "O lai trang /Login", "Boundary"},
            {"TC_Login_13", "Dang nhap that bai voi mat khau chua ky tu dac biet khong dung", "admin", "invalid_pass!@#$%^&*()", "O lai trang /Login", "Negative"},
            {"TC_Login_14", "Kiem tra hien thi va thao tac checkbox 'Giu toi luon dang nhap'", "N/A", "N/A", "Checkbox / Label hien thi va click duoc", "UI / Functional"},
            {"TC_Login_15", "Kiem tra tieu de trang va banner thuong hieu UTC tren form Login", "N/A", "N/A", "Title la 'Dang nhap' va tieu de banner hien thi", "UI Verification"},
            {"TC_Login_16", "Kiem tra bao mat: O nhap mat khau duoc an ky tu (type='password')", "N/A", "N/A", "Thuoc tinh type cua o mat khau phai la password", "Security"}
        };

        for (int i = 0; i < testData.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < testData[i].length; j++) {
                row.createCell(j).setCellValue(testData[i][j].toString());
            }
        }
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fos = new FileOutputStream("testdata/LoginTestData.xlsx")) {
            workbook.write(fos);
        }
        workbook.close();
        System.out.println("Da tao thanh cong: testdata/LoginTestData.xlsx voi 16 Test Cases!");
    }
}