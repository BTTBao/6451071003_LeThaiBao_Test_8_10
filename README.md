# Login Automation Test - Van Phong Dien Tu UTC
## Thong tin du an
- **Website:** https://vanphongdientu.utc.edu.vn/Login
- **Framework:** Selenium WebDriver + TestNG + Maven
- **Ngon ngu:** Java 11+
- **Bao cao:** ExtentReports 5.x (HTML Report)
- **Test Data:** Apache POI - Excel (.xlsx)

## Cau truc thu muc (theo slide buoi 8)
```
LoginTest/
|-- src/
|   |-- main/java/
|   |   |-- pageobjects/
|   |   |   `-- LoginPage.java          # Page Object Model
|   |   `-- utilities/
|   |       |-- ExcelUtils.java         # Doc du lieu Excel
|   |       |-- ExtentReportManager.java # Quan ly bao cao
|   |       |-- ScreenshotUtils.java    # Chup anh man hinh
|   |       `-- CreateTestData.java     # Tao file Excel test data
|   `-- test/java/
|       |-- base/
|       |   `-- BaseTest.java           # Lop nen (setup/teardown)
|       `-- testcases/
|           `-- LoginTest.java          # Cac test case
|-- testdata/
|   `-- LoginTestData.xlsx              # Du lieu test (Excel)
|-- reports/
|   `-- TestReport.html                 # Bao cao HTML (sau khi chay)
|-- screenshots/                        # Anh chup khi test that bai
|-- testng.xml                          # Cau hinh TestNG
`-- pom.xml                             # Cau hinh Maven
```

## Cac Test Case (6 test)

| TC ID        | Mo Ta                              | Loai          | Ket Qua Mong Doi              |
|--------------|-------------------------------------|---------------|-------------------------------|
| TC_Login_01  | Dang nhap thanh cong                | Positive      | Chuyen trang dashboard        |
| TC_Login_02  | Sai mat khau                        | Negative      | O lai trang dang nhap         |
| TC_Login_03  | Bo trong ten dang nhap              | Boundary      | O lai trang dang nhap         |
| TC_Login_04  | Bo trong mat khau                   | Boundary      | O lai trang dang nhap         |
| TC_Login_05  | Bo trong ca hai truong              | Boundary      | O lai trang dang nhap         |
| TC_Login_06  | Kiem tra lien ket "Quen mat khau"   | Navigation    | Chuyen trang /Login/GetPass   |

## Cach chay test

### Yeu cau truoc khi chay
1. **Java 11+** da cai dat
2. **Maven 3.6+** da cai dat
3. **Google Chrome** da cai dat
4. **ChromeDriver** - tu dong tai boi WebDriverManager

### Chay tat ca test
```bash
mvn clean test
```

### Xem bao cao
Mo file `reports/TestReport.html` trong trinh duyet

## Cach moi test case la 1 commit
```bash
# Commit TC_Login_01
git add -A && git commit -m "TC_Login_01: Dang nhap thanh cong voi tai khoan hop le"

# Commit TC_Login_02
git add -A && git commit -m "TC_Login_02: Dang nhap that bai - sai mat khau"

# Commit TC_Login_03
git add -A && git commit -m "TC_Login_03: Dang nhap that bai - bo trong ten dang nhap"

# Commit TC_Login_04
git add -A && git commit -m "TC_Login_04: Dang nhap that bai - bo trong mat khau"

# Commit TC_Login_05
git add -A && git commit -m "TC_Login_05: Dang nhap that bai - bo trong ca hai truong"

# Commit TC_Login_06
git add -A && git commit -m "TC_Login_06: Kiem tra lien ket Quen mat khau"
```
