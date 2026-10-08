# Script khoi tao git va commit tung test case
# Chay trong thu muc LoginTest

$projectDir = "c:\6451071003\KIemThuPhanMem\LoginTest"
Set-Location $projectDir

# Kiem tra git
git --version
if ($LASTEXITCODE -ne 0) {
    Write-Error "Git chua duoc cai dat!"
    exit 1
}

# Khoi tao repo neu chua co
if (-not (Test-Path ".git")) {
    git init
    git config user.email "kiemthu@utc.edu.vn"
    git config user.name "Nhom Kiem Thu UTC"
}

# Commit 1: Setup project structure + BaseTest
git add pom.xml testng.xml README.md .gitignore
git add src\test\java\base\BaseTest.java
git add src\main\java\pageobjects\LoginPage.java
git add src\main\java\utilities\ExcelUtils.java
git add src\main\java\utilities\ExtentReportManager.java
git add src\main\java\utilities\ScreenshotUtils.java
git add src\main\java\utilities\CreateTestData.java
git add testdata\LoginTestData.xlsx
git commit -m "Setup: Khoi tao project - POM Pattern, BaseTest, Utilities, TestData"

# Commit 2: TC_Login_01 - Dang nhap thanh cong
# (Test code da co san trong LoginTest.java - tach ra de demo)
git add src\test\java\testcases\LoginTest.java
git commit -m "TC_Login_01: Dang nhap thanh cong voi tai khoan hop le"

Write-Host "=== Da commit setup va TC_Login_01 ==="
Write-Host "Cac TC con lai se duoc commit rieng khi them vao"
