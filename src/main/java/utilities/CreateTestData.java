package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * CreateTestData: Script tao file Excel test data (chay mot lan)
 * Run: mvn exec:java -Dexec.mainClass="utilities.CreateTestData"
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

        String[] headers = {"TC_ID", "Mo Ta", "Username", "Password", "Ket Qua Mong Doi", "Ghi Chu"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        Object[][] testData = {
            {"TC_Login_01","Dang nhap thanh cong voi tai khoan hop le","admin","admin123","Dang nhap thanh cong - chuyen trang","Positive"},
            {"TC_Login_02","Dang nhap that bai - sai mat khau","admin","wrongpass999","O lai trang dang nhap","Negative"},
            {"TC_Login_03","Dang nhap that bai - bo trong ten dang nhap","","admin123","O lai trang dang nhap","Boundary"},
            {"TC_Login_04","Dang nhap that bai - bo trong mat khau","admin","","O lai trang dang nhap","Boundary"},
            {"TC_Login_05","Dang nhap that bai - bo trong ca hai truong","","","O lai trang dang nhap","Boundary"},
            {"TC_Login_06","Kiem tra chuc nang Quen mat khau","N/A","N/A","Chuyen den /Login/GetPass","Navigation"}
        };

        for (int i = 0; i < testData.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < testData[i].length; j++) {
                row.createCell(j).setCellValue(testData[i][j].toString());
            }
        }
        for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

        try (FileOutputStream fos = new FileOutputStream("testdata/LoginTestData.xlsx")) {
            workbook.write(fos);
        }
        workbook.close();
        System.out.println("Da tao: testdata/LoginTestData.xlsx");
    }
}