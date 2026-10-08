# Login Automation Test - Van Phong Dien Tu UTC
## Thong tin du an
- **Website:** https://vanphongdientu.utc.edu.vn/Login
- **Framework:** Selenium WebDriver + TestNG + Maven
- **Ngon ngu:** Java 11+ / Java 21
- **Bao cao:** ExtentReports 5.x & Allure Report 2.x
- **Test Data:** Apache POI - Excel (`testdata/LoginTestData.xlsx`)
- **Tong so Test Case:** 16 Test Cases (Moi Test Case la 1 commit)

## Cau truc thu muc (theo slide buoi 8)
```
LoginTest/
|-- src/
|   |-- main/java/
|   |   |-- pageobjects/
|   |   |   `-- LoginPage.java          # Page Object Model
|   |   `-- utilities/
|   |       |-- ExcelUtils.java         # Doc du lieu Excel (Apache POI)
|   |       |-- ExtentReportManager.java # Quan ly bao cao HTML ExtentReports
|   |       |-- ScreenshotUtils.java    # Chup va dinh kem anh (Allure + ExtentReports)
|   |       `-- CreateTestData.java     # Script khoi tao / cap nhat du lieu Excel
|   `-- test/java/
|       |-- base/
|       |   `-- BaseTest.java           # Lop nen (setup/teardown Chrome)
|       `-- testcases/
|           `-- LoginTest.java          # 16 test cases doc tu Excel + Allure Annotations
|-- testdata/
|   `-- LoginTestData.xlsx              # Du lieu test Excel tich hop san trong du an
|-- reports/
|   `-- TestReport.html                 # Bao cao ExtentReports HTML truc quan
|-- screenshots/                        # Anh chup khi test that bai
|-- allure-results/                     # Du lieu ket qua Allure sinh ra sau khi chay test
|-- testng.xml                          # Cau hinh TestNG Suite + AllureTestNg Listener
`-- pom.xml                             # Cau hinh Maven + Allure plugins & aspectjweaver
```

## Danh sach 16 Test Cases (Moi Test Case = 1 Commit rieng biet)

| TC ID        | Mo Ta                                                  | Loai Test       | Ket Qua Mong Doi                            |
|--------------|--------------------------------------------------------|-----------------|---------------------------------------------|
| TC_Login_01  | Dang nhap thanh cong voi tai khoan hop le              | Positive        | Chuyen den trang dashboard                  |
| TC_Login_02  | Dang nhap that bai voi mat khau sai                    | Negative        | O lai trang /Login                          |
| TC_Login_03  | Dang nhap that bai khi bo trong ten dang nhap          | Boundary        | O lai trang /Login                          |
| TC_Login_04  | Dang nhap that bai khi bo trong mat khau               | Boundary        | O lai trang /Login                          |
| TC_Login_05  | Dang nhap that bai khi bo trong ca hai truong          | Boundary        | O lai trang /Login                          |
| TC_Login_06  | Kiem tra lien ket 'Quen mat khau' chuyen huong dung    | Navigation      | Chuyen den /Login/GetPass                   |
| TC_Login_07  | Dang nhap that bai voi tai khoan khong ton tai         | Negative        | O lai trang /Login                          |
| TC_Login_08  | Kiem tra bao mat: SQL Injection o o ten dang nhap      | Security        | O lai trang /Login, tu choi truy cap        |
| TC_Login_09  | Kiem tra bao mat: XSS Script o o ten dang nhap         | Security        | O lai trang /Login, khong thuc thi script   |
| TC_Login_10  | Dang nhap voi khoang trang dau va cuoi ten dang nhap   | Edge Case       | O lai trang /Login                          |
| TC_Login_11  | Dang nhap that bai voi ten dang nhap chua ky tu dac biet| Negative       | O lai trang /Login                          |
| TC_Login_12  | Kiem tra gioi han do dai ten dang nhap qua dai (>100 kt)| Boundary       | O lai trang /Login                          |
| TC_Login_13  | Dang nhap that bai voi mat khau chua ky tu dac biet sai| Negative        | O lai trang /Login                          |
| TC_Login_14  | Kiem tra hien thi va click checkbox 'Giu toi luon dang nhap'| UI/Functional| Hien thi va thao tac click thanh cong     |
| TC_Login_15  | Kiem tra tieu de trang va banner thuong hieu UTC       | UI Verification | Title la 'Đăng nhập', banner hien thi       |
| TC_Login_16  | Kiem tra bao mat: O mat khau an ky tu (type='password')| Security        | Thuoc tinh type cua input la 'password'     |

## Tich hop file Excel vao du an
File dữ liệu Excel được đặt tại: `testdata/LoginTestData.xlsx`.
Khi chạy test, class `utilities.ExcelUtils` tự động nạp dữ liệu từ file Excel này theo từng dòng tương ứng với ID của Test Case.
Nếu muốn tái tạo hoặc cập nhật file Excel theo mã nguồn:
```bash
mvn exec:java "-Dexec.mainClass=utilities.CreateTestData"
```

## Cach chay test & xem bao cao

### Yeu cau truoc khi chay
1. **Java 11+ / Java 21**
2. **Maven 3.6+**
3. **Google Chrome** (ChromeDriver tu dong duoc quan ly boi WebDriverManager)

### 1. Chay test
```bash
# Chay toan bo 16 test cases
mvn test

# Hoac chay 1 test case cu the
mvn test -Dtest="LoginTest#TC_Login_16_PasswordMasking"
```

### 2. Xem bao cao ExtentReports
Mo file `reports/TestReport.html` trong trinh duyet (Chrome/Edge):
```bash
Start-Process "reports\TestReport.html"
```

### 3. Xem bao cao Allure Report
Sau khi chạy test, thư mục `allure-results/` được tự động tạo chứa dữ liệu chi tiết của từng bước chạy, severity, story, và screenshot.
Để mở giao diện Allure Report trên trình duyệt:
```bash
# Cach 1: Dung Allure CLI
allure serve allure-results

# Cach 2: Dung Maven Allure Plugin
mvn allure:serve
```
